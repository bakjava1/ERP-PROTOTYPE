package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.converter.LumberConverter;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.NoValidIntegerException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.LumberController;
import at.ac.tuwien.sepm.assignment.group02.client.rest.TaskController;
import at.ac.tuwien.sepm.assignment.group02.client.validation.PrimitiveValidator;
import at.ac.tuwien.sepm.assignment.group02.client.validation.Validator;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.FilterDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.LinkedList;
import java.util.List;

@Service
public class LumberServiceImpl implements LumberService {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static LumberController lumberController;
    private static LumberConverter lumberConverter;
    private static TaskController taskController;
    private static Validator validator;
    private static PrimitiveValidator primitiveValidator;

    @Autowired
    public LumberServiceImpl (LumberController lumberController, LumberConverter lumberConverter,TaskController taskController,Validator validator,PrimitiveValidator primitiveValidator){
        LumberServiceImpl.lumberController = lumberController;
        LumberServiceImpl.lumberConverter = lumberConverter;
        LumberServiceImpl.taskController = taskController;
        LumberServiceImpl.validator = validator;
        LumberServiceImpl.primitiveValidator = primitiveValidator;
    }


    @Override
    public Lumber getLumber(int id) {

        LumberConverter lumberConverter = new LumberConverter();

        LumberDTO lumberDTO = null;
        try {
            lumberDTO = lumberController.getLumberById(id);
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
        }

        return lumberConverter.convertRestDTOToPlainObject(lumberDTO);
    }

    @Override
    public List<Lumber> getAll(FilterDTO filterDTO)throws ServiceLayerException {
        LOG.debug("getAllSchnittholz called");

        List<LumberDTO> allLumber;

        try {
            FilterDTO validatedFilter = validator.validateFilter(filterDTO);
            allLumber = lumberController.getAllLumber(validatedFilter);
        } catch (PersistenceLayerException e) {
            LOG.warn("Failed to get all Lumber"+e.getMessage());
            throw new ServiceLayerException(e.getMessage());
        }

        List<Lumber> allLumberConverted = new LinkedList<>();

        for (LumberDTO lumber: allLumber) {
            allLumberConverted.add(lumberConverter.convertRestDTOToPlainObject(lumber));
        }

        return allLumberConverted;
    }

    @Override
    public void reserveLumber(Lumber lumber, int quantity, TaskDTO taskDTO) throws ServiceLayerException {
        LOG.debug("reserveLumber called: {}", lumber);

        // validate method parameters
        validateLumber(lumber);
        primitiveValidator.validateNumber(quantity+"",1000);

        //check if lumber matches task details
        if( ! (
                lumber.getDescription().equals(taskDTO.getDescription()) &&
                        lumber.getFinishing().equals(taskDTO.getFinishing()) &&
                        lumber.getWood_type().equals(taskDTO.getWood_type()) &&
                        lumber.getQuality().equals(taskDTO.getQuality()) &&
                        lumber.getSize()==taskDTO.getSize() &&
                        lumber.getWidth()==taskDTO.getWidth() &&
                        lumber.getLength()==taskDTO.getLength()
        ) ) throw new ServiceLayerException("Ausgewähltes Schnittholz passt nicht zum Auftrag.");

        // check if reservation quantity is needed for task
        int openQuantityForTask = taskDTO.getQuantity()-taskDTO.getProduced_quantity();
        if(quantity>openQuantityForTask){
            throw new ServiceLayerException("Reservierungsmenge übersteigt für Auftrag benötigte Menge an Schnittholz.");
        }

        if(quantity > (lumber.getQuantity())){
           throw new ServiceLayerException("Reservierungsmenge übersteigt vorhandene Menge an Schnittholz.");
        }

        // convert lumber to lumberDTO
        LumberDTO lumberDTO = lumberConverter.convertPlainObjectToRestDTO(lumber);
        // update lumberDTO to define the quantity to be reserved
        lumberDTO.setQuantity(quantity);

        try {
            lumberController.reserveLumber(lumberDTO);
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
            throw new ServiceLayerException("Schnittholz konnte nicht reserviert werden. "+e.getMessage());
        }

        int producedQuantity = taskDTO.getProduced_quantity() + quantity;
        LOG.debug("taskDTO.getProduced_quantity(): {}, quantity: {}",taskDTO.getProduced_quantity(), quantity);
        taskDTO.setProduced_quantity(producedQuantity);

        try {
            taskController.updateTask(taskDTO);
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
            // TODO reset lumber reservation
            throw new ServiceLayerException("Schnittholz konnte dem Auftrag nicht hinzugefügt werden."+e.getMessage());
        }
    }

    @Override
    public void reserveLumberAlg(Lumber lumber, int quantity, TaskDTO taskDTO) throws ServiceLayerException {
        LOG.debug("reserveLumberAlg called: {}", lumber);

        int currentProducedQuantity = taskDTO.getProduced_quantity();
        int currentQuantity = lumber.getQuantity();
        int currentReservedQuantity = lumber.getReserved_quantity();
        if(taskDTO.getQuantity() > currentProducedQuantity + quantity) {
            taskDTO.setProduced_quantity(currentProducedQuantity + quantity);
            lumber.setReserved_quantity(currentReservedQuantity + quantity);
        } else if (taskDTO.getQuantity() == (currentProducedQuantity + quantity)){
            taskDTO.setProduced_quantity(currentProducedQuantity + quantity);
            lumber.setReserved_quantity(currentReservedQuantity + quantity);
            taskDTO.setDone(true);
            taskDTO.setIn_progress(false);
        } else {
            taskDTO.setProduced_quantity(taskDTO.getQuantity());
            lumber.setReserved_quantity(currentReservedQuantity + (taskDTO.getQuantity() - currentProducedQuantity));
        }

        lumber.setQuantity(currentQuantity + quantity);

        // convert lumber to lumberDTO
        LumberDTO lumberDTO = lumberConverter.convertPlainObjectToRestDTO(lumber);

        lumberController.reserveLumberAlg(lumberDTO);
        taskController.updateTaskAlg(taskDTO);
    }


    @Override
    public void deleteLumber(Lumber lumber) throws ServiceLayerException {

        LOG.debug("deleteLumber called: {}", lumber);

       // if (lumberExists(lumber)){
         //   return true;
        //}
       // validateLumber(lumber);

        LumberDTO lumberToDelete = lumberConverter.convertPlainObjectToRestDTO(lumber);
            try {
                lumberController.removeLumber(lumberToDelete);
            } catch (PersistenceLayerException e) {
                LOG.warn(e.getMessage());
            }
    }

    /*
    @Override
    public void updateLumber(Lumber lumber) throws ServiceLayerException {
        LOG.debug("updateLumber called: {}", lumber);
        try {
            validateLumber(lumber);
        } catch (NoValidIntegerException e) {
            LOG.warn(e.getMessage());
        }
        LumberDTO toUpdate = lumberConverter.convertPlainObjectToRestDTO(lumber);
        try {
        lumberController.updateLumber(toUpdate);
        } catch (PersistenceLayerException e) {
        LOG.warn(e.getMessage());
        }
    }
    */

    private void validateLumber(Lumber lumber) throws InvalidInputException {
        LOG.debug("Validating lumber: {}",lumber);

        if(lumber==null){
            LOG.debug("Lumber is null.");
            throw new InvalidInputException("Lumber can't be empty.");
        }

        if(lumber.getId()<0){
            LOG.warn("ID: {}", lumber.getId());
            try {
                throw new NoValidIntegerException("Invalid ID.");
            } catch (NoValidIntegerException e) {
                LOG.warn(e.getMessage());
            }
        }

        if(lumber.getSize()<=0){
            LOG.warn("SIZE: {}", lumber.getSize());
            try {
                throw new NoValidIntegerException("Negative Integer or Null entered");
            } catch (NoValidIntegerException e) {
                LOG.warn(e.getMessage());
            }
        }

        if(lumber.getWidth()<=0){
            LOG.warn("WIDTH: {}", lumber.getWidth());
            try {
                throw new NoValidIntegerException("Negative Integer or Null entered.");
            } catch (NoValidIntegerException e) {
                LOG.warn(e.getMessage());
            }
        }

        if(lumber.getLength()<=0){
            LOG.warn("LENGTH: {}", lumber.getLength());
            try {
                throw new NoValidIntegerException("Negative Integer or Null entered.");
            } catch (NoValidIntegerException e) {
                LOG.warn(e.getMessage());
            }
        }
        if(lumber.getQuantity()<0){
            LOG.warn("QUALITY: {}", lumber.getQuantity());
            try {
                throw new NoValidIntegerException("Negative Integer or Null entered.");
            } catch (NoValidIntegerException e) {
                LOG.warn(e.getMessage());
            }
        }
        if(lumber.getReserved_quantity()<0){
            LOG.warn("RESERVED QUANTITY: {}", lumber.getReserved_quantity());
            try {
                throw new NoValidIntegerException("Negative Integer or Null entered.");
            } catch (NoValidIntegerException e) {
                LOG.warn(e.getMessage());
            }
        }
        if(lumber.getDelivered_quantity()<0){
            LOG.warn("DELIVERED QUANTITY: {}", lumber.getDelivered_quantity());
            try {
                throw new NoValidIntegerException("Negative Integer or Null entered.");
            } catch (NoValidIntegerException e) {
                LOG.warn(e.getMessage());
            }
        }

        if (lumber.isAll_reserved()){
            LOG.warn("ALL RESERVED: {}", lumber.isAll_reserved());

            try {
                throw new PersistenceLayerException("All reserved persist.");
            }catch (PersistenceLayerException e){
                LOG.warn(e.getMessage());
            }
        }

        /*
        if(lumber.getLager()==null || lumber.getLager().isEmpty()){
            LOG.warn("Lager: '{}'.", lumber.getLager());
            throw new InvalidInputException("Lager can't be empty.");
        }*/

        if(lumber.getDescription()==null || lumber.getDescription().isEmpty()){
            LOG.warn("Description: '{}'.", lumber.getDescription());
            throw new InvalidInputException("Description can't be empty.");
        }
        if(lumber.getFinishing()==null || lumber.getFinishing().isEmpty()){
            LOG.warn("Finishing: '{}'.", lumber.getFinishing());
            throw new InvalidInputException("Finishing can't be empty.");
        }

        if(lumber.getWood_type()==null || lumber.getWood_type().isEmpty()){
            LOG.warn("Wood_type: '{}'.", lumber.getWood_type());
            throw new InvalidInputException("Wood_type can't be empty.");
        }

        if(lumber.getQuality()==null || lumber.getQuality().isEmpty()){
            LOG.warn("Quality: '{}'.", lumber.getQuality());
            throw new InvalidInputException(" Quality can't be empty.");
        }
    }



}
