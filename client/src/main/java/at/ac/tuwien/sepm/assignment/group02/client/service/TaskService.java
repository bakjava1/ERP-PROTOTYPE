package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.entity.UnvalidatedTask;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;

import java.util.List;

/**
 * CONVERSION HAPPENS ON THIS LAYER
 * VALIDATION HAPPENS ON THIS LAYER
 */
public interface TaskService {
    /**
     * 1.1.3 Auftrag Erstellung mit allen relevanten Daten + eindeutigem Schlüssel (id)
     * und Verbindung zu Bestellung (relationale Datenbank)
     * @param task
     */
    void createTask(Task task) throws InvalidInputException, ServiceLayerException;

    /**
     * 1.2.1 Aufträge löschen
     * method deletes a given task (identified by its id)
     * @param task the TaskDTO to be deleted
     * @throws ServiceLayerException in case the TaskDTO can't be deleted
     */
    void deleteTask(Task task) throws ServiceLayerException;


    /**
     *
     * @return
     */
    List<Task> getAllOpenTasks() throws ServiceLayerException;

    Task validateTaskInput(UnvalidatedTask toValidate) throws InvalidInputException;

}
