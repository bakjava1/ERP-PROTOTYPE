package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.rest.restController.OrderController;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderControllerImpl implements OrderController {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static RestTemplate restTemplate = new RestTemplate();

    @Override
    public void createOrder(@RequestBody OrderDTO orderDTO) {
        LOG.debug("Sending request for Order Creation to Server");
        restTemplate.postForObject("http://localhost:8080/createOrder", orderDTO, OrderDTO.class);
    }

    @Override
    public void deleteOrder(@RequestBody OrderDTO orderDTO) {
        LOG.debug("sending order to be deleted to server");
        restTemplate.postForObject("http://localhost:8080/deleteOrder", orderDTO, OrderDTO.class);
    }

    @Override
    public List<OrderDTO> getAllOpen() {
        LOG.debug("get all open order");

        List<OrderDTO> orderList = new ArrayList<>();
        OrderDTO[] orderArray = restTemplate.getForObject("http://localhost:8080/getAllOpen", OrderDTO[].class);

        for (int i = 0; orderArray!= null && i < orderArray.length; i++) {
            orderList.add(orderArray[i]);
        }


        return orderList;

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
