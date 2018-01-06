import at.ac.tuwien.sepm.assignment.group02.client.converter.LumberConverter;
import at.ac.tuwien.sepm.assignment.group02.client.rest.AssignmentController;
import at.ac.tuwien.sepm.assignment.group02.client.rest.LumberController;
import at.ac.tuwien.sepm.assignment.group02.client.rest.LumberControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.client.rest.TaskController;
import at.ac.tuwien.sepm.assignment.group02.client.service.LumberService;
import at.ac.tuwien.sepm.assignment.group02.client.service.LumberServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.client.validation.ValidateAssignmentDTO;
import at.ac.tuwien.sepm.assignment.group02.client.validation.Validator;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;

import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by raquelsima on 01.01.18.
 */
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
    private static ValidateAssignmentDTO validateAssignmentDTO;
    private static LumberDTO[] lumberDTOS;

    @Before
    public  void setUp() throws Exception {
        LOG.debug("update lumber test setup initiated");

        restTemplate = mock(RestTemplate.class);
        lumberController = new LumberControllerImpl(restTemplate);
        mockLumberController = mock(LumberControllerImpl.class);
        validator = mock(Validator.class);


       // lumberService = new LumberServiceImpl(lumberController, validator);
        //lumberServiceEmptyList = new LumberServiceImpl(mockLumberController, validator);

        lumberDTOS = new LumberDTO[0];

        LOG.debug("assignment management test setup completed");

    }


    @Test
    public void test_name() throws Exception {
    }

    @Test
    public void test_lumbe_name() throws Exception {
    }

    @Test
    public void test_lumber_update() throws Exception {
    }

    @AfterClass
    public static void tearDown() throws Exception {

    }
}



