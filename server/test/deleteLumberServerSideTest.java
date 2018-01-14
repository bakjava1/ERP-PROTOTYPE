import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import at.ac.tuwien.sepm.assignment.group02.server.converter.LumberConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.EntityCreationException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.LumberDAO;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.LumberDAOJDBC;
import at.ac.tuwien.sepm.assignment.group02.server.rest.LumberControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.server.service.LumberService;
import at.ac.tuwien.sepm.assignment.group02.server.service.LumberServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.server.util.DBUtil;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;

/**
 * Created by raquelsima on 01.01.18.
 */
public class deleteLumberServerSideTest {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    private static Connection dbConnection;
    private static LumberDAO lumberDAO;
    private static Lumber lumber1;
    private static Lumber lumber2;
    private static Lumber lumber3;
    private static LumberDTO lumberDTO1;
    private static LumberDTO lumberDTO2;
    private static LumberDTO lumberDTO3;

    private static LumberControllerImpl lumberController;
    private static LumberConverter lumberConverter;
    private static LumberService lumberService;
    private static RestTemplate restTemplate;


    @BeforeClass
    public static void setUp() throws EntityCreationException {

        LOG.debug("lumber management test setup initiated");
        dbConnection = DBUtil.getConnection();
        lumberDAO=new LumberDAOJDBC(dbConnection);

        lumber1=new Lumber();
        lumber1.setId(1);
        lumber2=new Lumber();
        lumber2.setId(2);
        lumber3=new Lumber();
        lumber3.setId(3);

        lumberDTO1=new LumberDTO();
       // lumberDTO1.setID(4);
        lumberDTO2=new LumberDTO();
        //lumberDTO2.setID(5);
        lumberDTO3=new LumberDTO();
        //lumberDTO3.setID(6);

        lumberController= new LumberControllerImpl(lumberService);
       // lumberService=new LumberServiceImpl(lumberDAO,lumberConverter);
        lumberConverter=new LumberConverter();

        LOG.debug("lumber management test setup completed");

    }

    @Before
    public void iniDBConnection(){

        dbConnection = DBUtil.getConnection();
        lumberDAO=new LumberDAOJDBC(dbConnection);
        lumberConverter=new LumberConverter();
        //lumberService=new LumberServiceImpl(lumberDAO,lumberConverter);
    }

    @Test(expected = PersistenceLayerException.class)
    public void deleteLumber_throws_PersistenceLayerExeption_without_DBConnection() throws PersistenceLayerException {
        LOG.debug("testing for lumber deletion in persistence layer without DB connection");

       // DBUtil.closeConnection();
        lumberDAO.deleteLumber(lumber1);
    }

    @Test
    public void testDeleteLumber_server_PersistenceLayer() throws PersistenceLayerException {


        LOG.debug("delete lumber throws HttpStatusCodeException in client rest interface");
        doThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND)).when(restTemplate).put(any(), any(OrderDTO.class), any(OrderDTO.class));
        lumberController.removeLumber(lumberDTO1);

    }

    @AfterClass
    public static void tearDown() {
        LOG.debug("lumber management test teardown initiated");
        DBUtil.closeConnection();
        LOG.debug("lumber management test teardown completed");

    }

}
