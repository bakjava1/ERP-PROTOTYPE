package validatorTest;

import at.ac.tuwien.sepm.assignment.group02.server.entity.Assignment;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.server.validation.PrimitiveValidator;
import at.ac.tuwien.sepm.assignment.group02.server.validation.ValidateAssignment;
import at.ac.tuwien.sepm.assignment.group02.server.validation.ValidateInput;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class assignmentValidatorTest {

    private static ValidateInput validateInput;
    private static PrimitiveValidator primitiveValidator;
    private Assignment assignment;


    @BeforeClass
    public static void setUp() {
        primitiveValidator = new PrimitiveValidator();
        validateInput = new ValidateAssignment(primitiveValidator);
    }

    @Before
    public void before() {
        assignment = new Assignment();
        assignment.setId(1);
        assignment.setAmount(20);
        assignment.setBox_id(15);
        assignment.setTask_id(10);
        assignment.setCreation_date("2018-01-01 12:38:40.123");
    }

    @Test(expected = InvalidInputException.class)
    public void assignmentCheckFailedNegativeId() throws InvalidInputException {
        assignment.setId(-1);
        validateInput.isValid(assignment);
    }

    @Test(expected = InvalidInputException.class)
    public void assignmentCheckFailedNegativeAmount() throws InvalidInputException {
        assignment.setAmount(-1);
        validateInput.isValid(assignment);
    }

    @Test(expected = InvalidInputException.class)
    public void assignmentCheckFailedTooBigAmount() throws InvalidInputException {
        assignment.setAmount(51);
        validateInput.isValid(assignment);
    }

    @Test(expected = InvalidInputException.class)
    public void assignmentCheckFailedNegativeBoxId() throws InvalidInputException {
        assignment.setBox_id(-1);
        validateInput.isValid(assignment);
    }

    @Test(expected = InvalidInputException.class)
    public void assignmentCheckFailedTooBigBoxId() throws InvalidInputException {
        assignment.setBox_id(26);
        validateInput.isValid(assignment);
    }

    @Test(expected = InvalidInputException.class)
    public void assignmentCheckFailedNegativeTaskId() throws InvalidInputException {
        assignment.setTask_id(-1);
        validateInput.isValid(assignment);
    }

    @Test(expected = InvalidInputException.class)
    public void assignmentCheckFailedInvalidDate() throws InvalidInputException {
        assignment.setCreation_date("abc");
        validateInput.isValid(assignment);
    }

    @Test
    public void assignmentCheckSuccess() throws InvalidInputException {
        validateInput.isValid(assignment);
    }

    @AfterClass
    public static void tearDown() {
        primitiveValidator = null;
        validateInput = null;
    }
}
