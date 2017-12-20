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
     * @return list of all open order
     * @throws ServiceLayerException is thrown if an error occurs in the client persistence layer
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
     * @return list of all closed order
     * @throws ServiceLayerException is thrown if an error occurs in the client persistence layer
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
     * takes an orders and invoices it, calculates the necessary prices and removes reserved lumber
     * converts order to an orderDTO
     * @author Philipp Klein
     * @param selectedOrder order that will be invoiced
     * @throws InvalidInputException if selectedOrder is not initialized correctly
     * @throws ServiceLayerException if error when trying to execute command
     */
    void invoiceOrder(Order selectedOrder) throws InvalidInputException, ServiceLayerException;
}
