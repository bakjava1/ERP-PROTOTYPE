package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.client.dao.Lumber;
import at.ac.tuwien.sepm.assignment.group02.client.dao.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;

@RestController
public class OrderController {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public void deleteOrder(@RequestParam(value="id", defaultValue="0") int id) {
        LOG.debug("called deleteOrder");

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getForObject(
                "http://localhost:8080/deleteOrderById/{id}",
                Order.class, id);
    }
}
