package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.converter.LumberConverter;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.client.entity.UnvalidatedLumber;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.NoValidIntegerException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.LumberController;
import at.ac.tuwien.sepm.assignment.group02.client.rest.TaskController;
import at.ac.tuwien.sepm.assignment.group02.client.validation.Validator;
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
    private static Validator validator=new Validator();

    @Autowired
    public LumberServiceImpl (LumberController lumberController, LumberConverter lumberConverter,TaskController taskController,Validator validator){
        LumberServiceImpl.lumberController = lumberController;
        LumberServiceImpl.lumberConverter = lumberConverter;
        LumberServiceImpl.taskController = taskController;
        LumberServiceImpl.validator = validator;
    }


    @Override
    public Lumber getLumber(int id) {

        LumberConverter lumberConverter = new LumberConverter();

        LumberDTO lumberDTO = null;
        try {
            lumberDTO = lumberController.getLumberById(id);
        } catch (PersistenceLayerException e) {
            e.printStackTrace();
        }
        Lumber lumber = lumberConverter.convertRestDTOToPlainObject(lumberDTO);

        return lumber;
    }

    @Override
    public void addReservedLumberToTask(String id, String quantity) throws ServiceLayerException {
        try {
            int[] validated = validator.temporaryAddTaskToLumberValidation(id,quantity);
            TaskDTO toAdd = new TaskDTO();
            toAdd.setId(validated[0]);
            toAdd.setProduced_quantity(validated[1]);
            taskController.updateTask(toAdd);
        }catch(InvalidInputException e) {
            LOG.error("Failed to validate Input: " + e.getMessage());
            throw new InvalidInputException(e.getMessage());
        } catch(PersistenceLayerException e) {
            LOG.error("Failed to reserve Lumber in Task: " + e.getMessage());
            throw new ServiceLayerException("Server Problems");
        }
    }

    @Override
    public boolean lumberExists(Lumber lumber) {

        return getLumber(lumber.getId()) != null;
    }


    @Override
    public void createLumber(Lumber lumber) {

    }

    @Override
    public List<Lumber> getAll(UnvalidatedLumber filter)throws ServiceLayerException {
        LOG.debug("getAllSchnittholz called");

        List<LumberDTO> allLumber = null;

        try {

            Lumber validatedLumber = validator.validateLumber(filter);

            LumberDTO filterDTO = lumberConverter.convertPlainObjectToRestDTO(validatedLumber);

            allLumber = lumberController.getAllLumber(filterDTO);

        } catch(InvalidInputException e) {
            LOG.error("Failed to validate Input: " + e.getMessage());
            throw new InvalidInputException(e.getMessage());
        } catch (PersistenceLayerException e) {
            LOG.warn("Failed to get all Lumber"+e.getMessage());
            throw new ServiceLayerException("Server Problems");
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
        validator.validateNumber(quantity+"");
        //validateTask(taskDTO); TODO

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
            throw new ServiceLayerException("Schnittholz konnte nicht reserviert werden.");
        }

        int producedQuantity = taskDTO.getProduced_quantity() + quantity;
        LOG.debug("taskDTO.getProduced_quantity(): {}, quantity: {}",taskDTO.getProduced_quantity(), quantity);
        taskDTO.setProduced_quantity(producedQuantity);

        try {
            taskController.updateTask(taskDTO);
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
            // TODO reset lumber reservation
            throw new ServiceLayerException("Schnittholz konnte dem Auftrag nicht hinzugefügt werden.");
        }
    }


    @Override
    public boolean deleteLumber(Lumber lumber) throws ServiceLayerException, PersistenceLayerException {

        LOG.debug("deleteLumber called: {}", lumber);

        if (lumberExists(lumber)){
            return true;
        }
        validateLumber(lumber);

        LumberDTO lumberToDelete = lumberConverter.convertPlainObjectToRestDTO(lumber);
            try {
                lumberController.removeLumber(lumberToDelete);
            } catch (PersistenceLayerException e) {
                LOG.warn(e.getMessage());
            }
            return true;

    }



   // @Override
   /* public void updateLumber(LumberDTO lumberDTO) throws ServiceLayerException {

        LOG.debug("updateLumber called: {}", lumberDTO);
        try {
            Lumber lumber=new Lumber();
            validateLumber(lumber);
        } catch (NoValidIntegerException e) {
            e.printStackTrace();
        } catch (InvalidInputException e) {
            e.printStackTrace();
        }


        LumberDTO toUpdate = lumberConverter.convertPlainObjectToRestDTO(lumberDTO);
        try {
            lumberController.updateLumber(toUpdate);
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
        }

    }*/




@Override
public void updateLumber(Lumber lumber) throws ServiceLayerException {

        LOG.debug("updateLumber called: {}", lumber);

        try {
            lumber=new Lumber();
        validateLumber(lumber);
        } catch (InvalidInputException e) {
        e.printStackTrace();
        }
        LumberDTO toUpdate = lumberConverter.convertPlainObjectToRestDTO(lumber);
        try {
        lumberController.updateLumber(toUpdate);
        } catch (PersistenceLayerException e) {
        LOG.warn(e.getMessage());
        }
}

    private void validateLumber(Lumber lumber) throws InvalidInputException {
        LOG.debug("Validating lumber: {}",lumber);

        if(lumber==null){
            LOG.debug("Lumber is null.");
            try {
                throw new InvalidInputException("Lumber can't be empty.");
            } catch (InvalidInputException e) {
                e.printStackTrace();
            }
        }

        if(lumber.getId()<0){
            LOG.warn("ID: {}", lumber.getId());
            try {
                throw new NoValidIntegerException("Invalid ID.");
            } catch (NoValidIntegerException e) {
                e.printStackTrace();
            }
        }

        if(lumber.getSize()<=0){
            LOG.warn("SIZE: {}", lumber.getSize());
            try {
                throw new NoValidIntegerException("Negative Integer or Null entered");
            } catch (NoValidIntegerException e) {
                e.printStackTrace();
            }
        }

        if(lumber.getWidth()<=0){
            LOG.warn("WIDTH: {}", lumber.getWidth());
            try {
                throw new NoValidIntegerException("Negative Integer or Null entered.");
            } catch (NoValidIntegerException e) {
                e.printStackTrace();
            }
        }

        if(lumber.getLength()<=0){
            LOG.warn("LENGTH: {}", lumber.getLength());
            try {
                throw new NoValidIntegerException("Negative Integer or Null entered.");
            } catch (NoValidIntegerException e) {
                e.printStackTrace();
            }
        }
        if(lumber.getQuantity()<0){
            LOG.warn("QUALITY: {}", lumber.getQuantity());
            try {
                throw new NoValidIntegerException("Negative Integer or Null entered.");
            } catch (NoValidIntegerException e) {
                e.printStackTrace();
            }
        }
        if(lumber.getReserved_quantity()<0){
            LOG.warn("RESERVED QUANTITY: {}", lumber.getReserved_quantity());
            try {
                throw new NoValidIntegerException("Negative Integer or Null entered.");
            } catch (NoValidIntegerException e) {
                e.printStackTrace();
            }
        }
        if(lumber.getDelivered_quantity()<0){
            LOG.warn("DELIVERED QUANTITY: {}", lumber.getDelivered_quantity());
            try {
                throw new NoValidIntegerException("Negative Integer or Null entered.");
            } catch (NoValidIntegerException e) {
                e.printStackTrace();
            }
        }

        if (lumber.isAll_reserved()){
            LOG.warn("ALL RESERVED: {}", lumber.isAll_reserved());

            try {
                throw new PersistenceLayerException("All reserved persist.");
            }catch (PersistenceLayerException e){
                e.printStackTrace();
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
