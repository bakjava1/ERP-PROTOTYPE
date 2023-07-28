package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Task;

import java.util.List;

/**
 * CONVERSION HAPPENS ON THIS LAYER
 * VALIDATION HAPPENS ON THIS LAYER
 */
public interface OrderService {

    /**
     * create an order and a task with all the relevant data
     * @param order order to be created
     * @param tasks list of tasks connected to order
     * @throws ServiceLayerException if the order couldn't be created
     */
    void addOrder(Order order, List<Task> tasks) throws ServiceLayerException;

    /**
     * delete an order
     * @param order to be deleted
     * @throws ServiceLayerException if the order couldn't be deleted
     */
    void deleteOrder(Order order) throws ServiceLayerException;

    /**
     * retrieve all orders from data base
     * @return list of all open order
     * @throws ServiceLayerException is thrown if an error occurs in the client persistence layer
     */
    List<Order> getAllOpen() throws ServiceLayerException;

    /**
     * retrieve all clossed orders
     * @return list of all closed order
     * @throws ServiceLayerException is thrown if an error occurs in the client persistence layer
     */
    List<Order> getAllClosed() throws ServiceLayerException;

    /**
     * takes an orders and invoices it, calculates the necessary prices and removes reserved lumber
     * converts order to an orderDTO
     * @author Philipp Klein
     * @param selectedOrder order that will be invoiced
     * @throws InvalidInputException if selectedOrder is not initialized correctly
     * @throws ServiceLayerException if error when trying to execute command
     */
    void invoiceOrder(Order selectedOrder) throws ServiceLayerException;
}
