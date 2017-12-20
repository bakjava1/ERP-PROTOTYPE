import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.AssignmentController;
import at.ac.tuwien.sepm.assignment.group02.client.rest.AssignmentControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

// @RunWith attach a runner to initialize the test data
@RunWith(MockitoJUnitRunner.class)
public class AssignmentClientControllerImplTest_setDone {

    @Mock
    private RestTemplate restTemplate;


    @Test
    public void testSetDone_works() throws PersistenceLayerException {
        AssignmentController assignmentController = new AssignmentControllerImpl(restTemplate);

        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentController.setDone(assignmentDTO);

        verify(restTemplate, times(1)).put(any(), any(AssignmentDTO.class), eq(AssignmentDTO.class));
    }

    @Test(expected = PersistenceLayerException.class)
    public void testSetDone_reactsToRestClientException() throws PersistenceLayerException {
        AssignmentController assignmentController = new AssignmentControllerImpl(restTemplate);

        doThrow(RestClientException.class)
                .when(restTemplate).put(any(), any(AssignmentDTO.class), eq(AssignmentDTO.class));

        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentController.setDone(assignmentDTO);
    }

    @Test(expected = PersistenceLayerException.class)
    public void testSetDone_reactsTo404() throws PersistenceLayerException {
        AssignmentController assignmentController = new AssignmentControllerImpl(restTemplate);

        AssignmentDTO assignmentDTO = new AssignmentDTO();
        doThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND))
                .when(restTemplate).put(any(), any(AssignmentDTO.class), eq(AssignmentDTO.class));

        assignmentController.setDone(assignmentDTO);
    }
}