package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.server.entity.Assignment;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;

import java.util.List;

public interface AssignmentDAO {

    /**
     * insert a new assignment for cran
     * @param assignment
     * @throws PersistenceLayerException
     */
    void createAssignment(Assignment assignment) throws PersistenceLayerException;

    /**
     * retrieve all not done assignments
     * @return a list of all assignments
     * @throws PersistenceLayerException
     */
    List<Assignment> getAllAssignments() throws PersistenceLayerException;

    /**
     * update an assignment and mark it as a done
     * @param assignment
     * @throws PersistenceLayerException
     */
    void updateAssignment(Assignment assignment) throws PersistenceLayerException;

    /**
     * delete an assignment
     * @param id
     * @throws PersistenceLayerException
     */
    void deleteAssignment(int id) throws PersistenceLayerException;

}
