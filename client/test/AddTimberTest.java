import at.ac.tuwien.sepm.assignment.group02.client.converter.TimberConverter;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.TimberController;
import at.ac.tuwien.sepm.assignment.group02.client.service.TimberService;
import at.ac.tuwien.sepm.assignment.group02.client.service.TimberServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.client.validation.ValidateInput;
import at.ac.tuwien.sepm.assignment.group02.client.validation.ValidateTimber;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;


public class AddTimberTest {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static Timber timberBoxErrorNegative;
    private static Timber timberBoxErrorWrongID;
    private static Timber timberAmountError;
    private static Timber timberNoError;
    private static TimberController timberControllerMock = Mockito.mock(TimberController.class);
    private static ValidateTimber timberValidator = new ValidateTimber();
    private static TimberService timberService = new TimberServiceImpl(timberControllerMock, new TimberConverter(), timberValidator);

    @BeforeClass
    public static void setup(){
        LOG.debug("add timber test initiated");


        timberBoxErrorNegative = new Timber();
        timberBoxErrorNegative.setBox_id(-1);
        timberBoxErrorNegative.setAmount(20);

        timberBoxErrorWrongID = new Timber();
        timberBoxErrorWrongID.setBox_id(42);

        timberAmountError = new Timber();
        timberAmountError.setBox_id(1);
        timberAmountError.setAmount(-1);

        timberNoError = new Timber(1,20);
    }

    @Test(expected = ServiceLayerException.class)
    public void testAddTimberServiceAmountNegative() throws InvalidInputException, ServiceLayerException {

        LOG.debug("add timber test with negative amount");
        Mockito.when(timberService.getNumberOfBoxes()).thenReturn(30);

        timberService.addTimber(timberAmountError);

    }

    @Test(expected=ServiceLayerException.class)
    public void testGetNumberOfBoxesNegative() throws ServiceLayerException, InvalidInputException {

        LOG.debug("get number of boxes test with negative id");
        Mockito.when(timberService.getNumberOfBoxes()).thenReturn(30);

        timberService.addTimber(timberBoxErrorNegative);
    }

    @Test(expected=ServiceLayerException.class)
    public void testGetNumberOfBoxesWrongID() throws ServiceLayerException, InvalidInputException {

        LOG.debug("get number of boxes test with id that is higher than number of boxes");
        Mockito.when(timberService.getNumberOfBoxes()).thenReturn(30);

        timberService.addTimber(timberBoxErrorWrongID);
    }

    @Test
    public void testAddTimberServiceNoError() throws ServiceLayerException, InvalidInputException {

        LOG.debug("add timber test with no error");
        Mockito.when(timberService.getNumberOfBoxes()).thenReturn(30);

        timberService.addTimber(timberNoError);
    }

    @Test
    public void testAddTimberPersistence(){

    }


    @AfterClass
    public static void teardown(){

        LOG.debug("add timber test shutdown");
    }
}
