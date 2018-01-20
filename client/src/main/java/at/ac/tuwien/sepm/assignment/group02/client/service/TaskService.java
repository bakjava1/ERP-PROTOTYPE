package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.entity.UnvalidatedTask;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;

import java.util.List;

/**
 * CONVERSION HAPPENS ON THIS LAYER
 * VALIDATION HAPPENS ON THIS LAYER
 */
public interface TaskService {

    /**
     * create a task with all relevant data
     * @param task
     * @throws InvalidInputException
     * @throws ServiceLayerException
     */
    void createTask(Task task) throws ServiceLayerException;

    /**
     * method deletes a given task (identified by its id)
     * @param task the TaskDTO to be deleted
     * @throws ServiceLayerException
     */
    void deleteTask(Task task) throws ServiceLayerException;

    /**
     * get all open task
     * @return a list of all open tasks
     * @throws ServiceLayerException
     */
    List<TaskDTO> getAllOpenTasks() throws ServiceLayerException;

    /**
     * validate task input
     * @param toValidate
     * @return a confirmation of validation
     * @throws InvalidInputException
     */
    Task validateTaskInput(UnvalidatedTask toValidate) throws InvalidInputException;

    /**
     * get a task by its id to update information
     * @param id id of the task to update
     * @return updated task with same id
     */
    Task getTaskById(int id) throws PersistenceLayerException;
}
