import at.ac.tuwien.sepm.assignment.group02.client.converter.LumberConverter;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.client.rest.LumberController;
import at.ac.tuwien.sepm.assignment.group02.client.service.LumberService;
import at.ac.tuwien.sepm.assignment.group02.client.validation.Validator;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;

/**
 * Created by raquelsima on 01.01.18.
 */

//@RunWith(MockitoJUnitRunner.class)
public class updateLumberClientSideTest {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    private static Lumber lumber;
    private static LumberDTO lumberDTO;
    private static LumberService lumberService, lumberServiceEmptyList;
    private static LumberController lumberController,mockLumberController;
    private static LumberConverter lumberConverter;
    private static Validator validator ;
    private static RestTemplate restTemplate;
    private static Connection dbConnection;
    private static LumberDTO[] lumberDTOS;



    /*@Before
    public  void setUp() throws Exception {
        LOG.debug("update lumber test setup initiated");

        restTemplate = mock(RestTemplate.class);
        lumberController = new LumberControllerImpl(restTemplate);
        mockLumberController = mock(LumberControllerImpl.class);
        validator = mock(Validator.class);

        lumberDTOS = new LumberDTO[0];

        LOG.debug("assignment management test setup completed");

    }*/

    /*@Ignore
    @Test(expected = InvalidInputException.class)
    public void test_Update_Lumber_InvalidInput() throws Exception {

    }

    @Ignore
    @Test(expected = ServiceLayerException.class)
    public void test_Update_Lumber_PersistenceLayerException() throws Exception {
    }*/

    /*@AfterClass
    public static void tearDown() throws Exception {

    }*/
}



