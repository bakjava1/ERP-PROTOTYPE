package persistenceLayer;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import at.ac.tuwien.sepm.assignment.group02.server.converter.LumberConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.EntityNotFoundException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.LumberDAO;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.LumberDAOJDBC;
import at.ac.tuwien.sepm.assignment.group02.server.service.LumberService;
import at.ac.tuwien.sepm.assignment.group02.server.service.LumberServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.server.service.OrderService;
import at.ac.tuwien.sepm.assignment.group02.server.service.OrderServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.server.validation.ValidateLumber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Created by raquelsima on 15.01.18.
 */

@RunWith(MockitoJUnitRunner.class)
public class LumberServerDAOJDBCTest {


    @Mock
    private Connection dbConnection;
    @Mock
    private LumberDAO lumberManagerDAO;
    @Mock
    private ValidateLumber validateLumber;

    @Test
    public void testUpdate_Lumber_valid() throws Exception {
        LumberDAO lumberDAO = new LumberDAOJDBC(dbConnection);
        PreparedStatement mockPreparedStatement = Mockito.mock(PreparedStatement.class);
        Lumber lumber = new Lumber();
        doReturn(mockPreparedStatement).when(dbConnection).prepareStatement(any(String.class));
        doReturn(1).when(mockPreparedStatement).executeUpdate();
        lumberDAO.updateLumber(lumber);
        verify(dbConnection, times(1)).prepareStatement(any(String.class));
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test(expected = PersistenceLayerException.class)
    public void testUpdate_Lumber_SQLException() throws Exception {
        LumberDAO assignmentDAO = new LumberDAOJDBC(dbConnection);
        PreparedStatement mockPreparedStatement = Mockito.mock(PreparedStatement.class);
        Lumber lumber = new Lumber();
        doReturn(mockPreparedStatement).when(dbConnection).prepareStatement(any(String.class));
        doThrow(SQLException.class).when(mockPreparedStatement).executeUpdate();
        assignmentDAO.updateLumber(lumber);
    }

   /* @Test
    public void testDelete_Lumber_persists() throws Exception {
        LumberDAO lumberDAO = new LumberDAOJDBC(dbConnection);
        PreparedStatement mockPreparedStatement = Mockito.mock(PreparedStatement.class);
        Lumber lumber = new Lumber();
        doReturn(mockPreparedStatement).when(dbConnection).prepareStatement(any(String.class));
        doReturn(1).when(mockPreparedStatement).executeUpdate();
        lumberDAO.deleteLumber(lumber);
        verify(dbConnection, times(1)).prepareStatement(any(String.class));
        verify(mockPreparedStatement).executeUpdate();
    }*/


    @Test
    public void testRemove_Lumber_valid() throws Exception {

        Lumber lumber=new Lumber();

         if(validateLumber.isValid(lumber)){
             return;
         }
        lumberManagerDAO.deleteLumber(lumber);

        verify(lumberManagerDAO,times(1)).deleteLumber(any(Lumber.class));
    }

}
