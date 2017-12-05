package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.rest.OrderControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.rest.converter.OrderConverter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.rest.restController.OrderController;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    public static OrderController orderController = new OrderControllerImpl();
    private static OrderConverter orderConverter = new OrderConverter();

    @Override
    public void addOrder(Order order, List<Task> tasks) {
        OrderDTO toAdd = orderConverter.convertPlainObjectToRestDTO(order);
        orderController.createOrder(toAdd);
    }

    @Override
    public void deleteOrder(Order order) {
        OrderDTO orderToDelete = orderConverter.convertPlainObjectToRestDTO(order);
        orderController.deleteOrder(orderToDelete);
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
