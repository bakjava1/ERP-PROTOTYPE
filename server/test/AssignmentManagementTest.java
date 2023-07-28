import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import at.ac.tuwien.sepm.assignment.group02.server.converter.AssignmentConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Assignment;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.AssignmentDAO;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.AssignmentDAOJDBC;
import at.ac.tuwien.sepm.assignment.group02.server.service.*;
import at.ac.tuwien.sepm.assignment.group02.server.util.DBUtil;
import at.ac.tuwien.sepm.assignment.group02.server.validation.ValidateAssignment;
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
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AssignmentManagementTest {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static Connection dbConnection;
    private static AssignmentDAO assignmentDAO;

    private static AssignmentService assignmentService;
    private static AssignmentConverter assignmentConverter;
    private static ValidateAssignment validateAssignment;
    private static TimberService timberService;
    private static LumberService lumberService;
    private static TaskService taskService;

    @BeforeClass
    public static void setup() {
        LOG.debug("assignment management test setup initiated");
        dbConnection = DBUtil.getConnection();
        assignmentDAO = new AssignmentDAOJDBC(dbConnection);
        assignmentConverter = new AssignmentConverter();

        assignmentService = new AssignmentServiceImpl(assignmentDAO, assignmentConverter, validateAssignment,
                timberService, lumberService, taskService);

        LOG.debug("assignment management test setup completed");
    }

    @Before
    public void initDBConnection() {
        dbConnection = DBUtil.getConnection();
        assignmentDAO = new AssignmentDAOJDBC(dbConnection);

        assignmentConverter = new AssignmentConverter();
        assignmentService = new AssignmentServiceImpl(assignmentDAO, assignmentConverter, validateAssignment,
                timberService, lumberService, taskService);
    }

    @Test (expected = PersistenceLayerException.class)
    public void getAllClosedAssignments_throws_Exception_in_persistenceLayer_withoutDB() throws PersistenceLayerException {
        LOG.debug("testing get all open assignments for exception when DB is not available");

        DBUtil.closeConnection();
        assignmentDAO.getAllClosedAssignments();
    }

    @Test
    public void testAssignmentOverview_server_persistenceLayer() throws PersistenceLayerException {
        LOG.debug("testing for assignment overview in server persistence layer");

        int activeAssignments = getActiveAssignments();

        List<Assignment> assignmentList = assignmentDAO.getAllOpenAssignments();

        assertEquals(activeAssignments, assignmentList.size());
    }

    @Test
    public void testAssignmentOverview_server_serviceLayer() throws ServiceLayerException {
        LOG.debug("testing for assignment overview in server service layer");

        int activeAssignments = getActiveAssignments();

        List<AssignmentDTO> assignmentList = assignmentService.getAllOpenAssignments();

        assertEquals(activeAssignments, assignmentList.size());
    }

    private int getActiveAssignments() {
        int count = 0;
        String getAssignment = "SELECT COUNT(ID) FROM ASSIGNMENT WHERE isDone = 0";

        try {
            PreparedStatement ps = dbConnection.prepareStatement(getAssignment);
            ps.execute();

            ResultSet rs = ps.getResultSet();
            rs.next();

            count = rs.getInt(1);

            rs.close();
            ps.close();

            return count;
        } catch (SQLException e) {
            System.out.println("error at testing for active assignments: " + e.getMessage());
        }

        return count;
    }
}
