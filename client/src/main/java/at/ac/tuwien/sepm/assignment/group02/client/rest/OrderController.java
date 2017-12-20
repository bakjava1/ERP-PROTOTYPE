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
     * @return  a list of  all orders
     * @throws PersistenceLayerException
     */
    List<OrderDTO> getAllOpen() throws PersistenceLayerException;

    /**
     * update an order, put flag and save additional values
     * @param orderDTO
     * @throws PersistenceLayerException
     */
    void updateOrder(OrderDTO orderDTO) throws PersistenceLayerException;

    /**
     * all closed orders equal request of invoice
     * @return a list of all closed orders
     * @throws PersistenceLayerException
     */
    List<OrderDTO> getAllClosed() throws PersistenceLayerException;

    /**
     * get an order by id
     * @param order_id
     * @return an id of order
     * @throws PersistenceLayerException
     */
    OrderDTO getOrderById(int order_id) throws PersistenceLayerException;

    /**
     * get the invoice of order
     * @param orderDTO
     * @throws PersistenceLayerException
     */
    void invoiceOrder(OrderDTO orderDTO) throws PersistenceLayerException;
}
