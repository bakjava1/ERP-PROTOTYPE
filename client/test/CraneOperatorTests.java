import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.AssignmentControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.client.service.AssignmentService;
import at.ac.tuwien.sepm.assignment.group02.client.service.AssignmentServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.client.validation.PrimitiveValidator;
import at.ac.tuwien.sepm.assignment.group02.client.validation.ValidateAssignmentDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import org.junit.Assert;
import org.junit.BeforeClass;
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

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CraneOperatorTests {

    @Mock
    private RestTemplate restTemplate;

    @BeforeClass
    public static void setup(){

    }

    @Test
    public void cleanUpAssignments_works() throws ServiceLayerException {
        AssignmentControllerImpl assignmentController = new AssignmentControllerImpl(restTemplate);
        ValidateAssignmentDTO validateAssignmentDTO = new ValidateAssignmentDTO(new PrimitiveValidator());
        AssignmentService assignmentService = new AssignmentServiceImpl(assignmentController, validateAssignmentDTO);

        assignmentService.cleanUpAssignments();

        verify(restTemplate, times(1))
                .exchange(Mockito.matches(".*cleanUpAssignments"), eq(HttpMethod.PUT), eq(null), eq(AssignmentDTO.class));
    }

    @Test(expected = ServiceLayerException.class)
    public void cleanUpAssignments_exception() throws ServiceLayerException {
        AssignmentControllerImpl assignmentController = new AssignmentControllerImpl(restTemplate);
        ValidateAssignmentDTO validateAssignmentDTO = new ValidateAssignmentDTO(new PrimitiveValidator());
        AssignmentService assignmentService = new AssignmentServiceImpl(assignmentController, validateAssignmentDTO);

        doThrow(RestClientException.class)
                .when(restTemplate).exchange(Mockito.matches(".*cleanUpAssignments"), eq(HttpMethod.PUT), eq(null), eq(AssignmentDTO.class));

        assignmentService.cleanUpAssignments();
    }

    @Test
    public void getAllOpenAssignments_works() throws ServiceLayerException {
        AssignmentControllerImpl assignmentController = new AssignmentControllerImpl(restTemplate);
        ValidateAssignmentDTO validateAssignmentDTO = new ValidateAssignmentDTO(new PrimitiveValidator());
        AssignmentService assignmentService = new AssignmentServiceImpl(assignmentController, validateAssignmentDTO);

        List<AssignmentDTO> assignmentList1 = new ArrayList<>();
        AssignmentDTO a1 = new AssignmentDTO();
        AssignmentDTO a2 = new AssignmentDTO();
        AssignmentDTO a3 = new AssignmentDTO();
        AssignmentDTO[] assignmentArray1 = {a1,a2,a3};
        assignmentList1.addAll(Arrays.asList(assignmentArray1));

        when(restTemplate.getForObject(Mockito.matches(".*getAllOpenAssignments"),eq(AssignmentDTO[].class))).thenReturn(assignmentArray1);


        List<AssignmentDTO> allOpenAssignments = assignmentService.getAllOpenAssignments();

        Assert.assertEquals(allOpenAssignments, assignmentList1);
    }

    @Test(expected = ServiceLayerException.class)
    public void getAllOpenAssignments_exception() throws ServiceLayerException {
        AssignmentControllerImpl assignmentController = new AssignmentControllerImpl(restTemplate);
        ValidateAssignmentDTO validateAssignmentDTO = new ValidateAssignmentDTO(new PrimitiveValidator());
        AssignmentService assignmentService = new AssignmentServiceImpl(assignmentController, validateAssignmentDTO);

        doThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND))
                .when(restTemplate).getForObject(Mockito.matches(".*getAllOpenAssignments"),eq(AssignmentDTO[].class));

        assignmentService.getAllOpenAssignments();
    }

    @Test
    public void getAllClosedAssignments_works() throws ServiceLayerException {
        AssignmentControllerImpl assignmentController = new AssignmentControllerImpl(restTemplate);
        ValidateAssignmentDTO validateAssignmentDTO = new ValidateAssignmentDTO(new PrimitiveValidator());
        AssignmentService assignmentService = new AssignmentServiceImpl(assignmentController, validateAssignmentDTO);

        List<AssignmentDTO> assignmentList1 = new ArrayList<>();
        AssignmentDTO a1 = new AssignmentDTO();
        AssignmentDTO a2 = new AssignmentDTO();
        AssignmentDTO a3 = new AssignmentDTO();
        AssignmentDTO[] assignmentArray1 = {a1,a2,a3};
        assignmentList1.addAll(Arrays.asList(assignmentArray1));

        when(restTemplate.getForObject(Mockito.matches(".*getAllClosedAssignments"),eq(AssignmentDTO[].class))).thenReturn(assignmentArray1);


        List<AssignmentDTO> allClosedAssignments = assignmentService.getAllClosedAssignments();

        Assert.assertEquals(allClosedAssignments, assignmentList1);
    }

        @Test(expected = ServiceLayerException.class)
        public void getAllClosedAssignments_exception() throws ServiceLayerException {
            AssignmentControllerImpl assignmentController = new AssignmentControllerImpl(restTemplate);
            ValidateAssignmentDTO validateAssignmentDTO = new ValidateAssignmentDTO(new PrimitiveValidator());
            AssignmentService assignmentService = new AssignmentServiceImpl(assignmentController, validateAssignmentDTO);

            doThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND))
                    .when(restTemplate).getForObject(Mockito.matches(".*getAllClosedAssignments"),eq(AssignmentDTO[].class));

            assignmentService.getAllClosedAssignments();
        }

        @Test
        public void setDone_works() throws ServiceLayerException {
            AssignmentControllerImpl assignmentController = new AssignmentControllerImpl(restTemplate);
            ValidateAssignmentDTO validateAssignmentDTO = new ValidateAssignmentDTO(new PrimitiveValidator());
            AssignmentService assignmentService = new AssignmentServiceImpl(assignmentController, validateAssignmentDTO);

            AssignmentDTO assignmentDTO = new AssignmentDTO();
            assignmentDTO.setId(1);
            assignmentDTO.setBox_id(1);
            assignmentDTO.setAmount(1);
            assignmentDTO.setTask_id(2);
            assignmentDTO.setDone(false);

            assignmentService.setDone(assignmentDTO);

            verify(restTemplate, times(1))
                    .exchange(Mockito.matches(".*setAssignmentDone"), eq(HttpMethod.PUT), eq(new HttpEntity<>(assignmentDTO)), eq(AssignmentDTO.class));
        }

    @Test
    public void setDone_alreadyDone() throws ServiceLayerException {
        AssignmentControllerImpl assignmentController = new AssignmentControllerImpl(restTemplate);
        ValidateAssignmentDTO validateAssignmentDTO = new ValidateAssignmentDTO(new PrimitiveValidator());
        AssignmentService assignmentService = new AssignmentServiceImpl(assignmentController, validateAssignmentDTO);

        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setId(1);
        assignmentDTO.setBox_id(1);
        assignmentDTO.setAmount(1);
        assignmentDTO.setTask_id(2);
        assignmentDTO.setDone(true);

        try {
            assignmentService.setDone(assignmentDTO);
        } catch (ServiceLayerException e){
            Assert.assertTrue(e.getMessage().contains("Aufgabe ist bereits abgeschlossen"));
        }

        verify(restTemplate, times(0))
                .exchange(Mockito.matches(".*setAssignmentDone"), eq(HttpMethod.PUT), eq(new HttpEntity<>(assignmentDTO)), eq(AssignmentDTO.class));
    }

    @Test(expected = InvalidInputException.class)
    public void setDone_invalidAssignmentDTO() throws ServiceLayerException {
        AssignmentControllerImpl assignmentController = new AssignmentControllerImpl(restTemplate);
        ValidateAssignmentDTO validateAssignmentDTO = new ValidateAssignmentDTO(new PrimitiveValidator());
        AssignmentService assignmentService = new AssignmentServiceImpl(assignmentController, validateAssignmentDTO);

        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setId(1);
        assignmentDTO.setBox_id(901);
        assignmentDTO.setAmount(1);
        assignmentDTO.setTask_id(2);
        assignmentDTO.setDone(false);

        assignmentService.setDone(assignmentDTO);

        //exception is thrown before this
        //verify(restTemplate, times(0))
        //        .exchange(Mockito.matches(".*setAssignmentDone"), eq(HttpMethod.PUT), eq(new HttpEntity<>(assignmentDTO)), eq(AssignmentDTO.class));
    }

    @Test
    public void setDone_invalidAssignmentDTO_AssureServerIsNeverCalled() throws ServiceLayerException {
        AssignmentControllerImpl assignmentController = new AssignmentControllerImpl(restTemplate);
        ValidateAssignmentDTO validateAssignmentDTO = new ValidateAssignmentDTO(new PrimitiveValidator());
        AssignmentService assignmentService = new AssignmentServiceImpl(assignmentController, validateAssignmentDTO);

        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setId(1);
        assignmentDTO.setBox_id(901);
        assignmentDTO.setAmount(1);
        assignmentDTO.setTask_id(2);
        assignmentDTO.setDone(false);

        try {
            assignmentService.setDone(assignmentDTO);
        }catch (InvalidInputException e){
            //do nothing
        }

        verify(restTemplate, times(0))
                .exchange(Mockito.matches(".*setAssignmentDone"), eq(HttpMethod.PUT), eq(new HttpEntity<>(assignmentDTO)), eq(AssignmentDTO.class));
    }

}
