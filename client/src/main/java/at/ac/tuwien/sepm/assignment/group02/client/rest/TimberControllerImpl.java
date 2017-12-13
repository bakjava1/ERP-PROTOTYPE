package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;

@RestController
public class TimberControllerImpl implements TimberController {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private RestTemplate restTemplate;

    @Autowired
    public TimberControllerImpl(RestTemplate restTemplate){

        this.restTemplate = restTemplate;
    }

    @Override
    public void createTimber(@RequestBody TimberDTO timberDTO) {
        LOG.debug("creating new Timber on Server");

        try{
            restTemplate.postForObject("http://localhost:8080/createTimber", timberDTO, TimberDTO.class);

        } catch(HttpStatusCodeException e){
            LOG.warn("HttpStatusCodeException {}", e.getResponseBodyAsString());
        } catch(RestClientException e){
            //no response payload, probably server not running
            LOG.warn("server is down? - {}", e.getMessage());
        }


    }

    @Override
    public void deleteTimber(TimberDTO timberDTO) {

    }

    @Override
    public TimberDTO getTimberById(@RequestParam(value="id", defaultValue="0") int id) {
        LOG.debug("called getTimberById");


        return restTemplate.getForObject(
                "http://localhost:8080/getTimberbyId/{id}",
                TimberDTO.class, id);
    }

    @Override
    public int getNumberOfBoxes() throws PersistenceLayerException {
        LOG.debug("called getNumberOfBoxes");

        return restTemplate.getForObject("http://localhost:8080/getNumberOfBoxes", Integer.class);
    }
}
