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
     * @return list of all open orders
     * @throws ServiceLayerException if the database is not available for the persistence layer
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
     * @return list of all closed orders
     * @throws ServiceLayerException if the database is not available for the persistence layer
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
     * takes an orderDTO converts it into an order and invoices it
     * @author Philipp Klein
     * @param orderDTO orderDTO that will be invoiced
     * @throws ServiceLayerException if error when trying to invoice order
     */
    void invoiceOrder(OrderDTO orderDTO) throws ServiceLayerException;
}
