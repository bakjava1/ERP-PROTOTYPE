package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.rest.converter.OrderConverter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLevelException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.OrderDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static OrderDAO orderManagementDAO;
    private static OrderConverter orderConverter = new OrderConverter();

    public OrderServiceImpl(OrderDAO orderManagementDAO) {
        OrderServiceImpl.orderManagementDAO = orderManagementDAO;
    }


    @Override
    public void deleteOrder(OrderDTO orderDTO) {

    }

    @Override
    public void deleteOrder(int id) {
        try {
            orderManagementDAO.deleteOrder(id);
        } catch (PersistenceLevelException e) {
            LOG.warn("error at deleting order: {}", e.getMessage());
        }
    }

    @Override
    public void createOrder(OrderDTO orderDTO) {
        Order order = orderConverter.convertRestDTOToPlainObject(orderDTO);
        try{
            orderManagementDAO.createOrder(order);
        } catch(PersistenceLevelException e) {
            LOG.error("Error while trying to create Object in Database");
        }
    }

    @Override
    public List<OrderDTO> getAllOpen() {
        return null;
    }

    @Override
    public void updateOrder(OrderDTO orderDTO) {

    }

    @Override
    public List<OrderDTO> getAllClosed() {
        return null;
    }

    @Override
    public OrderDTO getOrderById(int order_id) {
        return null;
    }
}
