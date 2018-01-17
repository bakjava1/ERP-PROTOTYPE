package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.util.HandleException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;
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
    public double costValueFunction(@RequestBody List<TaskDTO> taskList) throws PersistenceLayerException {
        double estimate = 0;

        try {
            estimate = restTemplate.postForObject("http://localhost:8080/costBenefit", taskList, Double.class);
        } catch(HttpClientErrorException e){
            HandleException.handleHttpStatusCodeException(e);
        } catch(RestClientException e){
            LOG.warn("RestClientException. Is the server up and running?", e.getMessage());
            throw new PersistenceLayerException("Keine Antwort vom Server. Ist der Server erreichbar?");
        }

        return estimate;
    }
}
