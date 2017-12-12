package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLevelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        String insertTaskSentence = "INSERT INTO TASK VALUES(default,?,?,?,?,?,?,?,?,?,?,false,false);";

        try{
            PreparedStatement stmt = dbConnection.prepareStatement(createSentence, Statement.RETURN_GENERATED_KEYS);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            List<Task> taskList = order.getTaskList();
            for(int i = 0; i < taskList.size();i++) {
                stmt = dbConnection.prepareStatement(insertTaskSentence);
                stmt.setInt(1,id);
                stmt.setString(2,taskList.get(i).getDescription());
                stmt.setString(3,taskList.get(i).getFinishing());
                stmt.setString(4,taskList.get(i).getWood_type());
                stmt.setString(5,taskList.get(i).getQuality());
                stmt.setInt(6,taskList.get(i).getSize());
                stmt.setInt(7,taskList.get(i).getWidth());
                stmt.setInt(8,taskList.get(i).getLength());
                stmt.setInt(9,taskList.get(i).getQuantity());
                stmt.setInt(10,0);
                stmt.executeUpdate();
            }
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
                throw new PersistenceLevelException("No open orders found");
            }

        } catch (SQLException e) {
            throw new PersistenceLevelException("Database error");
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

    @Override
    public void invoiceOrder(Order order) throws PersistenceLevelException {
        String updateSentence = "UPDATE ORDERS SET ISPAID=? WHERE ID=?";

        try {
            PreparedStatement stmt = dbConnection.prepareStatement(updateSentence);
            //set to ispaid to true
            stmt.setInt(1, 1);
            stmt.setInt(2, order.getID());
            stmt.execute();

            stmt.close();
        } catch (SQLException e) {
            LOG.error("SQLException: {}", e.getMessage());
            throw new PersistenceLevelException("Database error");
        }
    }
}
