package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.entity.UnvalidatedTask;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;

import java.util.List;

public interface TaskService {

    /**
     * get all open task
     * @return a list of all open tasks
     * @throws ServiceLayerException if no task list could be returned
     */
    List<TaskDTO> getAllOpenTasks() throws ServiceLayerException;

    /**
     * validate task input
     * @param toValidate task to be validated
     * @return a confirmation of validation
     * @throws InvalidInputException if given task is not valid
     */
    Task validateTaskInput(UnvalidatedTask toValidate) throws InvalidInputException;

    /**
     * get a task by its id to update information
     * @param id id of the task to update
     * @return updated task with same id
     * @throws PersistenceLayerException if no task with given id could be returned
     */
    Task getTaskById(int id) throws PersistenceLayerException;
}
