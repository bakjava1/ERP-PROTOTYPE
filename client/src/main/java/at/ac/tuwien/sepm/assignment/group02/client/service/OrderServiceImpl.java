package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.OrderController;
import at.ac.tuwien.sepm.assignment.group02.client.validation.Validator;
import at.ac.tuwien.sepm.assignment.group02.rest.converter.OrderConverter;
import at.ac.tuwien.sepm.assignment.group02.rest.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.rest.exceptions.EntityCreationException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static Validator validator = new Validator();

    private static OrderController orderController;
    private static OrderConverter orderConverter;
    private static TaskConverter taskConverter;

    @Autowired
    public OrderServiceImpl (OrderController orderController, OrderConverter orderConverter,TaskConverter taskConverter){
        OrderServiceImpl.orderController = orderController;
        OrderServiceImpl.orderConverter = orderConverter;
        OrderServiceImpl.taskConverter = taskConverter;
    }

    @Override
    public void addOrder(Order order, List<Task> tasks) throws InvalidInputException{
        LOG.debug("addOrder called: {},{}", order, tasks);
        OrderDTO toAdd = orderConverter.convertPlainObjectToRestDTO(order);
        try {
            validator.inputValidationOrder(order);
            List<TaskDTO> convertList = new ArrayList<>();
            for(int i = 0; i < tasks.size();i++) {
                convertList.add(taskConverter.convertPlainObjectToRestDTO(tasks.get(i)));
            }
            toAdd = orderConverter.convertPlainObjectToRestDTO(order);
            toAdd.setTaskList(convertList);
            orderController.createOrder(toAdd);
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
        } catch(InvalidInputException e) {
            //TODO maybe add another exception like Failed TaskCreationException
            LOG.error("Input Validation failed: " + e.getMessage());
            throw new InvalidInputException(e.getMessage());
        }
    }

    @Override
    public void deleteOrder(Order order) {
        LOG.debug("deleteOrder called: {}", order);
        OrderDTO orderToDelete = orderConverter.convertPlainObjectToRestDTO(order);
        try {
            orderController.deleteOrder(orderToDelete);
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
        }
    }

    @Override
    public List<Order> getAllOpen() {
        LOG.debug("getAllOpen called");
        List<OrderDTO> allOpen = null;

        try {
            allOpen = orderController.getAllOpen();
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
        }

        List<Order> allOpenConverted = new LinkedList<>();


        for (OrderDTO order: allOpen) {
            allOpenConverted.add(orderConverter.convertRestDTOToPlainObject(order));
        }

        return allOpenConverted;

    }

    @Override
    public void closeOrder(Order order) {
        LOG.debug("closeOrder called: {}", order);
    }

    @Override
    public List<Order> getAllClosed() {
        LOG.debug("getAllClosed called");
        return null;
    }

    @Override
    public Order getReceiptById(int order_id) {
        LOG.debug("getReceiptById called: {}", order_id);
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
