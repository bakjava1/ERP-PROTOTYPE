package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Task;

import java.util.List;

public class OrderServiceImpl implements OrderService{

    @Override
    public void addOrder(Order order, List<Task> tasks) {

    }

    @Override
    public void deleteOrder(Order order) {

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
