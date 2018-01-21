package validatorTests;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.validation.PrimitiveValidator;
import at.ac.tuwien.sepm.assignment.group02.client.validation.ValidateAssignmentDTO;
import at.ac.tuwien.sepm.assignment.group02.client.validation.ValidateInput;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class assignmentValidatorTest {

    private static ValidateInput validateInput;
    private static PrimitiveValidator primitiveValidator;
    private AssignmentDTO assignmentDTO;


    @BeforeClass
    public static void setUp() {
        primitiveValidator = new PrimitiveValidator();
        validateInput = new ValidateAssignmentDTO(primitiveValidator);
    }

    @Before
    public void before() {
        assignmentDTO = new AssignmentDTO();
        assignmentDTO.setId(1);
        assignmentDTO.setAmount(20);
        assignmentDTO.setBox_id(15);
        assignmentDTO.setTask_id(10);
        assignmentDTO.setCreation_date("2018-01-01 12:38:40.123");
    }

    //@Test(expected = InvalidInputException.class)


    @AfterClass
    public static void tearDown() {
        primitiveValidator = null;
        validateInput = null;
    }
}
