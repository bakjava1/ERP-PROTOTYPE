package serviceLayer;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.FilterDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.server.converter.AssignmentConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Assignment;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.AssignmentDAO;
import at.ac.tuwien.sepm.assignment.group02.server.service.*;
import at.ac.tuwien.sepm.assignment.group02.server.validation.ValidateAssignment;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

// @RunWith attach a runner to initialize the test data
@RunWith(MockitoJUnitRunner.class)
public class AssignmentServerServiceImplTest {

    @Mock
    private AssignmentDAO assignmentManagementDAO;

    @Mock
    private AssignmentConverter assignmentConverter;

    @Mock
    private ValidateAssignment validateAssignment;

    @Mock
    private TimberService timberService;
    @Mock
    private LumberService lumberService;
    @Mock
    private TaskService taskService;

    @Test
    public void testCreateAssignment_works() throws Exception {
        AssignmentService assignmentService
                = new AssignmentServiceImpl(assignmentManagementDAO, assignmentConverter, validateAssignment,
                timberService, lumberService, taskService);

        assignmentService.addAssignment(any(AssignmentDTO.class));

        verify(assignmentConverter,times(1)).convertRestDTOToPlainObject(any(AssignmentDTO.class));
        verify(validateAssignment,times(1)).isValid(any(Assignment.class));
        verify(assignmentManagementDAO,times(1)).createAssignment(any(Assignment.class));
    }

    @Test(expected = InvalidInputException.class)
    public void testCreateAssignment_ValidationException() throws Exception {
        AssignmentService assignmentService
                = new AssignmentServiceImpl(assignmentManagementDAO, assignmentConverter, validateAssignment,
                timberService, lumberService, taskService);

        doThrow(InvalidInputException.class).when(validateAssignment).isValid(any(Assignment.class));

        assignmentService.addAssignment(any(AssignmentDTO.class));
        verify(assignmentConverter,times(1)).convertRestDTOToPlainObject(any(AssignmentDTO.class));

    }

    @Test(expected = ServiceLayerException.class)
    public void testCreateAssignment_PersistenceLayerException() throws Exception {
        AssignmentService assignmentService
                = new AssignmentServiceImpl(assignmentManagementDAO, assignmentConverter, validateAssignment,
                timberService, lumberService, taskService);

        doThrow(PersistenceLayerException.class).when(assignmentManagementDAO).createAssignment(any(Assignment.class));

        assignmentService.addAssignment(any(AssignmentDTO.class));
    }

    @Test
    public void testGetAllOpenAssignments_returns2OpenAssignments() throws Exception {
        AssignmentService assignmentService
                = new AssignmentServiceImpl(assignmentManagementDAO, assignmentConverter, validateAssignment,
                timberService, lumberService, taskService);

        List<Assignment> allOpenAssignments = new LinkedList<>();
        Assignment l1 = Mockito.mock(Assignment.class);
        Assignment l2 = Mockito.mock(Assignment.class);
        allOpenAssignments.add(l1);
        allOpenAssignments.add(l2);
        when(assignmentManagementDAO.getAllOpenAssignments()).thenReturn(allOpenAssignments);

        AssignmentDTO aDTO = Mockito.mock(AssignmentDTO.class);
        when(assignmentConverter.convertPlainObjectToRestDTO(any(Assignment.class))).thenReturn(aDTO);
        when(aDTO.getCreation_date()).thenReturn("yyyy-MM-dd HH:mm:ss.S");

        List<AssignmentDTO> allOpenAssignmentsConverted = assignmentService.getAllOpenAssignments();

        verify(assignmentManagementDAO, times(1)).getAllOpenAssignments();
        verify(assignmentConverter, times(2)).convertPlainObjectToRestDTO(any(Assignment.class));

        Assert.assertEquals(allOpenAssignmentsConverted.size(),2);
    }

    @Test
    public void testGetAllOpenAssignments_PersistenceLayerException_returnNoOpenAssignments() throws Exception {
        AssignmentService assignmentService
                = new AssignmentServiceImpl(assignmentManagementDAO, assignmentConverter, validateAssignment,
                timberService, lumberService, taskService);

        doThrow(PersistenceLayerException.class).when(assignmentManagementDAO).getAllOpenAssignments();

        List<AssignmentDTO> allOpenAssignmentsConverted = assignmentService.getAllOpenAssignments();

        verify(assignmentManagementDAO, times(1)).getAllOpenAssignments();
        verify(assignmentConverter, times(0)).convertPlainObjectToRestDTO(any(Assignment.class));

        Assert.assertEquals(allOpenAssignmentsConverted.size(),0);
    }

    @Test
    public void testGetAllAssignments_returns2Assignments() throws Exception {
        AssignmentService assignmentService
                = new AssignmentServiceImpl(assignmentManagementDAO, assignmentConverter, validateAssignment,
                timberService, lumberService, taskService);

        List<Assignment> allAssignments = new LinkedList<>();
        Assignment l1 = Mockito.mock(Assignment.class);
        Assignment l2 = Mockito.mock(Assignment.class);
        allAssignments.add(l1);
        allAssignments.add(l2);
        when(assignmentManagementDAO.getAllClosedAssignments()).thenReturn(allAssignments);

        AssignmentDTO aDTO = Mockito.mock(AssignmentDTO.class);
        when(assignmentConverter.convertPlainObjectToRestDTO(any(Assignment.class))).thenReturn(aDTO);
        when(aDTO.getCreation_date()).thenReturn("yyyy-MM-dd HH:mm:ss.S");

        List<AssignmentDTO> allAssignmentsConverted = assignmentService.getAllClosedAssignments();

        verify(assignmentManagementDAO, times(1)).getAllClosedAssignments();
        verify(assignmentConverter, times(2)).convertPlainObjectToRestDTO(any(Assignment.class));

        Assert.assertEquals(allAssignmentsConverted.size(),2);
    }

    @Test
    public void testGetAllAssignments_PersistenceLayerException_returnNoAssignments() throws Exception {
        AssignmentService assignmentService
                = new AssignmentServiceImpl(assignmentManagementDAO, assignmentConverter, validateAssignment,
                timberService, lumberService, taskService);

        doThrow(PersistenceLayerException.class).when(assignmentManagementDAO).getAllOpenAssignments();

        List<AssignmentDTO> allAssignmentsConverted = assignmentService.getAllClosedAssignments();

        verify(assignmentManagementDAO, times(1)).getAllClosedAssignments();
        verify(assignmentConverter, times(0)).convertPlainObjectToRestDTO(any(Assignment.class));

        Assert.assertEquals(allAssignmentsConverted.size(),0);
    }

    @Test
    public void testSetDone_works() throws Exception {
        AssignmentService assignmentService
                = new AssignmentServiceImpl(assignmentManagementDAO, assignmentConverter, validateAssignment,
                timberService, lumberService, taskService);

        Assignment mockAssignment = Mockito.mock(Assignment.class);
        when(assignmentConverter.convertRestDTOToPlainObject(any(AssignmentDTO.class))).thenReturn(mockAssignment);

        TaskDTO mockTaskDTO = Mockito.mock(TaskDTO.class);
        when(taskService.getTaskById(mockAssignment.getTask_id())).thenReturn(mockTaskDTO);

        // return a mocked array list of LumberDTO that is empty
        List<LumberDTO> mockLumberDTOList = new ArrayList<>();
        doReturn(mockLumberDTOList).when(lumberService).getAllLumber(any(FilterDTO.class));

        assignmentService.setDone(any(AssignmentDTO.class));

        verify(assignmentConverter, times(1)).convertRestDTOToPlainObject(any(AssignmentDTO.class));
        verify(validateAssignment, times(1)).isValid(any(Assignment.class));

        // 3.2.2 (rest/AssignmentController) Aufgabe als erledigt markieren.
        verify(assignmentManagementDAO, times(1)).setAssignmentDone(any(Assignment.class));

        // 3.2.3 (rest/TimberController) Rundholz aus dem Lager entfernen.
        verify(timberService, times(1)).removeTimberFromBox(any(Integer.class),any(Integer.class));

        // 3.2.4 (rest/LumberController) Schnittholz ins Lager hinzufügen.
        verify(lumberService, times(1)).addLumber(any(LumberDTO.class));
        //verify(lumberService, times(1)).updateLumber(any(LumberDTO.class));

        // 3.2.5 (rest/LumberController) Hinzugefügtes Schnittholz bei Bedarf(?) reservieren.
        verify(lumberService, times(1)).reserveLumber(any(LumberDTO.class));

        // 3.2.6 (rest/TaskController) Reserviertes Schnittholz dem Auftrag hinzufügen.
        verify(taskService, times(1)).updateTask(any(TaskDTO.class));

    }

    @Test(expected = InvalidInputException.class)
    public void testSetDone_validationExceptionAssignment() throws Exception {
        AssignmentService assignmentService
                = new AssignmentServiceImpl(assignmentManagementDAO, assignmentConverter, validateAssignment,
                timberService, lumberService, taskService);

        doThrow(InvalidInputException.class).when(validateAssignment).isValid(any(Assignment.class)); // throws InvalidInputException

        assignmentService.setDone(any(AssignmentDTO.class));
    }

    @Test(expected = ServiceLayerException.class)
    public void testSetDone_PersistenceLayerExceptionAssignment() throws Exception {
        AssignmentService assignmentService
                = new AssignmentServiceImpl(assignmentManagementDAO, assignmentConverter, validateAssignment,
                timberService, lumberService, taskService);

        Assignment mockAssignment = Mockito.mock(Assignment.class);
        when(assignmentConverter.convertRestDTOToPlainObject(any(AssignmentDTO.class))).thenReturn(mockAssignment);
        TaskDTO taskDTO = Mockito.mock(TaskDTO.class);
        when(taskService.getTaskById(any(Integer.class))).thenReturn(taskDTO);

        doThrow(PersistenceLayerException.class).when(assignmentManagementDAO).setAssignmentDone(any(Assignment.class));

        assignmentService.setDone(any(AssignmentDTO.class));
    }

    @Test(expected = ServiceLayerException.class)
    public void testSetDone_ServiceLayerExceptionTimber() throws Exception {
        AssignmentService assignmentService
                = new AssignmentServiceImpl(assignmentManagementDAO, assignmentConverter, validateAssignment,
                timberService, lumberService, taskService);

        Assignment mockAssignment = Mockito.mock(Assignment.class);
        when(assignmentConverter.convertRestDTOToPlainObject(any(AssignmentDTO.class))).thenReturn(mockAssignment);

        doThrow(ServiceLayerException.class).when(timberService).removeTimberFromBox(any(Integer.class),any(Integer.class));

        assignmentService.setDone(any(AssignmentDTO.class));
    }

    @Test(expected = ServiceLayerException.class)
    public void testSetDone_ServiceLayerExceptionGetTask() throws Exception {
        AssignmentService assignmentService
                = new AssignmentServiceImpl(assignmentManagementDAO, assignmentConverter, validateAssignment,
                timberService, lumberService, taskService);

        Assignment mockAssignment = Mockito.mock(Assignment.class);
        when(assignmentConverter.convertRestDTOToPlainObject(any(AssignmentDTO.class))).thenReturn(mockAssignment);

        doThrow(ServiceLayerException.class).when(taskService).getTaskById(any(Integer.class));

        assignmentService.setDone(any(AssignmentDTO.class));
    }

    @Test(expected = ServiceLayerException.class)
    public void testSetDone_ServiceLayerExceptionLumberService1() throws Exception {
        AssignmentService assignmentService
                = new AssignmentServiceImpl(assignmentManagementDAO, assignmentConverter, validateAssignment,
                timberService, lumberService, taskService);

        Assignment mockAssignment = Mockito.mock(Assignment.class);
        when(assignmentConverter.convertRestDTOToPlainObject(any(AssignmentDTO.class))).thenReturn(mockAssignment);

        TaskDTO mockTaskDTO = Mockito.mock(TaskDTO.class);
        when(taskService.getTaskById(mockAssignment.getTask_id())).thenReturn(mockTaskDTO);

        /*
        LumberDTO mockLumberDTO = Mockito.mock(LumberDTO.class);
        List<LumberDTO> mockLumberDTOList = Mockito.mock(List.class);
        doReturn(mockLumberDTOList).when(lumberService).getAllLumber(mockLumberDTO);
        */
        doThrow(ServiceLayerException.class).when(lumberService).getAllLumber(any(FilterDTO.class));

        assignmentService.setDone(any(AssignmentDTO.class));
    }

    @Test
    public void testSetDone_ExistingLumberIsUpdated() throws Exception {
        AssignmentService assignmentService
                = new AssignmentServiceImpl(assignmentManagementDAO, assignmentConverter, validateAssignment,
                timberService, lumberService, taskService);

        Assignment mockAssignment = Mockito.mock(Assignment.class);
        when(assignmentConverter.convertRestDTOToPlainObject(any(AssignmentDTO.class))).thenReturn(mockAssignment);

        TaskDTO mockTaskDTO = Mockito.mock(TaskDTO.class);
        when(taskService.getTaskById(mockAssignment.getTask_id())).thenReturn(mockTaskDTO);

        // mock an array list of LumberDTO that is not empty
        List<LumberDTO> mockLumberDTOList = new ArrayList<>();
        LumberDTO l1 = Mockito.mock(LumberDTO.class);
        LumberDTO l2 = Mockito.mock(LumberDTO.class);
        mockLumberDTOList.add(l1);
        mockLumberDTOList.add(l2);

        doReturn(mockLumberDTOList).when(lumberService).getAllLumber(any(FilterDTO.class));

        assignmentService.setDone(any(AssignmentDTO.class));

        verify(lumberService, never()).addLumber(any(LumberDTO.class));
        verify(lumberService, times(1)).updateLumber(any(LumberDTO.class));

    }

    @Test
    public void testSetDone_NewLumberIsCreated() throws Exception {
        AssignmentService assignmentService
                = new AssignmentServiceImpl(assignmentManagementDAO, assignmentConverter, validateAssignment,
                timberService, lumberService, taskService);

        Assignment mockAssignment = Mockito.mock(Assignment.class);
        when(assignmentConverter.convertRestDTOToPlainObject(any(AssignmentDTO.class))).thenReturn(mockAssignment);

        TaskDTO mockTaskDTO = Mockito.mock(TaskDTO.class);
        when(taskService.getTaskById(mockAssignment.getTask_id())).thenReturn(mockTaskDTO);

        // return null instead of array list
        doReturn(null).when(lumberService).getAllLumber(any(FilterDTO.class));

        assignmentService.setDone(any(AssignmentDTO.class));

        verify(lumberService, times(1)).addLumber(any(LumberDTO.class));
        verify(lumberService, never()).updateLumber(any(LumberDTO.class));

    }


    @Test(expected = ServiceLayerException.class)
    public void testSetDone_ServiceLayerExceptionLumberService2() throws Exception {
        AssignmentService assignmentService
                = new AssignmentServiceImpl(assignmentManagementDAO, assignmentConverter, validateAssignment,
                timberService, lumberService, taskService);

        Assignment mockAssignment = Mockito.mock(Assignment.class);
        when(assignmentConverter.convertRestDTOToPlainObject(any(AssignmentDTO.class))).thenReturn(mockAssignment);

        TaskDTO mockTaskDTO = Mockito.mock(TaskDTO.class);
        when(taskService.getTaskById(mockAssignment.getTask_id())).thenReturn(mockTaskDTO);

        // return a mocked array list of LumberDTO that is empty
        List<LumberDTO> mockLumberDTOList = new ArrayList<>();
        doReturn(mockLumberDTOList).when(lumberService).getAllLumber(any(FilterDTO.class));

        doThrow(ServiceLayerException.class).when(lumberService).reserveLumber(any(LumberDTO.class));

        assignmentService.setDone(any(AssignmentDTO.class));

        verify(assignmentConverter, times(1)).convertRestDTOToPlainObject(any(AssignmentDTO.class));
        verify(validateAssignment, times(1)).isValid(any(Assignment.class));

        // 3.2.2 (rest/AssignmentController) Aufgabe als erledigt markieren.
        verify(assignmentManagementDAO, times(1)).setAssignmentDone(any(Assignment.class));

        // 3.2.3 (rest/TimberController) Rundholz aus dem Lager entfernen.
        verify(timberService, times(1)).removeTimberFromBox(any(Integer.class),any(Integer.class));

        // 3.2.4 (rest/LumberController) Schnittholz ins Lager hinzufügen.
        verify(lumberService, times(1)).addLumber(any(LumberDTO.class));

    }


    @Test(expected = ServiceLayerException.class)
    public void testSetDone_ServiceLayerExceptionTaskService() throws Exception {
        AssignmentService assignmentService
                = new AssignmentServiceImpl(assignmentManagementDAO, assignmentConverter, validateAssignment,
                timberService, lumberService, taskService);

        Assignment mockAssignment = Mockito.mock(Assignment.class);
        when(assignmentConverter.convertRestDTOToPlainObject(any(AssignmentDTO.class))).thenReturn(mockAssignment);

        TaskDTO mockTaskDTO = Mockito.mock(TaskDTO.class);
        when(taskService.getTaskById(mockAssignment.getTask_id())).thenReturn(mockTaskDTO);

        // return a mocked array list of LumberDTO that is empty
        List<LumberDTO> mockLumberDTOList = new ArrayList<>();
        doReturn(mockLumberDTOList).when(lumberService).getAllLumber(any(FilterDTO.class));

        doThrow(ServiceLayerException.class).when(taskService).updateTask(any(TaskDTO.class));

        assignmentService.setDone(any(AssignmentDTO.class));

        verify(assignmentConverter, times(1)).convertRestDTOToPlainObject(any(AssignmentDTO.class));
        verify(validateAssignment, times(1)).isValid(any(Assignment.class));

        // 3.2.2 (rest/AssignmentController) Aufgabe als erledigt markieren.
        verify(assignmentManagementDAO, times(1)).setAssignmentDone(any(Assignment.class));

        // 3.2.3 (rest/TimberController) Rundholz aus dem Lager entfernen.
        verify(timberService, times(1)).removeTimberFromBox(any(Integer.class),any(Integer.class));

        // 3.2.4 (rest/LumberController) Schnittholz ins Lager hinzufügen.
        verify(lumberService, times(1)).addLumber(any(LumberDTO.class));

        // 3.2.5 (rest/LumberController) Hinzugefügtes Schnittholz bei Bedarf(?) reservieren.
        verify(lumberService, times(1)).reserveLumber(any(LumberDTO.class));

    }
}