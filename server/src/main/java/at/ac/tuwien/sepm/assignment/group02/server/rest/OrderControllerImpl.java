package at.ac.tuwien.sepm.assignment.group02.server.rest;

import at.ac.tuwien.sepm.assignment.group02.server.exceptions.EntityCreationException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.EntityNotFoundException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
public class OrderControllerImpl {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static OrderService orderService;

    @Autowired
    public OrderControllerImpl(OrderService orderService){

        OrderControllerImpl.orderService = orderService;
    }

    @RequestMapping(value="/createOrder",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public void createOrder(@RequestBody OrderDTO orderDTO) throws EntityCreationException {
        LOG.debug("Trying creation of Order with id: " + orderDTO.getID());

        try {
            orderService.createOrder(orderDTO);
        } catch(ServiceLayerException e) {
            LOG.error("Database Error: " + e.getMessage());
            throw new EntityCreationException("Failed Creation");
        }
    }

    @RequestMapping(value="/deleteOrder",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteOrder(@RequestBody OrderDTO orderDTO) throws EntityNotFoundException {
        LOG.debug("Deleting order " + orderDTO.getID());
        try {
            orderService.deleteOrder(orderDTO);
        } catch (ServiceLayerException e) {
            throw new EntityNotFoundException("Failed to delete order.");
        }
    }

    @RequestMapping(value="/getAllOpen",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderDTO> getAllOpen() throws EntityNotFoundException{
        LOG.debug("Get all order");
        try {
            return orderService.getAllOpen();
        } catch (ServiceLayerException e) {
            throw new EntityNotFoundException("failed to get all orders.");
        }
    }

    public void updateOrder(OrderDTO orderDTO) { }

    public List<OrderDTO> getAllClosed() {
        return null;
    }

    public OrderDTO getOrderById(int order_id) {
        return null;
    }
}
