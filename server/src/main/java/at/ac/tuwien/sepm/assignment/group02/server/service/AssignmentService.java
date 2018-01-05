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
     * This method creates a new assignment for the crane operator
     * @param assignmentDTO
     * @throws ServiceLayerException
     */
    void addAssignment(AssignmentDTO assignmentDTO) throws ServiceLayerException;

    /**
     * retrieve all open assignments
     * @return a list of all open assignments
     * @throws ServiceLayerException
     */
    List<AssignmentDTO> getAllOpenAssignments() throws ServiceLayerException;

    /**
     * retrieve all open and closed assignments
     * @return a list of all assignments
     * @throws ServiceLayerException
     */
    List<AssignmentDTO> getAllAssignments() throws ServiceLayerException;


    /**
     * mark an assignment as done
     * @param assignmentDTO
     * @throws ServiceLayerException
     */
    void setDone(AssignmentDTO assignmentDTO) throws ServiceLayerException;

}
