package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Task;

import java.util.List;

public interface TaskService {
    /**
     * 1.1.3 Auftrag Erstellung mit allen relevanten Daten + eindeutigem Schlüssel (id)
     * und Verbindung zu Bestellung (relationale Datenbank)
     * @param task
     */
    void createTask(Task task);

    /**
     *
     * @return
     */
    List<Task> getAllOpenTasks();

}
