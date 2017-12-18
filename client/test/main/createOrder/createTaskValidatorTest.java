package main.createOrder;

import at.ac.tuwien.sepm.assignment.group02.client.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.validation.Validator;
import org.junit.*;

public class createTaskValidatorTest {

    private static Validator validator;
    private static Task task;

    @BeforeClass
    public static void setUp() {
        validator =  new Validator();
    }

    @Before
    public void beforeMethod() {
        task = new Task();
    }

    @Test(expected = InvalidInputException.class)
    public void testEmptyOrderFailedValidation() throws InvalidInputException{

    }

    @After
    public void afterMethod() {
        task = null;
    }

    @AfterClass
    public static void tearDown() {
        validator = null;
    }
}
