package converterTests;

import at.ac.tuwien.sepm.assignment.group02.client.converter.LumberConverter;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import org.junit.Assert;
import org.junit.Test;

public class LumberConverterTest {

    @Test
    public void convertPlainObjectToRestDTO() {
        Lumber lumberToConvert = new Lumber();
        lumberToConvert.setId(3);
        lumberToConvert.setDescription("Latten");
        lumberToConvert.setFinishing("prismiert");
        lumberToConvert.setWood_type("Ta");
        lumberToConvert.setQuality("O/III");
        lumberToConvert.setSize(22);
        lumberToConvert.setWidth(48);
        lumberToConvert.setLength(3500);
        lumberToConvert.setQuantity(40);

        LumberConverter lumberConverter = new LumberConverter();
        LumberDTO convertedLumber = lumberConverter.convertPlainObjectToRestDTO(lumberToConvert);

        Assert.assertEquals(convertedLumber.getId(),lumberToConvert.getId());
        Assert.assertEquals(convertedLumber.getDescription(),lumberToConvert.getDescription());
        Assert.assertEquals(convertedLumber.getFinishing(),lumberToConvert.getFinishing());
        Assert.assertEquals(convertedLumber.getWood_type(),lumberToConvert.getWood_type());
        Assert.assertEquals(convertedLumber.getQuality(),lumberToConvert.getQuality());
        Assert.assertEquals(convertedLumber.getSize(),lumberToConvert.getSize());
        Assert.assertEquals(convertedLumber.getWidth(),lumberToConvert.getWidth());
        Assert.assertEquals(convertedLumber.getLength(),lumberToConvert.getLength());
        Assert.assertEquals(convertedLumber.getQuantity(),lumberToConvert.getQuantity());
    }


    @Test
    public void convertRestDTOToPlainObject() {
        LumberDTO lumberToConvert = new LumberDTO();
        lumberToConvert.setId(3);
        lumberToConvert.setDescription("Latten");
        lumberToConvert.setFinishing("prismiert");
        lumberToConvert.setWood_type("Ta");
        lumberToConvert.setQuality("O/III");
        lumberToConvert.setSize(22);
        lumberToConvert.setWidth(48);
        lumberToConvert.setLength(3500);
        lumberToConvert.setQuantity(40);

        LumberConverter lumberConverter = new LumberConverter();
        Lumber convertedLumber = lumberConverter.convertRestDTOToPlainObject(lumberToConvert);

        Assert.assertEquals(convertedLumber.getId(),lumberToConvert.getId());
        Assert.assertEquals(convertedLumber.getDescription(),lumberToConvert.getDescription());
        Assert.assertEquals(convertedLumber.getFinishing(),lumberToConvert.getFinishing());
        Assert.assertEquals(convertedLumber.getWood_type(),lumberToConvert.getWood_type());
        Assert.assertEquals(convertedLumber.getQuality(),lumberToConvert.getQuality());
        Assert.assertEquals(convertedLumber.getSize(),lumberToConvert.getSize());
        Assert.assertEquals(convertedLumber.getWidth(),lumberToConvert.getWidth());
        Assert.assertEquals(convertedLumber.getLength(),lumberToConvert.getLength());
        Assert.assertEquals(convertedLumber.getQuantity(),lumberToConvert.getQuantity());
    }

}