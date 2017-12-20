import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.validation.ValidateAssignmentDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import org.junit.Assert;
import org.junit.Test;

public class ValidateAssignmentDTOTest {

    @Test
    public void isValid_valid() throws Exception {
        ValidateAssignmentDTO validateAssignmentDTO = new ValidateAssignmentDTO();

        AssignmentDTO input = new AssignmentDTO();
        input.setId(1);
        input.setAmount(1);
        input.setBox_id(1);

        Assert.assertEquals(validateAssignmentDTO.isValid(input),true);
    }


    @Test(expected = InvalidInputException.class)
    public void isValid_NotValid_ID() throws Exception {
        ValidateAssignmentDTO validateAssignmentDTO = new ValidateAssignmentDTO();

        AssignmentDTO input = new AssignmentDTO();
        input.setId(-1);
        input.setAmount(1);
        input.setBox_id(0);

        validateAssignmentDTO.isValid(input);
    }

    @Test(expected = InvalidInputException.class)
    public void isValid_NotValid_Amount() throws Exception {
        ValidateAssignmentDTO validateAssignmentDTO = new ValidateAssignmentDTO();

        AssignmentDTO input = new AssignmentDTO();
        input.setId(1);
        input.setAmount(-1);
        input.setBox_id(0);

        validateAssignmentDTO.isValid(input);
    }

    @Test(expected = InvalidInputException.class)
    public void isValid_NotValid_Box() throws Exception {
        ValidateAssignmentDTO validateAssignmentDTO = new ValidateAssignmentDTO();

        AssignmentDTO input = new AssignmentDTO();
        input.setId(1);
        input.setAmount(1);
        input.setBox_id(-1);

        validateAssignmentDTO.isValid(input);
    }

}