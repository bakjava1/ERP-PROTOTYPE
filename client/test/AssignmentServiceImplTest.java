import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.AssignmentController;
import at.ac.tuwien.sepm.assignment.group02.client.service.AssignmentService;
import at.ac.tuwien.sepm.assignment.group02.client.service.AssignmentServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.client.validation.ValidateAssignmentDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.*;

public class AssignmentServiceImplTest {

    @Mock
    AssignmentController assignmentController;

    @Mock
    ValidateAssignmentDTO validateAssignmentDTO;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    /**
     * method marks an assignment as done
     * assignmentDTO an assignment to mark as done
     * ServiceLayerException if the assignment couldn't be marked as done
     *
     *     void setDone(AssignmentDTO assignmentDTO) throws ServiceLayerException;
     */

    @Test
    public void testSetDone_works() throws Exception {
        AssignmentService assignmentService = new AssignmentServiceImpl(assignmentController, validateAssignmentDTO);

        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setDone(false);
        assignmentService.setDone(assignmentDTO);
        verify(assignmentController, times(1)).setDone(assignmentDTO);
    }

    @Test(expected = InvalidInputException.class)
    public void testSetDone_validationException() throws Exception {
        AssignmentService assignmentService = new AssignmentServiceImpl(assignmentController, validateAssignmentDTO);

        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setDone(false);
        doThrow(InvalidInputException.class).when(validateAssignmentDTO).isValid(assignmentDTO); // throws InvalidInputException

        assignmentService.setDone(assignmentDTO);

        verify(assignmentController, never()).setDone(assignmentDTO);
    }

    @Test(expected = ServiceLayerException.class)
    public void testSetDone_restControllerException() throws Exception {
        AssignmentService assignmentService = new AssignmentServiceImpl(assignmentController, validateAssignmentDTO);

        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setDone(false);
        doThrow(new PersistenceLayerException("server error")).when(assignmentController).setDone(assignmentDTO);
        verify(assignmentController, never()).setDone(assignmentDTO);
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