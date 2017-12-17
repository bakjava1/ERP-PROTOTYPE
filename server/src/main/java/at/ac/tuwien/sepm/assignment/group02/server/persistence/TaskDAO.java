package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;

import java.util.List;

public interface TaskDAO {


    /**
     * 1.1.3 Aufträge erstellen
     */
    void createTask(Task task) throws PersistenceLayerException;

    /**
     * 1.2.1 Aufträge löschen
     */
    void deleteTask(Task task) throws PersistenceLayerException;

    /**
     * 2.2.3 & 3.2.6 Reserviertes SchnittholzDAO dem Auftrag hinzufügen.
     */
    void updateTask(Task task) throws PersistenceLayerException;

    /**
     * 2.5.2 Eine tabellarische Übersicht der vorhandenen Aufträge anzeigen.
     */
    List<Task> getAllOpenTasks() throws PersistenceLayerException;

    /**
     * 3.2.7 maybe not needed.
     */
    void getTaskById(int task_id) throws PersistenceLayerException;



    List<Task> getTasksByOrderId(int order_id) throws PersistenceLayerException;

}
