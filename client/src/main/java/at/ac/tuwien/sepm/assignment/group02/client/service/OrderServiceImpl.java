package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.converter.OrderConverter;
import at.ac.tuwien.sepm.assignment.group02.client.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.OrderController;
import at.ac.tuwien.sepm.assignment.group02.client.validation.Validator;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static Validator validator;

    private static OrderController orderController;
    private static OrderConverter orderConverter;
    private static TaskConverter taskConverter;

    @Autowired
    public OrderServiceImpl (OrderController orderController, OrderConverter orderConverter,TaskConverter taskConverter,Validator validator){
        OrderServiceImpl.orderController = orderController;
        OrderServiceImpl.orderConverter = orderConverter;
        OrderServiceImpl.taskConverter = taskConverter;
        OrderServiceImpl.validator = validator;
    }

    @Override
    public void addOrder(Order order, List<Task> tasks) throws ServiceLayerException{
        LOG.debug("addOrder called: {},{}", order, tasks);
        try {
            validator.inputValidationOrder(order);
            List<TaskDTO> convertList = new ArrayList<>();
            for(Task t : tasks){
                convertList.add(taskConverter.convertPlainObjectToRestDTO(t));
            }
            OrderDTO toAdd = orderConverter.convertPlainObjectToRestDTO(order);
            toAdd.setTaskList(convertList);
            orderController.createOrder(toAdd);
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
            throw new ServiceLayerException(e.getMessage());
        } catch(InvalidInputException e) {
            LOG.error("Input Validation failed: " + e.getMessage());
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public void deleteOrder(Order order) throws ServiceLayerException {
        LOG.debug("deleteOrder called: {}", order);
        OrderDTO orderToDelete = orderConverter.convertPlainObjectToRestDTO(order);
        try {
            orderController.deleteOrder(orderToDelete);
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public List<Order> getAllOpen() throws ServiceLayerException {
        LOG.debug("getAllOpen called");
        List<OrderDTO> allOpen;

        try {
            allOpen = orderController.getAllOpen();
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
            throw new ServiceLayerException(e.getMessage());
        }

        List<Order> convertedOrders = convertTaskLists(allOpen);
        setPrices(convertedOrders);

        return convertedOrders;

    }

    @Override
    public List<Order> getAllClosed() throws ServiceLayerException {
        LOG.debug("getAllClosed called");
        List<OrderDTO> allClosed;

        try {
            allClosed = orderController.getAllClosed();
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
            throw new ServiceLayerException(e.getMessage());
        }

        List<Order> convertedOrders = convertTaskLists(allClosed);

        setPrices(convertedOrders);

        return convertedOrders;
    }

    @Override
    public void invoiceOrder(Order selectedOrder) throws ServiceLayerException {

        validator.inputValidationOrder(selectedOrder);

        if(selectedOrder.isPaid()){
            throw new InvalidInputException("Order already invoiced");
        }

        int netSumTasks = 0;
        for(Task task : selectedOrder.getTaskList()){
            validator.inputValidationTaskOnOrder(task);
            netSumTasks += task.getPrice();
        }


        selectedOrder.setNetAmount(netSumTasks);
        double taxAmount = (double)netSumTasks * (20.0/100.0);
        int grossAmount = netSumTasks + (int)taxAmount;
        selectedOrder.setGrossAmount(grossAmount);
        selectedOrder.setTaxAmount((int)taxAmount);
        selectedOrder.setDeliveryDate(null);
        selectedOrder.setInvoiceDate(null);
        selectedOrder.setPaid(true);

        validator.inputValidationInvoice(selectedOrder);

        OrderDTO orderDTO = orderConverter.convertPlainObjectToRestDTO(selectedOrder);

        try {
            orderController.invoiceOrder(orderDTO);
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
            throw new ServiceLayerException(e.getMessage());
        }
    }



    private List<Order> convertTaskLists(List<OrderDTO> allOrder){
        List<Order> allConverted = new LinkedList<>();

        for (OrderDTO currentOrder: allOrder) {
            Order order = orderConverter.convertRestDTOToPlainObject(currentOrder);
            List<Task> tasksConverted = new LinkedList<>();

            for (TaskDTO task: currentOrder.getTaskList()) {
                tasksConverted.add(taskConverter.convertRestDTOToPlainObject(task));
            }

            order.setTaskList(tasksConverted);

            allConverted.add(order);
        }

        return allConverted;
    }


    private void setPrices(List<Order> orders) {

        for (Order order : orders) {

            int sum = 0;

            for (Task task : order.getTaskList()) {
                sum += task.getPrice();
            }

            order.setNetAmount(sum);
            order.setTaxAmount((int) (sum * 0.2));
            order.setGrossAmount(order.getNetAmount() + order.getTaxAmount());
        }

    }
}
