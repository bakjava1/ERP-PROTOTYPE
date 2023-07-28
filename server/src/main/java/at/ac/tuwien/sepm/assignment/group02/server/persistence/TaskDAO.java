package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.server.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;

import java.util.List;

public interface TaskDAO {

    /**
     * insert a new task in the data base
     * @param task
     * @throws PersistenceLayerException
     * @inv task is validated
     */
    int createTask(Task task) throws PersistenceLayerException;

    /**
     * delete an existing task from the data base
     * @param task
     * @throws PersistenceLayerException
     * @inv task is validated
     */
    void deleteTask(Task task) throws PersistenceLayerException;

    /**
     * update a task
     * @param task
     * @throws PersistenceLayerException
     * @inv task is validated
     */
    void updateTask(Task task) throws PersistenceLayerException;

    /**
     * updating task with reserved lumber
     * @param task to be updated
     */
    void updateTaskAlg(Task task);

    /**
     * retrieve all open tasks
     * @return a list of all tasks
     * @throws PersistenceLayerException
     */
    List<Task> getAllOpenTasks() throws PersistenceLayerException;

    /**
     * retrieve all tasks
     * @return a list of all tasks
     * @throws PersistenceLayerException
     */
    List<Task> getAllTasks() throws PersistenceLayerException;

    /**
     * get task by id
     * @param task_id
     * @throws PersistenceLayerException
     */
    Task getTaskById(int task_id) throws PersistenceLayerException;

    /**
     * get task by order_id
     * @param order_id
     * @return the id of a given order
     * @throws PersistenceLayerException
     */
    List<Task> getTasksByOrderId(int order_id) throws PersistenceLayerException;

}
