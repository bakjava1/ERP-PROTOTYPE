import at.ac.tuwien.sepm.assignment.group02.client.converter.OrderConverter;
import at.ac.tuwien.sepm.assignment.group02.client.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.OrderController;
import at.ac.tuwien.sepm.assignment.group02.client.rest.OrderControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.client.service.OrderService;
import at.ac.tuwien.sepm.assignment.group02.client.service.OrderServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

public class InvoiceOrderTest {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static Order orderNoError = new Order();
    private static Order orderAlreadyInvoiced = new Order();
    private static Order orderNetPriceIsNegative = new Order();
    private static Order orderCustomerError = new Order();

    private static OrderDTO orderDTONoError = new OrderDTO();

    private static RestTemplate restTemplate;
    private static OrderController orderController;

    private static OrderController orderControllerMock = Mockito.mock(OrderControllerImpl.class);
    private static OrderService orderService = new OrderServiceImpl(orderControllerMock, new OrderConverter(), new TaskConverter());

    @BeforeClass
    public static void setup(){
        LOG.debug("invoice order setup initiated");

        restTemplate = Mockito.mock(RestTemplate.class);
        orderController = new OrderControllerImpl(restTemplate);

        orderNoError.setPaid(false);
        orderNoError.setCustomerName("Max Mustermmann");
        orderNoError.setCustomerAddress("Musterstraße 12, 1000 Musterdorf");
        orderNoError.setCustomerUID("1234567890");
        orderNoError.setID(1);
        orderNoError.setOrderDate("");
        Task temp = new Task();
        temp.setDescription("test");
        temp.setDone(false);
        temp.setFinishing("test");
        temp.setId(1);
        temp.setLength(12);
        temp.setSize(12);
        temp.setOrder_id(1);
        temp.setPrice(21);
        temp.setProduced_quantity(0);
        temp.setQuality("test");
        temp.setQuantity(12);
        temp.setWidth(12);
        temp.setWood_type("test");
        orderNoError.addTask(temp);

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

        orderDTONoError.setID(1);
        orderDTONoError.setTaxAmount(12);
        orderDTONoError.setNetAmount(111);
        orderDTONoError.setGrossAmount(123);
        orderDTONoError.setInvoiceDate(new java.util.Date());
        orderDTONoError.setDeliveryDate(new java.util.Date());
        orderDTONoError.setCustomerAddress("Musterstraße 12, 1000 Musterdorf");
        orderDTONoError.setCustomerName("Max Mustermann");
        orderDTONoError.setCustomerUID("123456");
        orderDTONoError.setOrderDate(new java.util.Date());
        orderDTONoError.setPaid(false);
        orderDTONoError.setTaskList(new ArrayList<TaskDTO>());

        orderAlreadyInvoiced.setPaid(true);

    }

    @Test(expected = InvalidInputException.class)
    public void testInvoiceOrderNetPrice() throws ServiceLayerException {
        orderService.invoiceOrder(orderNetPriceIsNegative);
    }

    @Test(expected = InvalidInputException.class)
    public void testInvoiceOrderCustomerError() throws ServiceLayerException {
        orderService.invoiceOrder(orderCustomerError);
    }

    @Test(expected = InvalidInputException.class)
    public void testInvoiceOrderAlreadyInvoiced() throws ServiceLayerException {
        orderService.invoiceOrder(orderAlreadyInvoiced);
    }

    @Ignore
    @Test
    public void testInvoiceOrderServicePositive() throws ServiceLayerException {
        orderService.invoiceOrder(orderNoError);
    }

    @Test(expected = PersistenceLayerException.class)
    public void testInvoiceOrderPersistenceServerIsDown() throws PersistenceLayerException{
        Mockito.doThrow(new HttpServerErrorException(HttpStatus.BAD_GATEWAY)).when(restTemplate).exchange("http://localhost:8080/invoiceOrder", HttpMethod.PUT, new HttpEntity<>(orderDTONoError), OrderDTO.class);
        orderController.invoiceOrder(orderDTONoError);
        //Mockito.verify(restTemplate, Mockito.times(1)).exchange("http://localhost:8080/invoiceOrder", HttpMethod.PUT, new HttpEntity<>(orderDTONoError), OrderDTO.class);
    }

    @Test(expected = PersistenceLayerException.class)
    public void testInvoiceOrderPersistenceRestClientException() throws PersistenceLayerException{
        Mockito.doThrow(RestClientException.class).when(restTemplate).exchange("http://localhost:8080/invoiceOrder", HttpMethod.PUT, new HttpEntity<>(orderDTONoError), OrderDTO.class);
        orderController.invoiceOrder(orderDTONoError);
        //Mockito.verify(restTemplate, Mockito.times(1)).exchange("http://localhost:8080/invoiceOrder", HttpMethod.PUT, new HttpEntity<>(orderDTONoError), OrderDTO.class);
    }

    @Test
    public void testInvoiceOrderPersistencePositive() throws PersistenceLayerException{
        Mockito.doReturn(null).when(restTemplate).exchange("http://localhost:8080/invoiceOrder", HttpMethod.PUT, new HttpEntity<>(orderDTONoError), OrderDTO.class);
        orderController.invoiceOrder(orderDTONoError);
        //Mockito.verify(restTemplate, Mockito.times(1)).exchange("http://localhost:8080/invoiceOrder", HttpMethod.PUT, new HttpEntity<>(orderDTONoError), OrderDTO.class);
    }

    @AfterClass
    public static void teardown(){

        LOG.debug("invoice order teardown initiated");
    }
}
