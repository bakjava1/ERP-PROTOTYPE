import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLevelException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.OrderManagementDAO;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.OrderManagementDAOJDBC;
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

public class OrderManagementTest {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private Connection dbConnection;

    private OrderService orderService;
    private OrderManagementDAO orderManagementDAO;

    @Before
    public void setup() {
        LOG.debug("test setup initiated");
        dbConnection = DBUtil.getConnection();

        orderService = new OrderServiceImpl(new OrderManagementDAOJDBC(dbConnection));
        LOG.debug("test setup completed");
    }

    @Test
    public void testdeleteOrder() {
        LOG.debug("testing for order deletion");

        //TODO add test cases
        orderService.deleteOrder(1);

        int orderCountAfterDeletion = getActiveOrderByOrderID(1);
        //int taskCountAfterDeletion = getActiveTaskByOrderID(1);

        Assert.assertEquals("The number of active orders should be 0",0, orderCountAfterDeletion);
        //Assert.assertEquals("The number of active tasks should be 0",0, taskCountAfterDeletion);
    }

    @After
    public void teardown() {
        LOG.debug("test teardown initiated");

        DBUtil.closeConnection();

        LOG.debug("test teardown completed");
    }





    private int getActiveOrderByOrderID(int id) {
        String getOrder = "SELECT COUNT(ID) FROM ORDERS WHERE ID = ? AND DELETED = 0";

        return getActiveOrderTask(getOrder, id);
    }

    private int getActiveTaskByOrderID(int id) {
        String getTask = "SELECT COUNT(ID) FROM TASK WHERE ORDERID = ? AND DELETED = 0";

        return getActiveOrderTask(getTask, id);
    }

    private int getActiveOrderTask(String sql, int id) {
        int count = 0;

        try {
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setInt(1, id);
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
