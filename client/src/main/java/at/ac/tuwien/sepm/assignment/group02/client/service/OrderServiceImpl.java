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
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static Validator validator = new Validator();

    private static OrderController orderController;
    private static OrderConverter orderConverter;
    private static TaskConverter taskConverter;

    @Autowired
    public OrderServiceImpl (OrderController orderController, OrderConverter orderConverter,TaskConverter taskConverter){
        OrderServiceImpl.orderController = orderController;
        OrderServiceImpl.orderConverter = orderConverter;
        OrderServiceImpl.taskConverter = taskConverter;
    }

    @Override
    public void addOrder(Order order, List<Task> tasks) throws ServiceLayerException {
        LOG.debug("addOrder called: {},{}", order, tasks);
        try {
            validator.inputValidationOrder(order);
            List<TaskDTO> convertList = new ArrayList<>();
            for(int i = 0; i < tasks.size();i++) {
                convertList.add(taskConverter.convertPlainObjectToRestDTO(tasks.get(i)));
            }
            OrderDTO toAdd = orderConverter.convertPlainObjectToRestDTO(order);
            toAdd.setTaskList(convertList);
            orderController.createOrder(toAdd);
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
            throw new ServiceLayerException(e.getMessage());
        } catch(InvalidInputException e) {
            //TODO maybe add another exception like Failed TaskCreationException
            LOG.error("Input Validation failed: " + e.getMessage());
            throw new InvalidInputException(e.getMessage());
        }
    }

    @Override
    public void deleteOrder(Order order) {
        LOG.debug("deleteOrder called: {}", order);
        OrderDTO orderToDelete = orderConverter.convertPlainObjectToRestDTO(order);
        try {
            orderController.deleteOrder(orderToDelete);
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
        }
    }

    @Override
    public List<Order> getAllOpen() {
        LOG.debug("getAllOpen called");
        List<OrderDTO> allOpen = null;

        try {
            allOpen = orderController.getAllOpen();
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
        }

        List<Order> convertedOrders = convertTaskLists(allOpen);
        setNetPrice(convertedOrders);

        return convertedOrders;

    }

    @Override
    public void closeOrder(Order order) {
        LOG.debug("closeOrder called: {}", order);
    }

    @Override
    public List<Order> getAllClosed() {
        LOG.debug("getAllClosed called");
        List<OrderDTO> allClosed = null;

        try {
            allClosed = orderController.getAllClosed();
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
        }


        List<Order> convertedOrders = convertTaskLists(allClosed);

        setNetPrice(convertedOrders);

        return convertedOrders;
    }

    @Override
    public Order getReceiptById(int order_id) {
        LOG.debug("getReceiptById called: {}", order_id);
        return null;
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
        //TODO get tax rate from properties file
        double taxAmount = (double)netSumTasks * (20.0/100.0);
        int grossAmount = netSumTasks + (int)taxAmount;
        selectedOrder.setGrossAmount(grossAmount);
        selectedOrder.setTaxAmount((int)taxAmount);
        selectedOrder.setDeliveryDate(new Date());
        selectedOrder.setInvoiceDate(new Date());


        validator.inputValidationInvoice(selectedOrder);

        OrderDTO orderDTO = orderConverter.convertPlainObjectToRestDTO(selectedOrder);


        try {
            orderController.invoiceOrder(orderDTO);
        } catch (PersistenceLayerException e) {
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


    private void setNetPrice(List<Order> orders) {

        for (Order order : orders) {

            int sum = 0;

            for (Task task : order.getTaskList()) {
                sum += task.getPrice();

            }

            order.setNetAmount(sum);

        }

    }
}
