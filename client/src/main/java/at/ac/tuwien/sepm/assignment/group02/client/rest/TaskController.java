package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;

import java.util.List;

public interface TaskController {

    /**
     * create a new task
     * @param task
     * @throws PersistenceLayerException
     * @inv task is validated
     */
    void createTask(TaskDTO task) throws PersistenceLayerException;

    /**
     * delete a task
     * @param task
     * @throws PersistenceLayerException
     * @inv task is validated
     */
    void deleteTask(TaskDTO task) throws PersistenceLayerException;

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
     * @param task_id
     * @throws PersistenceLayerException
     */
    void getTaskById(int task_id) throws PersistenceLayerException;

}
