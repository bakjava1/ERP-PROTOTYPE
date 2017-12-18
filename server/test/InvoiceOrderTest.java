
import at.ac.tuwien.sepm.assignment.group02.rest.converter.OrderConverter;
import at.ac.tuwien.sepm.assignment.group02.rest.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.OrderDAO;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.OrderDAOJDBC;
import at.ac.tuwien.sepm.assignment.group02.server.service.OrderService;
import at.ac.tuwien.sepm.assignment.group02.server.service.OrderServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.server.util.DBUtil;
import org.junit.*;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;

public class InvoiceOrderTest {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static Connection dbConnection;
    private static Connection fakeDBConnection;

    private static OrderDAO orderDAO;
    private static OrderDAO orderDAOMock;

    private static OrderService orderService;

    private static OrderDTO orderDTONoError = new OrderDTO();

    private static Order orderNoError1 = new Order();
    private static Order orderNoError2 = new Order();
    private static Order orderNoError3 = new Order();


    @BeforeClass
    public static void setup(){
        LOG.debug("invoice order setup initiated");

        dbConnection = DBUtil.getConnection();
        fakeDBConnection = mock(Connection.class);

        orderDAO = new OrderDAOJDBC(dbConnection);
        orderDAOMock = mock(OrderDAOJDBC.class);

        orderService = new OrderServiceImpl(orderDAO, new OrderConverter());

        orderDTONoError.setPaid(false);
        orderDTONoError.setCustomerName("Max Mustermmann");
        orderDTONoError.setCustomerAddress("Musterstraße 12, 1000 Musterdorf");
        orderDTONoError.setCustomerUID("1234567890");
        orderDTONoError.setID(1);
        orderDTONoError.setNetAmount(123456);

        orderNoError1.setID(2);
        orderNoError2.setID(3);
        orderNoError3.setID(4);

        LOG.debug("invoice order setup finished ");
    }

    @Test
    public void testInvoiceOrderPersistenceLayer() throws PersistenceLayerException {
        int beforeInvoice = countOpenOrders();

        orderDAO.invoiceOrder(orderNoError1);
        beforeInvoice--;
        orderDAO.invoiceOrder(orderNoError2);
        beforeInvoice--;
        orderDAO.invoiceOrder(orderNoError3);
        beforeInvoice--;

        int afterInvoice = countOpenOrders();

        Assert.assertEquals(beforeInvoice,afterInvoice);
    }

    @Test
    public void testInvoiceOrderNoErrorServiceLayer() throws ServiceLayerException {

        orderService.invoiceOrder(orderDTONoError);
    }


    @AfterClass
    public static void teardown(){
        LOG.debug("invoice order teardown initiated");
        DBUtil.closeConnection();
        LOG.debug("invoice order teardown finished");
    }

    private int countOpenOrders() throws PersistenceLayerException {

            int numberOfOrders = 0;
            String countSentence = "SELECT COUNT(*) FROM ORDERS WHERE isPaidFlag=0";

            try {
                PreparedStatement stmt = dbConnection.prepareStatement(countSentence);
                ResultSet rs = stmt.executeQuery();
                rs.next();
                numberOfOrders = rs.getInt(1);
                stmt.close();
                rs.close();
            } catch (SQLException e) {
                LOG.error("SQL Exception: " + e.getMessage());
                throw new PersistenceLayerException("Database Error");
            }
            return numberOfOrders;
    }
}
