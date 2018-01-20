package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.client.entity.UnvalidatedTask;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.TaskController;
import at.ac.tuwien.sepm.assignment.group02.client.validation.Validator;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private Validator validator;
    private static TaskController taskController;
    private static TaskConverter taskConverter;

    @Autowired
    public TaskServiceImpl (Validator validator, TaskController taskController, TaskConverter taskConverter) {
        this.validator = validator;
        TaskServiceImpl.taskController = taskController;
        TaskServiceImpl.taskConverter = taskConverter;
    }

    @Override
    public void createTask(Task task) throws InvalidInputException {

    }

    @Override
    public List<TaskDTO> getAllOpenTasks() throws ServiceLayerException {
        LOG.trace("getAllOpenTasks called");
        List<TaskDTO> allOpen;

        try {
            allOpen = taskController.getAllOpenTasks();
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
            throw new ServiceLayerException(e.getMessage());
        }

        return allOpen;
    }

    @Override
    public Task validateTaskInput(UnvalidatedTask toValidate) throws InvalidInputException {
        Task validated;
        try {
            validated = validator.inputValidationTask(toValidate);
        } catch (InvalidInputException e) {
            //TODO maybe add another exception like Failed TaskCreationException
            LOG.error("Input Validation failed: " + e.getMessage());
            throw new InvalidInputException(e.getMessage());
        }
        return validated;
    }

    @Override
    public Task getTaskById(int id) throws PersistenceLayerException {

        TaskDTO taskDTO = null;
        try {
            taskDTO = taskController.getTaskById(id);
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
        }
        return taskConverter.convertRestDTOToPlainObject(taskDTO);
    }

    @Override
    public void deleteTask(Task task) throws ServiceLayerException {
        LOG.debug("deleteTask called: {}", task);

        TaskDTO taskToDelete = taskConverter.convertPlainObjectToRestDTO(task);

        try {
            taskController.deleteTask(taskToDelete);
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
            throw new ServiceLayerException(e.getMessage());
        }
    }
}
