package converterTests;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import at.ac.tuwien.sepm.assignment.group02.server.converter.AssignmentConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Assignment;
import org.junit.Assert;
import org.junit.Test;

public class AssignmentConverterTest {

    @Test
    public void convertPlainObjectToRestDTO() {
        Assignment toConvert = new Assignment();
        toConvert.setBox_id(1);
        toConvert.setCreation_date("1123");
        toConvert.setTask_id(1);
        toConvert.setAmount(1);
        toConvert.setDone(true);
        toConvert.setId(1);

        AssignmentConverter converter = new AssignmentConverter();
        AssignmentDTO converted = converter.convertPlainObjectToRestDTO(toConvert);

        Assert.assertEquals(converted.getId(),toConvert.getId());
        Assert.assertEquals(converted.isDone(),toConvert.isDone());
        Assert.assertEquals(converted.getAmount(),toConvert.getAmount());
        Assert.assertEquals(converted.getTask_id(),toConvert.getTask_id());
        Assert.assertEquals(converted.getCreation_date(),toConvert.getCreation_date());
        Assert.assertEquals(converted.getBox_id(),toConvert.getBox_id());

    }


    @Test
    public void convertRestDTOToPlainObject() {
        AssignmentDTO toConvert = new AssignmentDTO();
        toConvert.setBox_id(1);
        toConvert.setCreation_date("1123");
        toConvert.setTask_id(1);
        toConvert.setAmount(1);
        toConvert.setDone(true);
        toConvert.setId(1);

        AssignmentConverter converter = new AssignmentConverter();
        Assignment converted = converter.convertRestDTOToPlainObject(toConvert);

        Assert.assertEquals(converted.getId(),toConvert.getId());
        Assert.assertEquals(converted.isDone(),toConvert.isDone());
        Assert.assertEquals(converted.getAmount(),toConvert.getAmount());
        Assert.assertEquals(converted.getTask_id(),toConvert.getTask_id());
        Assert.assertEquals(converted.getCreation_date(),toConvert.getCreation_date());
        Assert.assertEquals(converted.getBox_id(),toConvert.getBox_id());

    }

}