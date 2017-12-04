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


    @Override
    public void addOrder(Order order, List<Task> tasks) {

    }

    @Override
    public void deleteOrder(Order order) {
        //TODO delete order by orderDTO
        //OrderConverter orderConverter = new OrderConverter();
        //OrderDTO orderDTO = orderConverter.convertPlainObjectToRestDTO(order);
        //orderController.deleteOrder(orderDTO);
        orderController.deleteOrder(order.getID());
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
