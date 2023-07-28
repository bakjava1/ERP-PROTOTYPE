import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OptAlgorithmResultDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;
import at.ac.tuwien.sepm.assignment.group02.server.converter.OptAlgorithmConverter;
import at.ac.tuwien.sepm.assignment.group02.server.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.server.converter.TimberConverter;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.OptimisationAlgorithmException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TaskDAO;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TaskDAOJDBC;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TimberDAO;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TimberDAOJDBC;
import at.ac.tuwien.sepm.assignment.group02.server.rest.OptAlgorithmControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.server.service.OptAlgorithmServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.server.util.DBUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class OptimisationAlgorithmTest {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static DBUtil dbUtil;

    private static TimberDAO timberDAO;
    private static TaskDAO taskDAO;
    private static OptAlgorithmConverter optAlgorithmConverter;
    private static TimberConverter timberConverter;
    private static TaskConverter taskConverter;

    private static OptAlgorithmServiceImpl optAlgorithmService;
    private static OptAlgorithmControllerImpl optAlgorithmController;
    private static OptAlgorithmResultDTO expectedResult;
    private static TimberDTO timberResult;
    private static TaskDTO mainTask;
    private static TaskDTO sideTask;
    private static TaskDTO mainTaskTooBig;
    private static TimberDTO timberNoSidetasks;

    @BeforeClass
    public static void initialize() {
        LOG.debug("optimisation algorithm test setup initiated");

        dbUtil = new DBUtil();
        timberDAO = new TimberDAOJDBC(dbUtil.getConnection());
        taskDAO = new TaskDAOJDBC(dbUtil.getConnection());
        optAlgorithmConverter = new OptAlgorithmConverter();
        timberConverter = new TimberConverter();
        taskConverter = new TaskConverter();

        optAlgorithmService = new OptAlgorithmServiceImpl(timberDAO, taskDAO, optAlgorithmConverter, timberConverter, taskConverter);

        optAlgorithmController = new OptAlgorithmControllerImpl();

        expectedResult = new OptAlgorithmResultDTO();
        timberResult = new TimberDTO();
        timberResult.setBox_id(22);
        expectedResult.setTimberResult(timberResult);

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
        expectedResult.setTaskResult(taskResult);

        mainTaskTooBig = new TaskDTO();
        mainTaskTooBig.setId(24);
        mainTaskTooBig.setAlgorithmResultAmount(14);
        mainTaskTooBig.setQuality("IV");
        mainTaskTooBig.setLength(6000);
        mainTaskTooBig.setWood_type("Ta");
        mainTaskTooBig.setWidth(112);
        mainTaskTooBig.setSize(31);

        timberNoSidetasks = new TimberDTO();
        timberNoSidetasks.setLength(6000);


        LOG.debug("optimisation algorithm test setup finished");
    }

    @Test
    public void test_optimisation_algorithm_result() throws PersistenceLayerException, OptimisationAlgorithmException, ServiceLayerException {
        LOG.debug("testing for the optimisation algorithm result");

        OptAlgorithmResultDTO actualResult = optAlgorithmService.getOptAlgorithmResult(mainTask);

        Assert.assertEquals(expectedResult.getTimberResult().getBox_id(), actualResult.getTimberResult().getBox_id());
        Assert.assertEquals(expectedResult.getTaskResult().size(), actualResult.getTaskResult().size());

        for(int i = 0; i < expectedResult.getTaskResult().size(); i++) {
            TaskDTO expected = expectedResult.getTaskResult().get(i);
            TaskDTO actual = actualResult.getTaskResult().get(i);

            Assert.assertEquals(expected.getId(), actual.getId());
            Assert.assertEquals(expected.getAlgorithmResultAmount(), actual.getAlgorithmResultAmount());
        }
    }

    @Test
    public void test_restClass_calling_optimisation_algorithm_result() throws PersistenceLayerException, OptimisationAlgorithmException, ServiceLayerException {
        LOG.debug("testing rest class for calling the optimisation algorithm");

        OptAlgorithmResultDTO actualResult = optAlgorithmController.getOptAlgorithmResult(mainTask);

        Assert.assertEquals(expectedResult.getTimberResult().getBox_id(), actualResult.getTimberResult().getBox_id());
        Assert.assertEquals(expectedResult.getTaskResult().size(), actualResult.getTaskResult().size());

        for(int i = 0; i < expectedResult.getTaskResult().size(); i++) {
            TaskDTO expected = expectedResult.getTaskResult().get(i);
            TaskDTO actual = actualResult.getTaskResult().get(i);

            Assert.assertEquals(expected.getId(), actual.getId());
            Assert.assertEquals(expected.getAlgorithmResultAmount(), actual.getAlgorithmResultAmount());
        }
    }


    @Test (expected = OptimisationAlgorithmException.class)
    public void expect_OptimisationAlgorithmException_when_no_timber_isAvailable() throws PersistenceLayerException, OptimisationAlgorithmException, ServiceLayerException {
        LOG.debug("testing for the optimisation algorithm exception when no timber is available");

        optAlgorithmService.getOptAlgorithmResult(mainTaskTooBig);
    }
}
