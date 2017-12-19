import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.AssignmentController;
import at.ac.tuwien.sepm.assignment.group02.client.rest.AssignmentControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;

public class AssignmentControllerImplTest {

    @Mock
    RestTemplate restTemplate;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


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