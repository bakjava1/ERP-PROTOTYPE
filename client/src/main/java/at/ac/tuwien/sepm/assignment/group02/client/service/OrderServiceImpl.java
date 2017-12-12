package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.OrderControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.client.validation.Validator;
import at.ac.tuwien.sepm.assignment.group02.rest.converter.OrderConverter;
import at.ac.tuwien.sepm.assignment.group02.rest.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.rest.exceptions.EntityCreationException;
import at.ac.tuwien.sepm.assignment.group02.rest.exceptions.EntityNotFoundException;
import at.ac.tuwien.sepm.assignment.group02.rest.restController.OrderController;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.lang.invoke.MethodHandles;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    public static OrderController orderController = new OrderControllerImpl();
    private static OrderConverter orderConverter = new OrderConverter();
    private static TaskConverter taskConverter = new TaskConverter();
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static Validator validator = new Validator();

    @Override
    public void addOrder(Order order, List<Task> tasks) throws InvalidInputException{
        try {
            validator.inputValidationOrder(order);
            List<TaskDTO> convertList = new ArrayList<>();
            for(int i = 0; i < tasks.size();i++) {
                convertList.add(taskConverter.convertPlainObjectToRestDTO(tasks.get(i)));
            }
            OrderDTO toAdd = orderConverter.convertPlainObjectToRestDTO(order);
            toAdd.setTaskList(convertList);
            orderController.createOrder(toAdd);
        } catch(EntityCreationException e) {
            LOG.error("Error creating Entity: " + e.getMessage());
            throw new InvalidInputException("Could not create Order");
        } catch(InvalidInputException e) {
            //TODO maybe add another exception like Failed TaskCreationException
            LOG.error("Input Validation failed: " + e.getMessage());
            throw new InvalidInputException(e.getMessage());
        }
    }

    @Override
    public void deleteOrder(Order order) {
        OrderDTO orderToDelete = orderConverter.convertPlainObjectToRestDTO(order);
        orderController.deleteOrder(orderToDelete);
    }

    @Override
    public List<Order> getAllOpen() {

        List<OrderDTO> allOpen = orderController.getAllOpen();
        List<Order> allOpenConverted = new LinkedList<Order>();


        for (OrderDTO order: allOpen) {
            allOpenConverted.add(orderConverter.convertRestDTOToPlainObject(order));
        }

        return allOpenConverted;

    }

    @Override
    public void closeOrder(Order order) {

    }

    @Override
    public List<Order> getAllClosed() {
        return null;
    }

    @Override
    public Order getReceiptById(int order_id) {
        return null;
    }

    @Override
    public void invoiceOrder(Order selectedOrder) throws InvalidInputException {

        if(selectedOrder==null){
            throw new InvalidInputException("");
        }
        int netAmount = selectedOrder.getNetAmount();
        //TODO get tax rate from properties file
        int taxAmount = netAmount * (20/100);
        int grossAmount = netAmount + taxAmount;
        selectedOrder.setGrossAmount(grossAmount);
        selectedOrder.setTaxAmount(taxAmount);
        selectedOrder.setDeliveryDate(new Date());
        OrderDTO orderDTO = orderConverter.convertPlainObjectToRestDTO(selectedOrder);

        orderController.invoiceOrder(orderDTO);
    }
}
