import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import at.ac.tuwien.sepm.assignment.group02.server.converter.AssignmentConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Assignment;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.AssignmentDAO;
import at.ac.tuwien.sepm.assignment.group02.server.service.AssignmentService;
import at.ac.tuwien.sepm.assignment.group02.server.service.AssignmentServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.server.validation.ValidateAssignment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

// @RunWith attach a runner to initialize the test data
@RunWith(MockitoJUnitRunner.class)
public class AssignmentServerServiceImplTest_setDone {

    @Mock
    private AssignmentDAO assignmentManagementDAO;

    @Mock
    private AssignmentConverter assignmentConverter;

    @Mock
    private ValidateAssignment validateAssignment;


    @Test
    public void testSetDone_works() throws Exception {
        AssignmentService assignmentService
                = new AssignmentServiceImpl(assignmentManagementDAO, assignmentConverter, validateAssignment);

        assignmentService.setDone(any(AssignmentDTO.class));

        verify(assignmentConverter, times(1)).convertRestDTOToPlainObject(any(AssignmentDTO.class));
        verify(validateAssignment, times(1)).isValid(any(Assignment.class));
        verify(assignmentManagementDAO, times(1)).setAssignmentDone(any(Assignment.class));
    }

    @Test(expected = InvalidInputException.class)
    public void testSetDone_validationException() throws Exception {
        AssignmentService assignmentService
                = new AssignmentServiceImpl(assignmentManagementDAO, assignmentConverter, validateAssignment);

        doThrow(InvalidInputException.class).when(validateAssignment).isValid(any(Assignment.class)); // throws InvalidInputException

        assignmentService.setDone(any(AssignmentDTO.class));
    }

    @Test(expected = ServiceLayerException.class)
    public void testSetDone_PersistenceLayerException() throws Exception {
        AssignmentService assignmentService
                = new AssignmentServiceImpl(assignmentManagementDAO, assignmentConverter, validateAssignment);

        doThrow(PersistenceLayerException.class).when(assignmentManagementDAO).setAssignmentDone(any(Assignment.class));

        assignmentService.setDone(any(AssignmentDTO.class));
    }

}