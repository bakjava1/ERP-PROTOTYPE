package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;

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
    void addAssignment(AssignmentDTO assignmentDTO) throws ServiceLayerException;

    /**
     * 3.1.2 Alle nicht erledigten Aufgaben anfordern.
     * @throws ServiceLayerException if the database is not available for the persistence layer
     * @return list of all open assignments to be shown in the assignment overview for crane
     */
    List<AssignmentDTO> getAllOpenAssignments() throws ServiceLayerException;

    /**
     * 3.2.2 Aufgabe als erledigt markieren.
     */
    void setDone(AssignmentDTO assignmentDTO) throws ServiceLayerException;

}
