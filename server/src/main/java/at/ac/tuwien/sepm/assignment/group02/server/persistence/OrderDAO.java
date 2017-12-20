package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.server.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;

import java.util.List;

public interface OrderDAO {

    /**
     * create an order and a task with all theire relevant data
     * @param order
     * @throws PersistenceLayerException
     */
    void createOrder(Order order) throws PersistenceLayerException;

    /**
     * delete an existing order from the data base
     * @param order
     * @throws PersistenceLayerException
     */
    void deleteOrder(Order order) throws PersistenceLayerException;

    /**
     * retrieve all open orders
     * @return a list of all open orders
     * @throws PersistenceLayerException
     */
    List<Order> getAllOpen() throws PersistenceLayerException;

    /**
     * update an existing order, put flag and save additional values
     * @param order
     * @throws PersistenceLayerException
     */
    void updateOrder(Order order) throws PersistenceLayerException;

    /**
     * all closed orders equal request of invoice
     * @return a lis of all closed orders
     * @throws PersistenceLayerException
     */
    List<Order> getAllClosed() throws PersistenceLayerException;

    /**
     * get order by its id
     * @param order_id
     * @return an order with its id
     * @throws PersistenceLayerException
     */
    Order getOrderById(int order_id) throws PersistenceLayerException;

    /**
     * get an invoice of oder
     * @param order
     * @throws PersistenceLayerException
     */
    void invoiceOrder(Order order) throws PersistenceLayerException;
}
