package at.ac.tuwien.sepm.assignment.group02.server.rest;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ResourceNotFoundException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.service.AssignmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@Api(value="Assignment Controller")
public class AssignmentControllerImpl {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private AssignmentService assignmentService;

    @Autowired
    public AssignmentControllerImpl(AssignmentService assignmentService){
        this.assignmentService = assignmentService;
    }

    /**
     * method marks a given assignment as done
     * @param assignmentDTO the assignment to be marked as done
     * @throws ResourceNotFoundException thrown if the assignment couldn't be updated. (HttpStatusCode 404)
     */
    @RequestMapping(value="/setAssignmentDone", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "mark assignment as done")
    public void setAssignmentDone(@RequestBody AssignmentDTO assignmentDTO) throws ResourceNotFoundException {
        LOG.debug("called setAssignmentDone");
        try {
            assignmentService.setDone(assignmentDTO);
        } catch (ServiceLayerException e) {
            LOG.error("Error in Service Layer of Server: " + e.getMessage());
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    /**
     * rest interface for getting all open assignments
     * @return list of all open assignments for the assignment overview for crane
     * @throws ResourceNotFoundException if the database is not available for the persistence layer
     */
    @RequestMapping(value="/getAllOpenAssignments",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "get all open assignments")
    public List<AssignmentDTO> getAllOpenAssignments() throws ResourceNotFoundException {
        LOG.debug("get all open assignments called in server assignment controller");

        try {
            return assignmentService.getAllOpenAssignments();
        } catch (ServiceLayerException e) {
            LOG.warn("error while getting all open assignments in server service layer", e.getMessage());
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    /**
     * rest interface for getting all open assignments
     * @return list of all open assignments for the assignment overview for crane
     * @throws ResourceNotFoundException if the database is not available for the persistence layer
     */
    @RequestMapping(value="/getAllAssignments",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "get all assignments")
    public List<AssignmentDTO> getAllAssignments() throws ResourceNotFoundException {
        LOG.debug("called getAllAssignments");

        try {
            return assignmentService.getAllAssignments();
        } catch (ServiceLayerException e) {
            LOG.warn("error while getting all assignments in server service layer", e.getMessage());
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @RequestMapping(value="/createAssignment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "create assignment")
    public void createAssignment(@RequestBody AssignmentDTO assignmentDTO) throws ResourceNotFoundException {
        LOG.debug("called createAssignment" + assignmentDTO.toString());

        try {
            assignmentService.addAssignment(assignmentDTO);
        } catch(ServiceLayerException e) {
            LOG.error(e.getMessage());
            throw new ResourceNotFoundException("Failed Creation");
        }
    }
}
