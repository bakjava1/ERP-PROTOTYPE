package validatorTest;

import at.ac.tuwien.sepm.assignment.group02.server.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.server.validation.PrimitiveValidator;
import at.ac.tuwien.sepm.assignment.group02.server.validation.ValidateInput;
import at.ac.tuwien.sepm.assignment.group02.server.validation.ValidateTask;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class taskValidatorTest {
    
    private static PrimitiveValidator primitiveValidator;
    private static ValidateInput validateInput;
    private Task task;
    
    @BeforeClass
    public static void setUp() {
        primitiveValidator = new PrimitiveValidator();
        validateInput = new ValidateTask(primitiveValidator);
    }
    
    @Before
    public void before() {
        task = new Task(0,0,"Latten",
                "roh","Fi",
                "I",20,40,
                4000,100,0,
                false,20000);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailNull() throws InvalidInputException {
        task = null;
        validateInput.isValid(task);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailNegativeId() throws InvalidInputException {
        task.setId(-1);
        validateInput.isValid(task);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailNegativeOrderId() throws InvalidInputException {
        task.setOrder_id(-1);
        validateInput.isValid(task);
    }
    @Test(expected = InvalidInputException.class)
    public void taskCheckFailDescriptionNull() throws InvalidInputException {
        task.setDescription(null);
        validateInput.isValid(task);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailDescriptionTooLong() throws InvalidInputException {
        String tooLong = "";
        for(int i = 0; i < 51;i++)
            tooLong += "a";
        task.setDescription(tooLong);
        validateInput.isValid(task);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailFinishingNull() throws InvalidInputException {
        task.setFinishing(null);
        validateInput.isValid(task);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailFinishingTooLong() throws InvalidInputException {
        String tooLong = "";
        for(int i = 0; i < 16;i++)
            tooLong += "a";
        task.setFinishing(tooLong);
        validateInput.isValid(task);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailUnknownFinishing() throws InvalidInputException {
        task.setFinishing("rohe");
        validateInput.isValid(task);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailWoodTypeNull() throws InvalidInputException {
        task.setWood_type(null);
        validateInput.isValid(task);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailWoodTypeTooLong() throws InvalidInputException {
        String tooLong = "";
        for(int i = 0; i < 11;i++)
            tooLong += "a";
        task.setWood_type(tooLong);
        validateInput.isValid(task);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailUnknownWoodType() throws InvalidInputException {
        task.setWood_type("unknown");
        validateInput.isValid(task);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailQualityNull() throws InvalidInputException {
        task.setQuality(null);
        validateInput.isValid(task);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailQualityTooLong() throws InvalidInputException {
        String tooLong = "";
        for(int i = 0; i < 11;i++)
            tooLong += "a";
        task.setQuality(tooLong);
        validateInput.isValid(task);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailUnknownQuality() throws InvalidInputException {
        task.setQuality("unknown");
        validateInput.isValid(task);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailNegativeSize() throws InvalidInputException {
        task.setSize(-1);
        validateInput.isValid(task);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailSizeTooBig() throws InvalidInputException {
        task.setSize(516);
        validateInput.isValid(task);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailNegativeWidth() throws InvalidInputException {
        task.setWidth(-1);
        validateInput.isValid(task);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailWidthTooBig() throws InvalidInputException {
        task.setWidth(516);
        validateInput.isValid(task);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailNegativeLength() throws InvalidInputException {
        task.setLength(-1);
        validateInput.isValid(task);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailLengthTooBig() throws InvalidInputException {
        task.setLength(5001);
        validateInput.isValid(task);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailUnknownLength() throws InvalidInputException {
        task.setLength(3000);
        validateInput.isValid(task);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailNegativeQuantity() throws InvalidInputException {
        task.setQuantity(-1);
        validateInput.isValid(task);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailQuantityTooBig() throws InvalidInputException {
        task.setQuantity(100001);
        validateInput.isValid(task);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailNegativePrice() throws InvalidInputException {
        task.setPrice(-1);
        validateInput.isValid(task);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailPriceTooBig() throws InvalidInputException {
        task.setPrice(100000000);
        validateInput.isValid(task);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailNegativeProducedQuantity() throws InvalidInputException {
        task.setProduced_quantity(-1);
        validateInput.isValid(task);
    }

    @Test
    public void taskCheckSuccess() throws InvalidInputException {
        validateInput.isValid(task);
    }
    
    @AfterClass
    public static void tearDown() {
        primitiveValidator = null;
        validateInput = null;
    }
}
