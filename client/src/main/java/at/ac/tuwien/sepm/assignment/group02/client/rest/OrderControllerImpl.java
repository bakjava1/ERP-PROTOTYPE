package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.rest.restController.OrderController;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
public class OrderControllerImpl implements OrderController {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static RestTemplate restTemplate = new RestTemplate();

    public void deleteOrder(@RequestParam(value="id", defaultValue="0") int id) {
        LOG.debug("called deleteOrder");

        restTemplate.getForObject(
                "http://localhost:8080/deleteOrderById/{id}",
                OrderDTO.class, id);
    }

    @Override
    public void createOrder(@RequestBody OrderDTO orderDTO) {
        LOG.debug("Sending request for Order Creation to Server");
        System.out.println("TEST");
        OrderDTO test = restTemplate.postForObject("http://localhost:8080/createOrder", orderDTO, OrderDTO.class);
        //if(test.getID() > 0) { LOG.debug("Success!"); }
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
