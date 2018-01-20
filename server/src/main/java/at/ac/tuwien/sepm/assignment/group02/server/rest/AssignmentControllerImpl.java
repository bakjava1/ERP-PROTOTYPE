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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController //@Controller + @ResponseBody
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
    @RequestMapping(value="/setAssignmentDone", method = RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "mark assignment as done")
    @ResponseStatus(HttpStatus.OK)
    public void setAssignmentDone(@RequestBody AssignmentDTO assignmentDTO) throws ServiceLayerException {
        LOG.debug("called setAssignmentDone");
        assignmentService.setDone(assignmentDTO);
    }

    /**
     * rest interface for getting all open assignments
     * @return list of all open assignments for the assignment overview for crane
     * @throws ServiceLayerException if the database is not available or if the object wasn't found
     */
    @RequestMapping(value="/getAllOpenAssignments",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "get all open assignments")
    @ResponseStatus(HttpStatus.OK)
    public List<AssignmentDTO> getAllOpenAssignments() throws ServiceLayerException {
        LOG.trace("get all open assignments called in server assignment controller");
        return assignmentService.getAllOpenAssignments();
    }

    /**
     * rest interface for getting all closed assignments
     * @return list of all closed assignments for the assignment overview for crane
     * @throws ServiceLayerException if the database is not available or if the object wasn't found
     */
    @RequestMapping(value="/getAllClosedAssignments",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "get all assignments")
    @ResponseStatus(HttpStatus.OK)
    public List<AssignmentDTO> getAllClosedAssignments() throws ServiceLayerException {
        LOG.trace("called getAllAssignments");
        return assignmentService.getAllClosedAssignments();

    }

    @RequestMapping(value="/createAssignment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "create assignment")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAssignment(@RequestBody AssignmentDTO assignmentDTO) throws ServiceLayerException {
        LOG.debug("called createAssignment" + assignmentDTO.toString());
        assignmentService.addAssignment(assignmentDTO);
    }

    @RequestMapping(value="/cleanUpAssignments", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "mark assignment as done")
    @ResponseStatus(HttpStatus.OK)
    public void cleanUpAssignments() throws ServiceLayerException {
        LOG.debug("called cleanUpAssignments");
        assignmentService.cleanUpAssignments();
    }
}
