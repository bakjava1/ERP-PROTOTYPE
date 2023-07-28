package converterTests;

import at.ac.tuwien.sepm.assignment.group02.client.converter.TimberConverter;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;
import org.junit.Assert;
import org.junit.Test;

public class TimberConverterTest {

    @Test
    public void convertPlainObjectToRestDTO() {
        Timber toConvert = new Timber();
        toConvert.setMAX_AMOUNT(50);
        toConvert.setBox_id(3);
        toConvert.setDiameter(35);
        toConvert.setAmount(2);
        toConvert.setWood_type("Ta");
        toConvert.setQuality("O");
        toConvert.setLength(3500);
        toConvert.setFestmeter(35.4);
        toConvert.setPrice(34);
        toConvert.setLast_edited("456");

        TimberConverter converter = new TimberConverter();
        TimberDTO converted = converter.convertPlainObjectToRestDTO(toConvert);

        Assert.assertEquals(converted.getMAX_AMOUNT(),toConvert.getMAX_AMOUNT());
        Assert.assertEquals(converted.getBox_id(),toConvert.getBox_id());
        Assert.assertEquals(converted.getDiameter(),toConvert.getDiameter());
        Assert.assertEquals(converted.getAmount(),toConvert.getAmount());
        Assert.assertEquals(converted.getWood_type(),toConvert.getWood_type());
        Assert.assertEquals(converted.getQuality(),toConvert.getQuality());
        Assert.assertEquals(converted.getLength(),toConvert.getLength());
        Assert.assertEquals(converted.getFestmeter(),toConvert.getFestmeter(),0);
        Assert.assertEquals(converted.getPrice(),toConvert.getPrice());
        Assert.assertEquals(converted.getLast_edited(),toConvert.getLast_edited());
    }


    @Test
    public void convertRestDTOToPlainObject() {
        TimberDTO toConvert = new TimberDTO();
        toConvert.setMAX_AMOUNT(50);
        toConvert.setBox_id(3);
        toConvert.setDiameter(35);
        toConvert.setAmount(2);
        toConvert.setWood_type("Ta");
        toConvert.setQuality("O");
        toConvert.setLength(3500);
        toConvert.setFestmeter(35.4);
        toConvert.setPrice(34);
        toConvert.setLast_edited("456");

        TimberConverter converter = new TimberConverter();
        Timber converted = converter.convertRestDTOToPlainObject(toConvert);

        Assert.assertEquals(converted.getMAX_AMOUNT(),toConvert.getMAX_AMOUNT());
        Assert.assertEquals(converted.getBox_id(),toConvert.getBox_id());
        Assert.assertEquals(converted.getDiameter(),toConvert.getDiameter());
        Assert.assertEquals(converted.getAmount(),toConvert.getAmount());
        Assert.assertEquals(converted.getWood_type(),toConvert.getWood_type());
        Assert.assertEquals(converted.getQuality(),toConvert.getQuality());
        Assert.assertEquals(converted.getLength(),toConvert.getLength());
        Assert.assertEquals(converted.getFestmeter(),toConvert.getFestmeter(),0);
        Assert.assertEquals(converted.getPrice(),toConvert.getPrice());
        Assert.assertEquals(converted.getLast_edited(),toConvert.getLast_edited());
    }

}