package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.client.configuration.RestTemplateConfiguration;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements interface from rest/restController
 */

@RestController
@RequestMapping("/lumbers")
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
    public void reserveLumber(LumberDTO lumberDTO) throws PersistenceLayerException {
        LOG.debug("Sending request for lumber reservation to server");

        try {
            restTemplate.put("http://"+RestTemplateConfiguration.host+":"+RestTemplateConfiguration.port+"/reserveLumber", lumberDTO, LumberDTO.class);
        } catch(HttpStatusCodeException e){
            LOG.warn("HttpStatusCodeException {}", e.getResponseBodyAsString());
            throw new PersistenceLayerException("Ressource nicht gefunden.");
        } catch(RestClientException e){
            //no response payload, probably server not running
            LOG.warn("server is down? - {}", e.getMessage());
            throw new PersistenceLayerException("Server nicht erreichbar.");
        }
    }

    @Override
    public List<LumberDTO> getAllLumber(@RequestBody LumberDTO filter) throws PersistenceLayerException {
        LOG.debug("get lumber");

        List<LumberDTO> lumberList = new ArrayList<>();

//        LumberDTO[] lumberArray = restTemplate.postForObject("http://"+RestTemplateConfiguration.host+":"+RestTemplateConfiguration.port+"/getAllLumber", filter, LumberDTO[].class);
        LumberDTO[] lumberArray;

        try {
            lumberArray = restTemplate.postForObject("http://"+RestTemplateConfiguration.host+":"+RestTemplateConfiguration.port+"/getAllLumber", filter, LumberDTO[].class);
        } catch(HttpStatusCodeException e){
            LOG.warn("HttpStatusCodeException {}", e.getResponseBodyAsString());
            throw new PersistenceLayerException("Connection Problem with Server");
        } catch(RestClientException e){
            //no response payload, probably server not running
            LOG.warn("server is down? - {}", e.getMessage());
            throw new PersistenceLayerException("Connection Problem with Server");
        }

            for (int i = 0; lumberArray!= null && i < lumberArray.length; i++) {
                lumberList.add(lumberArray[i]);
            }

        return lumberList;

    }

    @Override
    public void updateLumber(@RequestBody LumberDTO lumberDTO)  throws PersistenceLayerException {
        LOG.debug("Sending request for lumber updating to server");

        try {
            //restTemplate.postForObject("http://"+RestTemplateConfiguration.host+":"+RestTemplateConfiguration.port+"/updateLumber", lumberDTO, OrderDTO.class);
            restTemplate.put("http://"+RestTemplateConfiguration.host+":"+RestTemplateConfiguration.port+"/updateLumber", lumberDTO, OrderDTO.class);
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
    public void removeLumber(@RequestBody LumberDTO lumberDTO) throws PersistenceLayerException {

            LOG.debug("sending lumber to be deleted to server");

            try{
                //restTemplate.postForObject("http://"+RestTemplateConfiguration.host+":"+RestTemplateConfiguration.port+"/deleteLumber", lumberDTO, LumberDTO.class);
                //restTemplate.delete("http://"+RestTemplateConfiguration.host+":"+RestTemplateConfiguration.port+"/deleteLumber", lumberDTO, LumberDTO.class);
                restTemplate.put("http://"+RestTemplateConfiguration.host+":"+RestTemplateConfiguration.port+"/deleteLumber", lumberDTO, LumberDTO.class);

            } catch(HttpStatusCodeException e){
                LOG.warn("HttpStatusCodeException {}", e.getResponseBodyAsString());
                throw new PersistenceLayerException("Connection Problem with Server");
            } catch(RestClientException e){
                //no response payload, probably server not running
                LOG.warn("server is down? - {}", e.getMessage());
                throw new PersistenceLayerException("Connection Problem with Server");
            }
        }


    /**
     *
     * @param id int id of lumber to get
     * @return
     * @throws PersistenceLayerException
     */
    @Override
    public LumberDTO getLumberById(@RequestParam(value="id", defaultValue="0") int id) throws PersistenceLayerException {
        LOG.debug("called getLumber");

        LumberDTO lumberDTO;

        try {
            lumberDTO = restTemplate.getForObject(
                    "http://"+RestTemplateConfiguration.host+":"+ RestTemplateConfiguration.port+"/getLumberById/{id}",
                    LumberDTO.class, id);
        } catch(HttpStatusCodeException e){
            LOG.warn("HttpStatusCodeException {}", e.getResponseBodyAsString());
            throw new PersistenceLayerException("Connection Problem with Server");
        } catch(RestClientException e){
            //no response payload, probably server not running
            LOG.warn("server is down? - {}", e.getMessage());
            throw new PersistenceLayerException("Connection Problem with Server");
        }

        return lumberDTO;
    }
}