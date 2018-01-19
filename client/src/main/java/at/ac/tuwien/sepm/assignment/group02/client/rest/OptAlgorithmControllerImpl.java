package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.client.configuration.RestTemplateConfiguration;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.util.HandleException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OptAlgorithmResultDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;

@RestController
public class OptAlgorithmControllerImpl implements OptAlgorithmController {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private RestTemplate restTemplate;

    @Autowired
    public OptAlgorithmControllerImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public OptAlgorithmResultDTO getOptAlgorithmResult(@RequestBody TaskDTO taskDTO) throws PersistenceLayerException {
        LOG.debug("sending selected task to the server to execute the optimisation algorithm");

        OptAlgorithmResultDTO bestResult = null;


        try {
            //return restTemplate.getForObject("http://"+RestTemplateConfiguration.host+":"+RestTemplateConfiguration.port+"/getOptAlgorithmResult", OptAlgorithmResultDTO.class);
            bestResult =  restTemplate.postForObject("http://"+RestTemplateConfiguration.host+":"+ RestTemplateConfiguration.port+"/getOptAlgorithmResult", taskDTO, OptAlgorithmResultDTO.class);

        } catch(HttpStatusCodeException e){
            HandleException.handleHttpStatusCodeException(e);
        } catch(RestClientException e){
            LOG.warn("server down? ", e.getMessage());
            throw new PersistenceLayerException("Keine valide Antwort vom Server. Ist der Server erreichbar?");
        }



        return bestResult;
    }




}
