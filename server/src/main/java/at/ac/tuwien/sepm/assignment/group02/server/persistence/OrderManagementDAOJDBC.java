package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLevelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.*;

public class OrderManagementDAOJDBC implements OrderManagementDAO {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static Connection dbConnection;

    public OrderManagementDAOJDBC(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }


    @Override
    public void deleteOrder(int id) throws PersistenceLevelException {
        LOG.debug("deleting order number {} from database", id);
        LOG.debug("deleting tasks containing order number {} from database", id);

        //TODO soft delete?
        String deleteOrder = "UPDATE ORDERS SET DELETED = 1 WHERE ID = ?";
        //String deleteTask = "UPDATE TASK SET DELETED = 1 WHERE ORDERID = ?";

        try {
            PreparedStatement ps = dbConnection.prepareStatement(deleteOrder);
            ps.setInt(1, id);
            ps.execute();

            //ps = dbConnection.prepareStatement(deleteTask);
            //ps.setInt(1, id);
            //ps.execute();

            ps.close();
        } catch (SQLException e) {
            LOG.error("SQLException: {}", e.getMessage());
            throw new PersistenceLevelException(e.getMessage());
        }
    }
}
