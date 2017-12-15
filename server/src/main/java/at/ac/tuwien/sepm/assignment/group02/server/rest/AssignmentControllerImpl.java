package at.ac.tuwien.sepm.assignment.group02.server.rest;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.service.AssignmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
public class AssignmentControllerImpl {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static AssignmentService assignmentService;

    @Autowired
    public AssignmentControllerImpl(AssignmentService assignmentService) {
        AssignmentControllerImpl.assignmentService = assignmentService;
    }

    @RequestMapping(value="/getAllOpenAssignments",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AssignmentDTO> getAllOpenAssignments() {
        LOG.debug("get all open assignments called in server assignment controller");

        try {
            return assignmentService.getAllOpenAssignments();
        } catch (ServiceLayerException e) {
            LOG.warn("error while getting all open assignments in server service layer", e.getMessage());
        }

        return null;
    }
}
