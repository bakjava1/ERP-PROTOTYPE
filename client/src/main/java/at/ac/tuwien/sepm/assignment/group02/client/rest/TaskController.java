package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;

import java.util.List;

public interface TaskController {

    /**
     * 1.1.3 Aufträge erstellen
     */
    void createTask(TaskDTO task) throws PersistenceLayerException;

    /**
     * 1.2.1 Aufträge löschen
     */
    void deleteTask(TaskDTO task) throws PersistenceLayerException;

    /**
     * 2.2.3 & 3.2.6 Reserviertes Schnittholz dem Auftrag hinzufügen.
     */
    void updateTask(TaskDTO task) throws PersistenceLayerException;

    /**
     * 2.5.2 Eine tabellarische Übersicht der vorhandenen Aufträge anzeigen.
     */
    List<TaskDTO> getAllOpenTasks() throws PersistenceLayerException;

    /**
     * 3.2.7 maybe not needed.
     */
    void getTaskById(int task_id) throws PersistenceLayerException;

}
