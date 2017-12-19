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
     * method marks an assignment as done
     * @param assignmentDTO an assignment to mark as done
     * @throws ServiceLayerException if the assignment couldn't be marked as done
     */
    void setDone(AssignmentDTO assignmentDTO) throws ServiceLayerException;

}

