package at.ac.tuwien.sepm.assignment.group02.rest.restController;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;

import java.util.List;

public interface TaskController {

    /**
     * 1.1.3 Aufträge erstellen
     */
    void createTask(TaskDTO task);

    /**
     * 1.2.1 Aufträge löschen
     */
    void deleteTask(TaskDTO task);

    /**
     * 2.2.3 & 3.2.6 Reserviertes Schnittholz dem Auftrag hinzufügen.
     */
    void addLumberToTaks(LumberDTO lumber, TaskDTO task);

    /**
     * 2.5.2 Eine tabellarische Übersicht der vorhandenen Aufträge anzeigen.
     */
    List<TaskDTO> getAllOpenTDTOasks();

    /**
     * 3.2.7 maybe not needed.
     */
    void getTaskById(int task_id);

}
