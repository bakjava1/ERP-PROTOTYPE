import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import at.ac.tuwien.sepm.assignment.group02.server.converter.LumberConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.EntityCreationException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.EntityNotFoundException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.LumberDAO;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.LumberDAOJDBC;
import at.ac.tuwien.sepm.assignment.group02.server.rest.LumberControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.server.service.LumberService;
import at.ac.tuwien.sepm.assignment.group02.server.service.LumberServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.server.util.DBUtil;
import at.ac.tuwien.sepm.assignment.group02.server.validation.ValidateLumber;
import org.junit.*;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

/**
 * Created by raquelsima on 06.01.18.
 */
public class LumberManagementServerSideTest {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static Connection dbConnection;
    private static LumberDAO lumberDAO;
    private static Lumber lumber1 = new Lumber();
    private static Lumber lumber2 = new Lumber();
    private static Lumber lumber3 = new Lumber();
    private static LumberDTO lumberDTO1 = new LumberDTO();
    private static LumberDTO lumberDTO2 = new LumberDTO();

    private static LumberControllerImpl lumberController;
    private static LumberService lumberService;
    private static LumberConverter lumberConverter;
    private static ValidateLumber validateLumber;

    @BeforeClass
    public static void setup() throws EntityCreationException {

        dbConnection = DBUtil.getConnection();
        lumberDAO = new LumberDAOJDBC(dbConnection);

        activateLumbers();
        lumber1.setId(1);
        lumber1.setId(1);
        lumber2.setId(2);
        lumber2.setId(1);

        lumber3.setId(3);
        lumber3.setId(2);
        lumber3.setDescription("Latten");
        lumber3.setFinishing("Prismiert");
        lumber3.setWood_type("Ta");
        lumber3.setQuality("I/III");
        lumber3.setSize(22);
        lumber3.setWidth(48);
        lumber3.setLength(3000);
        lumber3.setQuantity(40);

        lumberDTO1.setId(4);
        lumberDTO1.setId(3);
        lumberDTO1.setDescription("Latten");
        lumberDTO1.setFinishing("Prismiert");
        lumberDTO1.setWood_type("Ta");
        lumberDTO1.setQuality("I/III");
        lumberDTO1.setSize(22);
        lumberDTO1.setWidth(48);
        lumberDTO1.setLength(3000);
        lumberDTO1.setQuantity(40);

        lumberDTO2.setId(5);
        lumberDTO2.setDescription("Latten");
        lumberDTO2.setFinishing("Prismiert");
        lumberDTO2.setWood_type("Ta");
        lumberDTO2.setQuality("I/III");
        lumberDTO2.setSize(22);
        lumberDTO2.setWidth(48);
        lumberDTO2.setLength(3000);
        lumberDTO2.setQuantity(40);

        validateLumber = new ValidateLumber();
        lumberConverter = new LumberConverter();
        lumberService = new LumberServiceImpl(lumberDAO, lumberConverter, validateLumber);
        lumberController = new LumberControllerImpl(lumberService);

        LOG.debug("task management test setup initiated");
    }

    @Before
    public void initDBConnection() throws EntityCreationException {
        dbConnection = DBUtil.getConnection();
        lumberDAO = new LumberDAOJDBC(dbConnection);

        lumberConverter = new LumberConverter();
        lumberService = new LumberServiceImpl(lumberDAO, lumberConverter, validateLumber);
        lumberController = new LumberControllerImpl(lumberService);
    }

    @Test(expected = PersistenceLayerException.class)
    public void deleteLumber_throws_Exception_in_persistenceLayer_without_DBConnection() throws PersistenceLayerException {

        LOG.debug("testing for lumber deletion in persistence layer without DB connection");

        DBUtil.closeConnection();
        lumberDAO.deleteLumber(lumber1);
    }

    @Ignore
    @Test
    public void testDeleteLumber_server_persistenceLayer() throws PersistenceLayerException {
        LOG.debug("testing for lumber deletion in server persistence layer");

        int lumberCountBeforeDeletion = getActiveLumbers();

        lumberDAO.deleteLumber(lumber1);
        lumberCountBeforeDeletion--;
        lumberDAO.deleteLumber(lumber2);
        lumberCountBeforeDeletion--;

        int lumberCountAfterDeletion = getActiveLumbers();

        assertEquals(lumberCountBeforeDeletion,lumberCountAfterDeletion);
    }

    @Ignore
    @Test
    public void testDeleteLumber_server_restController() throws ServiceLayerException {
        LOG.debug("testing for lumber deletion in server rest controller");

        int lumberCountBeforeDeletion = getActiveLumbers();

        lumberController.removeLumber(lumberDTO1);
        lumberCountBeforeDeletion--;
        lumberController.removeLumber(lumberDTO2);
        lumberCountBeforeDeletion--;

        int lumberCountAfterDeletion = getActiveLumbers();

        assertEquals(lumberCountBeforeDeletion,lumberCountAfterDeletion);
    }

    @AfterClass
    public static void teardown() {
        LOG.debug("task management test teardown initiated");

        DBUtil.closeConnection();

        LOG.debug("task management test teardown completed");
    }



    private static void activateLumbers() {
        String activateTasks = "UPDATE LUMBER SET QUANTITY = 0 WHERE ID = 1 OR ID = 2 OR ID = 3 OR ID = 4 OR ID = 5";

        try {
            PreparedStatement ps = dbConnection.prepareStatement(activateTasks);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            LOG.info("Error at praparing test for deleting lumbers");
        }
    }

    private int getActiveLumbers() {
        int count = 0;
        String getLumber = "SELECT COUNT(ID) FROM LUMBER WHERE QUANTITY = 0";

        try {
            PreparedStatement ps = dbConnection.prepareStatement(getLumber);
            ps.execute();

            ResultSet rs = ps.getResultSet();
            rs.next();

            count = rs.getInt(1);

            rs.close();
            ps.close();

            return count;
        } catch (SQLException e) {
            System.out.println("error at testing for deleting lumbers " + e.getMessage());
        }

        return count;
    }
}
