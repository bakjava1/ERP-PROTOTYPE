package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;

import java.util.List;

public interface TaskController {

    /**
     * adding a reserved lumber to the task
     * @param task
     * @throws PersistenceLayerException
     * @inv task is validated
     */
    void updateTask(TaskDTO task) throws PersistenceLayerException;

    /**
     * updating task with lumber
     * @param task to be updated
     */
    void updateTaskAlg(TaskDTO task);

    /**
     * showing an overview for the existing task
     * @return a list of tasks
     * @throws PersistenceLayerException
     */
    List<TaskDTO> getAllOpenTasks() throws PersistenceLayerException;

    /**
     * get a task by its id
     * @param id
     * @throws PersistenceLayerException
     */
    TaskDTO getTaskById(int id) throws PersistenceLayerException;

}
