import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import at.ac.tuwien.sepm.assignment.group02.server.converter.OrderConverter;
import at.ac.tuwien.sepm.assignment.group02.server.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.EntityNotFoundException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.OrderDAO;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.OrderDAOJDBC;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TaskDAOJDBC;
import at.ac.tuwien.sepm.assignment.group02.server.rest.OrderControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.server.service.OrderService;
import at.ac.tuwien.sepm.assignment.group02.server.service.OrderServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.server.util.DBUtil;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class OrderManagementTest {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static Connection dbConnection;
    private static OrderDAO orderDAO;

    private static Order order1;
    private static Order order2;
    private static Order order3;
    private static OrderDTO orderDTO1;
    private static OrderDTO orderDTO2;
    private static OrderDTO orderDTO3;

    private static OrderControllerImpl orderController;
    private static OrderService orderService;
    private static OrderConverter orderConverter;
    private static TaskConverter taskConverter;

    @BeforeClass
    public static void setup() {
        LOG.debug("order management test setup initiated");

        dbConnection = DBUtil.getConnection();
        orderDAO = new OrderDAOJDBC(dbConnection);

        order1 = new Order();
        order1.setID(1);
        order2 = new Order();
        order2.setID(2);
        order3 = new Order();
        order3.setID(3);

        orderDTO1 = new OrderDTO();
        orderDTO1.setID(4);
        orderDTO2 = new OrderDTO();
        orderDTO2.setID(5);
        orderDTO3 = new OrderDTO();
        orderDTO3.setID(6);

        orderConverter = new OrderConverter();
        taskConverter = new TaskConverter();
        orderService = new OrderServiceImpl(orderDAO, new TaskDAOJDBC(dbConnection) ,orderConverter,taskConverter);
        orderController = new OrderControllerImpl(orderService);

        LOG.debug("order management test setup completed");
    }

    @Before
    public void initDBConnection() {
        dbConnection = DBUtil.getConnection();
        orderDAO = new OrderDAOJDBC(dbConnection);
        activateOrders();
    }

    @Test
    public void testDeleteOrder_server_restController() throws ServiceLayerException {
        LOG.debug("testing for order deletion in server rest controller");

        int orderCountBeforeDeletion = getActiveOrders();

        orderController.deleteOrder(orderDTO1);
        orderCountBeforeDeletion--;
        orderController.deleteOrder(orderDTO2);
        orderCountBeforeDeletion--;
        orderController.deleteOrder(orderDTO3);
        orderCountBeforeDeletion--;

        int orderCountAfterDeletion = getActiveOrders();

        assertEquals(orderCountBeforeDeletion, orderCountAfterDeletion);
    }

    @Test
    public void testDeleteOrder_server_persistenceLayer() throws PersistenceLayerException {
        LOG.debug("testing for order deletion in server persistence layer");

        int orderCountBeforeDeletion = getActiveOrders();

        orderDAO.deleteOrder(order1);
        orderCountBeforeDeletion--;
        orderDAO.deleteOrder(order2);
        orderCountBeforeDeletion--;
        orderDAO.deleteOrder(order3);
        orderCountBeforeDeletion--;

        int orderCountAfterDeletion = getActiveOrders();

        assertEquals(orderCountBeforeDeletion, orderCountAfterDeletion);
    }

    @Test (expected = PersistenceLayerException.class)
    public void deleteOrderPersistenceLayer_throws_Exception_without_DBConnection() throws PersistenceLayerException, SQLException {
        LOG.debug("testing delete order in persistence layer when DB is not available");

        DBUtil.closeConnection();
        orderDAO.deleteOrder(order1);
    }

    @AfterClass
    public static void teardown() {
        LOG.debug("order management test teardown initiated");

        DBUtil.closeConnection();

        LOG.debug("order management test teardown completed");
    }



    private static void activateOrders() {
        String activateOrders = "UPDATE ORDERS SET isDoneFlag = 0 WHERE ID = 1 or ID = 2 or ID = 3 or ID = 4 or ID = 5 or ID = 6";

        try {
            PreparedStatement ps = dbConnection.prepareStatement(activateOrders);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            System.out.println("error at preparing tests for deleting orders" + e.getMessage());
        }
    }


    private int getActiveOrders() {
        int count = 0;
        String getOrder = "SELECT COUNT(ID) FROM ORDERS WHERE isDoneFlag = 0";

        try {
            PreparedStatement ps = dbConnection.prepareStatement(getOrder);
            ps.execute();

            ResultSet rs = ps.getResultSet();
            rs.next();

            count = rs.getInt(1);

            rs.close();
            ps.close();

            return count;
        } catch (SQLException e) {
            System.out.println("error at testing for active orders: " + e.getMessage());
        }

        return count;
    }
}
