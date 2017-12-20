package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;

import java.util.List;

public interface OrderService {

    /**
     * create an order and a task with all theire relevant data
     * @param orderDTO order to be created
     * @throws ServiceLayerException if an error occurs in the service layer or below (persistence)
     * @Invariant order got validated
     */
    void createOrder(OrderDTO orderDTO) throws ServiceLayerException;

    /**
     * delete an order
     * @param orderDTO order to be deleted
     * @throws ServiceLayerException if the database is not available for the persistence layer
     */
    void deleteOrder(OrderDTO orderDTO) throws ServiceLayerException;

    /**
     * retrieve all orders from data base
     * @return a list of all open orders
     * @throws ServiceLayerException
     */
    List<OrderDTO> getAllOpen() throws ServiceLayerException;

    /**
     * update an order, put flag and save additional values
     * @param orderDTO
     * @throws ServiceLayerException
     */
    void updateOrder(OrderDTO orderDTO) throws ServiceLayerException;

    /**
     * retrieve all closed orders equal
     * @return a list of all closed orders
     * @throws ServiceLayerException
     */
    List<OrderDTO> getAllClosed() throws ServiceLayerException;

    /**
     * get an order by its id
     * @param order_id
     * @return an order with its id
     * @throws ServiceLayerException
     */
    OrderDTO getOrderById(int order_id) throws ServiceLayerException;

    /**
     * get an invoice for the order
     * @param orderDTO
     * @throws ServiceLayerException
     */
    void invoiceOrder(OrderDTO orderDTO) throws ServiceLayerException;
}
