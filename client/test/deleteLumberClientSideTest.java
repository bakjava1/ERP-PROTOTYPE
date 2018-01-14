import at.ac.tuwien.sepm.assignment.group02.client.converter.LumberConverter;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.LumberController;
import at.ac.tuwien.sepm.assignment.group02.client.rest.LumberControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.client.rest.TaskController;
import at.ac.tuwien.sepm.assignment.group02.client.rest.TaskControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.client.service.LumberService;
import at.ac.tuwien.sepm.assignment.group02.client.service.LumberServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.client.validation.Validator;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import ch.qos.logback.core.db.dialect.DBUtil;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;

import static javafx.scene.input.DataFormat.URL;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

/**
 * Created by raquelsima on 01.01.18.
 */
public class deleteLumberClientSideTest {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static LumberService lumberService;
    private static LumberController lumberController;
    private static LumberConverter lumberConverter;
    private static TaskController taskController;
    private static RestTemplate restTemplate;
    private static Validator validator = new Validator();

    private static Lumber lumber;
    private static LumberDTO lumberDTO;
    private static LumberService lumberServiceMock;
    private MockMvc mockMvc;



    @BeforeClass
    public static void setUp() {
        LOG.debug("delete lumber setup initiated");
        restTemplate = mock(RestTemplate.class);
        lumberController=new LumberControllerImpl(restTemplate);
        lumberConverter=new LumberConverter();
        taskController=new TaskControllerImpl(restTemplate);
        lumberService=new LumberServiceImpl(lumberController,lumberConverter,taskController,validator);

        lumber= new Lumber();
        lumber.setId(1);

        lumberDTO=new LumberDTO();
        lumberDTO.setId(1);
        LOG.debug("delete lumber setup completed");
    }

    @Test
    public void testDelete_Lumber_client_serviceLayer() throws ServiceLayerException {
        LOG.debug("test delete lumber in client service layer");

         lumberService.deleteLumber(lumber);
        verify(restTemplate, times(1)).put(any(), any(OrderDTO.class),any(OrderDTO.class));
    }

    @Test (expected = PersistenceLayerException.class)
    public void deleteLumber_throws_RestClientException() throws PersistenceLayerException {
        LOG.debug("delete lumber throws RestClientException in client rest interface");
        lumberDTO=new LumberDTO();

       // doThrow(RestClientException.class).when(restTemplate).delete(any(), any(lumberDTO.class), any(lumberDTO.class));

        lumberController.removeLumber(lumberDTO);

    }


    @Test (expected = PersistenceLayerException.class)
    public void deleteLumber_throws_HttpStatusCodeException() throws PersistenceLayerException {
        LOG.debug("delete lumber throws HttpStatusCodeException in client rest interface");

        // execute - delete the record added while initializing database with
        // test data

        ResponseEntity<Void> responseEntity = restTemplate.exchange(URL + "{id}",
                HttpMethod.DELETE,
                null,
                Void.class);

        // verify
        int status = responseEntity.getStatusCodeValue();
        assertEquals("Incorrect Response Status", HttpStatus.GONE.value(), status);

    }

    @Test
    public void testDeleteLumber() throws Exception {
        // prepare data and mock's behaviour
        Lumber lumber = new Lumber(1,"Taffel","Ta",30);
        when(lumberServiceMock.getLumber(any(Integer.class))).thenReturn(lumber);

        // execute
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(URL + "{id}", new Long(1)))
                .andReturn();

        // verify
        int status = result.getResponse().getStatus();
        assertEquals("Incorrect Response Status", HttpStatus.GONE.value(), status);

        // verify that service method was called once
        verify(lumberServiceMock).deleteLumber(any());

    }
    
}
