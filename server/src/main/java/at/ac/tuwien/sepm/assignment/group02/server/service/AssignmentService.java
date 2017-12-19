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
     */
    List<AssignmentDTO> getAllOpenAssignments() throws ServiceLayerException;

    /**
     * mark a given assignment as done
     * method receives an assignmentDTO and converts it to an assignment object
     * if conversion is valid the object is passed onto the persistence layer
     *
     * it invokes the reduction of the according amount of round timber from timber,
     * increases the amount of lumber accordingly,
     * reserves the added amount of lumber in case (?)
     * and adds the reserved amount of lumber to the task.
     * finally the method checks if the task is finished.
     *
     * @param assignmentDTO the assignment to be marked as done
     * @throws ServiceLayerException thrown if the assignment couldn't be updated
     */
    void setDone(AssignmentDTO assignmentDTO) throws ServiceLayerException;

}
