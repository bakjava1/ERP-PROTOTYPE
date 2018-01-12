import at.ac.tuwien.sepm.assignment.group02.client.rest.LumberController;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.server.converter.LumberConverter;
import at.ac.tuwien.sepm.assignment.group02.server.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.LumberDAO;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.LumberDAOJDBC;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TaskDAO;
import at.ac.tuwien.sepm.assignment.group02.server.rest.LumberControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.server.rest.TaskControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.server.service.LumberService;
import at.ac.tuwien.sepm.assignment.group02.server.service.LumberServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.server.service.TaskService;
import at.ac.tuwien.sepm.assignment.group02.server.util.DBUtil;
import at.ac.tuwien.sepm.assignment.group02.server.validation.ValidateLumber;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Created by raquelsima on 01.01.18.
 */
public class updateLumberServerSideTest {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static LumberService lumberService;
    private static RestTemplate restTemplate;
    private static LumberConverter lumberConverter;
    private static LumberControllerImpl lumberController;
    private static ValidateLumber validateLumber=new ValidateLumber();

    private static Connection dbConnection;
    private static LumberDAO lumberDAO;

    private static Lumber lumber;
    private static LumberDTO lumberDTO;


 @BeforeClass
 public  static void setUp() throws Exception {
  LOG.debug("update lumber test setup initiated");
  dbConnection = DBUtil.getConnection();
  lumberDAO = new LumberDAOJDBC(dbConnection);


  lumberService = new LumberServiceImpl(lumberDAO, new LumberConverter(), validateLumber);

  lumberController = new LumberControllerImpl(lumberService);


 }

 @Test
 public void testUpdate_lumber_valid() throws Exception {
  LumberService lumberService
          = new LumberServiceImpl(lumberDAO,lumberConverter,validateLumber);

  Lumber lumber = new Lumber();
  when(lumberConverter.convertRestDTOToPlainObject(any(LumberDTO.class))).thenReturn(lumber);

  lumberService.updateLumber(any(LumberDTO.class));

  verify(lumberConverter, Mockito.times(1)).convertRestDTOToPlainObject(any(LumberDTO.class));
  verify(lumberDAO, Mockito.times(1)).updateLumber(lumber);
 }

 @Test(expected = ServiceLayerException.class)
 public void testUpdate_lumber_persist() throws Exception {

  LumberService lumberService
          = new LumberServiceImpl(lumberDAO,lumberConverter,validateLumber);

  doThrow(PersistenceLayerException.class).when(lumberDAO).updateLumber(any(Lumber.class));

  lumberService.updateLumber(any(LumberDTO.class));

 }

 @After
 public void tearDown() throws Exception {
  LOG.debug("update lumber test teardown initiated");
  DBUtil.closeConnection();
  LOG.debug("update lumber test teardown completed");

 }
}
