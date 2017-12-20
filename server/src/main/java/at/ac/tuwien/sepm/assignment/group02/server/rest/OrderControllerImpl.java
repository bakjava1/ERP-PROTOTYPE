package at.ac.tuwien.sepm.assignment.group02.server.rest;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ResourceNotFoundException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value="Order Controller")
public class OrderControllerImpl {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static OrderService orderService;

    @Autowired
    public OrderControllerImpl(OrderService orderService){
        OrderControllerImpl.orderService = orderService;
    }

    /**
     * rest interface of server for order creation
     * @param orderDTO order to be created
     * @throws ResourceNotFoundException if an error occurs on the server
     * @Invariant order got validated
     */
    @RequestMapping(value="/createOrder", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "create order")
    public void createOrder(@RequestBody OrderDTO orderDTO) throws ResourceNotFoundException {
        LOG.debug("Trying creation of Order with id: " + orderDTO.getID());

        try {
            orderService.createOrder(orderDTO);
        } catch(ServiceLayerException e) {
            LOG.error(e.getMessage());
            throw new ResourceNotFoundException("Failed Creation");
        }
    }

    /**
     * rest interface for deleting an order
     * @param orderDTO order to be deleted
     * @throws ResourceNotFoundException if an error occurs in the service layer
     */
    @RequestMapping(value="/deleteOrder", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "delete order")
    public void deleteOrder(@RequestBody OrderDTO orderDTO) throws ResourceNotFoundException {
        LOG.debug("Deleting order " + orderDTO.getID());
        try {
            orderService.deleteOrder(orderDTO);
        } catch (ServiceLayerException e) {
            LOG.error(e.getMessage());
            throw new ResourceNotFoundException("Failed to delete order.");
        }
    }

    @RequestMapping(value="/getAllOpen", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "get open orders")
    public List<OrderDTO> getAllOpen() throws ResourceNotFoundException{
        LOG.debug("Get all order");
        try {
            return orderService.getAllOpen();
        } catch (ServiceLayerException e) {
            LOG.error(e.getMessage());
            throw new ResourceNotFoundException("failed to get all orders.");
        }
    }

    @RequestMapping(value="/updateOrder", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "update order")
    public void updateOrder(OrderDTO orderDTO) throws ResourceNotFoundException {
        LOG.debug("called updateOrder, {}", orderDTO.toString());
        try {
            orderService.updateOrder(orderDTO);
        } catch (ServiceLayerException e) {
            LOG.error(e.getMessage());
            throw new ResourceNotFoundException("failed to update order.");
        }
    }

    @RequestMapping(value="/getAllClosedOrders", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "get all closed orders")
    public List<OrderDTO> getAllClosed() throws ResourceNotFoundException {
        LOG.debug("called getAllClosed");
        try {
            return orderService.getAllClosed();
        } catch (ServiceLayerException e) {
            LOG.error(e.getMessage());
            throw new ResourceNotFoundException("failed to get all closed orders.");
        }
    }

    @RequestMapping(value="/getOrderById/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get Order By Id")
    public OrderDTO getOrderById(int order_id) throws ResourceNotFoundException {
        LOG.debug("called getOrderById");
        try {
            return orderService.getOrderById(order_id);
        } catch (ServiceLayerException e) {
            LOG.error(e.getMessage());
            throw new ResourceNotFoundException("failed to get order by id.");
        }
    }

    @RequestMapping(value="/invoiceOrder", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "invoice order")
    public void invoiceOrder(@RequestBody OrderDTO orderDTO) throws ResourceNotFoundException {
        LOG.debug("invoice Order " + orderDTO.getID());
        try {
            orderService.invoiceOrder(orderDTO);
        } catch (ServiceLayerException e) {
            LOG.error(e.getMessage());
            throw new ResourceNotFoundException("failed to edit order.");
        }
    }
}
