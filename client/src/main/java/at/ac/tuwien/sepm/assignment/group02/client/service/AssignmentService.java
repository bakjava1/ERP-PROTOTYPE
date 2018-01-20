package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;

import java.util.List;

public interface AssignmentService {

    /**
     * create a new assignment for crane operator
     * @param assignmentDTO the assignment to create
     * @throws ServiceLayerException if assignment could not be created
     */
    void createAssignment(AssignmentDTO assignmentDTO) throws ServiceLayerException;

    /**
     * retrieve a list of all open assignments
     * @return List<AssignmentDTO> list of assignments
     * @throws ServiceLayerException if no list of assignments could be returned
     */
    List<AssignmentDTO> getAllOpenAssignments() throws ServiceLayerException;

    /**
     * retrieve all closed assignments
     * @return List<AssignmentDTO> a list of all closed assignments
     * @throws ServiceLayerException if no list of assignments could be returned
     */
    List<AssignmentDTO> getAllClosedAssignments() throws ServiceLayerException;


    /**
     * method marks an assignment as done
     * @param assignmentDTO an assignment to mark as done
     * @throws ServiceLayerException if the assignment couldn't be marked as done
     */
    void setDone(AssignmentDTO assignmentDTO) throws ServiceLayerException;

    /**
     * method invokes the deletion of past assignments if they are done
     * @throws ServiceLayerException if assignments couldn't be deleted
     */
    void cleanUpAssignments() throws ServiceLayerException;

}

