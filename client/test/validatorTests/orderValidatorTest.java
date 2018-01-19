package validatorTests;

import at.ac.tuwien.sepm.assignment.group02.client.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.validation.Validator;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Order;
import org.junit.*;

public class orderValidatorTest {

    private static Validator validator;
    private static Order order;

    @BeforeClass
    public static void setUp() {
        validator =  new Validator();
    }

    @Before
    public void beforeMethod() {
        order = new Order();
    }

    @Test(expected = InvalidInputException.class)
    public void testEmptyOrderFailedValidation() throws InvalidInputException{
        validator.inputValidationOrder(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testOrderWithNoCustomerNameFailedValidation() throws InvalidInputException{
        order.setCustomerAddress("test");
        order.setCustomerUID("test");
        validator.inputValidationOrder(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testOrderWithEmptyCustomerNameFailedValidation() throws InvalidInputException{
        order.setCustomerName("");
        order.setCustomerAddress("test");
        order.setCustomerUID("test");
        validator.inputValidationOrder(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testOrderWithTooLongCustomerNameFailedValidation() throws InvalidInputException {
        String tooLong = "";
        for(int i = 0; i < 55;i++)
            tooLong += "a";
        order.setCustomerAddress(tooLong);
        order.setCustomerAddress("b");
        order.setCustomerUID("c");
        validator.inputValidationOrder(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testOrderWithNoCustomerAddressFailedValidation() throws InvalidInputException{
        order.setCustomerName("test");
        order.setCustomerUID("test");
        validator.inputValidationOrder(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testOrderWithEmptyCustomerAddressFailedValidation() throws InvalidInputException{
        order.setCustomerName("test");
        order.setCustomerAddress("");
        order.setCustomerUID("test");
        validator.inputValidationOrder(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testOrderWithTooLongCustomerAddressFailedValidation() throws InvalidInputException {
        String tooLong = "";
        for(int i = 0; i < 55;i++)
            tooLong += "a";
        order.setCustomerAddress("a");
        order.setCustomerAddress(tooLong);
        order.setCustomerUID("c");
        validator.inputValidationOrder(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testOrderWithNoCustomerUIDFailedValidation() throws InvalidInputException{
        order.setCustomerAddress("test");
        order.setCustomerName("test");
        validator.inputValidationOrder(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testOrderWithEmptyCustomerUIDFailedValidation() throws InvalidInputException{
        order.setCustomerName("test");
        order.setCustomerAddress("test");
        order.setCustomerUID("");
        validator.inputValidationOrder(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testOrderWithTooLongCustomerUIDFailedValidation() throws InvalidInputException {
        String tooLong = "";
        for(int i = 0; i < 15;i++)
            tooLong += "a";
        order.setCustomerAddress("a");
        order.setCustomerAddress("b");
        order.setCustomerUID(tooLong);
        validator.inputValidationOrder(order);
    }

    @Test
    public void testValidOrderNoFail() {
        boolean success =  true;
        order.setCustomerName("test");
        order.setCustomerAddress("test");
        order.setCustomerUID("test");
        Task temp  = new Task();
        order.addTask(temp);
        try {
            validator.inputValidationOrder(order);
        } catch(InvalidInputException e) {
            success = false;
        }
        Assert.assertEquals(true,success);
    }

    @After
    public void afterMethod() {
        order = null;
    }

    @AfterClass
    public static void tearDown() {
        validator = null;
    }


}
