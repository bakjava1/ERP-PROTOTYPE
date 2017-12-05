package at.ac.tuwien.sepm.assignment.group02.server.rest;

import at.ac.tuwien.sepm.assignment.group02.rest.restController.OrderController;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import at.ac.tuwien.sepm.assignment.group02.server.MainApplication;
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
    public void createOrder(@RequestBody OrderDTO orderDTO) {
        LOG.debug("Trying creation of Order with id: " + orderDTO.getID());

        orderService.createOrder(orderDTO);
    }

    @Override
    @RequestMapping(value="/deleteOrder",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteOrder(@RequestBody OrderDTO orderDTO) {
        LOG.debug("Deleting order " + orderDTO.getID());
        orderService.deleteOrder(orderDTO);
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
