package validatorTests;

import at.ac.tuwien.sepm.assignment.group02.client.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.validation.PrimitiveValidator;
import at.ac.tuwien.sepm.assignment.group02.client.validation.Validator;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class invoiceValidationTest {

    private static PrimitiveValidator primitiveValidator;
    private static Validator validator;
    private Order invoice;

    @BeforeClass
    public static void setUp() {
        primitiveValidator = new PrimitiveValidator();
        validator = new Validator(primitiveValidator);
    }

    @Before
    public void before() {
        invoice = new Order();
        invoice.setCustomerName("test");
        invoice.setCustomerAddress("test");
        invoice.setCustomerUID("test");
        invoice.setOrderDate("2018-01-01 12:38:40.123");
        Task temp  = new Task();
        invoice.addTask(temp);
        invoice.setGrossAmount(100);
        invoice.setTaxAmount(100);
        invoice.setNetAmount(100);
        invoice.setDeliveryDate("2018-01-01 12:38:40.123");
        invoice.setInvoiceDate("2018-01-01 12:38:40.123");
    }

    @Test(expected = InvalidInputException.class)
    public void invoiceCheckFailNegativeGross() throws InvalidInputException {
        invoice.setGrossAmount(-1);
        validator.inputValidationInvoice(invoice);
    }

    @Test(expected = InvalidInputException.class)
    public void invoiceCheckFailGrossTooBig() throws InvalidInputException {
        invoice.setGrossAmount(102000001);
        validator.inputValidationInvoice(invoice);
    }

    @Test(expected = InvalidInputException.class)
    public void invoiceCheckFailNegativeTax() throws InvalidInputException {
        invoice.setTaxAmount(-1);
        validator.inputValidationInvoice(invoice);
    }

    @Test(expected = InvalidInputException.class)
    public void invoiceCheckFailTaxTooBig() throws InvalidInputException {
        invoice.setTaxAmount(2000001);
        validator.inputValidationInvoice(invoice);
    }

    @Test(expected = InvalidInputException.class)
    public void invoiceCheckFailNegativeNet() throws InvalidInputException {
        invoice.setNetAmount(-1);
        validator.inputValidationInvoice(invoice);
    }

    @Test(expected = InvalidInputException.class)
    public void invoiceCheckFailNetTooBig() throws InvalidInputException {
        invoice.setNetAmount(100000001);
        validator.inputValidationInvoice(invoice);
    }

    @Test(expected = InvalidInputException.class)
    public void invoiceCheckFailInvalidDeliveryDate() throws InvalidInputException {
        invoice.setDeliveryDate("abc");
        validator.inputValidationInvoice(invoice);
    }

    @Test(expected = InvalidInputException.class)
    public void invoiceCheckFailInvalidInvoiceDate() throws InvalidInputException {
        invoice.setInvoiceDate("abc");
        validator.inputValidationInvoice(invoice);
    }

    @Test
    public void invoiceCheckSuccess() throws InvalidInputException {
        validator.inputValidationInvoice(invoice);
    }

    @AfterClass
    public static void tearDown() {

    }
}
