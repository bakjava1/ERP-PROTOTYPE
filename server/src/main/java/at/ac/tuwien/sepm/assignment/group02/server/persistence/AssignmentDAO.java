package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.server.entity.Assignment;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;

import java.util.List;

public interface AssignmentDAO {

    /**
     * insert a new assignment for cran
     * @param assignment
     * @throws PersistenceLayerException
     * @inv assignment is validated
     */
    void createAssignment(Assignment assignment) throws PersistenceLayerException;

    /**
     * retrieve all assignments that are not done
     * @return a list of all open assignments
     * @throws PersistenceLayerException
     */
    List<Assignment> getAllOpenAssignments() throws PersistenceLayerException;

    /**
     * retrieve all assignments that are done
     * @return a list of all completed assignments
     * @throws PersistenceLayerException
     */
    List<Assignment> getAllClosedAssignments() throws PersistenceLayerException;

    /**
     * update an assignment and mark it as a done
     * @param assignment
     * @throws PersistenceLayerException
     * @inv assignment is validated
     */
    void setAssignmentDone(Assignment assignment) throws PersistenceLayerException;

    /**
     * delete an assignment
     * @param id
     * @throws PersistenceLayerException
     */
    void deleteAssignment(int id) throws PersistenceLayerException;

    /**
     * delete past assignments if they are done
     * @throws PersistenceLayerException
     */
    void deleteYesterdaysAssignments() throws PersistenceLayerException;
}
