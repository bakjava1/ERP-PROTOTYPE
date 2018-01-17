package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.client.configuration.RestTemplateConfiguration;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.util.HandleException;
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
    public void createTimber(@RequestBody TimberDTO timberDTO) throws PersistenceLayerException {
        LOG.debug("creating new Timber on Server");

        try{
            restTemplate.postForObject("http://"+RestTemplateConfiguration.host+":"+RestTemplateConfiguration.port+"/createTimber", timberDTO, TimberDTO.class);

        } catch(HttpStatusCodeException e){
            HandleException.handleHttpStatusCodeException(e);
        } catch(RestClientException e){
            LOG.warn("Unexpected server reaction. Is the server down? ", e.getMessage());
            throw new PersistenceLayerException("Keine valide Antwort vom Server. Ist der Server erreichbar?");
        }


    }

    @Override
    public void deleteTimber(TimberDTO timberDTO) {

    }

    @Override
    public TimberDTO getTimberById(@RequestParam(value="id", defaultValue="0") int id) throws PersistenceLayerException {
        LOG.debug("called getTimberById");
        TimberDTO timberDTO = null;

        try{
            timberDTO = restTemplate.getForObject(
                    "http://"+RestTemplateConfiguration.host+":"+ RestTemplateConfiguration.port+"/getTimberbyId/{id}",
                    TimberDTO.class, id);

        } catch(HttpStatusCodeException e){
            HandleException.handleHttpStatusCodeException(e);
        } catch(RestClientException e){
            LOG.warn("Unexpected server reaction. Is the server down? ", e.getMessage());
            throw new PersistenceLayerException("Keine valide Antwort vom Server. Ist der Server erreichbar?");
        }

        return timberDTO;
    }

    @Override
    public int getNumberOfBoxes() throws PersistenceLayerException {
        LOG.debug("called getNumberOfBoxes");
        int timber_int = -1;

        try {
            timber_int = restTemplate.getForObject("http://"+RestTemplateConfiguration.host+":"+RestTemplateConfiguration.port+"/getNumberOfBoxes", Integer.class);
        } catch(HttpStatusCodeException e){
            HandleException.handleHttpStatusCodeException(e);
        } catch(RestClientException e){
            LOG.warn("Unexpected server reaction. Is the server down? ", e.getMessage());
            throw new PersistenceLayerException("Keine valide Antwort vom Server. Ist der Server erreichbar?");
        }

        return timber_int;
    }
}
