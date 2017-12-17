package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;

import java.util.List;

/**
 * CONVERSION HAPPENS ON THIS LAYER
 * VALIDATION HAPPENS ON THIS LAYER
 */
public interface AssignmentService {

    /**
     * 2.4.4 Neue Aufgabe für Kranfahrer erstellen.
     * @param assignmentDTO
     */
    void createAssignment(AssignmentDTO assignmentDTO) throws ServiceLayerException;

    /**
     * 3.1 Aufgaben anzeigen
     * 3.1.2 (rest/AssignmentController) Eine tabellarische Übersicht der nicht erledigten Aufgaben anzeigen.
     * @return
     */
    List<AssignmentDTO> getAllOpenAssignments() throws ServiceLayerException;

    /**
     * 3.2 Aufgaben abschließen
     * 3.2.2 (rest/AssignmentController) Aufgabe als erledigt markieren.
     * 3.2.3 (rest/TimberController) Rundholz aus dem Lager entfernen.
     * 3.2.4 (rest/LumberController) Schnittholz ins Lager hinzufügen.
     * 3.2.5 (rest/LumberController) Hinzugefügtes Schnittholz bei Bedarf reservieren.
     * 3.2.6 (rest/TaskController) Reserviertes Schnittholz dem Auftrag hinzufügen.
     * 3.2.7 Überprüfen ob Auftrag fertig ist (? => (rest/TaskController) getTaskById)
     * @param assignmentDTO an assignment to mark as done
     * @throws ServiceLayerException if the assignment couldn't be updated
     */
    void setDone(AssignmentDTO assignmentDTO) throws ServiceLayerException;

}

