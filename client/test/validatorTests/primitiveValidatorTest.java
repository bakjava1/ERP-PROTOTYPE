package validatorTests;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.EmptyInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.NoValidIntegerException;
import at.ac.tuwien.sepm.assignment.group02.client.validation.PrimitiveValidator;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class primitiveValidatorTest {

    private static PrimitiveValidator primitiveValidator;

    @BeforeClass
    public static void setUp() {
        primitiveValidator = new PrimitiveValidator();
    }

    @Test
    public void nullDateSuccess() throws InvalidInputException {
        primitiveValidator.isValidDate(null);
    }

    @Test(expected = InvalidInputException.class)
    public void failDateCheckWrongFormat() throws InvalidInputException {
        primitiveValidator.isValidDate("abc");
    }

    @Test(expected = InvalidInputException.class)
    public void failDateCheckDateInFuture() throws InvalidInputException {
        primitiveValidator.isValidDate("2019-01-01 12:38:40.123");
    }

    @Test
    public void validDateSuccess() throws InvalidInputException {
        primitiveValidator.isValidDate("2018-01-01 12:38:40.123");
    }

    @Test(expected = EmptyInputException.class)
    public void failTextCheckTooLong() throws EmptyInputException {
        primitiveValidator.validateText("abcd",3);
    }

    @Test(expected = EmptyInputException.class)
    public void failTextCheckNull() throws EmptyInputException {
        primitiveValidator.validateText(null,3);
    }

    @Test(expected = EmptyInputException.class)
    public void failTextCheckEmpty() throws EmptyInputException{
        primitiveValidator.validateText("",3);
    }

    @Test
    public void validTextCheck() throws EmptyInputException{
        primitiveValidator.validateText("abc",3);
    }

    @Test(expected = NoValidIntegerException.class)
    public void failIsNumberCheckNegative() throws NoValidIntegerException {
        primitiveValidator.isNumber(-3,5);
    }

    @Test(expected = NoValidIntegerException.class)
    public void failIsNumberCheckTooBig() throws NoValidIntegerException {
        primitiveValidator.isNumber(6,5);
    }

    @Test
    public void isNumberCheckSuccess() throws NoValidIntegerException {
        primitiveValidator.isNumber(5,5);
    }

    @Test(expected = NoValidIntegerException.class)
    public void validateNumberFailNull() throws NoValidIntegerException {
        primitiveValidator.validateNumber(null,5);
    }

    @Test(expected = NoValidIntegerException.class)
    public void validateNumberFailEmpty() throws NoValidIntegerException {
        primitiveValidator.validateNumber("",5);
    }

    @Test(expected = NoValidIntegerException.class)
    public void validateNumberFailNegative() throws NoValidIntegerException {
        primitiveValidator.validateNumber("-1",5);
    }

    @Test(expected = NoValidIntegerException.class)
    public void validateNumberFailTooBig() throws NoValidIntegerException {
        primitiveValidator.validateNumber("6",5);
    }

    @Test(expected = NoValidIntegerException.class)
    public void validateNumberFailNoNumber() throws NoValidIntegerException {
        primitiveValidator.validateNumber("abc",5);
    }

    @Test
    public void validateNumberSuccess() throws NoValidIntegerException {
        primitiveValidator.validateNumber("5",5);
    }

    @AfterClass
    public static void tearDown() {
        primitiveValidator = null;
    }


}
