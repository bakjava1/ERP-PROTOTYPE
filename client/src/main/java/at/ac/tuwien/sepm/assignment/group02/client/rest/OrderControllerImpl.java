package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.rest.restController.OrderController;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
public class OrderControllerImpl implements OrderController {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public void deleteOrder(@RequestParam(value="id", defaultValue="0") int id) {
        LOG.debug("called deleteOrder");

        RestTemplate restTemplate = new RestTemplate();

        try{
            restTemplate.getForObject(
                    "http://localhost:8080/deleteOrderById/{id}",
                    OrderDTO.class, id);

        } catch(HttpStatusCodeException e){
            LOG.warn("HttpStatusCodeException {}", e.getResponseBodyAsString());
        } catch(RestClientException e){
            //no response payload, probably server not running
            LOG.warn("server is down? - {}", e.getMessage());
        }
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
