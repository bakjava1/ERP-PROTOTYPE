package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.server.converter.OrderConverter;
import at.ac.tuwien.sepm.assignment.group02.server.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.OrderDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static OrderDAO orderManagementDAO;
    private static OrderConverter orderConverter;
    private static TaskConverter taskConverter = new TaskConverter();

    @Autowired
    public OrderServiceImpl(OrderDAO orderManagementDAO, OrderConverter orderConverter) {
        OrderServiceImpl.orderManagementDAO = orderManagementDAO;
        OrderServiceImpl.orderConverter = orderConverter;
    }

    @Override
    public void deleteOrder(OrderDTO orderDTO) {
        Order orderToDelete = orderConverter.convertRestDTOToPlainObject(orderDTO);
        try {
            orderManagementDAO.deleteOrder(orderToDelete);
        } catch (PersistenceLayerException e) {
            LOG.error("Error while deleting an order");
        }
    }

    @Override
    public void createOrder(OrderDTO orderDTO) throws ServiceLayerException {
        List<TaskDTO> toConvert = orderDTO.getTaskList();
        List<Task> converted = new ArrayList<>();
        orderDTO.setTaskList(null);
        Order order = orderConverter.convertRestDTOToPlainObject(orderDTO);
        for(int i  = 0; i < toConvert.size();i++) {
            converted.add(taskConverter.convertRestDTOToPlainObject(toConvert.get(i)));
        }
        order.setTaskList(converted);
        try{
            orderManagementDAO.createOrder(order);
        } catch(PersistenceLayerException e) {
            LOG.error("Error while trying to create Object in Database");
            throw new ServiceLayerException("Failed Persistenz");
        }
    }

    @Override
    public List<OrderDTO> getAllOpen() {
        List<Order> allOpen = null;
        List<OrderDTO> allOpenConverted = null;


        try{
            allOpen = orderManagementDAO.getAllOpen();

        } catch(PersistenceLayerException e) {
            LOG.error("Error while trying to get objects from Database: " + e.getMessage());
        }

        if (allOpen!= null) {
            allOpenConverted = new ArrayList<>();

            for (int i = 0; i < allOpen.size(); i++) {
                allOpenConverted.add(orderConverter.convertPlainObjectToRestDTO(allOpen.get(i)));
            }
        }

        return allOpenConverted;
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

    @Override
    public void invoiceOrder(OrderDTO orderDTO) throws ServiceLayerException {
        Order order = orderConverter.convertRestDTOToPlainObject(orderDTO);

        try {
            orderManagementDAO.invoiceOrder(order);
        } catch (PersistenceLayerException e) {
            LOG.error("Error while tying to invoice Order: " + e.getMessage());
            throw new ServiceLayerException("couldn't edit order");
        }
    }
}
