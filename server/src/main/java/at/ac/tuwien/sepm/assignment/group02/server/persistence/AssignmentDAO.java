package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Assignment;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLevelException;

import java.util.List;

public interface AssignmentDAO {

    /**
     * 2.4.4 Neue Aufgabe für Kranfahrer erstellen.
     */
    void createAssignment(Assignment assignment) throws PersistenceLevelException;

    /**
     * 3.1.2 Alle nicht erledigten Aufgaben anfordern.
     */
    List<Assignment> getAllAssignments() throws PersistenceLevelException;

    /**
     * 3.2.2 Aufgabe als erledigt markieren.
     */
    void updateAssignment(Assignment assignment) throws PersistenceLevelException;
    void deleteAssignment(int id) throws PersistenceLevelException;

}
