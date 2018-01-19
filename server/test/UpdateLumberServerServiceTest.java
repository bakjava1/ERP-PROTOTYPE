import at.ac.tuwien.sepm.assignment.group02.server.converter.LumberConverter;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.LumberDAO;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.LumberDAOJDBC;
import at.ac.tuwien.sepm.assignment.group02.server.service.LumberService;
import at.ac.tuwien.sepm.assignment.group02.server.service.LumberServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.server.util.DBUtil;
import at.ac.tuwien.sepm.assignment.group02.server.validation.ValidateLumber;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;





/**
 * Created by raquelsima on 16.01.18.
 */
@Ignore
public class UpdateLumberServerServiceTest {



    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    private static LumberService lumberService;
    private static ValidateLumber validateLumber=new ValidateLumber();
    private static Connection dbConnection;
    private static LumberDAO lumberDAO;

    @BeforeClass
    public  static void setUp() throws Exception {
        LOG.debug("update lumber test setup initiated");
        dbConnection = DBUtil.getConnection();
        lumberDAO = new LumberDAOJDBC(dbConnection);


        lumberService = new LumberServiceImpl(lumberDAO, new LumberConverter(), validateLumber);

        //lumberController = new LumberController(lumberService);


    }

    @Test(expected = ServiceLayerException.class)

    public void testUpdate_lumber_valid() throws Exception {
        LOG.debug("update lumber test with a valid value");

        RestTemplate restTemplate = new RestTemplate();

    }

    @Test(expected=ServiceLayerException.class)
/* public void testUpdate_lumber_positive() throws Exception{
  // prepare data and mock's behaviour
  // here the stub is the updated lumber object with ID equal to ID of
  // lumber need to be updated
  LumberDTO lumberDTO = new LumberDTO();
  when(lumberService.getLumberById(any(Integer.class))).thenReturn(lumberDTO);

  // execute
  MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(String.valueOf(URL))
          .contentType(MediaType.APPLICATION_JSON_UTF8)
          .accept(MediaType.APPLICATION_JSON_UTF8)
          .content(BeanUtils.copyProperties(lumberDTO))
          .andReturn());

  // verify
  int status = result.getResponse().getStatus();
  assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

  // verify that service method was called once
  verify(lumberService).updateLumber(any(LumberDTO.class));

 }*/

    @After
    public void tearDown() throws Exception {
        LOG.debug("update lumber test teardown initiated");
        DBUtil.closeConnection();
        LOG.debug("update lumber test teardown completed");

    }
}
