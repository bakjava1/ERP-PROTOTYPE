import at.ac.tuwien.sepm.assignment.group02.client.converter.OptAlgorithmConverter;
import at.ac.tuwien.sepm.assignment.group02.client.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.OptimisationAlgorithmException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.OptAlgorithmController;
import at.ac.tuwien.sepm.assignment.group02.client.service.OptimisationAlgorithmServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OptAlgorithmResultDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class OptimisationAlgorithmTest {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static RestTemplate restTemplate;

    private static OptimisationAlgorithmServiceImpl optimisationAlgorithmService;
    private static OptAlgorithmController optAlgorithmController;
    private static TaskConverter taskConverter;
    private static OptAlgorithmConverter optAlgorithmConverter;

    private static OptAlgorithmResultDTO expectedResultValid;
    private static OptAlgorithmResultDTO expectedResultNoTimber;
    private static OptAlgorithmResultDTO expectedResultNoSideTasks;
    private static TimberDTO timberResult;
    private static TaskDTO mainTask;
    private static TaskDTO sideTask;

    @BeforeClass
    public static void setup() {
        LOG.debug("optimisation algorithm test setup initiated");

        restTemplate = mock(RestTemplate.class);
        optAlgorithmController = mock(OptAlgorithmController.class);
        taskConverter = mock(TaskConverter.class);
        optAlgorithmConverter = mock(OptAlgorithmConverter.class);
        optimisationAlgorithmService = new OptimisationAlgorithmServiceImpl (optAlgorithmController, taskConverter, optAlgorithmConverter);

        expectedResultValid = new OptAlgorithmResultDTO();
        timberResult = new TimberDTO();
        timberResult.setBox_id(22);
        expectedResultValid.setTimberResult(timberResult);

        mainTask = new TaskDTO();
        mainTask.setId(24);
        mainTask.setAlgorithmResultAmount(14);
        mainTask.setQuality("IV");
        mainTask.setLength(5000);
        mainTask.setWood_type("Ta");
        mainTask.setWidth(112);
        mainTask.setSize(31);

        sideTask = new TaskDTO();
        sideTask.setId(218);
        sideTask.setAlgorithmResultAmount(2);
        ArrayList<TaskDTO> taskResult = new ArrayList<>();
        taskResult.add(mainTask);
        taskResult.add(sideTask);
        expectedResultValid.setTaskResult(taskResult);

        expectedResultNoTimber = new OptAlgorithmResultDTO();
        expectedResultNoTimber.setTaskResult(taskResult);

        expectedResultNoSideTasks = new OptAlgorithmResultDTO();
        expectedResultNoSideTasks.setTimberResult(timberResult);


        LOG.debug("optimisation algorithm test setup completed");
    }

    @Ignore
    @Test
    public void test_optimisation_algorithm_in_client_serviceLayer() throws OptimisationAlgorithmException, PersistenceLayerException {
        LOG.debug("test call of optimisation algorithm in client service layer");

        optimisationAlgorithmService.getOptAlgorithmResult(mainTask);

        verify(restTemplate, times(1))
                .postForObject(Mockito.matches(".*getOptAlgorithmResult"), eq(TaskDTO.class), eq(OptAlgorithmResultDTO.class));
    }

    @Test (expected = OptimisationAlgorithmException.class)
    public void expect_optimisationAlgorithmException_in_client_serviceLayer_without_taskResult() throws OptimisationAlgorithmException, PersistenceLayerException {
        LOG.debug("testing for optimisation algorithm exception in client service layer");

        when(optAlgorithmController.getOptAlgorithmResult(mainTask)).thenReturn(expectedResultNoSideTasks);

        optimisationAlgorithmService.getOptAlgorithmResult(mainTask);
    }

    @Test (expected = OptimisationAlgorithmException.class)
    public void expect_optimisationAlgorithmException_in_client_serviceLayer_without_timberResult() throws PersistenceLayerException, OptimisationAlgorithmException {
        LOG.debug("testing for optimisation algorithm exception in client service layer");

        when(optAlgorithmController.getOptAlgorithmResult(mainTask)).thenReturn(expectedResultNoTimber);

        optimisationAlgorithmService.getOptAlgorithmResult(mainTask);
    }
}
