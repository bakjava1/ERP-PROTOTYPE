package validatorTest;

import at.ac.tuwien.sepm.assignment.group02.server.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.server.validation.PrimitiveValidator;
import at.ac.tuwien.sepm.assignment.group02.server.validation.ValidateInput;
import at.ac.tuwien.sepm.assignment.group02.server.validation.ValidateTimber;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class timberValidatorTest {

    private static PrimitiveValidator primitiveValidator;
    private static ValidateInput validateInput;
    private Timber timber;

    @BeforeClass
    public static void setUp() {
        primitiveValidator = new PrimitiveValidator();
        validateInput =  new ValidateTimber(primitiveValidator);
    }

    @Before
    public void before() {
        timber = new Timber();
        timber.setBox_id(2);
        timber.setAmount(20);
        timber.setMAX_AMOUNT(200);
        timber.setDiameter(350);
        timber.setWood_type("Fichte");
        timber.setQuality("A");
        timber.setLength(4000);
        timber.setFestmeter(400.0);
        timber.setPrice(10000);
        timber.setLast_edited("2018-01-01 12:38:40.123");
    }

    @Test(expected = InvalidInputException.class)
    public void timberCheckFailNull() throws InvalidInputException {
        timber = null;
        validateInput.isValid(timber);
    }

    @Test(expected = InvalidInputException.class)
    public void timberCheckFailNegativeBoxId() throws InvalidInputException {
        timber.setBox_id(-1);
        validateInput.isValid(timber);
    }

    @Test(expected = InvalidInputException.class)
    public void timberCheckFailTooBigBoxId() throws InvalidInputException {
        timber.setBox_id(26);
        validateInput.isValid(timber);
    }

    @Test(expected = InvalidInputException.class)
    public void timberCheckFailNegativeAmount() throws InvalidInputException {
        timber.setAmount(-1);
        validateInput.isValid(timber);
    }

    @Test(expected = InvalidInputException.class)
    public void timberCheckFailTooBigAmount() throws InvalidInputException {
        timber.setBox_id(801);
        validateInput.isValid(timber);
    }

    @Test(expected = InvalidInputException.class)
    public void timberCheckFailNegativeMaxAmount() throws InvalidInputException {
        timber.setMAX_AMOUNT(-1);
        validateInput.isValid(timber);
    }

    @Test(expected = InvalidInputException.class)
    public void timberCheckFailNegativeDiameter() throws InvalidInputException {
        timber.setDiameter(-1);
        validateInput.isValid(timber);
    }

    @Test(expected = InvalidInputException.class)
    public void timberCheckFailTooBigDiameter() throws InvalidInputException {
        timber.setDiameter(521);
        validateInput.isValid(timber);
    }

    @Test(expected = InvalidInputException.class)
    public void timberCheckFailWoodTypeEmpty() throws InvalidInputException {
        timber.setWood_type(null);
        validateInput.isValid(timber);
    }

    @Test(expected = InvalidInputException.class)
    public void timberCheckFailWoodTypeTooLong() throws InvalidInputException {
        timber.setWood_type("aaaaaaaaaaaaaaaaaaaaa");
        validateInput.isValid(timber);
    }

    @Test(expected = InvalidInputException.class)
    public void timberCheckFailUnknownWoodType() throws InvalidInputException {
        timber.setWood_type("Birne");
        validateInput.isValid(timber);
    }

    @Test(expected = InvalidInputException.class)
    public void timberCheckFailEmptyQuality() throws InvalidInputException {
        timber.setQuality(null);
        validateInput.isValid(timber);
    }

    @Test(expected = InvalidInputException.class)
    public void timberCheckFailTooLongQuality() throws InvalidInputException {
        timber.setQuality("aaaaaaaaaaa");
        validateInput.isValid(timber);
    }

    @Test(expected = InvalidInputException.class)
    public void timberCheckFailUnknownQUality() throws InvalidInputException {
        timber.setQuality("I");
        validateInput.isValid(timber);
    }

    @Test(expected = InvalidInputException.class)
    public void timberCheckFailNegativeLength() throws InvalidInputException {
        timber.setLength(-1);
        validateInput.isValid(timber);
    }

    @Test(expected = InvalidInputException.class)
    public void timberCheckFailTooBigLength() throws InvalidInputException {
        timber.setLength(5001);
        validateInput.isValid(timber);
    }

    @Test(expected = InvalidInputException.class)
    public void timberCheckFailNegativeFestmeter() throws InvalidInputException {
        timber.setFestmeter(-1);
        validateInput.isValid(timber);
    }

    @Test(expected = InvalidInputException.class)
    public void timberCheckFailTooBigFestmeter() throws InvalidInputException {
        timber.setFestmeter(801);
        validateInput.isValid(timber);
    }

    @Test(expected = InvalidInputException.class)
    public void timberCheckFailNegativePrice() throws InvalidInputException {
        timber.setPrice(-1);
        validateInput.isValid(timber);
    }

    @Test(expected = InvalidInputException.class)
    public void timberCheckFailTooBigPrice() throws InvalidInputException {
        timber.setPrice(100000100);
        validateInput.isValid(timber);
    }

    @Test(expected = InvalidInputException.class)
    public void timberCheckFailInvalidDate() throws InvalidInputException {
        timber.setLast_edited("abc");
        validateInput.isValid(timber);
    }

    @Test
    public void timberCheckSuccess() throws InvalidInputException {
        validateInput.isValid(timber);
    }

    @AfterClass
    public static void tearDown() {
        primitiveValidator = null;
        validateInput = null;
    }
}
