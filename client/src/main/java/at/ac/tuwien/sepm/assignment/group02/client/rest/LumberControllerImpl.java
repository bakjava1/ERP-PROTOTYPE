package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements interface from rest/restController
 */

@Controller
public class LumberControllerImpl implements LumberController {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private RestTemplate restTemplate;

    @Autowired
    public LumberControllerImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public void createLumber(LumberDTO lumberDTO) {

    }

    @Override
    public void reserveLumber(LumberDTO lumberDTO) {

    }

    @Override
    public List<LumberDTO> getAllLumber(@RequestBody LumberDTO filter) {
        LOG.debug("get lumber");

        List<LumberDTO> lumberList = new ArrayList<>();

        try{
            LumberDTO[] lumberArray = restTemplate.postForObject("http://localhost:8080/getAllLumber", filter, LumberDTO[].class);

            for (int i = 0; lumberArray!= null && i < lumberArray.length; i++) {
                lumberList.add(lumberArray[i]);
            }
        } catch(HttpStatusCodeException e){
            LOG.warn("HttpStatusCodeException {}", e.getResponseBodyAsString());
        } catch(RestClientException e){
            //no response payload, probably server not running
            LOG.warn("server is down? - {}", e.getMessage());
        }


        return lumberList;

    }

    @Override
    public void updateLumber(@RequestBody LumberDTO lumberDTO)  throws PersistenceLayerException {
        LOG.debug("Sending request for lumber updating to server");

        try {
            restTemplate.postForObject("http://localhost:8080/updateLumber", lumberDTO, OrderDTO.class);
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
    public void removeLumber(@RequestBody LumberDTO lumberDTO) {

            LOG.debug("sending lumber to be deleted to server");

            try{
                restTemplate.postForObject("http://localhost:8080/deleteLumber", lumberDTO, LumberDTO.class);
                //restTemplate.delete("http://localhost:8080/deleteLumber", lumberDTO, LumberDTO.class);

            } catch(HttpStatusCodeException e){
                LOG.warn("HttpStatusCodeException {}", e.getResponseBodyAsString());
            } catch(RestClientException e){
                //no response payload, probably server not running
                LOG.warn("server is down? - {}", e.getMessage());
            }
        }


    /**
     * HELLO WORLD example
     */
    @Override
    public LumberDTO getLumberById(@RequestParam(value="id", defaultValue="0") int id) {
        LOG.debug("called getLumber");

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(
                "http://localhost:8080/getLumberById/{id}",
                LumberDTO.class, id);
    }
}