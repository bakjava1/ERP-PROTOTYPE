package main.createOrder;

import at.ac.tuwien.sepm.assignment.group02.client.entity.UnvalidatedTask;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.validation.Validator;
import org.junit.*;

public class createTaskValidatorTest {

    private static Validator validator;
    private static UnvalidatedTask task;

    @BeforeClass
    public static void setUp() {
        validator =  new Validator();
    }


    @Test(expected = InvalidInputException.class)
    public void testEmptyTaskFailedValidation() throws InvalidInputException{
        task = new UnvalidatedTask(null,null,null,null,null,null,null,null,null);
        validator.inputValidationTask(task);
    }

    @Test(expected = InvalidInputException.class)
    public void testTaskNoDescriptionFailedValidation() throws InvalidInputException{
        task = new UnvalidatedTask("","b","c","d","1","2","3","4","5");
        validator.inputValidationTask(task);
    }

    @Test(expected = InvalidInputException.class)
    public void testTaskNoFinishingFailedValidation() throws InvalidInputException{
        task = new UnvalidatedTask("a","","c","d","1","2","3","4","5");
        validator.inputValidationTask(task);
    }

    @Test(expected = InvalidInputException.class)
    public void testTaskNoWoodTypeFailedValidation() throws InvalidInputException{
        task = new UnvalidatedTask("a","b","","d","1","2","3","4","5");
        validator.inputValidationTask(task);
    }

    @Test(expected = InvalidInputException.class)
    public void testTaskNoQualityFailedValidation() throws InvalidInputException{
        task = new UnvalidatedTask("a","b","c","","1","2","3","4","5");
        validator.inputValidationTask(task);
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
