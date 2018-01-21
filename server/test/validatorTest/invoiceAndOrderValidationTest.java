package validatorTest;

import at.ac.tuwien.sepm.assignment.group02.server.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.server.validation.PrimitiveValidator;
import at.ac.tuwien.sepm.assignment.group02.server.validation.ValidateInput;
import at.ac.tuwien.sepm.assignment.group02.server.validation.ValidateInvoice;
import at.ac.tuwien.sepm.assignment.group02.server.validation.ValidateOrder;
import org.junit.*;

public class invoiceAndOrderValidationTest {
    
    private static PrimitiveValidator primitiveValidator;
    private static ValidateInput validateInputOrder;
    private static ValidateInput validateInputInvoice;
    private Order order;
    private Order invoice;
    
    @BeforeClass
    public static void setUp() {
        primitiveValidator = new PrimitiveValidator();
        validateInputOrder = new ValidateOrder(primitiveValidator);
        validateInputInvoice = new ValidateInvoice(primitiveValidator);
    }
    
    @Before
    public void before() {
        order = new Order();
        order.setID(0);
        order.setCustomerName("test");
        order.setCustomerAddress("test");
        order.setCustomerUID("test");
        order.setOrderDate("2018-01-01 12:38:40.123");
        Task temp  = new Task();
        order.addTask(temp);

        invoice = new Order();
        invoice.setID(0);
        invoice.setCustomerName("test");
        invoice.setCustomerAddress("test");
        invoice.setCustomerUID("test");
        invoice.setOrderDate("2018-01-01 12:38:40.123");
        temp  = new Task();
        invoice.addTask(temp);
        invoice.setGrossAmount(100);
        invoice.setTaxAmount(100);
        invoice.setNetAmount(100);
        invoice.setDeliveryDate("2018-01-01 12:38:40.123");
        invoice.setInvoiceDate("2018-01-01 12:38:40.123");
    }

    @Test(expected = InvalidInputException.class)
    public void testEmptyOrderFailedValidation() throws InvalidInputException{
        order = null;
        validateInputOrder.isValid(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testOrderWithNoCustomerNameFailedValidation() throws InvalidInputException{
        order.setCustomerName(null);
        validateInputOrder.isValid(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testOrderWithEmptyCustomerNameFailedValidation() throws InvalidInputException{
        order.setCustomerName("");
        validateInputOrder.isValid(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testOrderWithTooLongCustomerNameFailedValidation() throws InvalidInputException {
        String tooLong = "";
        for(int i = 0; i < 101;i++)
            tooLong += "a";
        order.setCustomerAddress(tooLong);
        validateInputOrder.isValid(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testOrderWithNoCustomerAddressFailedValidation() throws InvalidInputException{
        order.setCustomerAddress(null);
        validateInputOrder.isValid(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testOrderWithEmptyCustomerAddressFailedValidation() throws InvalidInputException{
        order.setCustomerAddress("");
        validateInputOrder.isValid(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testOrderWithTooLongCustomerAddressFailedValidation() throws InvalidInputException {
        String tooLong = "";
        for(int i = 0; i < 101;i++)
            tooLong += "a";
        order.setCustomerAddress(tooLong);
        validateInputOrder.isValid(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testOrderWithNoCustomerUIDFailedValidation() throws InvalidInputException{
        order.setCustomerUID(null);
        validateInputOrder.isValid(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testOrderWithEmptyCustomerUIDFailedValidation() throws InvalidInputException{
        order.setCustomerUID("");
        validateInputOrder.isValid(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testOrderWithTooLongCustomerUIDFailedValidation() throws InvalidInputException {
        String tooLong = "";
        for(int i = 0; i < 51;i++)
            tooLong += "a";
        order.setCustomerUID(tooLong);
        validateInputOrder.isValid(order);
    }

    @Test
    public void testValidOrderNoFail() throws InvalidInputException {
        validateInputOrder.isValid(order);

    }

    @Test(expected = InvalidInputException.class)
    public void testFailInvalidDate() throws InvalidInputException {
        order.setOrderDate("abcde");
        validateInputOrder.isValid(order);
    }

    @Test
    public void testValidOrderNoFailWithDate() throws InvalidInputException{
        order.setOrderDate("2018-01-01 12:38:40.123");
        validateInputOrder.isValid(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testFailNegativeID() throws InvalidInputException {
        order.setID(-1);
        validateInputOrder.isValid(order);
    }

    @Test(expected = InvalidInputException.class)
    public void testFailEmptyList() throws InvalidInputException {
        order.setTaskList(null);
        validateInputOrder.isValid(order);
    }

    //invoice

    @Test(expected = InvalidInputException.class)
    public void testEmptyInvoiceFailedValidation() throws InvalidInputException{
        invoice = null;
        validateInputInvoice.isValid(invoice);
    }

    @Test(expected = InvalidInputException.class)
    public void testInvoiceWithNoCustomerNameFailedValidation() throws InvalidInputException{
        invoice.setCustomerName(null);
        validateInputInvoice.isValid(invoice);
    }

    @Test(expected = InvalidInputException.class)
    public void testInvoiceWithEmptyCustomerNameFailedValidation() throws InvalidInputException{
        invoice.setCustomerName("");
        validateInputInvoice.isValid(invoice);
    }

    @Test(expected = InvalidInputException.class)
    public void testInvoiceWithTooLongCustomerNameFailedValidation() throws InvalidInputException {
        String tooLong = "";
        for(int i = 0; i < 101;i++)
            tooLong += "a";
        invoice.setCustomerAddress(tooLong);
        validateInputInvoice.isValid(invoice);
    }

    @Test(expected = InvalidInputException.class)
    public void testInvoiceWithNoCustomerAddressFailedValidation() throws InvalidInputException{
        invoice.setCustomerAddress(null);
        validateInputInvoice.isValid(invoice);
    }

    @Test(expected = InvalidInputException.class)
    public void testInvoiceWithEmptyCustomerAddressFailedValidation() throws InvalidInputException{
        invoice.setCustomerAddress("");
        validateInputInvoice.isValid(invoice);
    }

    @Test(expected = InvalidInputException.class)
    public void testInvoiceWithTooLongCustomerAddressFailedValidation() throws InvalidInputException {
        String tooLong = "";
        for(int i = 0; i < 101;i++)
            tooLong += "a";
        invoice.setCustomerAddress(tooLong);
        validateInputInvoice.isValid(invoice);
    }

    @Test(expected = InvalidInputException.class)
    public void testInvoiceWithNoCustomerUIDFailedValidation() throws InvalidInputException{
        invoice.setCustomerUID(null);
        validateInputInvoice.isValid(invoice);
    }

    @Test(expected = InvalidInputException.class)
    public void testInvoiceWithEmptyCustomerUIDFailedValidation() throws InvalidInputException{
        invoice.setCustomerUID("");
        validateInputInvoice.isValid(invoice);
    }

    @Test(expected = InvalidInputException.class)
    public void testInvoiceWithTooLongCustomerUIDFailedValidation() throws InvalidInputException {
        String tooLong = "";
        for(int i = 0; i < 51;i++)
            tooLong += "a";
        invoice.setCustomerUID(tooLong);
        validateInputInvoice.isValid(invoice);
    }

    @Test
    public void testValidInvoiceNoFail() throws InvalidInputException {
        validateInputInvoice.isValid(invoice);

    }

    @Test(expected = InvalidInputException.class)
    public void testFailInvalidDateI() throws InvalidInputException {
        invoice.setOrderDate("abcde");
        validateInputInvoice.isValid(invoice);
    }

    @Test
    public void testValidInvoiceNoFailWithDateI() throws InvalidInputException{
        invoice.setOrderDate("2018-01-01 12:38:40.123");
        validateInputInvoice.isValid(invoice);
    }

    @Test(expected = InvalidInputException.class)
    public void testFailNegativeIDI() throws InvalidInputException {
        invoice.setID(-1);
        validateInputInvoice.isValid(invoice);
    }

    @Test(expected = InvalidInputException.class)
    public void testFailEmptyListI() throws InvalidInputException {
        invoice.setTaskList(null);
        validateInputInvoice.isValid(invoice);
    }

    @Test(expected = InvalidInputException.class)
    public void invoiceCheckFailNegativeGross() throws InvalidInputException {
        invoice.setGrossAmount(-1);
        validateInputInvoice.isValid(invoice);
    }

    @Test(expected = InvalidInputException.class)
    public void invoiceCheckFailGrossTooBig() throws InvalidInputException {
        invoice.setGrossAmount(102000001);
        validateInputInvoice.isValid(invoice);
    }

    @Test(expected = InvalidInputException.class)
    public void invoiceCheckFailNegativeTax() throws InvalidInputException {
        invoice.setTaxAmount(-1);
        validateInputInvoice.isValid(invoice);
    }

    @Test(expected = InvalidInputException.class)
    public void invoiceCheckFailTaxTooBig() throws InvalidInputException {
        invoice.setTaxAmount(2000001);
        validateInputInvoice.isValid(invoice);
    }

    @Test(expected = InvalidInputException.class)
    public void invoiceCheckFailNegativeNet() throws InvalidInputException {
        invoice.setNetAmount(-1);
        validateInputInvoice.isValid(invoice);
    }

    @Test(expected = InvalidInputException.class)
    public void invoiceCheckFailNetTooBig() throws InvalidInputException {
        invoice.setNetAmount(100000001);
        validateInputInvoice.isValid(invoice);
    }

    @Test(expected = InvalidInputException.class)
    public void invoiceCheckFailInvalidDeliveryDate() throws InvalidInputException {
        invoice.setDeliveryDate("abc");
        validateInputInvoice.isValid(invoice);
    }

    @Test(expected = InvalidInputException.class)
    public void invoiceCheckFailInvalidInvoiceDate() throws InvalidInputException {
        invoice.setInvoiceDate("abc");
        validateInputInvoice.isValid(invoice);
    }

    @Test
    public void invoiceCheckSuccess() throws InvalidInputException {
        validateInputInvoice.isValid(invoice);
    }


    @AfterClass
    public static void tearDown() {
        primitiveValidator = null;
        validateInputOrder = null;
        validateInputInvoice = null;
    }
}
