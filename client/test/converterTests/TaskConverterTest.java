package converterTests;

import at.ac.tuwien.sepm.assignment.group02.client.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import org.junit.Assert;
import org.junit.Test;

public class TaskConverterTest {

    @Test
    public void convertPlainObjectToRestDTO() {
        Task toConvert = new Task();
        toConvert.setId(3);
        toConvert.setDescription("Latten");
        toConvert.setFinishing("prismiert");
        toConvert.setWood_type("Ta");
        toConvert.setQuality("O/III");
        toConvert.setSize(22);
        toConvert.setWidth(48);
        toConvert.setLength(3500);
        toConvert.setQuantity(40);

        TaskConverter converter = new TaskConverter();
        TaskDTO converted = converter.convertPlainObjectToRestDTO(toConvert);

        Assert.assertEquals(converted.getId(),toConvert.getId());
        Assert.assertEquals(converted.getDescription(),toConvert.getDescription());
        Assert.assertEquals(converted.getFinishing(),toConvert.getFinishing());
        Assert.assertEquals(converted.getWood_type(),toConvert.getWood_type());
        Assert.assertEquals(converted.getQuality(),toConvert.getQuality());
        Assert.assertEquals(converted.getSize(),toConvert.getSize());
        Assert.assertEquals(converted.getWidth(),toConvert.getWidth());
        Assert.assertEquals(converted.getLength(),toConvert.getLength());
        Assert.assertEquals(converted.getQuantity(),toConvert.getQuantity());
    }


    @Test
    public void convertRestDTOToPlainObject() {
        TaskDTO toConvert = new TaskDTO();
        toConvert.setId(3);
        toConvert.setDescription("Latten");
        toConvert.setFinishing("prismiert");
        toConvert.setWood_type("Ta");
        toConvert.setQuality("O/III");
        toConvert.setSize(22);
        toConvert.setWidth(48);
        toConvert.setLength(3500);
        toConvert.setQuantity(40);

        TaskConverter converter = new TaskConverter();
        Task converted = converter.convertRestDTOToPlainObject(toConvert);

        Assert.assertEquals(converted.getId(),toConvert.getId());
        Assert.assertEquals(converted.getDescription(),toConvert.getDescription());
        Assert.assertEquals(converted.getFinishing(),toConvert.getFinishing());
        Assert.assertEquals(converted.getWood_type(),toConvert.getWood_type());
        Assert.assertEquals(converted.getQuality(),toConvert.getQuality());
        Assert.assertEquals(converted.getSize(),toConvert.getSize());
        Assert.assertEquals(converted.getWidth(),toConvert.getWidth());
        Assert.assertEquals(converted.getLength(),toConvert.getLength());
        Assert.assertEquals(converted.getQuantity(),toConvert.getQuantity());
    }

}