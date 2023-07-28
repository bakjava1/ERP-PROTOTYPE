package validatorTests;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.validation.PrimitiveValidator;
import at.ac.tuwien.sepm.assignment.group02.client.validation.Validator;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.FilterDTO;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class filterValidationTest {

    private static PrimitiveValidator primitiveValidator;
    private static Validator validator;
    private FilterDTO filterDTO;

    @BeforeClass
    public static void setUp() {
        primitiveValidator = new PrimitiveValidator();
        validator = new Validator(primitiveValidator);
    }

    @Before
    public void before() {
        filterDTO = new FilterDTO();
        filterDTO.setDescription("Latten");
        filterDTO.setFinishing("roh");
        filterDTO.setQuality("I");
        filterDTO.setWood_type("Fi");
        filterDTO.setSize("10");
        filterDTO.setWidth("10");
        filterDTO.setLength("4000");
    }

    @Test
    public void emptyFilterDTONoFail() throws InvalidInputException {
        filterDTO.setDescription("");
        filterDTO.setFinishing("");
        filterDTO.setQuality("");
        filterDTO.setWood_type("");
        filterDTO.setSize("");
        filterDTO.setWidth("");
        filterDTO.setLength("");
        validator.validateFilter(filterDTO);
    }

    @Test
    public void validFilterDTONoFail() throws InvalidInputException {
        validator.validateFilter(filterDTO);
    }

    @Test(expected = InvalidInputException.class)
    public void filterCheckFailDescriptionTooLong() throws InvalidInputException {
        String tooLong = "";
        for(int i = 0; i < 51;i++)
            tooLong += "a";
        filterDTO.setDescription(tooLong);
        validator.validateFilter(filterDTO);
    }

    @Test(expected = InvalidInputException.class)
    public void filterCheckFailFinishingTooLOng() throws InvalidInputException {
        String tooLong = "";
        for(int i = 0; i < 31;i++)
            tooLong += "a";
        filterDTO.setFinishing(tooLong);
        validator.validateFilter(filterDTO);
    }

    @Test(expected = InvalidInputException.class)
    public void filterCheckFailUnknownFinishing() throws InvalidInputException {
        filterDTO.setFinishing("rohe");
        validator.validateFilter(filterDTO);
    }

    @Test(expected = InvalidInputException.class)
    public void filterCheckFailWoodTypeTooLong() throws InvalidInputException {
        String tooLong = "";
        for(int i = 0; i < 21;i++)
            tooLong += "a";
        filterDTO.setWood_type(tooLong);
        validator.validateFilter(filterDTO);
    }

    @Test(expected = InvalidInputException.class)
    public void filterCheckFailUnknownWoodType() throws InvalidInputException {
        filterDTO.setWood_type("ABC");
        validator.validateFilter(filterDTO);
    }

    @Test(expected = InvalidInputException.class)
    public void filterCheckFailQualityTooLong() throws InvalidInputException {
        String tooLong = "";
        for(int i = 0; i < 21;i++)
            tooLong += "a";
        filterDTO.setQuality(tooLong);
        validator.validateFilter(filterDTO);
    }

    @Test(expected = InvalidInputException.class)
    public void filterCheckFailUnknownQuality() throws InvalidInputException {
        filterDTO.setQuality("ABC");
        validator.validateFilter(filterDTO);
    }

    @Test(expected = InvalidInputException.class)
    public void filterCheckFailNegativeSize() throws InvalidInputException {
        filterDTO.setSize("-1");
        validator.validateFilter(filterDTO);
    }

    @Test(expected = InvalidInputException.class)
    public void filterCheckFailNegativeWidth() throws InvalidInputException {
        filterDTO.setWidth("-1");
        validator.validateFilter(filterDTO);
    }

    @Test(expected = InvalidInputException.class)
    public void filterCheckFailNegativeLength() throws InvalidInputException {
        filterDTO.setLength("-1");
        validator.validateFilter(filterDTO);
    }

    @Test(expected = InvalidInputException.class)
    public void filterCheckFailUnknownLength() throws InvalidInputException {
        filterDTO.setLength("3000");
        validator.validateFilter(filterDTO);
    }

    @AfterClass
    public static void tearDown() {
        primitiveValidator = null;
        validator = null;
    }

}
