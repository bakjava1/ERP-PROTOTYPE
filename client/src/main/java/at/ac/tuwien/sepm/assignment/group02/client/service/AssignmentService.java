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
     * create a new assignment for crane
     * @param assignmentDTO
     * @throws ServiceLayerException
     */
    void createAssignment(AssignmentDTO assignmentDTO) throws ServiceLayerException;

    /**
     * retrieve all open assignments overviewing not finihsing assignments
     * @return a list of assignments
     * @throws ServiceLayerException
     */
    List<AssignmentDTO> getAllOpenAssignments() throws ServiceLayerException;

    /**
     * retrieve all assignments
     * @return a list of all assignments
     * @throws ServiceLayerException
     */
    List<AssignmentDTO> getAllAssignments() throws ServiceLayerException;


    /**
     * method marks an assignment as done
     * @param assignmentDTO an assignment to mark as done
     * @throws ServiceLayerException if the assignment couldn't be marked as done
     */
    void setDone(AssignmentDTO assignmentDTO) throws ServiceLayerException;

}

