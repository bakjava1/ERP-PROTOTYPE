import at.ac.tuwien.sepm.assignment.group02.client.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.validation.ValidateInput;
import at.ac.tuwien.sepm.assignment.group02.client.validation.ValidateTimber;
import org.junit.*;

public class AddTimberValidatorTest {

    private static ValidateInput<Timber> timberValidator;
    private Timber timber;

    @BeforeClass
    public static void setup(){

        timberValidator = new ValidateTimber();
    }

    @Before
    public void before(){
        timber = new Timber();
    }

    @Test(expected = InvalidInputException.class)
    public void testAddTimberWithNegativeAmount() throws InvalidInputException {
        timber.setBox_id(1);
        timber.setAmount(-1);
        timberValidator.isValid(timber);
    }

    @Test(expected = InvalidInputException.class)
    public void testAddTimberWithNegativeBoxID() throws InvalidInputException{
        timber.setBox_id(-1);
        timber.setAmount(1);
        timberValidator.isValid(timber);
    }

    @Test
    public void testAddTimberPositive() throws InvalidInputException{
        timber.setAmount(12);
        timber.setBox_id(1);
        timberValidator.isValid(timber);
    }

    @After
    public void after(){
        timber = null;
    }

    @AfterClass
    public static void teardown(){
        timberValidator = null;
    }
}
