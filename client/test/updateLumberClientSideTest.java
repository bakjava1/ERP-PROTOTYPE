import at.ac.tuwien.sepm.assignment.group02.client.converter.LumberConverter;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.LumberNotFountException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.AssignmentController;
import at.ac.tuwien.sepm.assignment.group02.client.rest.LumberController;
import at.ac.tuwien.sepm.assignment.group02.client.rest.LumberControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.client.rest.TaskController;
import at.ac.tuwien.sepm.assignment.group02.client.service.LumberService;
import at.ac.tuwien.sepm.assignment.group02.client.service.LumberServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.client.validation.ValidateAssignmentDTO;
import at.ac.tuwien.sepm.assignment.group02.client.validation.Validator;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import ch.qos.logback.core.db.dialect.DBUtil;
import org.junit.*;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.web.WebAppConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;


import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by raquelsima on 01.01.18.
 */

//@RunWith(MockitoJUnitRunner.class)

@RunWith(SpringJUnit4ClassRunner.class)
public class updateLumberClientSideTest {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static LumberDTO lumberDTO;
    //private static LumberService lumberServiceMock, lumberServiceEmptyList;
    private static LumberController lumberController,mockLumberController;
    private static LumberConverter lumberConverter;
    private static Validator validator ;
    private static RestTemplate restTemplate;
    private static Connection dbConnection;
    private static LumberDTO[] lumberDTOS;

    @MockBean
    private static LumberService lumberServiceMock;
    private MockMvc mockMvc;


    @BeforeClass
    public  static void setUp() throws Exception {
        LOG.debug("update lumber test setup initiated");

        restTemplate = mock(RestTemplate.class);
        lumberController = new LumberControllerImpl(restTemplate);
        mockLumberController = mock(LumberControllerImpl.class);
        validator = mock(Validator.class);

        lumberDTOS = new LumberDTO[0];

        LOG.debug("update lumber test setup completed");

    }



    @Test
    public void findById_LumberNotFound_ShouldReturnHttpStatusCode404() throws Exception {
        when(lumberServiceMock.getLumber(1)).thenThrow(new LumberNotFountException(""));

        mockMvc.perform(get("/lumbers/{id}", 1L))
                .andExpect(status().isNotFound());

        verify(lumberServiceMock, times(1)).getLumber(1);
        verifyNoMoreInteractions(lumberServiceMock);
    }

    @Test
    public void findById_LumberFound_ShouldReturnFoundLumber() throws Exception {
        Lumber found = new Lumber();

        when(lumberServiceMock.getLumber(1)).thenReturn(found);

        verifyNoMoreInteractions(lumberServiceMock);

    }


    @AfterClass
    public static void tearDown() throws Exception {

    }

}



