package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;

import java.util.List;

public interface AssignmentController {

    /**
     * 2.4.4 Neue Aufgabe für Kranfahrer erstellen.
     */
    void createAssignment(AssignmentDTO assignmentDTO) throws PersistenceLayerException;

    /**
     * 3.1.2 Alle nicht erledigten Aufgaben anfordern.
     */
    List<AssignmentDTO> getAllOpenAssignments() throws PersistenceLayerException;

    /**
     * method marks an assignment as done
     * @param assignmentDTO the assignmentDTO to be marked as done
     * @throws PersistenceLayerException thrown in case the assignment couldn't be updated
     */
    void setDone(AssignmentDTO assignmentDTO) throws PersistenceLayerException;

}
