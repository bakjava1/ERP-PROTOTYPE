import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.*;
import at.ac.tuwien.sepm.assignment.group02.client.service.AssignmentService;
import at.ac.tuwien.sepm.assignment.group02.client.service.AssignmentServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.client.validation.ValidateAssignmentDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Incubating;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AssignmentManagementTest {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static AssignmentController assignmentController;
    private static AssignmentService assignmentService;
    private static ValidateAssignmentDTO validateAssignmentDTO;
    private static RestTemplate restTemplate;

    private static AssignmentDTO[] assignmentDTO;

    @BeforeClass
    public static void setup() {
        LOG.debug("assignment management test setup initiated");

        restTemplate = mock(RestTemplate.class);
        assignmentController = new AssignmentControllerImpl(restTemplate);
        validateAssignmentDTO = mock(ValidateAssignmentDTO.class);

        assignmentService = new AssignmentServiceImpl(
                 assignmentController,
                 validateAssignmentDTO);

        assignmentDTO = new AssignmentDTO[0];

        LOG.debug("assignment management test setup completed");
    }

    @Ignore
    @Test
    public void testAssignmentOverview_client_serviceLayer_EmptyList() throws PersistenceLayerException, ServiceLayerException {
        LOG.debug("testing for an empty assignment overview in client service layer");
        List<AssignmentDTO> assignmentList = new ArrayList<>();

        when(assignmentController.getAllOpenAssignments()).thenReturn(assignmentList);
        assertEquals(0, assignmentService.getAllOpenAssignments().size());
    }

    @Ignore
    @Test
    public void testAssignmentOverview_client_serviceLayer() throws PersistenceLayerException, ServiceLayerException {
        LOG.debug("testing for assignment overview in client serice layer");

        AssignmentDTO[] assignmentArray = new AssignmentDTO[2];
        AssignmentDTO assignment1 = new AssignmentDTO();
        AssignmentDTO assignment2 = new AssignmentDTO();
        assignmentArray[0] = assignment1;
        assignmentArray[1] = assignment2;

        //when(assignmentController.getAllOpenAssignments()).thenReturn(assignmentList);
        when(restTemplate.getForObject(any(), eq(AssignmentDTO[].class))).thenReturn(assignmentArray);
        assertEquals(2, assignmentService.getAllOpenAssignments().size());
    }

    @Ignore
    @Test (expected = PersistenceLayerException.class)
    public void getAllOpenAssignments_throws_RestClientException() throws PersistenceLayerException, ServiceLayerException {
        LOG.debug("get all open assignments throws rest client exception in client rest interface");

        doThrow(RestClientException.class).when(restTemplate).getForObject(any(), eq(AssignmentDTO[].class));

        assignmentController.getAllOpenAssignments();
    }

    @Ignore
    @Test (expected = PersistenceLayerException.class)
    public void getAllOpenAssignments_throws_HttpStatusCodeException() throws PersistenceLayerException, ServiceLayerException {
        LOG.debug("get all open assignments throws http status code exception in client rest interface");

        //doThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND)).when(restTemplate).getForObject(any(), eq(assignmentDTO.getClass()));
        doThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND)).when(restTemplate).getForObject(any(), any());


        assignmentController.getAllOpenAssignments();
    }
}
