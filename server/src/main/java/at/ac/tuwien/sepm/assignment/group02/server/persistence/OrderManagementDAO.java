package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLevelException;

/**
 *  This interface defines all methods needed for order management in the persistence layer.
 */
public interface OrderManagementDAO {
    void deleteOrder(int id) throws PersistenceLevelException;
}
