package at.ac.tuwien.sepm.assignment.group02.client.rest;


import at.ac.tuwien.sepm.assignment.group02.client.configuration.RestTemplateConfiguration;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.util.HandleException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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
            restTemplate.postForObject("http://"+RestTemplateConfiguration.host+":"+RestTemplateConfiguration.port+"/createOrder", orderDTO, OrderDTO.class);
        } catch(HttpStatusCodeException e){
            HandleException.handleHttpStatusCodeException(e);
        } catch(RestClientException e){
            LOG.warn("server down? ", e.getMessage());
            throw new PersistenceLayerException("Keine valide Antwort vom Server. Ist der Server erreichbar?");
        }
    }

    @Override
    public void deleteOrder(@RequestBody OrderDTO orderDTO) throws PersistenceLayerException {
        LOG.debug("sending order to be deleted to server");

        try{
            //restTemplate.put("http://"+RestTemplateConfiguration.host+":"+RestTemplateConfiguration.port+"/deleteOrder",
            // orderDTO, OrderDTO.class);
            restTemplate.exchange(
                    "http://"+RestTemplateConfiguration.host+":"+RestTemplateConfiguration.port+"/deleteOrder",
                    HttpMethod.PUT, new HttpEntity<>(orderDTO), OrderDTO.class);
        } catch(HttpStatusCodeException e){
            HandleException.handleHttpStatusCodeException(e);
        } catch(RestClientException e){
            LOG.warn("server down? ", e.getMessage());
            throw new PersistenceLayerException("Keine valide Antwort vom Server. Ist der Server erreichbar?");
        }
    }

    @Override
    public List<OrderDTO> getAllOpen() throws PersistenceLayerException {
        LOG.debug("get all open order");

        List<OrderDTO> orderList = new ArrayList<>();
        OrderDTO[] orderArray; // = restTemplate.getForObject("http://"+RestTemplateConfiguration.host+":"+RestTemplateConfiguration.port+"/getAllOpen", OrderDTO[].class);
        try{
            orderArray = restTemplate.getForObject("http://"+RestTemplateConfiguration.host+":"+RestTemplateConfiguration.port+"/getAllOpen", OrderDTO[].class);

            for (int i = 0; orderArray!= null && i < orderArray.length; i++) {
                orderList.add(orderArray[i]);
            }
        } catch(HttpStatusCodeException e){
            HandleException.handleHttpStatusCodeException(e);
        } catch(RestClientException e){
            LOG.warn("server down? ", e.getMessage());
            throw new PersistenceLayerException("Keine valide Antwort vom Server. Ist der Server erreichbar?");
        }

        return orderList;

    }

    @Override
    public List<OrderDTO> getAllClosed() throws PersistenceLayerException {
        LOG.debug("get all closed order");
        List<OrderDTO> billList = new ArrayList<>();

        try {
            OrderDTO[] billArray = restTemplate.getForObject("http://"+RestTemplateConfiguration.host+":"+RestTemplateConfiguration.port+"/getAllClosedOrders", OrderDTO[].class);

            for (int i = 0; billArray!= null && i < billArray.length; i++) {
                billList.add(billArray[i]);
            }
        } catch(HttpStatusCodeException e){
            HandleException.handleHttpStatusCodeException(e);
        } catch(RestClientException e){
            LOG.warn("server down? ", e.getMessage());
            throw new PersistenceLayerException("Keine valide Antwort vom Server. Ist der Server erreichbar?");
        }

        return billList;
    }

    @Override
    public void invoiceOrder(OrderDTO orderDTO) throws PersistenceLayerException {
        LOG.debug("sending order that will be invoiced on server");

        try{
            //restTemplate.put("http://"+RestTemplateConfiguration.host+":"+ RestTemplateConfiguration.port+"/invoiceOrder", orderDTO, OrderDTO.class);
            restTemplate.exchange(
                    "http://"+RestTemplateConfiguration.host+":"+RestTemplateConfiguration.port+"/invoiceOrder",
                    HttpMethod.PUT, new HttpEntity<>(orderDTO), OrderDTO.class);
        } catch(HttpStatusCodeException e){
            HandleException.handleHttpStatusCodeException(e);
        } catch(RestClientException e){
            LOG.warn("server down? ", e.getMessage());
            throw new PersistenceLayerException("Keine valide Antwort vom Server. Ist der Server erreichbar?");
        }

    }
}
