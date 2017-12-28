import at.ac.tuwien.sepm.assignment.group02.server.entity.Assignment;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.EntityNotFoundException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.AssignmentDAO;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.AssignmentDAOJDBC;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

// @RunWith attach a runner to initialize the test data
@RunWith(MockitoJUnitRunner.class)
public class AssignmentServerDAOJDBCTest_setDone {

    @Mock
    private Connection dbConnection;

    @Test
    public void testSetAssignmentDone_works() throws Exception {
        AssignmentDAO assignmentDAO
                = new AssignmentDAOJDBC(dbConnection);

        PreparedStatement mockPreparedStatement = Mockito.mock(PreparedStatement.class);

        Assignment assignment = new Assignment();

        doReturn(mockPreparedStatement).when(dbConnection).prepareStatement(any(String.class));
        doReturn(1).when(mockPreparedStatement).executeUpdate();

        assignmentDAO.setAssignmentDone(assignment);

        verify(dbConnection, times(1)).prepareStatement(any(String.class));
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test(expected = PersistenceLayerException.class)
    public void testSetDone_SQLException() throws Exception {
        AssignmentDAO assignmentDAO
                = new AssignmentDAOJDBC(dbConnection);

        PreparedStatement mockPreparedStatement = Mockito.mock(PreparedStatement.class);

        Assignment assignment = new Assignment();

        doReturn(mockPreparedStatement).when(dbConnection).prepareStatement(any(String.class));
        doThrow(SQLException.class).when(mockPreparedStatement).executeUpdate();

        assignmentDAO.setAssignmentDone(assignment);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testSetDone_EntityNotFound() throws Exception {
        AssignmentDAO assignmentDAO
                = new AssignmentDAOJDBC(dbConnection);

        PreparedStatement mockPreparedStatement = Mockito.mock(PreparedStatement.class);

        Assignment assignment = new Assignment();

        doReturn(mockPreparedStatement).when(dbConnection).prepareStatement(any(String.class));
        doReturn(0).when(mockPreparedStatement).executeUpdate();

        assignmentDAO.setAssignmentDone(assignment);
    }

}