import at.ac.tuwien.sepm.assignment.group02.server.converter.TimberConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.EntityCreationException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TimberDAO;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TimberDAOJDBC;
import at.ac.tuwien.sepm.assignment.group02.server.rest.TimberControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.server.service.TimberService;
import at.ac.tuwien.sepm.assignment.group02.server.service.TimberServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.server.util.DBUtil;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class AddTimberTest {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static Connection dbConnection;

    private static TimberDAO timberDAO;

    private static TimberService timberService;
    private static TimberService timberServiceMock;

    private static TimberControllerImpl timberController;

    private static TimberDTO timberDTONegativeAmount = new TimberDTO();
    private static TimberDTO timberDTO1 = new TimberDTO();
    private static TimberDTO timberDTO2 = new TimberDTO();

    private static Timber timber1 = new Timber();
    private static Timber timber2 = new Timber();


    @BeforeClass
    public static void setup() {
        LOG.debug("add timber test setup initiated");
        dbConnection = DBUtil.getConnection();

        timberDAO = new TimberDAOJDBC(dbConnection);

        timberService = new TimberServiceImpl(timberDAO, new TimberConverter());

        timberController = new TimberControllerImpl(timberService);

        timberDTONegativeAmount.setBox_id(1);
        timberDTONegativeAmount.setAmount(-1);

        timberDTO1.setBox_id(1);
        timberDTO1.setAmount(12);

        timberDTO2.setBox_id(1);
        timberDTO2.setAmount(38);

        timber1.setBox_id(1);
        timber1.setAmount(12);

        timber2.setBox_id(1);
        timber2.setAmount(38);

        LOG.debug("add timber test setup completed");
    }

    @Test
    public void testGetNumberOfBoxes() throws ServiceLayerException, PersistenceLayerException {
        LOG.debug("test get number of Boxes initiated");

        timberService.numberOfBoxes();
    }

    @Test
    public void testAddTimberPersistence() throws PersistenceLayerException {
        int startAmount = getTimberAmount();

        timberDAO.createTimber(timber1);
        timberDAO.createTimber(timber2);

        int endAmount = startAmount + timber1.getAmount() + timber2.getAmount();
        int currentAmount = getTimberAmount();

        Assert.assertEquals(endAmount,currentAmount);
    }

    //TODO test not working properly: error in db
    @Test
    public void testAddTimberController() throws PersistenceLayerException, EntityCreationException {
        int startAmount = getTimberAmount();

        timberController.createTimber(timberDTO1);
        timberController.createTimber(timberDTO2);

        int endAmount = startAmount + timberDTO1.getAmount() + timberDTO2.getAmount();
        int currentAmount = getTimberAmount();

        Assert.assertEquals(endAmount,currentAmount);
    }

    @Test(expected = ServiceLayerException.class)
    public void testAddTimberServiceAmountNegative() throws PersistenceLayerException, ServiceLayerException {
        timberService.addTimber(timberDTONegativeAmount);
    }
    
    //TODO test not working properly: error in db
    @Test
    public void testAddTimberService() throws PersistenceLayerException, ServiceLayerException {
        int startAmount = getTimberAmount();

        timberService.addTimber(timberDTO1);
        timberService.addTimber(timberDTO2);

        int endAmount = startAmount + timberDTO1.getAmount() + timberDTO2.getAmount();
        int currentAmount = getTimberAmount();

        Assert.assertEquals(endAmount,currentAmount);
    }

    @AfterClass
    public static void teardown() {
        LOG.debug("add timber test teardown initiated");

        DBUtil.closeConnection();

        LOG.debug("add timber test teardown completed");
    }

    private int getTimberAmount() throws PersistenceLayerException {
        int currentAmount = 0;

        String selectSentence = "SELECT AMOUNT FROM TIMBER WHERE ID = 1";

        try {
            PreparedStatement stmt = dbConnection.prepareStatement(selectSentence);
            ResultSet rs = stmt.executeQuery();

            rs.next();
            currentAmount = rs.getInt(1);

            stmt.close();
            rs.close();
        } catch (SQLException e) {
            LOG.error("SQL Exception: " + e.getMessage());
            throw new PersistenceLayerException("Database Error");
        }
        return currentAmount;
    }
}
