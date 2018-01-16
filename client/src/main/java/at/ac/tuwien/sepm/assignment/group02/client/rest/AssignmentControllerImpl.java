package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.client.configuration.RestTemplateConfiguration;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class AssignmentControllerImpl implements AssignmentController {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private RestTemplate restTemplate;

    @Autowired
    public AssignmentControllerImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void createAssignment(AssignmentDTO assignmentDTO) throws PersistenceLayerException {
        try {
            restTemplate.postForObject("http://"+RestTemplateConfiguration.host+":"+RestTemplateConfiguration.port+"/createAssignment", assignmentDTO, AssignmentDTO.class);
        } catch(HttpStatusCodeException e){
            LOG.warn("HttpStatusCodeException {}", e.getResponseBodyAsString());
            throw new PersistenceLayerException("HttpStatusCodeException");
        } catch(RestClientException e){
            LOG.warn("server down? ", e.getMessage());
            throw new PersistenceLayerException("Keine Antwort vom Server. Ist der Server erreichbar?");
        }
    }

    @Override
    public List<AssignmentDTO> getAllOpenAssignments() throws PersistenceLayerException {
        LOG.debug("get all open assignments called in client assignment controller");

        List<AssignmentDTO> assignmentList = new ArrayList<>();
        AssignmentDTO[] assignmentArray;

        try {
            assignmentArray = restTemplate.getForObject("http://"+RestTemplateConfiguration.host+":"+RestTemplateConfiguration.port+"/getAllOpenAssignments", AssignmentDTO[].class);
        } catch(HttpClientErrorException e){
            LOG.warn("HttpStatusCodeException {}", e.getResponseBodyAsString());
            throw new PersistenceLayerException("HttpStatusCodeException");
        } catch(RestClientException e){
            LOG.warn("server is down? - {}", e.getMessage());
            throw new PersistenceLayerException("Keine Antwort vom Server. Ist der Server erreichbar?");
        }

        if(assignmentArray != null) {
            assignmentList.addAll(Arrays.asList(assignmentArray));
        }

        return assignmentList;
    }

    @Override
    public List<AssignmentDTO> getAllAssignments() throws PersistenceLayerException {
        LOG.debug("get all open assignments called in client assignment controller");

        List<AssignmentDTO> assignmentList = new ArrayList<>();
        AssignmentDTO[] assignmentArray;

        try {
            assignmentArray = restTemplate.getForObject("http://"+RestTemplateConfiguration.host+":"+RestTemplateConfiguration.port+"/getAllAssignments", AssignmentDTO[].class);
        } catch(HttpClientErrorException e){
            LOG.warn("HttpStatusCodeException {}", e.getResponseBodyAsString());
            throw new PersistenceLayerException("HttpStatusCodeException");
        } catch(RestClientException e){
            LOG.warn("server is down? - {}", e.getMessage());
            throw new PersistenceLayerException("Keine Antwort vom Server. Ist der Server erreichbar?");
        }

        if(assignmentArray != null) {
            assignmentList.addAll(Arrays.asList(assignmentArray));
        }

        return assignmentList;
    }

    @Override
    public void setDone(@RequestBody AssignmentDTO assignmentDTO) throws PersistenceLayerException {
        try {
            restTemplate.put("http://"+RestTemplateConfiguration.host+":"+RestTemplateConfiguration.port+"/setAssignmentDone", assignmentDTO, AssignmentDTO.class);
        } catch(HttpStatusCodeException e){
            LOG.warn("HttpStatusCodeException {}", e.getResponseBodyAsString());
            throw new PersistenceLayerException("HttpStatusCodeException");
        } catch(RestClientException e){
            LOG.warn("server is down? - {}", e.getMessage());
            throw new PersistenceLayerException("Keine Antwort vom Server. Ist der Server erreichbar?");
        }
    }
}