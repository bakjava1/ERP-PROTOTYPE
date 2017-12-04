package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLevelException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.OrderManagementDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class OrderServiceImpl implements OrderService {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static OrderManagementDAO orderManagementDAO;

    public OrderServiceImpl() {}

    public OrderServiceImpl(OrderManagementDAO orderManagementDAO) {
        this.orderManagementDAO = orderManagementDAO;
    }

    @Override
    public void deleteOrder(int id) {
        try {
            orderManagementDAO.deleteOrder(id);
        } catch (PersistenceLevelException e) {
            LOG.warn("error at deleting order: {}", e.getMessage());
        }
    }
}
