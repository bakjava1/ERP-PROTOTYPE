import at.ac.tuwien.sepm.assignment.group02.client.rest.LumberController;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.server.converter.LumberConverter;
import at.ac.tuwien.sepm.assignment.group02.server.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.LumberDAO;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TaskDAO;
import at.ac.tuwien.sepm.assignment.group02.server.rest.LumberControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.server.rest.TaskControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.server.service.LumberService;
import at.ac.tuwien.sepm.assignment.group02.server.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Created by raquelsima on 01.01.18.
 */
public class updateLumberServerSideTest {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

   // private static LumberService lumberService;
    private static RestTemplate restTemplate;
    private static LumberConverter lumberConverter;
    //private static LumberControllerImpl lumberController;


    private static Connection dbConnection;
    private static LumberDAO lumberDAO;
    private static Lumber lumber1 = new Lumber();
    private static Lumber lumber2 = new Lumber();
    private static Lumber lumber3 = new Lumber();

    private static LumberDTO lumberDTO1 = new LumberDTO();
    private static LumberDTO lumberDTO2 = new LumberDTO();

    private static Lumber lumber;
    private static LumberDTO lumberDTO;






}
