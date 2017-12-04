package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;

import java.util.List;

public interface TaskService {

    /**
     * 1.1.3 Aufträge erstellen
     */
    void createTask(TaskDTO task);

    /**
     * 1.2.1 Aufträge löschen
     */
    void deleteTask(TaskDTO task);

    /**
     * 2.2.3 & 3.2.6 Add lumber to task
     */
    void updateTask(TaskDTO task);

    /**
     * 2.5.2
     * return a list of all open tasks
     */
    List<TaskDTO> getAllOpenTasks();

    /**
     * 3.2.7 maybe not needed.
     */
    void getTaskById(int task_id);

}
