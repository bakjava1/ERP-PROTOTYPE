package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;

import java.util.List;

public interface OrderController {

    /**
     * create an order with all relevant data
     * @param orderDTO
     * @throws PersistenceLayerException
     */
    void createOrder(OrderDTO orderDTO) throws PersistenceLayerException;

    /**
     * delete an order
     * @param orderDTO
     * @throws PersistenceLayerException
     */
    void deleteOrder(OrderDTO orderDTO) throws PersistenceLayerException;

    /**
     * retrieve all open orders
     * @return list of all open Order
     * @throws PersistenceLayerException if the server is not available
     */
    List<OrderDTO> getAllOpen() throws PersistenceLayerException;

    /**
     * all closed orders equal request of invoice
     * @return list of all closed Order
     * @throws PersistenceLayerException if the server is not available
     */
    List<OrderDTO> getAllClosed() throws PersistenceLayerException;

    /**
     * get the invoice of order
     * @param orderDTO
     * @throws PersistenceLayerException
     */
    void invoiceOrder(OrderDTO orderDTO) throws PersistenceLayerException;
}
