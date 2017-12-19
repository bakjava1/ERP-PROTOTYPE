import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.AssignmentController;
import at.ac.tuwien.sepm.assignment.group02.client.rest.LumberController;
import at.ac.tuwien.sepm.assignment.group02.client.rest.TaskController;
import at.ac.tuwien.sepm.assignment.group02.client.rest.TimberController;
import at.ac.tuwien.sepm.assignment.group02.client.service.AssignmentService;
import at.ac.tuwien.sepm.assignment.group02.client.service.AssignmentServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.client.validation.ValidateAssignmentDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AssignmentManagementTest {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static AssignmentController assignmentController;
    private static AssignmentService assignmentService;

    private static TimberController timberController;
    private static LumberController lumberController;
    private static TaskController taskController;
    private static ValidateAssignmentDTO validateAssignmentDTO;

    @BeforeClass
    public static void setup() {
        LOG.debug("assignment management test setup initiated");

        assignmentController = mock(AssignmentController.class);
        timberController = mock(TimberController.class);
        lumberController = mock(LumberController.class);
        taskController = mock(TaskController.class);
        validateAssignmentDTO = mock(ValidateAssignmentDTO.class);

        assignmentService = new AssignmentServiceImpl(
                 assignmentController,
                 timberController,
                 lumberController,
                 taskController,
                 validateAssignmentDTO);

        LOG.debug("assignment management test setup completed");
    }

    @Test
    public void testAssignmentOverview_client_serviceLayer_EmptyList() throws PersistenceLayerException, ServiceLayerException {
        LOG.debug("testing for an empty assignment overview in client serice layer");

        when(assignmentController.getAllOpenAssignments()).thenReturn(new LinkedList<>());
        assertEquals(0, assignmentService.getAllOpenAssignments().size());
    }

    @Test
    public void testAssignmentOverview_client_serviceLayer() throws PersistenceLayerException, ServiceLayerException {
        LOG.debug("testing for assignment overview in client serice layer");

        List<AssignmentDTO> assignmentList = new LinkedList<>();
        AssignmentDTO assignment1 = new AssignmentDTO();
        AssignmentDTO assignment2 = new AssignmentDTO();
        assignmentList.add(assignment1);
        assignmentList.add(assignment2);

        when(assignmentController.getAllOpenAssignments()).thenReturn(assignmentList);
        assertEquals(2, assignmentService.getAllOpenAssignments().size());
    }
}
