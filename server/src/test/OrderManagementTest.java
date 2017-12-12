import at.ac.tuwien.sepm.assignment.group02.rest.converter.OrderConverter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.EntityNotFoundException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.OrderDAO;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.OrderDAOJDBC;
import at.ac.tuwien.sepm.assignment.group02.server.rest.OrderControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.server.service.OrderService;
import at.ac.tuwien.sepm.assignment.group02.server.service.OrderServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.server.util.DBUtil;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class OrderManagementTest {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static Connection dbConnection;
    private static Connection fakeDBConnection;
    private static OrderDAO orderDAO;
    private static OrderDAO mockOrderDAO;
    private static Order order1 = new Order();
    private static Order order2 = new Order();
    private static Order order3 = new Order();
    private static OrderDTO orderDTO1 = new OrderDTO();
    private static OrderDTO orderDTO2 = new OrderDTO();
    private static OrderDTO orderDTO3 = new OrderDTO();

    private static OrderControllerImpl orderController;
    private static OrderService orderService;
    private static OrderConverter orderConverter;

    @BeforeClass
    public static void setup() {
        LOG.debug("order management test setup initiated");
        dbConnection = DBUtil.getConnection();
        fakeDBConnection = mock(Connection.class);
        orderDAO = new OrderDAOJDBC(dbConnection);
        mockOrderDAO = mock(OrderDAOJDBC.class);

        order1.setID(1);
        order2.setID(2);
        order3.setID(3);

        orderDTO1.setID(4);
        orderDTO2.setID(5);
        orderDTO3.setID(6);

        orderConverter = new OrderConverter();
        orderService = new OrderServiceImpl(orderDAO, orderConverter);
        orderController = new OrderControllerImpl(orderService);

        LOG.debug("order management test setup completed");
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

    @Test
    public void testDeleteOrder_server_restController() throws EntityNotFoundException {
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

    //TODO fix test
    @Ignore
    @Test (expected = PersistenceLayerException.class)
    public void deleteOrderPersistenceLayer_should_get_errorMessage_when_persistenceLayer_throws_Exception() throws SQLException, PersistenceLayerException {
        String SQLStatement = "UPDATE ORDERS SET DELETED = 1 WHERE ID = ?";
        when(fakeDBConnection.prepareStatement(SQLStatement)).thenThrow(new PersistenceLayerException("Database error"));
        //doThrow(new PersistenceLayerException("Database error")).when(mockOrderDAO).deleteOrder(order1);
        try {
            orderDAO = new OrderDAOJDBC(fakeDBConnection);
            orderDAO.deleteOrder(order1);
        } catch (PersistenceLayerException e) {
            assertEquals("Database error", e.getMessage());
            //verify(mockOrderDAO, times(1)).deleteOrder(order1);
        }
    }

    @AfterClass
    public static void teardown() {
        LOG.debug("order management test teardown initiated");

        DBUtil.closeConnection();

        LOG.debug("order management test teardown completed");
    }




    private int getActiveOrders() {
        int count = 0;
        String getOrder = "SELECT COUNT(ID) FROM ORDERS WHERE DELETED = 0";

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
            System.out.println("SQLException: {}" + e.getMessage());
        }

        return count;
    }
}
