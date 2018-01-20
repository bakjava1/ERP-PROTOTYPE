package serviceLayer;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.AssignmentController;
import at.ac.tuwien.sepm.assignment.group02.client.service.AssignmentService;
import at.ac.tuwien.sepm.assignment.group02.client.service.AssignmentServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.client.validation.ValidateAssignmentDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

// @RunWith attach a runner to initialize the test data
@RunWith(MockitoJUnitRunner.class)
public class AssignmentClientServiceImplTest {

    @Mock
    private AssignmentController assignmentController;

    @Mock
    private ValidateAssignmentDTO validateAssignmentDTO;

    @Test
    public void CreateAssignments_works() throws Exception {
        AssignmentService assignmentService = new AssignmentServiceImpl(assignmentController, validateAssignmentDTO);
        assignmentService.createAssignment(any(AssignmentDTO.class));
        verify(validateAssignmentDTO, times(1)).isValid(any(AssignmentDTO.class));
        verify(assignmentController, times(1)).createAssignment(any(AssignmentDTO.class));
    }

    @Test(expected = InvalidInputException.class)
    public void CreateAssignments_ValidationException() throws Exception {
        AssignmentService assignmentService = new AssignmentServiceImpl(assignmentController, validateAssignmentDTO);
        doThrow(InvalidInputException.class).when(validateAssignmentDTO).isValid(any(AssignmentDTO.class)); // throws InvalidInputException
        assignmentService.createAssignment(any(AssignmentDTO.class));
    }

    @Test(expected = ServiceLayerException.class)
    public void CreateAssignments_RestException() throws Exception {
        AssignmentService assignmentService = new AssignmentServiceImpl(assignmentController, validateAssignmentDTO);
        doThrow(PersistenceLayerException.class).when(assignmentController).createAssignment(any(AssignmentDTO.class));
        assignmentService.createAssignment(any(AssignmentDTO.class));
        verify(validateAssignmentDTO, times(1)).isValid(any(AssignmentDTO.class));
    }

    @Test
    public void getAllOpenAssignments_works() throws Exception {
        AssignmentService assignmentService = new AssignmentServiceImpl(assignmentController, validateAssignmentDTO);

        List<AssignmentDTO> assignmentDTOList1 = new ArrayList<>();
        List<AssignmentDTO> assignmentDTOList2;

        AssignmentDTO a1 = new AssignmentDTO();
        AssignmentDTO a2 = new AssignmentDTO();
        assignmentDTOList1.add(a1);
        assignmentDTOList1.add(a2);

        when(assignmentController.getAllOpenAssignments()).thenReturn(assignmentDTOList1);
        assignmentDTOList2 = assignmentService.getAllOpenAssignments();
        Assert.assertSame(assignmentDTOList1, assignmentDTOList2);
    }

    @Test(expected = ServiceLayerException.class)
    public void getAllOpenAssignments_restLayerException() throws Exception {
        AssignmentService assignmentService = new AssignmentServiceImpl(assignmentController, validateAssignmentDTO);
        doThrow(PersistenceLayerException.class).when(assignmentController).getAllOpenAssignments();
        assignmentService.getAllOpenAssignments();
    }

    @Test
    public void getAllClosedAssignments_works() throws Exception {
        AssignmentService assignmentService = new AssignmentServiceImpl(assignmentController, validateAssignmentDTO);

        List<AssignmentDTO> assignmentDTOList1 = new ArrayList<>();
        List<AssignmentDTO> assignmentDTOList2;

        AssignmentDTO a1 = new AssignmentDTO();
        AssignmentDTO a2 = new AssignmentDTO();
        assignmentDTOList1.add(a1);
        assignmentDTOList1.add(a2);

        when(assignmentController.getAllClosedAssignments()).thenReturn(assignmentDTOList1);
        assignmentDTOList2 = assignmentService.getAllClosedAssignments();
        Assert.assertSame(assignmentDTOList1, assignmentDTOList2);

    }

    @Test(expected = ServiceLayerException.class)
    public void getAllClosedAssignments_restLayerException() throws Exception {
        AssignmentService assignmentService = new AssignmentServiceImpl(assignmentController, validateAssignmentDTO);
        doThrow(PersistenceLayerException.class).when(assignmentController).getAllClosedAssignments();
        assignmentService.getAllClosedAssignments();
    }

    @Test
    public void testSetDone_works() throws Exception {

        AssignmentService assignmentService = new AssignmentServiceImpl(assignmentController, validateAssignmentDTO);

        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setDone(false);

        assignmentService.setDone(assignmentDTO);

        verify(validateAssignmentDTO, times(1)).isValid(assignmentDTO);
        verify(assignmentController, times(1)).setDone(assignmentDTO);

    }

    @Test(expected = InvalidInputException.class)
    public void testSetDone_validationException() throws Exception {
        AssignmentService assignmentService = new AssignmentServiceImpl(assignmentController, validateAssignmentDTO);

        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setDone(false);
        doThrow(InvalidInputException.class).when(validateAssignmentDTO).isValid(assignmentDTO); // throws InvalidInputException

        assignmentService.setDone(assignmentDTO);
    }

    @Test(expected = ServiceLayerException.class)
    public void testSetDone_restLayerException() throws Exception {
        AssignmentService assignmentService = new AssignmentServiceImpl(assignmentController, validateAssignmentDTO);

        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setDone(false);
        doThrow(PersistenceLayerException.class).when(assignmentController).setDone(assignmentDTO);

        assignmentService.setDone(assignmentDTO);
    }

    @Test(expected = ServiceLayerException.class)
    public void testSetDone_AssignmentIsAlreadyDone() throws Exception {
        AssignmentService assignmentService = new AssignmentServiceImpl(assignmentController, validateAssignmentDTO);

        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setDone(true); // throws ServiceLayerException

        assignmentService.setDone(assignmentDTO);

        verify(assignmentController, never()).setDone(assignmentDTO);
    }

}