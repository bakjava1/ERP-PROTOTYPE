package serviceLayer;

import at.ac.tuwien.sepm.assignment.group02.client.configuration.RestTemplateConfiguration;
import at.ac.tuwien.sepm.assignment.group02.client.converter.TimberConverter;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.TimberController;
import at.ac.tuwien.sepm.assignment.group02.client.rest.TimberControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.client.service.TimberService;
import at.ac.tuwien.sepm.assignment.group02.client.service.TimberServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.client.validation.PrimitiveValidator;
import at.ac.tuwien.sepm.assignment.group02.client.validation.ValidateTimber;
import at.ac.tuwien.sepm.assignment.group02.client.validation.Validator;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.persistence.PersistenceContextType;
import java.lang.invoke.MethodHandles;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class TimberClientServiceImplTest {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static Timber timber;
    private static TimberConverter timberConverter;
    private static RestTemplate restTemplate;
    private static TimberController timberController;
    private static TimberService timberService;
    private static ValidateTimber validator;


    @BeforeClass
    public static void setup(){
        LOG.debug("timber service test at client initiated");
        timberConverter = new TimberConverter();
        validator = new ValidateTimber(new PrimitiveValidator());

    }

    @Before
    public void before(){
        timber = new Timber(10, 2);
        restTemplate = mock(RestTemplate.class);
        timberController = new TimberControllerImpl(restTemplate);
        timberService = new TimberServiceImpl(timberController, timberConverter, validator);

    }

    @Test(expected = ServiceLayerException.class)
    public void testAddTimberRestLayerException() throws ServiceLayerException, PersistenceLayerException {
        LOG.debug("add timber test with rest layer exception");

        TimberDTO timberDTO = timberConverter.convertPlainObjectToRestDTO(timber);

        when(restTemplate.getForObject("http://"+RestTemplateConfiguration.host+":"+RestTemplateConfiguration.port+"/getNumberOfBoxes", Integer.class)).thenReturn(42);
        Mockito.when( restTemplate.postForObject(anyString(), any(TimberDTO.class), eq(TimberDTO.class))).thenThrow(RestClientException.class);
        timberService.addTimber(timber);
        Mockito.verify(restTemplate, times(1)).postForObject("http://"+RestTemplateConfiguration.host+":"+RestTemplateConfiguration.port+"/createTimber", timberDTO, TimberDTO.class);
    }

    @Test
    public void testAddTimberPositiveTest() throws Exception {

        LOG.debug("add timber test with no error");

        when(restTemplate.getForObject("http://"+RestTemplateConfiguration.host+":"+RestTemplateConfiguration.port+"/getNumberOfBoxes", Integer.class)).thenReturn(42);
        timberService.addTimber(timber);
        Mockito.verify(restTemplate, times(1)).postForObject(anyString(), any(TimberDTO.class), eq(TimberDTO.class));

    }

    @Test(expected = InvalidInputException.class)
    public void testSelectedBoxNumberIsNotValid() throws Exception {
        LOG.debug("test selected box number is not valid");

        when(restTemplate.getForObject("http://localhost:8080/getNumberOfBoxes", Integer.class)).thenReturn(1);
        timberService.addTimber(timber);
        verify(timberController, times(1)).getNumberOfBoxes();
    }


    @Test(expected = ServiceLayerException.class)
    public void testGetNumOfBoxesNegativeTest() throws Exception {
        LOG.debug("test timber get number of boxes negative");

        when(restTemplate.getForObject("http://localhost:8080/getNumberOfBoxes", Integer.class)).thenThrow(PersistenceLayerException.class);
        timberService.getNumberOfBoxes();
    }

    @Test
    public void testGetNumOfBoxesPositiveTest() throws Exception {
        LOG.debug("test timber get number of boxes negative");

        when(restTemplate.getForObject("http://localhost:8080/getNumberOfBoxes", Integer.class)).thenReturn(2);

        assertEquals(2, timberService.getNumberOfBoxes());
    }

    @After
    public void after(){
        timber = null;
    }

    @AfterClass
    public static void teardown(){

        LOG.debug("add timber test shutdown");
    }
}
