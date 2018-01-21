package serviceLayer;

import at.ac.tuwien.sepm.assignment.group02.client.converter.OrderConverter;
import at.ac.tuwien.sepm.assignment.group02.client.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.OrderController;
import at.ac.tuwien.sepm.assignment.group02.client.rest.OrderControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.client.service.OrderService;
import at.ac.tuwien.sepm.assignment.group02.client.service.OrderServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.client.validation.PrimitiveValidator;
import at.ac.tuwien.sepm.assignment.group02.client.validation.Validator;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

public class OrderClientServiceImplTest {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private OrderService orderService;
    @Mock
    private OrderController orderController;
    private static OrderConverter orderConverter;
    private Order order;

    @BeforeClass
    public static void setup(){
        LOG.debug("add timber test initiated");

        orderConverter = new OrderConverter();

    }

    @Before
    public void before(){
        //create a valid order
        order = new Order();
        order.setID(1);
        order.setCustomerAddress("Wien");
        order.setCustomerName("Max");
        order.setPaid(false);
        order.setCustomerUID("ATU123");
        ArrayList<Task> taskList = new ArrayList<>();
        Task task = new Task();
        task.setId(1);
        task.setPrice(2);
        task.setFinishing("roh");
        task.setLength(3500);
        task.setQuality("I");
        task.setWood_type("Ta");
        task.setDescription("Latten");
        task.setSize(30);
        task.setWidth(30);
        task.setProduced_quantity(10);
        task.setQuantity(30);
        taskList.add(task);
        order.setTaskList(taskList);
        order.setOrderDate("2018-01-01 12:12:12.1");

        orderController =  mock(OrderControllerImpl.class);
        PrimitiveValidator primitiveValidator = new PrimitiveValidator();
        Validator validator = new Validator(primitiveValidator);
        orderService = new OrderServiceImpl(orderController,new OrderConverter(),new TaskConverter(),validator);
    }

    @Test
    public void invoiceOrderPositiveTest() throws Exception {
        LOG.debug("invoice order positive test");

        orderService.invoiceOrder(order);
        Mockito.verify(orderController, times(1)).invoiceOrder(any(OrderDTO.class));
    }

    @Test(expected = ServiceLayerException.class)
    public void invoiceOrderAlreadyPaid() throws Exception{
        LOG.debug("invoice order already paid");
        order.setPaid(true);
        orderService.invoiceOrder(order);
        Mockito.verify(orderController, times(1)).invoiceOrder(any(OrderDTO.class));
    }

    @Test(expected = ServiceLayerException.class)
    public void invoiceOrderRestLayerException() throws Exception{
        LOG.debug("invoice order catch persistence layer exception");
        doThrow(PersistenceLayerException.class).when(orderController).invoiceOrder(any(OrderDTO.class));

        orderService.invoiceOrder(order);
        Mockito.verify(orderController, times(1)).invoiceOrder(any(OrderDTO.class));
    }

    @Test(expected = ServiceLayerException.class)
    public void getAllOpenOrdersRestLayerException() throws Exception{
        LOG.debug("get all open orders persistence layer exception");
        doThrow(PersistenceLayerException.class).when(orderController).getAllOpen();

        orderService.getAllOpen();
        Mockito.verify(orderController, times(1)).getAllOpen();
    }

    @Test
    public void getAllOpenOrdersExpectedNumberFalse() throws Exception{
        LOG.debug("get all open orders expected number false");

        List<Order> orderList = new LinkedList<>();
        orderList.add(order);
        orderList.add(order);

        List<OrderDTO> orderListDTO = new ArrayList<>();
        OrderDTO orderDTO = orderConverter.convertPlainObjectToRestDTO(order);
        TaskConverter taskConverter = new TaskConverter();
        TaskDTO taskDTO = taskConverter.convertPlainObjectToRestDTO(order.getTaskList().get(0));
        List<TaskDTO> taskDTOList = new ArrayList<>();
        taskDTOList.add(taskDTO);
        orderDTO.setTaskList(taskDTOList);

        orderListDTO.add(orderDTO);
        Mockito.when(orderController.getAllOpen()).thenReturn(orderListDTO);

        assertNotEquals(orderList.toString(), orderService.getAllOpen().toString());
        Mockito.verify(orderController, times(1)).getAllOpen();
    }

    @Test
    public void getAllOpenOrdersPositive() throws Exception{
        LOG.debug("get all open orders persistence layer exception");

        List<Order> orderList = new LinkedList<>();
        orderList.add(order);

        List<OrderDTO> orderListDTO = new ArrayList<>();
        OrderDTO orderDTO = orderConverter.convertPlainObjectToRestDTO(order);
        TaskConverter taskConverter = new TaskConverter();
        TaskDTO taskDTO = taskConverter.convertPlainObjectToRestDTO(order.getTaskList().get(0));
        List<TaskDTO> taskDTOList = new ArrayList<>();
        taskDTOList.add(taskDTO);
        orderDTO.setTaskList(taskDTOList);

        orderListDTO.add(orderDTO);
        Mockito.when(orderController.getAllOpen()).thenReturn(orderListDTO);

        assertEquals(orderList.toString(), orderService.getAllOpen().toString());
        Mockito.verify(orderController, times(1)).getAllOpen();
    }

    @Test(expected = ServiceLayerException.class)
    public void getAllClosedOrdersRestLayerException() throws Exception{
        LOG.debug("get all open orders persistence layer exception");
        order.setPaid(true);
        doThrow(PersistenceLayerException.class).when(orderController).getAllClosed();

        orderService.getAllClosed();
        Mockito.verify(orderController, times(1)).getAllClosed();
    }

    @Test
    public void getAllClosedOrdersExpectedNumberFalse() throws Exception{
        LOG.debug("get all open orders expected number false");
        order.setPaid(true);
        List<Order> orderList = new LinkedList<>();
        orderList.add(order);
        orderList.add(order);

        List<OrderDTO> orderListDTO = new ArrayList<>();
        OrderDTO orderDTO = orderConverter.convertPlainObjectToRestDTO(order);
        TaskConverter taskConverter = new TaskConverter();
        TaskDTO taskDTO = taskConverter.convertPlainObjectToRestDTO(order.getTaskList().get(0));
        List<TaskDTO> taskDTOList = new ArrayList<>();
        taskDTOList.add(taskDTO);
        orderDTO.setTaskList(taskDTOList);

        orderListDTO.add(orderDTO);
        Mockito.when(orderController.getAllClosed()).thenReturn(orderListDTO);

        assertNotEquals(orderList.toString(), orderService.getAllClosed().toString());
        Mockito.verify(orderController, times(1)).getAllClosed();
    }

    @Test
    public void getAllOpenClosedPositive() throws Exception{
        LOG.debug("get all open orders persistence layer exception");
        order.setPaid(true);
        List<Order> orderList = new LinkedList<>();
        orderList.add(order);

        List<OrderDTO> orderListDTO = new ArrayList<>();
        OrderDTO orderDTO = orderConverter.convertPlainObjectToRestDTO(order);
        TaskConverter taskConverter = new TaskConverter();
        TaskDTO taskDTO = taskConverter.convertPlainObjectToRestDTO(order.getTaskList().get(0));
        List<TaskDTO> taskDTOList = new ArrayList<>();
        taskDTOList.add(taskDTO);
        orderDTO.setTaskList(taskDTOList);

        orderListDTO.add(orderDTO);
        Mockito.when(orderController.getAllClosed()).thenReturn(orderListDTO);

        assertEquals(orderList.toString(), orderService.getAllClosed().toString());
        Mockito.verify(orderController, times(1)).getAllClosed();
    }


    @After
    public void after(){
        order = null;
    }

    @AfterClass
    public static void teardown(){

    }
}
