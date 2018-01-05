package serviceLayer;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.server.converter.OrderConverter;
import at.ac.tuwien.sepm.assignment.group02.server.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.OrderDAO;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TaskDAO;
import at.ac.tuwien.sepm.assignment.group02.server.service.OrderService;
import at.ac.tuwien.sepm.assignment.group02.server.service.OrderServiceImpl;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

// @RunWith attach a runner to initialize the test data
@RunWith(MockitoJUnitRunner.class)
public class OrderServerServiceImplTest {

    @Mock
    private TaskDAO taskManagementDAO;
    @Mock
    private TaskConverter taskConverter;

    @Mock
    private OrderDAO orderManagementDAO;
    @Mock
    private OrderConverter orderConverter;


    @Test
    public void testDeleteOrder_works() throws Exception {
        OrderService orderService
                = new OrderServiceImpl(orderManagementDAO,taskManagementDAO,orderConverter,taskConverter);

        Order order = new Order();
        when(orderConverter.convertRestDTOToPlainObject(any(OrderDTO.class))).thenReturn(order);

        orderService.deleteOrder(any(OrderDTO.class));

        verify(orderConverter,times(1)).convertRestDTOToPlainObject(any(OrderDTO.class));
        verify(orderManagementDAO,times(1)).deleteOrder(order);
    }

    @Ignore
    @Test(expected = InvalidInputException.class)
    public void testDeleteOrder_InvalidInputException() throws Exception {
        OrderService orderService
                = new OrderServiceImpl(orderManagementDAO,taskManagementDAO,orderConverter,taskConverter);

        //doThrow(InvalidInputException.class).when(validateOrder).isValid(any(Order.class));

        orderService.deleteOrder(any(OrderDTO.class));
    }

    @Test(expected = ServiceLayerException.class)
    public void testDeleteOrder_PersistenceLayerException() throws Exception {
        OrderService orderService
                = new OrderServiceImpl(orderManagementDAO,taskManagementDAO,orderConverter,taskConverter);

        Order order = new Order();
        when(orderConverter.convertRestDTOToPlainObject(any(OrderDTO.class))).thenReturn(order);

        doThrow(PersistenceLayerException.class).when(orderManagementDAO).deleteOrder(any(Order.class));

        orderService.deleteOrder(any(OrderDTO.class));
    }

    @Test
    public void testCreateOrder_works() throws Exception {
        OrderService orderService
                = new OrderServiceImpl(orderManagementDAO,taskManagementDAO,orderConverter,taskConverter);

        List<TaskDTO> taskList = new ArrayList<>();
        TaskDTO t1 = Mockito.mock(TaskDTO.class);
        TaskDTO t2 = Mockito.mock(TaskDTO.class);
        TaskDTO t3 = Mockito.mock(TaskDTO.class);
        taskList.add(t1);
        taskList.add(t2);
        taskList.add(t3);
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setTaskList(taskList);

        Order order = new Order();
        when(orderConverter.convertRestDTOToPlainObject(orderDTO)).thenReturn(order);

        orderService.createOrder(orderDTO);

        verify(orderConverter,times(1)).convertRestDTOToPlainObject(orderDTO);
        verify(taskConverter,times(3)).convertRestDTOToPlainObject(any(TaskDTO.class));
        verify(orderManagementDAO,times(1)).createOrder(order);

        Assert.assertTrue(order.getTaskList().size() == 3);

    }

    @Ignore
    @Test(expected = InvalidInputException.class)
    public void testCreateOrder_ValidationException() throws Exception {
        OrderService orderService
                = new OrderServiceImpl(orderManagementDAO,taskManagementDAO,orderConverter,taskConverter);

        List<TaskDTO> taskList = new ArrayList<>();
        TaskDTO t1 = Mockito.mock(TaskDTO.class);
        TaskDTO t2 = Mockito.mock(TaskDTO.class);
        TaskDTO t3 = Mockito.mock(TaskDTO.class);
        taskList.add(t1);
        taskList.add(t2);
        taskList.add(t3);
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setTaskList(taskList);

        Order order = new Order();
        when(orderConverter.convertRestDTOToPlainObject(orderDTO)).thenReturn(order);

//        doThrow(InvalidInputException.class).when(validateOrder).isValid(any(Order.class));

        orderService.createOrder(orderDTO);
        verify(orderConverter,times(1)).convertRestDTOToPlainObject(any(OrderDTO.class));

    }

    @Test(expected = ServiceLayerException.class)
    public void testCreateOrder_PersistenceLayerException() throws Exception {
        OrderService orderService
                = new OrderServiceImpl(orderManagementDAO,taskManagementDAO,orderConverter,taskConverter);

        List<TaskDTO> taskList = new ArrayList<>();
        TaskDTO t1 = Mockito.mock(TaskDTO.class);
        TaskDTO t2 = Mockito.mock(TaskDTO.class);
        TaskDTO t3 = Mockito.mock(TaskDTO.class);
        taskList.add(t1);
        taskList.add(t2);
        taskList.add(t3);
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setTaskList(taskList);

        Order order = new Order();
        when(orderConverter.convertRestDTOToPlainObject(orderDTO)).thenReturn(order);

        doThrow(PersistenceLayerException.class).when(orderManagementDAO).createOrder(any(Order.class));

        orderService.createOrder(orderDTO);
    }

    @Test
    public void testGetAllOpenOrders_returns2OpenOrders() throws Exception {
        OrderService orderService
                = new OrderServiceImpl(orderManagementDAO,taskManagementDAO,orderConverter,taskConverter);

        List<Order> allOrders = new ArrayList<>();

        Order l1 = Mockito.mock(Order.class);
        Order l2 = Mockito.mock(Order.class);
        allOrders.add(l1);
        allOrders.add(l2);
        when(orderManagementDAO.getAllOpen()).thenReturn(allOrders);

        List<OrderDTO> allOrdersConverted = orderService.getAllOpen();

        verify(orderManagementDAO, times(1)).getAllOpen();
        verify(orderConverter, times(2)).convertPlainObjectToRestDTO(any(Order.class));

        Assert.assertEquals(allOrdersConverted.size(),2);
    }

    @Test
    public void testGetAllOpenOrders_returnsNoOrder() throws Exception {
        OrderService orderService
                = new OrderServiceImpl(orderManagementDAO,taskManagementDAO,orderConverter,taskConverter);

        List<Order> allOrder = new ArrayList<>();
        when(orderManagementDAO.getAllOpen()).thenReturn(allOrder);

        List<OrderDTO> allOrderConverted = orderService.getAllOpen();

        verify(orderManagementDAO, times(1)).getAllOpen();
        verify(orderConverter, times(0)).convertPlainObjectToRestDTO(any(Order.class));

        Assert.assertEquals(allOrderConverted.size(),0);
    }

    @Test(expected = ServiceLayerException.class)
    public void testGetAllOpenOrders_PersistenceLayerException() throws Exception {
        OrderService orderService
                = new OrderServiceImpl(orderManagementDAO,taskManagementDAO,orderConverter,taskConverter);

        List<Order> allOrder = new ArrayList<>();
        when(orderManagementDAO.getAllOpen()).thenReturn(allOrder);

        doThrow(PersistenceLayerException.class).when(orderManagementDAO).getAllOpen();

        orderService.getAllOpen();
    }

    @Test
    public void testGetAllClosedOrders_returns2ClosedOrders() throws Exception {
        OrderService orderService
                = new OrderServiceImpl(orderManagementDAO,taskManagementDAO,orderConverter,taskConverter);

        List<Order> allOrders = new ArrayList<>();

        Order l1 = Mockito.mock(Order.class);
        Order l2 = Mockito.mock(Order.class);
        allOrders.add(l1);
        allOrders.add(l2);
        when(orderManagementDAO.getAllClosed()).thenReturn(allOrders);

        List<OrderDTO> allOrdersConverted = orderService.getAllClosed();

        verify(orderManagementDAO, times(1)).getAllClosed();
        verify(orderConverter, times(2)).convertPlainObjectToRestDTO(any(Order.class));

        Assert.assertEquals(allOrdersConverted.size(),2);
    }

    @Test
    public void testGetAllClosed_returnsNoOrder() throws Exception {
        OrderService orderService
                = new OrderServiceImpl(orderManagementDAO,taskManagementDAO,orderConverter,taskConverter);

        List<Order> allOrder = new ArrayList<>();
        when(orderManagementDAO.getAllClosed()).thenReturn(allOrder);

        List<OrderDTO> allOrderConverted = orderService.getAllClosed();

        verify(orderManagementDAO, times(1)).getAllClosed();
        verify(orderConverter, times(0)).convertPlainObjectToRestDTO(any(Order.class));

        Assert.assertEquals(allOrderConverted.size(),0);
    }

    @Test(expected = ServiceLayerException.class)
    public void testGetAllClosed_PersistenceLayerException() throws Exception {
        OrderService orderService
                = new OrderServiceImpl(orderManagementDAO,taskManagementDAO,orderConverter,taskConverter);

        List<Order> allOrder = new ArrayList<>();
        when(orderManagementDAO.getAllClosed()).thenReturn(allOrder);

        doThrow(PersistenceLayerException.class).when(orderManagementDAO).getAllClosed();

        orderService.getAllClosed();
    }

    @Test
    public void testGetOrderById_works() throws Exception {
        OrderService orderService
                = new OrderServiceImpl(orderManagementDAO,taskManagementDAO,orderConverter,taskConverter);

        Order order = Mockito.mock(Order.class);
        OrderDTO orderDTO = Mockito.mock(OrderDTO.class);

        when(orderManagementDAO.getOrderById(anyInt())).thenReturn(order);
        when(orderConverter.convertPlainObjectToRestDTO(order)).thenReturn(orderDTO);

        OrderDTO orderDTO1 = orderService.getOrderById(anyInt());

        verify(orderManagementDAO, times(1)).getOrderById(anyInt());
        verify(orderConverter,times(1)).convertPlainObjectToRestDTO(any(Order.class));

        Assert.assertSame(orderDTO, orderDTO1);
    }


    @Test(expected = ServiceLayerException.class)
    public void testGetOrderById_PersistenceLayerException() throws Exception {
        OrderService orderService
                = new OrderServiceImpl(orderManagementDAO,taskManagementDAO,orderConverter,taskConverter);

        doThrow(PersistenceLayerException.class).when(orderManagementDAO).getOrderById(anyInt());

        orderService.getOrderById(anyInt());
    }

    @Test
    public void testInvoiceOrder_works() throws Exception {
        OrderService orderService
                = new OrderServiceImpl(orderManagementDAO,taskManagementDAO,orderConverter,taskConverter);

        Order order = Mockito.mock(Order.class);
        when(orderConverter.convertRestDTOToPlainObject(any(OrderDTO.class))).thenReturn(order);

        orderService.invoiceOrder(any(OrderDTO.class));

        verify(orderConverter,times(1)).convertRestDTOToPlainObject(any(OrderDTO.class));
        verify(orderManagementDAO, times(1)).invoiceOrder(order);
    }

    @Test(expected = ServiceLayerException.class)
    public void testInvoiceOrder_PersistenceLayerException() throws Exception {
        OrderService orderService
                = new OrderServiceImpl(orderManagementDAO,taskManagementDAO,orderConverter,taskConverter);

        Order order = Mockito.mock(Order.class);
        when(orderConverter.convertRestDTOToPlainObject(any(OrderDTO.class))).thenReturn(order);

        doThrow(PersistenceLayerException.class).when(orderManagementDAO).invoiceOrder(order);

        orderService.invoiceOrder(any(OrderDTO.class));
    }

}