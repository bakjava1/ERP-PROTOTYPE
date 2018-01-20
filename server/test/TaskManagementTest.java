import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.server.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TaskDAO;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TaskDAOJDBC;
import at.ac.tuwien.sepm.assignment.group02.server.rest.TaskControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.server.service.TaskService;
import at.ac.tuwien.sepm.assignment.group02.server.service.TaskServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.server.util.DBUtil;
import at.ac.tuwien.sepm.assignment.group02.server.validation.PrimitiveValidator;
import at.ac.tuwien.sepm.assignment.group02.server.validation.ValidateTask;
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

public class TaskManagementTest {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static Connection dbConnection;
    private static TaskDAO taskDAO;
    private static Task task1 = new Task();
    private static Task task2 = new Task();
    private static Task task3 = new Task();
    private static TaskDTO taskDTO1 = new TaskDTO();
    private static TaskDTO taskDTO2 = new TaskDTO();

    private static TaskControllerImpl taskController;
    private static TaskService taskService;
    private static TaskConverter taskConverter;
    private static ValidateTask validateTask;

    @BeforeClass
    public static void setup() {

        dbConnection = DBUtil.getConnection();
        taskDAO = new TaskDAOJDBC(dbConnection);

        activateTasks();
        task1.setId(1);
        task1.setOrder_id(1);
        task2.setId(2);
        task2.setOrder_id(1);

        task3.setId(3);
        task3.setOrder_id(2);
        task3.setDescription("Latten");
        task3.setFinishing("Prismiert");
        task3.setWood_type("Ta");
        task3.setQuality("I/III");
        task3.setSize(22);
        task3.setWidth(48);
        task3.setLength(3000);
        task3.setQuantity(40);

        taskDTO1.setId(4);
        taskDTO1.setOrder_id(3);
        taskDTO1.setDescription("Latten");
        taskDTO1.setFinishing("Prismiert");
        taskDTO1.setWood_type("Ta");
        taskDTO1.setQuality("I/III");
        taskDTO1.setSize(22);
        taskDTO1.setWidth(48);
        taskDTO1.setLength(3000);
        taskDTO1.setQuantity(40);

        taskDTO2.setId(5);
        taskDTO2.setOrder_id(3);
        taskDTO2.setDescription("Latten");
        taskDTO2.setFinishing("Prismiert");
        taskDTO2.setWood_type("Ta");
        taskDTO2.setQuality("I/III");
        taskDTO2.setSize(22);
        taskDTO2.setWidth(48);
        taskDTO2.setLength(3000);
        taskDTO2.setQuantity(40);

        validateTask = new ValidateTask(new PrimitiveValidator());
        taskConverter = new TaskConverter();
        taskService = new TaskServiceImpl(taskDAO, taskConverter, validateTask);
        taskController = new TaskControllerImpl(taskService);

        LOG.debug("task management test setup initiated");
    }

    @Before
    public void initDBConnection() {
        dbConnection = DBUtil.getConnection();
        taskDAO = new TaskDAOJDBC(dbConnection);

        taskConverter = new TaskConverter();
        taskService = new TaskServiceImpl(taskDAO, taskConverter, validateTask);
        taskController = new TaskControllerImpl(taskService);
    }

    @Test (expected = PersistenceLayerException.class)
    public void deleteTask_throws_Exception_in_persistenceLayer_without_DBConnection() throws PersistenceLayerException {

        LOG.debug("testing for task deletion in persistence layer without DB connection");

        DBUtil.closeConnection();
        taskDAO.deleteTask(task1);
    }

    //TODO: rework
    /*@Ignore
    @Test
    public void testDeleteTask_server_persistenceLayer() throws PersistenceLayerException {
        LOG.debug("testing for task deletion in server persistence layer");

        int taskCountBeforeDeletion = getActiveTasks();

        taskDAO.deleteTask(task1);
        taskCountBeforeDeletion--;
        taskDAO.deleteTask(task2);
        taskCountBeforeDeletion--;

        int taskCountAfterDeletion = getActiveTasks();

        assertEquals(taskCountBeforeDeletion,taskCountAfterDeletion);
    }

    @Ignore
    @Test
    public void testDeleteTask_reduceReservation_server_persistenceLayer() throws PersistenceLayerException {
        LOG.debug("testing for reducing amount of reservation in server persistence layer");

        int reserverationAmountBeforeDeletion = getReservationAmount(task3.getDescription(), task3.getFinishing(), task3.getWood_type(), task3.getQuality(), task3.getSize(), task3.getWidth(), task3.getLength());
        taskDAO.deleteTask(task3);
        reserverationAmountBeforeDeletion -= task3.getQuantity();

        int reserverationAmountAfterDeletion = getReservationAmount(task3.getDescription(), task3.getFinishing(), task3.getWood_type(), task3.getQuality(), task3.getSize(), task3.getWidth(), task3.getLength());

        assertEquals(reserverationAmountBeforeDeletion, reserverationAmountAfterDeletion);
    }

    @Ignore
    @Test
    public void testDeleteTask_server_restController() throws ServiceLayerException {
        LOG.debug("testing for task deletion in server rest controller");

        int taskCountBeforeDeletion = getActiveTasks();

        taskController.deleteTask(taskDTO1);
        taskCountBeforeDeletion--;
        taskController.deleteTask(taskDTO2);
        taskCountBeforeDeletion--;

        int taskCountAfterDeletion = getActiveTasks();

        assertEquals(taskCountBeforeDeletion,taskCountAfterDeletion);
    }*/

    @AfterClass
    public static void teardown() {
        LOG.debug("task management test teardown initiated");

        DBUtil.closeConnection();

        LOG.debug("task management test teardown completed");
    }

    private static void activateTasks() {
        String activateTasks = "UPDATE TASK SET DELETED = 0 WHERE ID = 1 OR ID = 2 OR ID = 3 OR ID = 4 OR ID = 5";

        try {
            PreparedStatement ps = dbConnection.prepareStatement(activateTasks);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            System.out.println("error at preparing test cases for deleting tasks");
        }
    }

    private int getActiveTasks() {
        int count = 0;
        String getTask = "SELECT COUNT(ID) FROM TASK WHERE DELETED = 0";

        try {
            PreparedStatement ps = dbConnection.prepareStatement(getTask);
            ps.execute();

            ResultSet rs = ps.getResultSet();
            rs.next();

            count = rs.getInt(1);

            rs.close();
            ps.close();

            return count;
        } catch (SQLException e) {
            System.out.println("error at testing for deleting tasks " + e.getMessage());
        }

        return count;
    }

    private int getReservationAmount(String description, String finishing, String wood_type, String quality, int size, int width, int length) {
        int amount = 0;
        String getReservationAmount = "SELECT RESERVED_QUANTITY FROM LUMBER WHERE DESCRIPTION = ? AND FINISHING = ? AND WOOD_TYPE = ? AND QUALITY = ? AND SIZE = ? AND WIDTH = ? AND LENGTH = ?";

        try {
            PreparedStatement ps = dbConnection.prepareStatement(getReservationAmount);
            ps.setString(1, description);
            ps.setString(2, finishing);
            ps.setString(3, wood_type);
            ps.setString(4, quality);
            ps.setInt(5, size);
            ps.setInt(6, width);
            ps.setInt(7, length);
            ps.execute();

            ps.getResultSet().next();
            amount = ps.getResultSet().getInt("RESERVED_QUANTITY");
            ps.close();

            return amount;
        } catch (SQLException e) {
            System.out.println("error at testing for deleting tasks and reduce reserved lumber amount " + e.getMessage());
        }

        return amount;
    }
}
