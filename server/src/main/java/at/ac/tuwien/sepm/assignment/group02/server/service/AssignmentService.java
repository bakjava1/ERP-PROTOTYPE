package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;

import java.util.List;

/**
 * CONVERSION HAPPENS ON THIS LAYER
 * VALIDATION HAPPENS ON THIS LAYER
 */
public interface AssignmentService {

    /**
     * 2.4.4
     * This method creates a new assignment for the crane operator
     */
    void createAssignment(AssignmentDTO assignmentDTO);

    /**
     * 3.1.2 Alle nicht erledigten Aufgaben anfordern.
     */
    List<AssignmentDTO> getAllOpenAssignments();

    /**
     * 3.2.2 Aufgabe als erledigt markieren.
     */
    void setDone(AssignmentDTO assignmentDTO);

}
