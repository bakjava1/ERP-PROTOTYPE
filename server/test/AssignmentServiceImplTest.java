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
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.*;

public class AssignmentServiceImplTest {

    @Mock
    AssignmentDAO assignmentManagementDAO;

    @Mock
    AssignmentConverter assignmentConverter;

    @Mock
    ValidateAssignment validateAssignment;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @BeforeClass
    public static void setup() {

    }

    @Test
    public void testSetDone_works() throws Exception {
        AssignmentService assignmentService
                = new AssignmentServiceImpl(assignmentManagementDAO, assignmentConverter, validateAssignment);

        doReturn(true).when(validateAssignment).isValid(any(Assignment.class));

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

        verify(assignmentConverter, times(1)).convertRestDTOToPlainObject(any(AssignmentDTO.class));
        verify(validateAssignment, times(1)).isValid(any(Assignment.class));
        verify(assignmentManagementDAO, never()).setAssignmentDone(any(Assignment.class));

    }

    @Test(expected = ServiceLayerException.class)
    public void testSetDone_PersistenceLayerException() throws Exception {
        AssignmentService assignmentService
                = new AssignmentServiceImpl(assignmentManagementDAO, assignmentConverter, validateAssignment);

        doThrow(PersistenceLayerException.class).when(assignmentManagementDAO).setAssignmentDone(any(Assignment.class));

        assignmentService.setDone(any(AssignmentDTO.class));

        verify(assignmentConverter, times(1)).convertRestDTOToPlainObject(any(AssignmentDTO.class));
        verify(validateAssignment, times(1)).isValid(any(Assignment.class));
        verify(assignmentManagementDAO, times(1)).setAssignmentDone(any(Assignment.class));
    }

}