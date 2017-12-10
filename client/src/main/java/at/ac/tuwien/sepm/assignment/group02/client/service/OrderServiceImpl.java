package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.OrderController;
import at.ac.tuwien.sepm.assignment.group02.rest.converter.OrderConverter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.LinkedList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static OrderController orderController;
    private static OrderConverter orderConverter;

    @Autowired
    public OrderServiceImpl (OrderController orderController, OrderConverter orderConverter){
        OrderServiceImpl.orderController = orderController;
        OrderServiceImpl.orderConverter = orderConverter;
    }

    @Override
    public void addOrder(Order order, List<Task> tasks) throws InvalidInputException{
        LOG.debug("addOrder called: {},{}", order, tasks);
        OrderDTO toAdd = orderConverter.convertPlainObjectToRestDTO(order);
        try {
            orderController.createOrder(toAdd);
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
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
}
