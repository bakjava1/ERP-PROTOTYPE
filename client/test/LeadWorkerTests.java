import at.ac.tuwien.sepm.assignment.group02.client.configuration.RestTemplateConfiguration;
import at.ac.tuwien.sepm.assignment.group02.client.converter.LumberConverter;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.AssignmentControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.client.rest.LumberControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.client.rest.TaskControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.client.service.AssignmentService;
import at.ac.tuwien.sepm.assignment.group02.client.service.AssignmentServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.client.service.LumberService;
import at.ac.tuwien.sepm.assignment.group02.client.service.LumberServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.client.validation.PrimitiveValidator;
import at.ac.tuwien.sepm.assignment.group02.client.validation.ValidateAssignmentDTO;
import at.ac.tuwien.sepm.assignment.group02.client.validation.Validator;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.FilterDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LeadWorkerTests {

    @Mock
    private RestTemplate restTemplate;

    @BeforeClass
    public static void setup(){

    }

    // assignmentService.createAssignment(assignmentDTO);
    @Test
    public void createAssignment_works() throws ServiceLayerException {
        AssignmentControllerImpl assignmentController = new AssignmentControllerImpl(restTemplate);
        ValidateAssignmentDTO validateAssignmentDTO = new ValidateAssignmentDTO(new PrimitiveValidator());
        AssignmentService assignmentService = new AssignmentServiceImpl(assignmentController, validateAssignmentDTO);

        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setBox_id(1);
        assignmentDTO.setAmount(1);
        assignmentDTO.setTask_id(1);

        assignmentService.createAssignment(assignmentDTO);

        verify(restTemplate, times(1))
                .postForObject(Mockito.matches(".*createAssignment")
                , eq(assignmentDTO), eq(AssignmentDTO.class));
    }

    @Test
    public void createAssignment_BoxIDdoesNotExist_AssureServerIsNotCalled() {
        AssignmentControllerImpl assignmentController = new AssignmentControllerImpl(restTemplate);
        ValidateAssignmentDTO validateAssignmentDTO = new ValidateAssignmentDTO(new PrimitiveValidator());
        AssignmentService assignmentService = new AssignmentServiceImpl(assignmentController, validateAssignmentDTO);

        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setBox_id(100);
        assignmentDTO.setAmount(1);
        assignmentDTO.setTask_id(1);

        try {
            assignmentService.createAssignment(assignmentDTO);
        } catch (ServiceLayerException e) {
            //do nothing
        }

        verify(restTemplate, times(0))
                .postForObject(Mockito.matches(".*createAssignment")
                        , eq(assignmentDTO), eq(AssignmentDTO.class));
    }

    @Test(expected = ServiceLayerException.class)
    public void createAssignment_InvalidAssignment() throws ServiceLayerException {
        AssignmentControllerImpl assignmentController = new AssignmentControllerImpl(restTemplate);
        ValidateAssignmentDTO validateAssignmentDTO = new ValidateAssignmentDTO(new PrimitiveValidator());
        AssignmentService assignmentService = new AssignmentServiceImpl(assignmentController, validateAssignmentDTO);

        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setBox_id(1);
        assignmentDTO.setAmount(-1);
        assignmentDTO.setTask_id(1);

        assignmentService.createAssignment(assignmentDTO);

        verify(restTemplate, times(0))
                .postForObject(Mockito.matches(".*createAssignment")
                        , eq(assignmentDTO), eq(AssignmentDTO.class));
    }

    // lumberService.getAll(filterDTO);
    @Ignore
    @Test
    public void getAllOpenAssignments_works() throws ServiceLayerException {
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        LumberControllerImpl lumberController = new LumberControllerImpl(restTemplate);
        LumberConverter lumberConverter = new LumberConverter();
        TaskControllerImpl taskController = new TaskControllerImpl(restTemplate);
        Validator validator = new Validator(new PrimitiveValidator());
        LumberService lumberService = new LumberServiceImpl(
                lumberController,
                lumberConverter,
                taskController,
                validator, new PrimitiveValidator());

        List<LumberDTO> lumberDTOList = new ArrayList<>();
        LumberDTO a1 = new LumberDTO();
        LumberDTO a2 = new LumberDTO();
        LumberDTO a3 = new LumberDTO();
        LumberDTO[] lumberDTOS = {a1,a2,a3};
        lumberDTOList.addAll(Arrays.asList(lumberDTOS));

        FilterDTO filterDTO = new FilterDTO();
        // create a proper filter
        filterDTO.setDescription("bla");
        filterDTO.setFinishing("roh");
        filterDTO.setWood_type("Fi");
        filterDTO.setQuality("O");
        filterDTO.setSize(20+"");
        filterDTO.setWidth(35+"");
        filterDTO.setLength(4000+"");


        when(restTemplate
                .postForObject(eq("http://"+ RestTemplateConfiguration.host+":"+RestTemplateConfiguration.port+"/getAllLumber"),
                        eq(validator.validateFilter(filterDTO)),eq(LumberDTO[].class))).thenReturn(lumberDTOS);

        List<Lumber> lumberList = lumberService.getAll(filterDTO);

        verify(restTemplate, times(1))
                .postForObject(eq("http://"+ RestTemplateConfiguration.host+":"+RestTemplateConfiguration.port+"/getAllLumber"),
                        eq(validator.validateFilter(filterDTO)),eq(LumberDTO[].class));


        Assert.assertTrue(lumberDTOList.equals(lumberList));
    }

    // lumberService.reserveLumber(lumber, qu, selectedTask);

    // taskService.getAllOpenTasks()

    /*
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
*/
}
