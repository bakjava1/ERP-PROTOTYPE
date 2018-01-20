import at.ac.tuwien.sepm.assignment.group02.client.converter.OrderConverter;
import at.ac.tuwien.sepm.assignment.group02.client.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.OrderController;
import at.ac.tuwien.sepm.assignment.group02.client.rest.OrderControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.client.service.OrderService;
import at.ac.tuwien.sepm.assignment.group02.client.service.OrderServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.client.validation.PrimitiveValidator;
import at.ac.tuwien.sepm.assignment.group02.client.validation.Validator;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import org.junit.*;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.*;
import static org.mockito.Matchers.*;

import java.util.ArrayList;

import static org.mockito.Mockito.times;

public class createOrderClientSideTest {

    private OrderService orderService;
    private RestTemplate restTemplate;
    private OrderController orderController;
    private static OrderConverter orderConverter;
    private Order order;
    private OrderDTO orderDTO;

    @BeforeClass
    public static void setUp() {
        orderConverter = new OrderConverter();
    }

    @Before
    public void beforeMethod() {
        order = new Order();
        orderDTO = new OrderDTO();
        restTemplate = Mockito.mock(RestTemplate.class);
        orderController =  new OrderControllerImpl(restTemplate);
        PrimitiveValidator primitiveValidator = new PrimitiveValidator();
        Validator validator = new Validator(primitiveValidator);
        orderService = new OrderServiceImpl(orderController,new OrderConverter(),new TaskConverter(),validator);
    }

    @Test(expected = PersistenceLayerException.class)
    public void createOrderFailedCauseOfServerConnectionProblems() throws PersistenceLayerException {
        Mockito.when(restTemplate.postForObject("http://localhost:8080/createOrder", orderDTO, OrderDTO.class)).thenThrow(new HttpServerErrorException(HttpStatus.BAD_GATEWAY));
        orderController.createOrder(orderDTO);
        Mockito.verify(restTemplate, times(1)).postForObject("http://localhost:8080/createOrder", orderDTO, OrderDTO.class);
    }

    @Test(expected = PersistenceLayerException.class)
    public void createOrderFailedCauseOfServerDownProblems() throws PersistenceLayerException {
        Mockito.when(restTemplate.postForObject("http://localhost:8080/createOrder", orderDTO, OrderDTO.class)).thenThrow(RestClientException.class);
        orderController.createOrder(orderDTO);
        Mockito.verify(restTemplate, times(1)).postForObject("http://localhost:8080/createOrder", orderDTO, OrderDTO.class);
    }

    @Test
    public void createOrderSuccessFull() throws PersistenceLayerException {
        orderDTO.setCustomerName("test");
        orderDTO.setCustomerAddress("test");
        orderDTO.setCustomerUID("test");
        orderController.createOrder(orderDTO);
        Mockito.verify(restTemplate, times(1)).postForObject(anyString(), any(OrderDTO.class), eq(OrderDTO.class));
    }

    @Test(expected = ServiceLayerException.class)
    public void createOrderFailedCauseOfServerConnectionProblemsServiceLayer() throws ServiceLayerException {
        order.setCustomerName("test");
        order.setCustomerAddress("test");
        order.setCustomerUID("test");
        Mockito.when(restTemplate.postForObject(anyString(), any(OrderDTO.class), eq(OrderDTO.class))).thenThrow(new HttpServerErrorException(HttpStatus.BAD_GATEWAY));
        orderService.addOrder(order,new ArrayList<>());
        Mockito.verify(restTemplate, times(1)).postForObject("http://localhost:8080/createOrder", orderDTO, OrderDTO.class);
    }

    @Test(expected = ServiceLayerException.class)
    public void createOrderFailedCauseOfServerDownProblemsServiceLayer() throws ServiceLayerException {
        Mockito.when(restTemplate.postForObject(anyString(), any(OrderDTO.class), eq(OrderDTO.class))).thenThrow(RestClientException.class);
        order.setCustomerName("test");
        order.setCustomerAddress("test");
        order.setCustomerUID("test");
        orderService.addOrder(order,new ArrayList<>());
        Mockito.verify(restTemplate, times(1)).postForObject("http://localhost:8080/createOrder", orderDTO, OrderDTO.class);
    }

    @Test
    public void createOrderSuccessFullServiceLayer() throws ServiceLayerException {
        order.setCustomerName("test");
        order.setCustomerAddress("test");
        order.setCustomerUID("test");
        orderService.addOrder(order,new ArrayList<>());
        Mockito.verify(restTemplate, times(1)).postForObject(anyString(), any(OrderDTO.class), eq(OrderDTO.class));
    }

    @After
    public void afterMethod() {
        order = null;
        orderDTO = null;
        restTemplate = null;
        orderController = null;
        orderService = null;
    }

    @AfterClass
    public static void tearDown() {
        orderConverter = null;
    }
}
