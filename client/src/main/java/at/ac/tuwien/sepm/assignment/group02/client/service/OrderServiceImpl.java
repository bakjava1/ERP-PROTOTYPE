package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.OrderControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.rest.converter.OrderConverter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.rest.exceptions.EntityCreationException;
import at.ac.tuwien.sepm.assignment.group02.rest.exceptions.EntityNotFoundException;
import at.ac.tuwien.sepm.assignment.group02.rest.restController.OrderController;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    public static OrderController orderController = new OrderControllerImpl();
    private static OrderConverter orderConverter = new OrderConverter();
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void addOrder(Order order, List<Task> tasks) throws InvalidInputException{
        OrderDTO toAdd = orderConverter.convertPlainObjectToRestDTO(order);
        try {
            orderController.createOrder(toAdd);
        } catch(EntityCreationException e) {
            LOG.error("Error creating Entity: " + e.getMessage());
            throw new InvalidInputException("Could not create Order");
        }
    }

    @Override
    public void deleteOrder(Order order) {
        //TODO delete order by orderDTO
        //OrderConverter orderConverter = new OrderConverter();
        //OrderDTO orderDTO = orderConverter.convertPlainObjectToRestDTO(order);
        //orderController.deleteOrder(orderDTO);
        try {
            orderController.deleteOrder(order.getID());
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> getAllOpen() {
        return null;
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
}
