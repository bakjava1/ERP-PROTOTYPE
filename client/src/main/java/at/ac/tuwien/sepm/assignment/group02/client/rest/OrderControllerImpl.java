package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderControllerImpl implements OrderController {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private RestTemplate restTemplate;

    @Autowired
    public OrderControllerImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }


    @Override
    public void createOrder(@RequestBody OrderDTO orderDTO) throws PersistenceLayerException {
        LOG.debug("Sending request for Order Creation to Server");
        try {
            restTemplate.postForObject("http://localhost:8080/createOrder", orderDTO, OrderDTO.class);
        } catch(HttpStatusCodeException e){
            LOG.warn("HttpStatusCodeException {}", e.getResponseBodyAsString());
            throw new PersistenceLayerException("Connection Problem with Server");
        } catch(RestClientException e){
            //no response payload, probably server not running
            LOG.warn("server is down? - {}", e.getMessage());
            throw new PersistenceLayerException("Connection Problem with Server");
        }
    }

    @Override
    public void deleteOrder(@RequestBody OrderDTO orderDTO) throws PersistenceLayerException {
        LOG.debug("sending order to be deleted to server");

        try{
            restTemplate.put("http://localhost:8080/deleteOrder", orderDTO, OrderDTO.class);
        } catch(HttpStatusCodeException e){
            LOG.warn("HttpStatusCodeException {}", e.getResponseBodyAsString());
            throw new PersistenceLayerException("Connection Problem with Server");
        } catch(RestClientException e){
            //no response payload, probably server not running
            LOG.warn("server is down? - {}", e.getMessage());
            throw new PersistenceLayerException("Connection Problem with Server");
        }
    }

    @Override
    public List<OrderDTO> getAllOpen() throws PersistenceLayerException {
        LOG.debug("get all open order");

        List<OrderDTO> orderList = new ArrayList<>();
        OrderDTO[] orderArray; // = restTemplate.getForObject("http://localhost:8080/getAllOpen", OrderDTO[].class);
        try{
            orderArray = restTemplate.getForObject("http://localhost:8080/getAllOpen", OrderDTO[].class);

            for (int i = 0; orderArray!= null && i < orderArray.length; i++) {
                orderList.add(orderArray[i]);
            }
        } catch(HttpStatusCodeException e){
            LOG.warn("HttpStatusCodeException {}", e.getResponseBodyAsString());
            throw new PersistenceLayerException("Connection Problem with Server");
        } catch(RestClientException e){
            //no response payload, probably server not running
            LOG.warn("server is down? - {}", e.getMessage());
            throw new PersistenceLayerException("Connection Problem with Server");
        }

        return orderList;

    }

    @Override
    public void updateOrder(OrderDTO orderDTO) throws PersistenceLayerException {

    }

    @Override
    public List<OrderDTO> getAllClosed() throws PersistenceLayerException {
        LOG.debug("get all closed order");
        List<OrderDTO> billList = new ArrayList<>();

        try {
            OrderDTO[] billArray = restTemplate.getForObject("http://localhost:8080/getAllClosedOrders", OrderDTO[].class);

            for (int i = 0; billArray!= null && i < billArray.length; i++) {
                billList.add(billArray[i]);
            }
        } catch(HttpStatusCodeException e){
            LOG.warn("HttpStatusCodeException {}", e.getResponseBodyAsString());
        } catch(RestClientException e){
            //no response payload, probably server not running
            LOG.warn("server is down? - {}", e.getMessage());
        }


        return billList;
    }

    @Override
    public OrderDTO getOrderById(int order_id) throws PersistenceLayerException  {
        return null;
    }

    @Override
    public void invoiceOrder(OrderDTO orderDTO) throws PersistenceLayerException {
        LOG.debug("sending order that will be invoiced on server");

        try{
            restTemplate.put("http://localhost:8080/invoiceOrder", orderDTO, OrderDTO.class);
        } catch(HttpStatusCodeException e){
            LOG.warn("HttpStatusCodeException {}", e.getResponseBodyAsString());
            throw new PersistenceLayerException("Connection Problem with Server");
        } catch(RestClientException e){
            //no response payload, probably server not running
            LOG.warn("server is down? - {}", e.getMessage());
            throw new PersistenceLayerException("Connection Problem with Server");
        }



    }
}
