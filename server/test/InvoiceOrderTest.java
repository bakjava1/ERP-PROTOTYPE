
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import at.ac.tuwien.sepm.assignment.group02.server.converter.OrderConverter;
import at.ac.tuwien.sepm.assignment.group02.server.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.LumberDAOJDBC;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.OrderDAO;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.OrderDAOJDBC;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TaskDAOJDBC;
import at.ac.tuwien.sepm.assignment.group02.server.service.OrderService;
import at.ac.tuwien.sepm.assignment.group02.server.service.OrderServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.server.util.DBUtil;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.*;

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


    @BeforeClass
    public static void setup(){
        LOG.debug("invoice order setup initiated");

        dbConnection = DBUtil.getConnection();
        fakeDBConnection = mock(Connection.class);

        orderDAO = new OrderDAOJDBC(dbConnection);
        orderDAOMock = mock(OrderDAOJDBC.class);

        orderService = new OrderServiceImpl(orderDAO, new TaskDAOJDBC(dbConnection), new LumberDAOJDBC(dbConnection),new OrderConverter(),new TaskConverter());

        orderDTONoError.setPaid(false);
        orderDTONoError.setCustomerName("Max Mustermmann");
        orderDTONoError.setCustomerAddress("Musterstraße 12, 1000 Musterdorf");
        orderDTONoError.setCustomerUID("1234567890");
        orderDTONoError.setID(5);
        orderDTONoError.setNetAmount(123456);
        orderDTONoError.setDeliveryDate("");
        orderDTONoError.setInvoiceDate("");
        orderDTONoError.setGrossAmount(123);
        orderDTONoError.setNetAmount(111);
        orderDTONoError.setTaxAmount(12);


        orderNoError1.setID(7);
        orderNoError1.setPaid(false);
        orderNoError1.setCustomerName("Max Mustermmann");
        orderNoError1.setCustomerAddress("Musterstraße 12, 1000 Musterdorf");
        orderNoError1.setCustomerUID("1234567890");
        orderNoError1.setNetAmount(123456);
        orderNoError1.setDeliveryDate("");
        orderNoError1.setInvoiceDate("");
        orderNoError1.setGrossAmount(123);
        orderNoError1.setNetAmount(111);
        orderNoError1.setTaxAmount(12);

        orderNoError2.setID(8);
        orderNoError2.setPaid(false);
        orderNoError2.setCustomerName("Max Mustermmann");
        orderNoError2.setCustomerAddress("Musterstraße 12, 1000 Musterdorf");
        orderNoError2.setCustomerUID("1234567890");
        orderNoError2.setNetAmount(123456);
        orderNoError2.setDeliveryDate("");
        orderNoError2.setInvoiceDate("");
        orderNoError2.setGrossAmount(123);
        orderNoError2.setNetAmount(111);
        orderNoError2.setTaxAmount(12);

        LOG.debug("invoice order setup finished ");
    }

    @Ignore
    @Test
    public void testInvoiceOrderPersistenceLayer() throws PersistenceLayerException {
        int beforeInvoice = countOpenOrders();

        orderDAO.invoiceOrder(orderNoError1);
        beforeInvoice--;
        orderDAO.invoiceOrder(orderNoError2);
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
