package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.server.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;

import java.util.List;

public interface OrderDAO {

    /**
     * create an order and a task with all theire relevant data
     * @param order
     * @throws PersistenceLayerException
     * @inv order is validated
     */
    void createOrder(Order order) throws PersistenceLayerException;

    /**
     * delete an existing order from the data base
     * @param order
     * @throws PersistenceLayerException
     * @inv order is validated
     */
    void deleteOrder(Order order) throws PersistenceLayerException;

    /**
     * retrieve all open orders
     * @return list of all open orders
     * @throws PersistenceLayerException if the database is not available
     */
    List<Order> getAllOpen() throws PersistenceLayerException;

    /**
     * all closed orders equal request of invoice
     * @return list of all closed orders
     * @throws PersistenceLayerException if the database is not available
     */
    List<Order> getAllClosed() throws PersistenceLayerException;

    /**
     * takes an orders and marks it as invoiced and sets necessary fields to create an invoice
     * @author Philipp Klein
     * @param order order that will be invoiced
     * @throws PersistenceLayerException when error executing update of order
     * @inv order is validated
     */
    void invoiceOrder(Order order) throws PersistenceLayerException;
}
