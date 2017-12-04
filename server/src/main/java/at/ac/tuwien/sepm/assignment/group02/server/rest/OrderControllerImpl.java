package at.ac.tuwien.sepm.assignment.group02.server.rest;

import at.ac.tuwien.sepm.assignment.group02.rest.restController.OrderController;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import at.ac.tuwien.sepm.assignment.group02.server.MainApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
public class OrderControllerImpl implements OrderController {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @RequestMapping(value="/deleteOrderById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteOrder(@PathVariable(value = "id") int id) {

        LOG.debug("deleted order number {}", id);

        MainApplication.orderService.deleteOrder(id);
    }

    @Override
    public void createOrder(OrderDTO orderDTO) {

    }

    @Override
    public void deleteOrder(OrderDTO orderDTO) {

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
