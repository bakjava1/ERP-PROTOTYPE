package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.LumberController;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.LumberControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.client.rest.TaskControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.client.validation.Validator;
import at.ac.tuwien.sepm.assignment.group02.rest.converter.LumberConverter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Filter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.rest.exceptions.EntityNotFoundException;
import at.ac.tuwien.sepm.assignment.group02.rest.restController.LumberController;
import at.ac.tuwien.sepm.assignment.group02.rest.restController.TaskController;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class LumberServiceImpl implements LumberService {

    private static LumberController lumberController;
    private static LumberConverter lumberConverter;

    @Autowired
    public LumberServiceImpl (LumberController lumberController, LumberConverter lumberConverter){
        LumberServiceImpl.lumberController = lumberController;
        LumberServiceImpl.lumberConverter = lumberConverter;
    }

    public static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static LumberController lumberController = new LumberControllerImpl();
    public static TaskController taskController = new TaskControllerImpl();
    public static Validator validator =  new Validator();
    /**
     * HELLO WORLD example
     *
     * @param id
     * @return
     */
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
    public void addReservedLumberToTask(String id, String quantity) throws InvalidInputException {
        try {
            int[] validated = validator.temporaryAddTaskToLumberValidation(id,quantity);
            TaskDTO toAdd = new TaskDTO();
            toAdd.setId(validated[0]);
            toAdd.setProduced_quantity(validated[1]);
            taskController.updateTask(toAdd);
        }catch(InvalidInputException e) {
            LOG.error("Failed to validate Input: " + e.getMessage());
            throw new InvalidInputException(e.getMessage());
        }
    }

    @Override
    public List<Lumber> getAll(Filter filter) {
        return null;
    }

    @Override
    public void reserveLumber(Lumber lumber, int quantity) {

    }


}
