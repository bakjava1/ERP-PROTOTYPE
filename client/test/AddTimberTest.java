import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.TimberController;
import at.ac.tuwien.sepm.assignment.group02.client.rest.TimberControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.client.service.TimberService;
import at.ac.tuwien.sepm.assignment.group02.client.service.TimberServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.rest.converter.TimberConverter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Timber;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.invoke.MethodHandles;


public class AddTimberTest {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static Timber timberBoxErrorNegative;
    private static Timber timberBoxErrorWrongID;
    private static Timber timberAmountError;
    private static Timber timberNoError;
    private static TimberController timberControllerMock = Mockito.mock(TimberController.class);
    private static TimberService timberService = new TimberServiceImpl(timberControllerMock, new TimberConverter());

    @BeforeClass
    public static void setup(){
        LOG.debug("add timber test initiated");


        timberBoxErrorNegative = new Timber();
        timberBoxErrorNegative.setBox_id(-1);
        timberBoxErrorNegative.setAmount(20);

        timberBoxErrorWrongID = new Timber();
        timberBoxErrorWrongID.setBox_id(42);
    }


    @Test
    public void testAddTimberService() throws InvalidInputException, ServiceLayerException {



    }

    @Test(expected=ServiceLayerException.class)
    public void testGetNumberOfBoxesNegative() throws ServiceLayerException, InvalidInputException {

        Mockito.when(timberService.getNumberOfBoxes()).thenReturn(30);

        timberService.addTimber(timberBoxErrorNegative);
    }

    @Test(expected=ServiceLayerException.class)
    public void testGetNumberOfBoxesWrongID() throws ServiceLayerException, InvalidInputException {

        Mockito.when(timberService.getNumberOfBoxes()).thenReturn(30);

        timberService.addTimber(timberBoxErrorWrongID);
    }

    @Test
    public void testAddTimberPersistence(){

    }


    @AfterClass
    public static void teardown(){
        LOG.debug("add timber test shutdown");
    }
}
