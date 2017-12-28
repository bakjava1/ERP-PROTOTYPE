package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Component
public class CostBenefitControllerImpl implements CostBenefitController {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private RestTemplate restTemplate;

    @Autowired
    public CostBenefitControllerImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public int costValueFunction(@RequestBody List<TaskDTO> taskList) throws PersistenceLayerException {
        int estimate;

        try {
            estimate = restTemplate.postForObject("http://localhost:8080/costBenefit", taskList, Integer.class);
        } catch(HttpStatusCodeException e){
            LOG.warn("HttpStatusCodeException {}", e.getResponseBodyAsString());
            throw new PersistenceLayerException("Connection Problem with Server");
        } catch(RestClientException e){
            //no response payload, probably server not running
            LOG.warn("server is down? - {}", e.getMessage());
            throw new PersistenceLayerException("Connection Problem with Server");
        }

        return estimate;
    }
}
