import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.server.converter.OrderConverter;
import at.ac.tuwien.sepm.assignment.group02.server.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.OrderDAO;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.OrderDAOJDBC;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TaskDAO;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TaskDAOJDBC;
import at.ac.tuwien.sepm.assignment.group02.server.service.OrderService;
import at.ac.tuwien.sepm.assignment.group02.server.service.OrderServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.server.util.DBUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class createOrderServerSideTest {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private OrderService orderService;
    private OrderDAO orderDAO;
    private TaskDAO taskDAO;
    private Connection dbConnection;
    private OrderDTO orderDTO;
    private Order order;
    private Task task;
    private TaskDTO taskDTO;

    @Before
    public void beforeMethod() {
        dbConnection = DBUtil.getConnection();

        orderDAO = new OrderDAOJDBC(dbConnection);
        taskDAO = new TaskDAOJDBC(dbConnection);

        orderService = new OrderServiceImpl(orderDAO,taskDAO,new OrderConverter(),new TaskConverter());

        orderDTO = new OrderDTO();
        order = new Order();
        task = new Task();
        taskDTO = new TaskDTO();
    }

    @Test(expected = PersistenceLayerException.class)
    public void testInvalidOrderCausesPersistenceLayerException() throws PersistenceLayerException {
        order.setCustomerName("test");
        order.setCustomerAddress("test");
        orderDAO.createOrder(order);
    }

    @Test
    public void testValidOrderCreationPersistenceLayer() throws PersistenceLayerException {
        int size = databaseSizeOrders();
        order.setCustomerAddress("test");
        order.setCustomerName("test");
        order.setCustomerUID("test");
        orderDAO.createOrder(order);
        Assert.assertEquals(size + 1,databaseSizeOrders());
    }

    @Test(expected = PersistenceLayerException.class)
    public void testInvalidTaskCausesPersistenceLayerException() throws PersistenceLayerException {
        order.setCustomerName("test");
        order.setCustomerAddress("test");
        order.setCustomerUID("test");

        task.setDescription("test");

        order.addTask(task);
        orderDAO.createOrder(order);
    }

    @Test
    public void testValidTaskCreationPersistenceLayer() throws PersistenceLayerException {
        int size = databaseSizeTasks();
        order.setCustomerAddress("test");
        order.setCustomerName("test");
        order.setCustomerUID("test");

        task.setDescription("test");
        task.setFinishing("test");
        task.setLength(2);
        task.setPrice(3);
        task.setQuality("test");
        task.setWood_type("test");
        task.setWidth(1);
        task.setSize(4);
        task.setQuantity(5);

        order.addTask(task);
        orderDAO.createOrder(order);
        Assert.assertEquals(size + 1,databaseSizeTasks());
    }

    @Test(expected = ServiceLayerException.class)
    public void testInvalidOrderCausesServiceLayerException() throws ServiceLayerException {
        orderDTO.setCustomerName("test");
        orderDTO.setCustomerAddress("test");
        List<TaskDTO> taskList = new ArrayList<>();
        orderDTO.setTaskList(taskList);
        orderService.createOrder(orderDTO);
    }

    @Test
    public void testValidOrderCreationServiceLayer() throws ServiceLayerException,PersistenceLayerException {
        int size = databaseSizeOrders();
        orderDTO.setCustomerAddress("test");
        orderDTO.setCustomerName("test");
        orderDTO.setCustomerUID("test");
        List<TaskDTO> taskList = new ArrayList<>();
        orderDTO.setTaskList(taskList);
        orderService.createOrder(orderDTO);
        Assert.assertEquals(size + 1,databaseSizeOrders());
    }

    @Test(expected = ServiceLayerException.class)
    public void testInvalidTaskCausesServiceLayerException() throws ServiceLayerException {
        orderDTO.setCustomerName("test");
        orderDTO.setCustomerAddress("test");
        orderDTO.setCustomerUID("test");

        List<TaskDTO> taskList = new ArrayList<>();
        taskDTO.setDescription("test");
        taskList.add(taskDTO);

        orderDTO.setTaskList(taskList);
        orderService.createOrder(orderDTO);
    }

    @Test
    public void testValidTaskCreationServiceLayer() throws ServiceLayerException,PersistenceLayerException {
        int size = databaseSizeTasks();
        orderDTO.setCustomerAddress("test");
        orderDTO.setCustomerName("test");
        orderDTO.setCustomerUID("test");

        List<TaskDTO> taskList = new ArrayList<>();
        taskDTO.setDescription("test");
        taskDTO.setFinishing("test");
        taskDTO.setLength(2);
        taskDTO.setPrice(3);
        taskDTO.setQuality("test");
        taskDTO.setWood_type("test");
        taskDTO.setWidth(1);
        taskDTO.setSize(4);
        taskDTO.setQuantity(5);
        taskList.add(taskDTO);

        orderDTO.setTaskList(taskList);
        orderService.createOrder(orderDTO);
        Assert.assertEquals(size + 1,databaseSizeTasks());
    }

    @Test(expected = PersistenceLayerException.class)
    public void testPersistenceLayerExceptionWhenDatabaseNotAvaiable() throws PersistenceLayerException {
        DBUtil.closeConnection();
        orderDAO.createOrder(order);
    }

    @After
    public void tearDown() {
        DBUtil.closeConnection();
        orderService = null;
        taskDAO = null;
        orderDAO = null;

        task = null;
        order = null;
        taskDTO = null;
        orderDTO = null;
    }

    private int databaseSizeOrders() throws PersistenceLayerException {
        String getCount = "SELECT COUNT(*) FROM ORDERS";
        int count = -1;
        try{
            PreparedStatement stmt = dbConnection.prepareStatement(getCount);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                count = rs.getInt(1);
        } catch (SQLException e) {
          throw new PersistenceLayerException("Database Problems");
        }
        return count;
    }

    private int databaseSizeTasks() throws PersistenceLayerException {
        String getCount = "SELECT COUNT(*) FROM Task";
        int count = -1;
        try{
            PreparedStatement stmt = dbConnection.prepareStatement(getCount);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                count = rs.getInt(1);
        } catch (SQLException e) {
            throw new PersistenceLayerException("Database Problems" + e.getMessage());
        }
        return count;
    }
}
