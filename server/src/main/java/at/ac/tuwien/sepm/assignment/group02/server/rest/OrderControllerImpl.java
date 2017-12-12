package at.ac.tuwien.sepm.assignment.group02.server.rest;

import at.ac.tuwien.sepm.assignment.group02.rest.exceptions.EntityCreationException;
import at.ac.tuwien.sepm.assignment.group02.rest.restController.OrderController;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import at.ac.tuwien.sepm.assignment.group02.server.MainApplication;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLevelException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceDatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;

import static at.ac.tuwien.sepm.assignment.group02.server.MainApplication.orderService;

@RestController
public class OrderControllerImpl implements OrderController {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    @RequestMapping(value="/createOrder",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public void createOrder(@RequestBody OrderDTO orderDTO) throws EntityCreationException {
        LOG.debug("Trying creation of Order with id: " + orderDTO.getID());

        try {
            orderService.createOrder(orderDTO);
        } catch(ServiceDatabaseException e) {
            LOG.error("Database Error: " + e.getMessage());
            throw new EntityCreationException("Failed Creation");
        }
    }

    @Override
    @RequestMapping(value="/deleteOrder",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteOrder(@RequestBody OrderDTO orderDTO) {
        LOG.debug("Deleting order " + orderDTO.getID());
        orderService.deleteOrder(orderDTO);
    }

    @Override
    @RequestMapping(value="/getAllOpen",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderDTO> getAllOpen() {
        LOG.debug("Get all order");
        return orderService.getAllOpen();
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

    @RequestMapping(value="/invoiceOrder",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public void invoiceOrder(@RequestBody OrderDTO orderDTO){
        LOG.debug("invoice Order " + orderDTO.getID());
        orderService.invoiceOrder(orderDTO);
    }
}
