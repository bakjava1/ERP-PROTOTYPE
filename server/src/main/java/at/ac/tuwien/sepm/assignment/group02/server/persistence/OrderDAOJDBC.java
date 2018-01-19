package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.server.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Task;
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
        String createSentence = "INSERT INTO ORDERS(id,customer_name,customer_address,customer_uid,order_date,isPaidFlag,isDoneFlag) VALUES(default,?,?,?,now(),false,false)";
        String insertTaskSentence = "INSERT INTO TASK (ID, ORDERID, DESCRIPTION, FINISHING, WOOD_TYPE, QUALITY, " +
                "SIZE, WIDTH, LENGTH, QUANTITY, PRODUCED_QUANTITY, PRICE, DONE, IN_PROGRESS, DELETED)" +
                "VALUES(default,?,?,?,?,?,?,?,?,?,?,?,false,false,false);";

        try{
            PreparedStatement stmt = dbConnection.prepareStatement(createSentence, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,order.getCustomerName());
            stmt.setString(2,order.getCustomerAddress());
            stmt.setString(3,order.getCustomerUID());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            List<Task> taskList = order.getTaskList();
            for(int i = 0; i < taskList.size();i++) {
                LOG.debug("Creating Task for Order");
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
                stmt.setInt(11,taskList.get(i).getPrice());
                stmt.executeUpdate();
            }
        } catch(SQLException e) {
            LOG.error("SQL Exception: " + e.getMessage());
            throw new PersistenceLayerException("Database Error");
        }
    }

    @Override
    public void deleteOrder(Order order) throws PersistenceLayerException {
        LOG.debug("deleting order number {} from database", order.getID());

        String deleteOrder = "UPDATE ORDERS SET isDoneFlag = TRUE WHERE ID = ?";

        try {
            PreparedStatement ps = dbConnection.prepareStatement(deleteOrder);
            ps.setInt(1, order.getID());
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
            ps = dbConnection.prepareStatement("SELECT * FROM ORDERS WHERE ISDONEFLAG = FALSE AND ISPAIDFLAG = FALSE ORDER BY ORDER_DATE");
            rs = ps.executeQuery();

            while (rs.next()) {


                Order currentOrder = new Order();
                currentOrder.setID(rs.getInt("ID"));
                currentOrder.setCustomerName(rs.getString("customer_name"));
                currentOrder.setCustomerAddress(rs.getString("customer_address"));
                currentOrder.setCustomerUID(rs.getString("customer_uid"));
                currentOrder.setOrderDate(rs.getString("order_date"));
                currentOrder.setPaid(rs.getBoolean("isPaidFlag"));
                //currentOrder.setGrossAmount(rs.getInt("summe"));


                orderList.add(currentOrder);
            }

            if (orderList.size() == 0) {
                //no open order was found
                throw new PersistenceLayerException("No open orders found");
            }

        } catch (SQLException e) {
            LOG.error("SQL Exception: " +  e.getMessage());
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
        LOG.debug("Get all closed Order from database");

        List<Order> billList = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            //connect to db
            ps = dbConnection.prepareStatement("SELECT * FROM ORDERS WHERE ISDONEFLAG = FALSE AND ISPAIDFLAG = TRUE ORDER BY ORDER_DATE");
            rs = ps.executeQuery();

            while (rs.next()) {


                Order currentBill = new Order();
                currentBill.setID(rs.getInt("ID"));
                currentBill.setCustomerName(rs.getString("customer_name"));
                currentBill.setInvoiceDate(rs.getString("order_date"));
                currentBill.setCustomerAddress(rs.getString("customer_address"));
                currentBill.setCustomerUID(rs.getString("customer_uid"));
                //currentBill.setGrossAmount(rs.getInt("summe"));


                billList.add(currentBill);
            }

            if (billList.size() == 0) {
                //no closed order was found
                throw new PersistenceLayerException("No closed orders found");
            }

        } catch (SQLException e) {
            throw new PersistenceLayerException("Database error:" + e.getMessage());
        }
        return billList;
    }

    @Override
    public Order getOrderById(int order_id) throws PersistenceLayerException {
        return null;
    }

    @Override
    public void invoiceOrder(Order order) throws PersistenceLayerException {
        String updateSentence = "UPDATE ORDERS SET isPaidFlag=?, delivery_date=?, invoice_date=?, gross_amount=?, net_amount=?, tax_amount=? WHERE ID=?";

        //TODO prices get not written because not clear how they are working now
        System.err.println(order.toString());
        try {
            PreparedStatement stmt = dbConnection.prepareStatement(updateSentence);
            //set necessary fields in order
            stmt.setBoolean(1, true);
            stmt.setString(2, order.getDeliveryDate());
            stmt.setString(3,  order.getInvoiceDate());
            stmt.setInt(4, order.getGrossAmount());
            stmt.setInt(5, order.getNetAmount());
            stmt.setInt(6, order.getTaxAmount());
            stmt.setInt(7, order.getID());
            stmt.execute();

            stmt.close();
        } catch (SQLException e) {
            LOG.error("SQLException: {}", e.getMessage());
            throw new PersistenceLayerException("Database error");
        }
    }
}
