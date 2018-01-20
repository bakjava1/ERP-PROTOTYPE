package restLayer;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.AssignmentController;
import at.ac.tuwien.sepm.assignment.group02.client.rest.AssignmentControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

// @RunWith attach a runner to initialize the test data
@RunWith(MockitoJUnitRunner.class)
public class AssignmentClientControllerImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void testCreateAssignment_works() throws PersistenceLayerException {
        AssignmentController assignmentController = new AssignmentControllerImpl(restTemplate);
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentController.createAssignment(assignmentDTO);
        verify(restTemplate, times(1)).postForObject(
                Mockito.matches(".*createAssignment")
                , eq(assignmentDTO), eq(AssignmentDTO.class));
    }

    @Test(expected = PersistenceLayerException.class)
    public void testCreateAssignment_reactsToRestClientException() throws PersistenceLayerException {
        AssignmentController assignmentController = new AssignmentControllerImpl(restTemplate);
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        doThrow(RestClientException.class)
                .when(restTemplate).postForObject(Mockito.matches(".*createAssignment"), eq(assignmentDTO), eq(AssignmentDTO.class));
        assignmentController.createAssignment(assignmentDTO);
    }

    @Test(expected = PersistenceLayerException.class)
    public void testCreateAssignment_reactsTo404() throws PersistenceLayerException {
        AssignmentController assignmentController = new AssignmentControllerImpl(restTemplate);
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        doThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND))
                .when(restTemplate).postForObject(Mockito.matches(".*createAssignment"), eq(assignmentDTO), eq(AssignmentDTO.class));
        assignmentController.createAssignment(assignmentDTO);
    }

    @Test
    public void testGetAllOpenAssignments_works() throws PersistenceLayerException {
        AssignmentController assignmentController = new AssignmentControllerImpl(restTemplate);

        List<AssignmentDTO> assignmentList1 = new ArrayList<>();
        AssignmentDTO a1 = new AssignmentDTO();
        AssignmentDTO a2 = new AssignmentDTO();
        AssignmentDTO a3 = new AssignmentDTO();
        AssignmentDTO[] assignmentArray1 = {a1,a2,a3};
        assignmentList1.addAll(Arrays.asList(assignmentArray1));

        when(restTemplate.getForObject(Mockito.matches(".*getAllOpenAssignments"),eq(AssignmentDTO[].class))).thenReturn(assignmentArray1);

        List<AssignmentDTO> assignmentList2 = assignmentController.getAllOpenAssignments();

        verify(restTemplate, times(1))
                .getForObject(anyString(), eq(AssignmentDTO[].class));

        Assert.assertTrue(assignmentList1.equals(assignmentList2));
    }

    @Test(expected = PersistenceLayerException.class)
    public void testGetAllOpenAssignments_reactsToRestClientException() throws PersistenceLayerException {
        AssignmentController assignmentController = new AssignmentControllerImpl(restTemplate);
        doThrow(RestClientException.class)
                .when(restTemplate).getForObject(Mockito.matches(".*getAllOpenAssignments"),eq(AssignmentDTO[].class));
        assignmentController.getAllOpenAssignments();
    }

    @Test(expected = PersistenceLayerException.class)
    public void testGetAllOpenAssignments_reactsTo404() throws PersistenceLayerException {
        AssignmentController assignmentController = new AssignmentControllerImpl(restTemplate);
        doThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND))
                .when(restTemplate).getForObject(Mockito.matches(".*getAllOpenAssignments"),eq(AssignmentDTO[].class));
        assignmentController.getAllOpenAssignments();
    }

    @Test
    public void testGetAllAssignments_works() throws PersistenceLayerException {
        AssignmentController assignmentController = new AssignmentControllerImpl(restTemplate);

        List<AssignmentDTO> assignmentList1 = new ArrayList<>();
        AssignmentDTO a1 = new AssignmentDTO();
        AssignmentDTO a2 = new AssignmentDTO();
        AssignmentDTO a3 = new AssignmentDTO();
        AssignmentDTO[] assignmentArray1 = {a1,a2,a3};
        assignmentList1.addAll(Arrays.asList(assignmentArray1));

        when(restTemplate.getForObject(Mockito.matches(".*getAllClosedAssignments"),eq(AssignmentDTO[].class))).thenReturn(assignmentArray1);

        List<AssignmentDTO> assignmentList2 = assignmentController.getAllClosedAssignments();

        verify(restTemplate, times(1))
                .getForObject(anyString(), eq(AssignmentDTO[].class));

        Assert.assertTrue(assignmentList1.equals(assignmentList2));
    }

    @Test(expected = PersistenceLayerException.class)
    public void testGetAllAssignments_reactsToRestClientException() throws PersistenceLayerException {
        AssignmentController assignmentController = new AssignmentControllerImpl(restTemplate);
        doThrow(RestClientException.class)
                .when(restTemplate).getForObject(Mockito.matches(".*getAllClosedAssignments"),eq(AssignmentDTO[].class));
        assignmentController.getAllClosedAssignments();
    }

    @Test(expected = PersistenceLayerException.class)
    public void testGetAllAssignments_reactsTo404() throws PersistenceLayerException {
        AssignmentController assignmentController = new AssignmentControllerImpl(restTemplate);
        doThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND))
                .when(restTemplate).getForObject(Mockito.matches(".*getAllClosedAssignments"),eq(AssignmentDTO[].class));
        assignmentController.getAllClosedAssignments();
    }

    @Test
    public void testSetDone_works() throws PersistenceLayerException {
        AssignmentController assignmentController = new AssignmentControllerImpl(restTemplate);
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentController.setDone(assignmentDTO);
        verify(restTemplate, times(1))
                .exchange(Mockito.matches(".*setAssignmentDone"), eq(HttpMethod.PUT), any(HttpEntity.class), eq(AssignmentDTO.class));
    }

    @Test(expected = PersistenceLayerException.class)
    public void testSetDone_reactsToRestClientException() throws PersistenceLayerException {
        AssignmentController assignmentController = new AssignmentControllerImpl(restTemplate);
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        doThrow(RestClientException.class)
                .when(restTemplate).exchange(Mockito.matches(".*setAssignmentDone"), eq(HttpMethod.PUT), any(HttpEntity.class), eq(AssignmentDTO.class));
        assignmentController.setDone(assignmentDTO);
    }

    @Test(expected = PersistenceLayerException.class)
    public void testSetDone_reactsTo404() throws PersistenceLayerException {
        AssignmentController assignmentController = new AssignmentControllerImpl(restTemplate);

        AssignmentDTO assignmentDTO = new AssignmentDTO();
        doThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND))
                .when(restTemplate).exchange(Mockito.matches(".*setAssignmentDone"), eq(HttpMethod.PUT), any(HttpEntity.class), eq(AssignmentDTO.class));

        assignmentController.setDone(assignmentDTO);
    }
}