package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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

    }

    @Override
    public List<AssignmentDTO> getAllOpenAssignments() throws PersistenceLayerException {
        LOG.debug("get all open assignments called in client assignment controller");

        List<AssignmentDTO> assignmentList = new ArrayList<>();
        AssignmentDTO[] assignmentArray = restTemplate.getForObject("http://localhost:8080/getAllOpenAssignments", AssignmentDTO[].class);

        if(assignmentArray != null) {
            assignmentList.addAll(Arrays.asList(assignmentArray));
        }

        return assignmentList;
    }

    @Override
    public void setDone(AssignmentDTO assignmentDTO) throws PersistenceLayerException {

    }
}
