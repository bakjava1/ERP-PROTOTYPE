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
import at.ac.tuwien.sepm.assignment.group02.client.validation.PrimitiveValidator;
import at.ac.tuwien.sepm.assignment.group02.client.validation.Validator;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by raquelsima on 01.01.18.
 */
public class deleteLumberClientSideTest {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static LumberController lumberController;
    private static LumberConverter lumberConverter;
    private static TaskController taskController;
    private static RestTemplate restTemplate;
    private static PrimitiveValidator primitiveValidator = new PrimitiveValidator();
    private static Validator validator = new Validator(primitiveValidator);

    private static Lumber lumber;
    private static LumberDTO lumberDTO;
    private static LumberService lumberService;

    @BeforeClass
    public static void setUp() {
        LOG.debug("delete lumber setup initiated");
        restTemplate = mock(RestTemplate.class);
        lumberController=new LumberControllerImpl(restTemplate);
        lumberConverter=mock(LumberConverter.class);
        taskController=new TaskControllerImpl(restTemplate);
        lumberService=new LumberServiceImpl(lumberController,lumberConverter,taskController,validator,primitiveValidator);

        lumber= new Lumber();
        lumber.setId(1);

        lumberDTO=new LumberDTO();
        lumberDTO.setId(1);
        LOG.debug("delete lumber setup completed");
    }

    @Ignore
    @Test
    public void testDelete_Lumber_client_serviceLayer() throws ServiceLayerException {
        LOG.debug("test delete lumber in client service layer");
        lumberService.deleteLumber(lumber);
        verify(restTemplate, times(1))
                .delete(Mockito.matches(".*deleteLumber"), any(LumberDTO.class), eq(LumberDTO.class));
    }

    @Test (expected = PersistenceLayerException.class)
    public void deleteLumber_throws_RestClientException() throws PersistenceLayerException {
        LOG.debug("delete lumber throws RestClientException in client rest interface");
        doThrow(RestClientException.class).when(restTemplate)
                .delete(Mockito.matches(".*deleteLumber"), any(LumberDTO.class), eq(LumberDTO.class));
        lumberController.removeLumber(lumberDTO);
    }

    @Test (expected = PersistenceLayerException.class)
    public void deleteLumber_throws_HttpStatusCodeException() throws PersistenceLayerException {
        LOG.debug("delete lumber throws HttpStatusCodeException in client rest interface");
        doThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND)).when(restTemplate)
                .delete(Mockito.matches(".*deleteLumber"), any(LumberDTO.class), eq(LumberDTO.class));
        lumberController.removeLumber(lumberDTO);
    }

}
