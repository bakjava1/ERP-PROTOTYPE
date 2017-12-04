package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLevelException;

/**
 * This interface defines all methods needed for persistence.
 */
public interface LumberManagementDAO {
    /**
     * Hello World
     */
    void createLumber(Lumber lumber) throws PersistenceLevelException;
    Lumber readLumberById(int id) throws PersistenceLevelException;
}
