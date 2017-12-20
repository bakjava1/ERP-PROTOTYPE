import at.ac.tuwien.sepm.assignment.group02.client.converter.OrderConverter;
import at.ac.tuwien.sepm.assignment.group02.client.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.OrderController;
import at.ac.tuwien.sepm.assignment.group02.client.rest.OrderControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.client.service.OrderService;
import at.ac.tuwien.sepm.assignment.group02.client.service.OrderServiceImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class InvoiceOrderTest {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static Order orderNoError = new Order();
    private static Order orderAlreadyInvoiced = new Order();
    private static Order orderNetPriceIsNegative = new Order();
    private static Order orderCustomerError = new Order();

    private static OrderController orderControllerMock = Mockito.mock(OrderControllerImpl.class);
    private static OrderService orderService = new OrderServiceImpl(orderControllerMock, new OrderConverter(), new TaskConverter());

    @BeforeClass
    public static void setup(){
        LOG.debug("invoice order setup initiated");

        orderNoError.setPaid(false);
        orderNoError.setCustomerName("Max Mustermmann");
        orderNoError.setCustomerAddress("Musterstraße 12, 1000 Musterdorf");
        orderNoError.setCustomerUID("1234567890");
        orderNoError.setID(1);
        orderNoError.setNetAmount(123456);

        orderCustomerError.setPaid(false);
        orderCustomerError.setCustomerName("");
        orderCustomerError.setCustomerAddress("");
        orderCustomerError.setCustomerUID("");
        orderCustomerError.setID(1);
        orderCustomerError.setNetAmount(123456);

        orderNetPriceIsNegative.setPaid(false);
        orderNetPriceIsNegative.setCustomerName("Max Mustermmann");
        orderNetPriceIsNegative.setCustomerAddress("Musterstraße 12, 1000 Musterdorf");
        orderNetPriceIsNegative.setCustomerUID("1234567890");
        orderNetPriceIsNegative.setID(1);
        orderNetPriceIsNegative.setNetAmount(-2);


        orderAlreadyInvoiced.setPaid(true);

    }

    @Test(expected = InvalidInputException.class)
    public void testInvoiceOrderNetPrice() throws InvalidInputException, ServiceLayerException {
        orderService.invoiceOrder(orderNetPriceIsNegative);
    }

    @Test(expected = InvalidInputException.class)
    public void testInvoiceOrderCustomerError() throws InvalidInputException, ServiceLayerException {
        orderService.invoiceOrder(orderCustomerError);
    }

    @Test(expected = InvalidInputException.class)
    public void testInvoiceOrderAlreadyInvoiced() throws InvalidInputException, ServiceLayerException {
        orderService.invoiceOrder(orderAlreadyInvoiced);
    }

    @Test(expected = InvalidInputException.class)
    public void testInvoiceOrderNoErrorServiceLayer() throws InvalidInputException, ServiceLayerException {
        orderService.invoiceOrder(orderNoError);
    }


    @AfterClass
    public static void teardown(){
        LOG.debug("invoice order teardown initiated");
    }
}
