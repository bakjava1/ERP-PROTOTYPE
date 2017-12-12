package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderDAOJDBC implements OrderDAO {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static Connection dbConnection;

    @Autowired
    public OrderDAOJDBC(Connection dbConnection) {
        OrderDAOJDBC.dbConnection = dbConnection;
    }


    @Override
    public void createOrder(Order order) throws PersistenceLayerException {
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
            throw new PersistenceLayerException("Database Error");
        }
    }

    @Override
    public void deleteOrder(Order order) throws PersistenceLayerException {
        LOG.debug("deleting order number {} from database", order.getID());

        String deleteOrder = "UPDATE ORDERS SET DELETED = 1 WHERE ID = ?";

        try {
            PreparedStatement ps = dbConnection.prepareStatement(deleteOrder);
            System.out.print("");
            ps.setInt(1, order.getID());
            System.out.print("");
            ps.execute();

            ps.close();
        } catch (SQLException e) {
            LOG.error("SQLException: {}", e.getMessage());
            throw new PersistenceLayerException("Database error");
        }
    }

    @Override
    public List<Order> getAllOpen() throws PersistenceLayerException {
        LOG.debug("Get all open Order from database");

        List<Order> orderList = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            //connect to db
            ps = dbConnection.prepareStatement("SELECT * FROM ORDERS WHERE ISPAID = 0 AND DELETED = 0 ORDER BY ORDERDATE");
            rs = ps.executeQuery();

            while (rs.next()) {


                Order currentOrder = new Order(rs.getInt("ID"), rs.getTimestamp("ORDERDATE"));
                currentOrder.setPaid(false);

                orderList.add(currentOrder);
            }

            if (orderList.size() == 0) {
                //no open order was found
                throw new PersistenceLayerException("No open orders found");
            }

        } catch (SQLException e) {
            throw new PersistenceLayerException("Database error");
        } finally {
            //close connections
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* ignored */}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { /* ignored */}
            }

        }

        return orderList;
    }

    @Override
    public void updateOrder(Order order) throws PersistenceLayerException {

    }

    @Override
    public List<Order> getAllClosed() throws PersistenceLayerException {
        return null;
    }

    @Override
    public Order getOrderById(int order_id) throws PersistenceLayerException {
        return null;
    }
}
