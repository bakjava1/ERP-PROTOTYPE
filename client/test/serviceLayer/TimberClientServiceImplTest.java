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
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import javax.persistence.PersistenceContextType;
import java.lang.invoke.MethodHandles;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class TimberClientServiceImplTest {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static Timber timber;
    private static TimberConverter timberConverter;
    @Mock
    private static TimberController timberController;
    private static TimberService timberService;


    @BeforeClass
    public static void setup(){
        LOG.debug("add timber test initiated");
        timberConverter = new TimberConverter();

    }

    @Before
    public void before(){
        timber = new Timber();
        timberController = mock(TimberControllerImpl.class);
        timberService = new TimberServiceImpl(timberController,timberConverter,new ValidateTimber(new PrimitiveValidator()));

    }

    @Test(expected = ServiceLayerException.class)
    public void testAddTimberNegativeTest() throws Exception {

        LOG.debug("add timber negative test");
        timber = new Timber(1, 2);
        when(timberController.getNumberOfBoxes()).thenReturn(42);
        doThrow(PersistenceLayerException.class).when(timberController).createTimber(any(TimberDTO.class));

        timberService.addTimber(timber);
    }

    @Test(expected = ServiceLayerException.class)
    public void testAddTimberRestLayerException() throws Exception {
        LOG.debug("add timber test with rest layer exception");
        timber = new Timber(1, 1);

        doThrow(PersistenceLayerException.class).when(timberController).createTimber(any(TimberDTO.class));
        //when(timberController.createTimber(timberDTO);
        // when(restTemplate.getForObject("http://localhost:8080/getNumberOfBoxes", Integer.class)).thenReturn(42);
        // when(restTemplate.postForObject("http://localhost:8080/createTimber", timberDTO, TimberDTO.class)).thenReturn(timberDTO);

        timberService.addTimber(timber);
        verify(timberController, times(1)).createTimber(any(TimberDTO.class));
    }

    @Test
    public void testAddTimberPositiveTest() throws Exception {

        LOG.debug("add timber test with no error");
        timber = new Timber(1, 1);

        TimberDTO timberDTO = timberConverter.convertPlainObjectToRestDTO(timber);
        when(timberController.getNumberOfBoxes()).thenReturn(42);
        //when(timberController.createTimber(timberDTO);
        // when(restTemplate.getForObject("http://localhost:8080/getNumberOfBoxes", Integer.class)).thenReturn(42);
       // when(restTemplate.postForObject("http://localhost:8080/createTimber", timberDTO, TimberDTO.class)).thenReturn(timberDTO);

        timberService.addTimber(timber);
        verify(timberController, times(1)).getNumberOfBoxes();
    }

    @Test(expected = ServiceLayerException.class)
    public void testSelectedBoxNumberIsNotValid() throws Exception {
        LOG.debug("test selected box number is not valid");
        timber = new Timber(123, 3);

        //when(restTemplate.getForObject("http://localhost:8080/getNumberOfBoxes", Integer.class)).thenReturn(2);
        timberService.addTimber(timber);
        verify(timberController, times(1)).getNumberOfBoxes();
    }


    @Test(expected = ServiceLayerException.class)
    public void testGetNumOfBoxesNegativeTest() throws Exception {
        LOG.debug("test timber get number of boxes negative");


        //when(restTemplate.getForObject("http://localhost:8080/getNumberOfBoxes", Integer.class)).thenReturn(2);
        when(timberController.getNumberOfBoxes()).thenThrow(PersistenceLayerException.class);

        timberService.getNumberOfBoxes();
    }

    @Test
    public void testGetNumOfBoxesPositiveTest() throws Exception {
        LOG.debug("test timber get number of boxes negative");

        //when(restTemplate.getForObject("http://localhost:8080/getNumberOfBoxes", Integer.class)).thenReturn(2);
        when(timberController.getNumberOfBoxes()).thenReturn(2);

        assert(timberService.getNumberOfBoxes()==2);
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
