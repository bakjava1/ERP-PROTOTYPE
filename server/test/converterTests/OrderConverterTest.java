package converterTests;

import at.ac.tuwien.sepm.assignment.group02.server.converter.OrderConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class OrderConverterTest {

    @Test
    public void convertPlainObjectToRestDTO() {
        Order toConvert = new Order();
        toConvert.setID(2);
        toConvert.setInvoiceDate("123");
        toConvert.setDeliveryDate("345");
        toConvert.setCustomerName("asd");
        toConvert.setCustomerAddress("asd");
        toConvert.setCustomerUID("asd");
        toConvert.setOrderDate("567");
        toConvert.setPaid(true);
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task());
        toConvert.setTaskList(taskList);

        OrderConverter converter = new OrderConverter();
        OrderDTO converted = converter.convertPlainObjectToRestDTO(toConvert);

        Assert.assertEquals(converted.getID(),toConvert.getID());
        Assert.assertEquals(converted.getInvoiceDate(),toConvert.getInvoiceDate());
        Assert.assertEquals(converted.getDeliveryDate(),toConvert.getDeliveryDate());
        Assert.assertEquals(converted.getCustomerName(),toConvert.getCustomerName());
        Assert.assertEquals(converted.getCustomerAddress(),toConvert.getCustomerAddress());
        Assert.assertEquals(converted.getCustomerUID(),toConvert.getCustomerUID());
        Assert.assertEquals(converted.getOrderDate(),toConvert.getOrderDate());
        Assert.assertEquals(converted.isPaid(),toConvert.isPaid());
        Assert.assertEquals(converted.getTaskList().size(),toConvert.getTaskList().size());

    }


    @Test
    public void convertRestDTOToPlainObject() {
        OrderDTO toConvert = new OrderDTO();
        toConvert.setID(2);
        toConvert.setInvoiceDate("123");
        toConvert.setDeliveryDate("345");
        toConvert.setCustomerName("asd");
        toConvert.setCustomerAddress("asd");
        toConvert.setCustomerUID("asd");
        toConvert.setOrderDate("567");
        toConvert.setPaid(true);
        List<TaskDTO> taskList = new ArrayList<>();
        taskList.add(new TaskDTO());
        toConvert.setTaskList(taskList);

        OrderConverter converter = new OrderConverter();
        Order converted = converter.convertRestDTOToPlainObject(toConvert);

        Assert.assertEquals(converted.getID(),toConvert.getID());
        Assert.assertEquals(converted.getInvoiceDate(),toConvert.getInvoiceDate());
        Assert.assertEquals(converted.getDeliveryDate(),toConvert.getDeliveryDate());
        Assert.assertEquals(converted.getCustomerName(),toConvert.getCustomerName());
        Assert.assertEquals(converted.getCustomerAddress(),toConvert.getCustomerAddress());
        Assert.assertEquals(converted.getCustomerUID(),toConvert.getCustomerUID());
        Assert.assertEquals(converted.getOrderDate(),toConvert.getOrderDate());
        Assert.assertEquals(converted.isPaid(),toConvert.isPaid());
        Assert.assertEquals(converted.getTaskList().size(),toConvert.getTaskList().size());

    }

}