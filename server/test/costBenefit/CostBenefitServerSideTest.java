package costBenefit;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.server.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.*;
import at.ac.tuwien.sepm.assignment.group02.server.service.CostBenefitService;
import at.ac.tuwien.sepm.assignment.group02.server.service.CostBenefitServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.server.util.DBUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CostBenefitServerSideTest {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private Connection connection;
    private LumberDAO lumberDAO;
    private TimberDAO timberDAO;
    private CostBenefitService costBenefitService;
    private List<TaskDTO> taskDTOList;

    @Before
    public void setUp() {
        connection = DBUtil.getConnection();
        lumberDAO = new LumberDAOJDBC(connection);
        timberDAO = new TimberDAOJDBC(connection);
        costBenefitService = new CostBenefitServiceImpl(new TaskConverter(),lumberDAO,timberDAO);
        taskDTOList = new ArrayList<>();
    }

    @Test
    public void allArticelNeededAviable() throws ServiceLayerException {
        TaskDTO task1 = new TaskDTO();
        task1.setDescription("Latten");
        task1.setFinishing("lutro");
        task1.setWood_type("Lae");
        task1.setQuality("O");
        task1.setSize(19);
        task1.setWidth(52);
        task1.setLength(4500);
        task1.setQuantity(5);
        task1.setPrice(1500);
        taskDTOList.add(task1);

        TaskDTO task2 = new TaskDTO();
        task2.setDescription("Latten");
        task2.setFinishing("frisch");
        task2.setWood_type("Lae");
        task2.setQuality("III/IV");
        task2.setSize(38);
        task2.setWidth(54);
        task2.setLength(4500);
        task2.setQuantity(10);
        task2.setPrice(5000);
        taskDTOList.add(task2);

        double result = costBenefitService.costValueFunction(taskDTOList);
        Assert.assertTrue(result == 65.0);
    }

    @Test
    public void OneArticelCompleteNotAviablePositveOutcome() throws ServiceLayerException {
        TaskDTO task1 = new TaskDTO();
        task1.setDescription("Latten");
        task1.setFinishing("lutro");
        task1.setWood_type("Lae");
        task1.setQuality("O");
        task1.setSize(19);
        task1.setWidth(52);
        task1.setLength(4500);
        task1.setQuantity(5);
        task1.setPrice(1500);
        taskDTOList.add(task1);

        TaskDTO task2 = new TaskDTO();
        task2.setDescription("Latten");
        task2.setFinishing("besäumt");
        task2.setWood_type("Lae");
        task2.setQuality("V");
        task2.setSize(20);
        task2.setWidth(40);
        task2.setLength(4000);
        task2.setQuantity(20);
        task2.setPrice(10000);
        taskDTOList.add(task2);

        double result = costBenefitService.costValueFunction(taskDTOList);
        Assert.assertTrue(result > 0);
    }

    @Test
    public void OneArticelCompleteNotAviableNegativeOutcome() throws ServiceLayerException {
        TaskDTO task1 = new TaskDTO();
        task1.setDescription("Latten");
        task1.setFinishing("lutro");
        task1.setWood_type("Lae");
        task1.setQuality("O");
        task1.setSize(19);
        task1.setWidth(52);
        task1.setLength(4500);
        task1.setQuantity(5);
        task1.setPrice(1500);
        taskDTOList.add(task1);

        TaskDTO task2 = new TaskDTO();
        task2.setDescription("Latten");
        task2.setFinishing("besäumt");
        task2.setWood_type("Lae");
        task2.setQuality("III/IV");
        task2.setSize(38);
        task2.setWidth(54);
        task2.setLength(4500);
        task2.setQuantity(2);
        task2.setPrice(1000);
        taskDTOList.add(task2);

        double result = costBenefitService.costValueFunction(taskDTOList);
        Assert.assertTrue(result < 0);
    }

    @Test
    public void OneArticlePartlyNotAviablePositiveOutcome() throws ServiceLayerException {
        TaskDTO task1 = new TaskDTO();
        task1.setDescription("Latten");
        task1.setFinishing("lutro");
        task1.setWood_type("Lae");
        task1.setQuality("O");
        task1.setSize(19);
        task1.setWidth(52);
        task1.setLength(4500);
        task1.setQuantity(5);
        task1.setPrice(1500);
        taskDTOList.add(task1);

        TaskDTO task2 = new TaskDTO();
        task2.setDescription("Latten");
        task2.setFinishing("besäumt");
        task2.setWood_type("Ta");
        task2.setQuality("O/III");
        task2.setSize(27);
        task2.setWidth(60);
        task2.setLength(5000);
        task2.setQuantity(100);
        task2.setPrice(20000);
        taskDTOList.add(task2);

        double result = costBenefitService.costValueFunction(taskDTOList);
        Assert.assertTrue(result > 65.0);
    }

    @Test
    public void OneArticlePartlyNotAviableNegativeOutcome() throws ServiceLayerException {
        TaskDTO task1 = new TaskDTO();
        task1.setDescription("Latten");
        task1.setFinishing("lutro");
        task1.setWood_type("Lae");
        task1.setQuality("O");
        task1.setSize(19);
        task1.setWidth(52);
        task1.setLength(4500);
        task1.setQuantity(5);
        task1.setPrice(1500);
        taskDTOList.add(task1);

        TaskDTO task2 = new TaskDTO();
        task2.setDescription("Latten");
        task2.setFinishing("besäumt");
        task2.setWood_type("Ta");
        task2.setQuality("O/III");
        task2.setSize(27);
        task2.setWidth(60);
        task2.setLength(5000);
        task2.setQuantity(100);
        task2.setPrice(10000);
        taskDTOList.add(task2);

        double result = costBenefitService.costValueFunction(taskDTOList);
        Assert.assertTrue(result < 0);
    }

    @Test
    public void NoArticelNeededAviablePositiveOutcome() throws ServiceLayerException {
        TaskDTO task1 = new TaskDTO();
        task1.setDescription("Latten");
        task1.setFinishing("besäumt");
        task1.setWood_type("Ta");
        task1.setQuality("O/III");
        task1.setSize(19);
        task1.setWidth(52);
        task1.setLength(4000);
        task1.setQuantity(5);
        task1.setPrice(15000);
        taskDTOList.add(task1);

        TaskDTO task2 = new TaskDTO();
        task2.setDescription("Latten");
        task2.setFinishing("besäumt");
        task2.setWood_type("Fi");
        task2.setQuality("O/III");
        task2.setSize(38);
        task2.setWidth(54);
        task2.setLength(4000);
        task2.setQuantity(10);
        task2.setPrice(10000);
        taskDTOList.add(task2);

        double result = costBenefitService.costValueFunction(taskDTOList);
        Assert.assertTrue(result > 0);
    }

    @Test
    public void NoArticelNeededAviableNegativeOutcome() throws ServiceLayerException {
        TaskDTO task1 = new TaskDTO();
        task1.setDescription("Latten");
        task1.setFinishing("besäumt");
        task1.setWood_type("Ta");
        task1.setQuality("O/III");
        task1.setSize(19);
        task1.setWidth(52);
        task1.setLength(4000);
        task1.setQuantity(5);
        task1.setPrice(1500);
        taskDTOList.add(task1);

        TaskDTO task2 = new TaskDTO();
        task2.setDescription("Latten");
        task2.setFinishing("besäumt");
        task2.setWood_type("Fi");
        task2.setQuality("O/III");
        task2.setSize(38);
        task2.setWidth(54);
        task2.setLength(4000);
        task2.setQuantity(10);
        task2.setPrice(5000);
        taskDTOList.add(task2);

        double result = costBenefitService.costValueFunction(taskDTOList);
        Assert.assertTrue(result < 0);
    }

    @Test(expected = ServiceLayerException.class)
    public void NullListError() throws ServiceLayerException {
        taskDTOList = null;
        costBenefitService.costValueFunction(taskDTOList);
    }

    @Test(expected = ServiceLayerException.class)
    public void EmptyListError() throws ServiceLayerException {
        costBenefitService.costValueFunction(taskDTOList);
    }

    @Test(expected = ServiceLayerException.class)
    public void NoBoxesFoundError() throws ServiceLayerException {
        TaskDTO task1 = new TaskDTO();
        task1.setDescription("Latten");
        task1.setFinishing("besäumt");
        task1.setWood_type("Ta");
        task1.setQuality("O/III");
        task1.setSize(19);
        task1.setWidth(52);
        task1.setLength(2000);
        task1.setQuantity(5);
        task1.setPrice(1500);
        taskDTOList.add(task1);

        costBenefitService.costValueFunction(taskDTOList);
    }

    @Test(expected = ServiceLayerException.class)
    public void NotPossibleToProduceError() throws ServiceLayerException {
        TaskDTO task1 = new TaskDTO();
        task1.setDescription("Latten");
        task1.setFinishing("besäumt");
        task1.setWood_type("Ta");
        task1.setQuality("O/III");
        task1.setSize(300);
        task1.setWidth(530);
        task1.setLength(4000);
        task1.setQuantity(5);
        task1.setPrice(1500);
        taskDTOList.add(task1);

        costBenefitService.costValueFunction(taskDTOList);
    }

    @After
    public void tearDown() {
        connection = null;
        lumberDAO = null;
        timberDAO = null;
        costBenefitService = null;
    }

}
