package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;

import java.util.List;

public interface TaskService {

    /**
     * method creates a new task
     * @param task the TaskDTO to be created
     * @throws ServiceLayerException in case the TaskDTO can't be created
     */
    int createTask(TaskDTO task) throws ServiceLayerException;

    /**
     * method deletes a given task (identified by its id)
     * @param task the TaskDTO to be deleted
     * @throws ServiceLayerException in case the TaskDTO can't be deleted
     */
    void deleteTask(TaskDTO task) throws ServiceLayerException;

    /**
     * method used to add lumber to task
     * method updates the values of a given task (identified by its id)
     * @param task the updated version of the TaskDTO
     * @throws ServiceLayerException in case the TaskDTO can't be updated
     */
    void updateTask(TaskDTO task) throws ServiceLayerException;

    /**
     * updating task with reserved lumber
     * @param task to be updated
     */
    void updateTaskAlg(TaskDTO task);

    /**
     * method returns all open tasks
     * @return a List of TaskDTO that are marked as open tasks
     * @throws ServiceLayerException in case the TaskDTO can't be returned
     */
    List<TaskDTO> getAllOpenTasks() throws ServiceLayerException;

    /**
     * method returns a TaskDTO defined by the task id
     * @param task_id integer defining the id of the task
     * @return TaskDTO with specified task_id
     * @throws ServiceLayerException in case the TaskDTO can't be returned
     */
    TaskDTO getTaskById(int task_id) throws ServiceLayerException;

}
