package validatorTests;

import at.ac.tuwien.sepm.assignment.group02.client.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.client.entity.UnvalidatedTask;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.validation.PrimitiveValidator;
import at.ac.tuwien.sepm.assignment.group02.client.validation.Validator;
import io.swagger.models.auth.In;
import org.junit.*;

public class taskValidatorTest {

    private static Validator validator;
    private static UnvalidatedTask task;
    private Task task2;

    @BeforeClass
    public static void setUp() {
        PrimitiveValidator primitiveValidator = new PrimitiveValidator();
        validator = new Validator(primitiveValidator);
    }

    @Before
    public void before() {
        task2 = new Task(0,0,"Latten",
                "roh","Fi",
                "I",20,40,
                4000,100,0,
                false,20000);
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
    public void testTaskTooLongDescriptionFailedValidation() throws InvalidInputException {
        String tooLong = "";
        for(int i = 0; i < 55;i++)
            tooLong += "a";
        task = new UnvalidatedTask(tooLong,"b","c","d","1","2","3","4","5");
        validator.inputValidationTask(task);
    }

    @Test(expected = InvalidInputException.class)
    public void testTaskWrongFinishingFailedValidation() throws InvalidInputException{
        task = new UnvalidatedTask("a","","c","d","1","2","3","4","5");
        validator.inputValidationTask(task);
    }

    @Test(expected = InvalidInputException.class)
    public void testTaskTooLongFinishingFailedValidation() throws InvalidInputException {
        String tooLong = "";
        for(int i = 0; i < 20;i++)
            tooLong += "a";
        task = new UnvalidatedTask("a",tooLong,"c","d","1","2","3","4","5");
        validator.inputValidationTask(task);
    }

    @Test(expected = InvalidInputException.class)
    public void testTaskNoWoodTypeFailedValidation() throws InvalidInputException{
        task = new UnvalidatedTask("a","roh","","d","1","2","3","4","5");
        validator.inputValidationTask(task);
    }

    @Test(expected = InvalidInputException.class)
    public void testTaskWrongWoodTypeFailedValidation() throws InvalidInputException{
        task = new UnvalidatedTask("a","roh","abc","d","1","2","3","4","5");
        validator.inputValidationTask(task);
    }

    @Test(expected = InvalidInputException.class)
    public void testTaskTooLongWoodTypeFailedValidation() throws InvalidInputException {
        String tooLong = "";
        for(int i = 0; i < 15;i++)
            tooLong += "a";
        task = new UnvalidatedTask("a","roh",tooLong,"d","1","2","3","4","5");
        validator.inputValidationTask(task);
    }

    @Test(expected = InvalidInputException.class)
    public void testTaskWrongQualityFailedValidation() throws InvalidInputException{
        task = new UnvalidatedTask("a","roh","Fi","","1","2","3","4","5");
        validator.inputValidationTask(task);
    }

    @Test(expected = InvalidInputException.class)
    public void testTaskTooLongQualityFailedValidation() throws InvalidInputException {
        String tooLong = "";
        for(int i = 0; i < 15;i++)
            tooLong += "a";
        task = new UnvalidatedTask("a","roh","Fi",tooLong,"1","2","3","4","5");
        validator.inputValidationTask(task);
    }

    @Test(expected = InvalidInputException.class)
    public void testTaskNoValidIntegerSizeFailedValidation() throws InvalidInputException{
        task = new UnvalidatedTask("a","roh","Fi","I","arohc","2","3","4","5");
        validator.inputValidationTask(task);
    }

    @Test(expected = InvalidInputException.class)
    public void testTaskNegativeIntegerSizeFailedValidation() throws InvalidInputException{
        task = new UnvalidatedTask("a","roh","Fi","I","-1","2","3","4","5");
        validator.inputValidationTask(task);
    }

    @Test(expected = InvalidInputException.class)
    public void testTaskNoSizeFailedValidation() throws InvalidInputException{
        task = new UnvalidatedTask("a","roh","Fi","I","","2","3","4","5");
        validator.inputValidationTask(task);
    }

    @Test(expected = InvalidInputException.class)
    public void testTaskNoValidIntegerWidthFailedValidation() throws InvalidInputException{
        task = new UnvalidatedTask("a","roh","Fi","I","1","abc","3","4","5");
        validator.inputValidationTask(task);
    }

    @Test(expected = InvalidInputException.class)
    public void testTaskNegativeIntegerWidthFailedValidation() throws InvalidInputException{
        task = new UnvalidatedTask("a","roh","Fi","I","1","-2","3","4","5");
        validator.inputValidationTask(task);
    }

    @Test(expected = InvalidInputException.class)
    public void testTaskNoWidthFailedValidation() throws InvalidInputException{
        task = new UnvalidatedTask("a","roh","Fi","I","1","","3","4","5");
        validator.inputValidationTask(task);
    }

    @Test(expected = InvalidInputException.class)
    public void testTaskNoValidIntegerLengthFailedValidation() throws InvalidInputException{
        task = new UnvalidatedTask("a","roh","Fi","I","1","2","abc","4","5");
        validator.inputValidationTask(task);
    }

    @Test(expected = InvalidInputException.class)
    public void testTaskNegativeIntegerLengthFailedValidation() throws InvalidInputException{
        task = new UnvalidatedTask("a","roh","Fi","I","1","2","-3","4","5");
        validator.inputValidationTask(task);
    }

    @Test(expected = InvalidInputException.class)
    public void testTaskNoLengthFailedValidation() throws InvalidInputException{
        task = new UnvalidatedTask("a","roh","Fi","I","1","2","","4","5");
        validator.inputValidationTask(task);
    }

    @Test(expected = InvalidInputException.class)
    public void testTaskNoValidIntegerQuantityFailedValidation() throws InvalidInputException{
        task = new UnvalidatedTask("a","roh","Fi","I","1","2","4000","abc","5");
        validator.inputValidationTask(task);
    }

    @Test(expected = InvalidInputException.class)
    public void testTaskNegativeIntegerQuantityFailedValidation() throws InvalidInputException{
        task = new UnvalidatedTask("a","roh","Fi","I","1","2","4000","-4","5");
        validator.inputValidationTask(task);
    }

    @Test(expected = InvalidInputException.class)
    public void testTaskNoQuantityFailedValidation() throws InvalidInputException{
        task = new UnvalidatedTask("a","roh","Fi","I","1","2","4000","","5");
        validator.inputValidationTask(task);
    }

    @Test(expected = InvalidInputException.class)
    public void testTaskNoValidIntegerPriceFailedValidation() throws InvalidInputException{
        task = new UnvalidatedTask("a","roh","Fi","I","1","2","4000","4","abc");
        validator.inputValidationTask(task);
    }

    @Test(expected = InvalidInputException.class)
    public void testTaskNegativeIntegerPriceFailedValidation() throws InvalidInputException{
        task = new UnvalidatedTask("a","roh","Fi","I","1","2","4000","4","-5");
        validator.inputValidationTask(task);
    }

    @Test(expected = InvalidInputException.class)
    public void testTaskNoPriceFailedValidation() throws InvalidInputException{
        task = new UnvalidatedTask("a","roh","Fi","I","1","2","4000","4","");
        validator.inputValidationTask(task);
    }


    @Test
    public void testCorrectTask() {
        boolean success = true;
        task = new UnvalidatedTask("Latten","roh","Fi","I","1","2","3500","4","5");
        Task temp = null;
        try {
            temp = validator.inputValidationTask(task);
        } catch(InvalidInputException e) {
            success = false;
        }
        if(temp == null) {
            success = false;
        }
        Assert.assertEquals(success,true);
        Assert.assertEquals(temp.getDescription(),"Latten");
        Assert.assertEquals(temp.getFinishing(),"roh");
        Assert.assertEquals(temp.getWood_type(),"Fi");
        Assert.assertEquals(temp.getQuality(),"I");
        Assert.assertEquals(temp.getSize(),1);
        Assert.assertEquals(temp.getWidth(),2);
        Assert.assertEquals(temp.getLength(),3500);
        Assert.assertEquals(temp.getQuantity(),4);
        Assert.assertEquals(temp.getPrice(),5);

    }

    @Test(expected = InvalidInputException.class)
    public void testFailedWrongLength() throws InvalidInputException{
        task = new UnvalidatedTask("Latten","roh","Fi","I","1","2","3000","4","5");
        validator.inputValidationTask(task);
    }

    @Test(expected = InvalidInputException.class)
    public void testFailedWrongQuality() throws InvalidInputException {
        task = new UnvalidatedTask("Latten","roh","Fi","VII","1","2","3500","4","5");
        validator.inputValidationTask(task);
    }

    @Test(expected = InvalidInputException.class)
    public void testFailedWrongFinishing() throws InvalidInputException{
        task = new UnvalidatedTask("Latten","rohkant","Fi","I","1","2","3500","4","5");
        validator.inputValidationTask(task);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailNull() throws InvalidInputException {
        task2 = null;
        validator.inputValidationTaskOnOrder(task2);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailNegativeId() throws InvalidInputException {
        task2.setId(-1);
        validator.inputValidationTaskOnOrder(task2);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailNegativeOrderId() throws InvalidInputException {
        task2.setOrder_id(-1);
        validator.inputValidationTaskOnOrder(task2);
    }
    @Test(expected = InvalidInputException.class)
    public void taskCheckFailDescriptionNull() throws InvalidInputException {
        task2.setDescription(null);
        validator.inputValidationTaskOnOrder(task2);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailDescriptionTooLong() throws InvalidInputException {
        String tooLong = "";
        for(int i = 0; i < 51;i++)
            tooLong += "a";
        task2.setDescription(tooLong);
        validator.inputValidationTaskOnOrder(task2);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailFinishingNull() throws InvalidInputException {
        task2.setFinishing(null);
        validator.inputValidationTaskOnOrder(task2);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailFinishingTooLong() throws InvalidInputException {
        String tooLong = "";
        for(int i = 0; i < 16;i++)
            tooLong += "a";
        task2.setFinishing(tooLong);
        validator.inputValidationTaskOnOrder(task2);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailUnknownFinishing() throws InvalidInputException {
        task2.setFinishing("rohe");
        validator.inputValidationTaskOnOrder(task2);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailWoodTypeNull() throws InvalidInputException {
        task2.setWood_type(null);
        validator.inputValidationTaskOnOrder(task2);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailWoodTypeTooLong() throws InvalidInputException {
        String tooLong = "";
        for(int i = 0; i < 11;i++)
            tooLong += "a";
        task2.setWood_type(tooLong);
        validator.inputValidationTaskOnOrder(task2);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailUnknownWoodType() throws InvalidInputException {
        task2.setWood_type("unknown");
        validator.inputValidationTaskOnOrder(task2);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailQualityNull() throws InvalidInputException {
        task2.setQuality(null);
        validator.inputValidationTaskOnOrder(task2);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailQualityTooLong() throws InvalidInputException {
        String tooLong = "";
        for(int i = 0; i < 11;i++)
            tooLong += "a";
        task2.setQuality(tooLong);
        validator.inputValidationTaskOnOrder(task2);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailUnknownQuality() throws InvalidInputException {
        task2.setQuality("unknown");
        validator.inputValidationTaskOnOrder(task2);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailNegativeSize() throws InvalidInputException {
        task2.setSize(-1);
        validator.inputValidationTaskOnOrder(task2);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailSizeTooBig() throws InvalidInputException {
        task2.setSize(516);
        validator.inputValidationTaskOnOrder(task2);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailNegativeWidth() throws InvalidInputException {
        task2.setWidth(-1);
        validator.inputValidationTaskOnOrder(task2);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailWidthTooBig() throws InvalidInputException {
        task2.setWidth(516);
        validator.inputValidationTaskOnOrder(task2);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailNegativeLength() throws InvalidInputException {
        task2.setLength(-1);
        validator.inputValidationTaskOnOrder(task2);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailLengthTooBig() throws InvalidInputException {
        task2.setLength(5001);
        validator.inputValidationTaskOnOrder(task2);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailUnknownLength() throws InvalidInputException {
        task2.setLength(3000);
        validator.inputValidationTaskOnOrder(task2);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailNegativeQuantity() throws InvalidInputException {
        task2.setQuantity(-1);
        validator.inputValidationTaskOnOrder(task2);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailQuantityTooBig() throws InvalidInputException {
        task2.setQuantity(100001);
        validator.inputValidationTaskOnOrder(task2);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailNegativePrice() throws InvalidInputException {
        task2.setPrice(-1);
        validator.inputValidationTaskOnOrder(task2);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailPriceTooBig() throws InvalidInputException {
        task2.setPrice(100000000);
        validator.inputValidationTaskOnOrder(task2);
    }

    @Test(expected = InvalidInputException.class)
    public void taskCheckFailNegativeProducedQuantity() throws InvalidInputException {
        task2.setProduced_quantity(-1);
        validator.inputValidationTaskOnOrder(task2);
    }

    @Test
    public void taskCheckSuccess() throws InvalidInputException {
        validator.inputValidationTaskOnOrder(task2);
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
