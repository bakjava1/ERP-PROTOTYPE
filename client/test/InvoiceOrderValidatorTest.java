import at.ac.tuwien.sepm.assignment.group02.client.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.validation.Validator;
import org.junit.*;

public class InvoiceOrderValidatorTest {

    private static Validator validator;
    private static Order order;

    @BeforeClass
    public static void setup(){
        validator = new Validator();
    }

    @Before
    public void before(){
        order = new Order();
    }

    @Test(expected = InvalidInputException.class)
    public void testEmptyInvoiceFailedValidation() throws InvalidInputException{
        validator.inputValidationInvoice(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testInvoiceWithNoCustomerNameFailedValidation() throws InvalidInputException{
        order.setCustomerAddress("test");
        order.setCustomerUID("test");
        validator.inputValidationInvoice(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testInvoiceWithEmptyCustomerNameFailedValidation() throws InvalidInputException{
        order.setCustomerName("");
        order.setCustomerAddress("test");
        order.setCustomerUID("test");
        validator.inputValidationInvoice(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testInvoiceWithNoCustomerAddressFailedValidation() throws InvalidInputException{
        order.setCustomerName("test");
        order.setCustomerUID("test");
        validator.inputValidationInvoice(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testInvoiceWithEmptyCustomerAddressFailedValidation() throws InvalidInputException{
        order.setCustomerName("test");
        order.setCustomerAddress("");
        order.setCustomerUID("test");
        validator.inputValidationInvoice(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testOrderWithNoCustomerUIDFailedValidation() throws InvalidInputException{
        order.setCustomerAddress("test");
        order.setCustomerName("test");
        validator.inputValidationInvoice(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testInvoiceWithEmptyCustomerUIDFailedValidation() throws InvalidInputException{
        order.setCustomerName("test");
        order.setCustomerAddress("test");
        order.setCustomerUID("");
        validator.inputValidationInvoice(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testInvoiceWithEmptyDeliveryDate() throws InvalidInputException {
        order.setCustomerName("test");
        order.setCustomerAddress("test");
        order.setCustomerUID("test");
        validator.inputValidationInvoice(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testInvoiceWithEmptyOrderDate() throws InvalidInputException {
        order.setCustomerName("test");
        order.setCustomerAddress("test");
        order.setCustomerUID("test");
        order.setDeliveryDate(new java.util.Date());
        validator.inputValidationInvoice(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testInvoicerWithEmptyInvoiceDate() throws InvalidInputException {
        order.setCustomerName("test");
        order.setCustomerAddress("test");
        order.setCustomerUID("test");
        order.setDeliveryDate(new java.util.Date());
        order.setOrderDate(new java.util.Date());
        validator.inputValidationInvoice(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testInvoiceWithNoTasks() throws InvalidInputException {
        order.setCustomerName("test");
        order.setCustomerAddress("test");
        order.setCustomerUID("test");
        order.setDeliveryDate(new java.util.Date());
        order.setOrderDate(new java.util.Date());
        order.setInvoiceDate(new java.util.Date());
        validator.inputValidationInvoice(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testInvoiceWithNegativeGrossAmount() throws InvalidInputException {
        order.setCustomerName("test");
        order.setCustomerAddress("test");
        order.setCustomerUID("test");
        order.setDeliveryDate(new java.util.Date());
        order.setOrderDate(new java.util.Date());
        order.setInvoiceDate(new java.util.Date());
        Task temp = new Task();
        temp.setPrice(12);
        order.addTask(temp);
        order.setGrossAmount(-1);
        validator.inputValidationInvoice(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testInvoiceWithNegativeTaxAmount() throws InvalidInputException {
        order.setCustomerName("test");
        order.setCustomerAddress("test");
        order.setCustomerUID("test");
        order.setDeliveryDate(new java.util.Date());
        order.setOrderDate(new java.util.Date());
        order.setInvoiceDate(new java.util.Date());
        Task temp = new Task();
        temp.setPrice(12);
        order.addTask(temp);
        order.setTaxAmount(-1);
        validator.inputValidationInvoice(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testInvoiceWithNegativeNetAmount() throws InvalidInputException {
        order.setCustomerName("test");
        order.setCustomerAddress("test");
        order.setCustomerUID("test");
        order.setDeliveryDate(new java.util.Date());
        order.setOrderDate(new java.util.Date());
        order.setInvoiceDate(new java.util.Date());
        Task temp = new Task();
        temp.setPrice(12);
        order.addTask(temp);
        order.setNetAmount(-1);
        validator.inputValidationInvoice(order);
    }

    @Test
    public void testValidOrderNoFail() throws InvalidInputException {
        boolean success =  true;
        order.setCustomerName("test");
        order.setCustomerAddress("test");
        order.setCustomerUID("test");
        order.setDeliveryDate(new java.util.Date());
        order.setOrderDate(new java.util.Date());
        order.setInvoiceDate(new java.util.Date());
        Task temp = new Task();
        temp.setPrice(12);
        order.addTask(temp);
        validator.inputValidationInvoice(order);
        try {
            validator.inputValidationInvoice(order);
        } catch(InvalidInputException e) {
            success = false;
        }
        Assert.assertEquals(success,true);
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
