package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.FilterDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import javafx.fxml.FXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * Implements interface from rest/restController
 */

@RestController
public class LumberControllerImpl implements LumberController {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private RestTemplate restTemplate;

    public LumberControllerImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<LumberDTO> getAllLumber(FilterDTO filter) {

        return null;
    }

    @Override
    public void removeLumber(@RequestBody LumberDTO lumberDTO) {

            LOG.debug("sending lumber to be deleted to server");

            try{
               // restTemplate.postForObject("http://localhost:8080/deleteLumber", lumberDTO, LumberDTO.class);
                restTemplate.delete("http://localhost:8080/deleteLumber", lumberDTO, LumberDTO.class);

            } catch(HttpStatusCodeException e){
                LOG.warn("HttpStatusCodeException {}", e.getResponseBodyAsString());
            } catch(RestClientException e){
                //no response payload, probably server not running
                LOG.warn("server is down? - {}", e.getMessage());
            }
        }



    @Override
    public void reserveLumber(LumberDTO lumber) {

    }

    @Override
    public void updateLumber(LumberDTO lumber) throws PersistenceLayerException {


    }

    @Override
    public void createLumber(LumberDTO lumber) {

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