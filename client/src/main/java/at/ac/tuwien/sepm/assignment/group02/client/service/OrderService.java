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
     * create an order and a task with all theire relevant data
     * @param order
     * @param tasks
     * @throws InvalidInputException
     * @throws ServiceLayerException
     */
    void addOrder(Order order, List<Task> tasks) throws InvalidInputException, ServiceLayerException;

    /**
     * delete an order
     * @param order
     * @throws InvalidInputException
     * @throws ServiceLayerException
     */
    void deleteOrder(Order order) throws InvalidInputException, ServiceLayerException;

    /**
     *  retrieve all orders from data base
     * @return a list of all open orders
     * @throws ServiceLayerException
     */
    List<Order> getAllOpen() throws ServiceLayerException;

    /**
     * update an order, put flag and save additional values
     * @param order
     * @throws InvalidInputException
     * @throws ServiceLayerException
     */
    void closeOrder(Order order) throws InvalidInputException, ServiceLayerException;

    /**
     * retrieve all clossed orders
     * @return a list of all closed orders
     * @throws ServiceLayerException
     */
    List<Order> getAllClosed() throws ServiceLayerException;

    /**
     * get an invoice by its id
     * @param order_id
     * @return the invoice
     * @throws InvalidInputException
     * @throws ServiceLayerException
     */
    Order getReceiptById(int order_id) throws InvalidInputException, ServiceLayerException;

    /**
     * get the invoice of order
     * @param selectedOrder
     * @throws InvalidInputException
     * @throws ServiceLayerException
     */
    void invoiceOrder(Order selectedOrder) throws InvalidInputException, ServiceLayerException;
}
