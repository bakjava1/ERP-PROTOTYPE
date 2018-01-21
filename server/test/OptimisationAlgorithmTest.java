import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OptAlgorithmResultDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.OptimisationAlgorithmException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.service.OptAlgorithmServiceImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OptimisationAlgorithmTest {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static OptAlgorithmServiceImpl optAlgorithmService;
    private static OptAlgorithmResultDTO expectedResult;
    private static TaskDTO mainTask;
    private static TaskDTO mainTaskTooBig;
    private static TimberDTO timberNoSidetasks;

    @BeforeClass
    public static void setup() {
        LOG.debug("optimisation algorithm test setup initiated");

        optAlgorithmService = new OptAlgorithmServiceImpl();
        expectedResult = new OptAlgorithmResultDTO();
        //TODO expected result

        mainTask = new TaskDTO();
        //TODO main task

        mainTaskTooBig = new TaskDTO();
        //TODO zu großer hauptauftrag
        //und gleichzeitig Maße (zB Länge 6000) für die keine Seitenware passt

        timberNoSidetasks = new TimberDTO();
        //TODO gleiche Maße wie mainTaskTooBig
        //damit keine Seitenware gefunden wird


        LOG.debug("optimisation algorithm test setup finished");
    }

    @Test
    public void test_optimisation_algorithm_result() throws PersistenceLayerException, OptimisationAlgorithmException, ServiceLayerException {
        LOG.debug("testing for the optimisation algorithm result");

        OptAlgorithmResultDTO actualResult = optAlgorithmService.getOptAlgorithmResult(mainTask);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test (expected = OptimisationAlgorithmException.class)
    public void expect_OptimisationAlgorithmException_when_no_timber_isAvailable() {

    }

    @Test (expected = OptimisationAlgorithmException.class)
    public void expect_OptimisationAlgorithmException_when_no_verticalTasks_areAvailable() throws PersistenceLayerException, OptimisationAlgorithmException, ServiceLayerException {
        LOG.debug("testing for optimisation algorithm exception when no vertical tasks are available");

        List<TimberDTO> timberList = new ArrayList<>();
        timberList.add(timberNoSidetasks);
        Mockito.when(optAlgorithmService.getPossibleTimbers(mainTaskTooBig)).thenReturn(timberList);

        optAlgorithmService.getOptAlgorithmResult(mainTaskTooBig);
    }
}
