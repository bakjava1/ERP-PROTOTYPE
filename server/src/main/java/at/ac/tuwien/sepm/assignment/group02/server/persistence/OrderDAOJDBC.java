package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLevelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.util.List;

public class OrderDAOJDBC implements OrderDAO {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static Connection dbConnection;

    public OrderDAOJDBC(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }


    @Override
    public void createOrder(Order order) throws PersistenceLevelException {
        LOG.debug("Creating new Order");
        String createSentence = "INSERT INTO ORDERS VALUES(default,now(),false,false)";

        try{
            PreparedStatement stmt = dbConnection.prepareStatement(createSentence, Statement.RETURN_GENERATED_KEYS);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
        } catch(SQLException e) {
            LOG.error("SQL Exception: " + e.getMessage());
            throw new PersistenceLevelException("Database Error");
        }
    }

    @Override
    public void deleteOrder(Order order) throws PersistenceLevelException {
        LOG.debug("deleting order number {} from database", order.getID());

        String deleteOrder = "UPDATE ORDERS SET DELETED = 1 WHERE ID = ?";

        try {
            PreparedStatement ps = dbConnection.prepareStatement(deleteOrder);
            ps.setInt(1, order.getID());
            ps.execute();

            ps.close();
        } catch (SQLException e) {
            LOG.error("SQLException: {}", e.getMessage());
            throw new PersistenceLevelException("Database error");
        }
    }

    @Override
    public List<Order> getAllOpen() throws PersistenceLevelException {
        return null;
    }

    @Override
    public void updateOrder(Order order) throws PersistenceLevelException {

    }

    @Override
    public List<Order> getAllClosed() throws PersistenceLevelException {
        return null;
    }

    @Override
    public Order getOrderById(int order_id) throws PersistenceLevelException {
        return null;
    }
}
