package at.ac.tuwien.sepm.assignment.group02.server.rest;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.EntityCreationException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ResourceNotFoundException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.service.LumberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;


@RestController
@Api(value="Lumber Controller")
public class LumberControllerImpl {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static LumberService lumberService;

    @Autowired
    public LumberControllerImpl(LumberService lumberService) throws EntityCreationException{

        LumberControllerImpl.lumberService = lumberService;
    }

    @RequestMapping(value="/updateLumber", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update Lumber")
    public void updateLumber(@RequestBody LumberDTO lumberDTO) throws ResourceNotFoundException {
        LOG.debug("Updating the Lumber  with id: " + lumberDTO.getID());

        try {
            lumberService.updateLumber(lumberDTO);
        }catch (ServiceLayerException e){
            LOG.error("Database Error:"+e.getMessage());
            throw new ResourceNotFoundException("Update failed");
        }
    }

    @RequestMapping(value="/deleteLumber", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Remove Lumber")
    public void removeLumber(@RequestBody LumberDTO lumberDTO) throws ResourceNotFoundException {
        LOG.debug("removing lumber " + lumberDTO.getID());
        try {
            lumberService.removeLumber(lumberDTO);
        } catch (ServiceLayerException e) {
            throw new ResourceNotFoundException("Failed to reduce/remove lumber.");
        }
    }

    /**
     * get lumber by id
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value="/getLumberById/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get Lumber By Id")
    public LumberDTO getLumberById(@PathVariable(value = "id") int id) throws ResourceNotFoundException {
        LOG.debug("called getLumberById");

        try {
            return lumberService.getLumberById(id);
        } catch (ServiceLayerException e) {
            throw new ResourceNotFoundException("failed to get lumber.");
        }
    }


    @RequestMapping(value="/getAllLumber",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "get all lumber")
    public List<LumberDTO> getAllLumber(@RequestBody LumberDTO filter) throws ResourceNotFoundException {
        LOG.debug("Get all lumber");
        try {
            return lumberService.getAllLumber(filter);
        } catch (ServiceLayerException e) {
            throw new ResourceNotFoundException("failed to get all lumbers.");
        }

    }

    /*
    @RequestMapping(value="/reserveLumber", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Reserve Lumber")
    public void reserveLumber(@RequestBody LumberDTO lumberDTO) throws ResourceNotFoundException {
        LOG.debug("called reserveLumber, {}", lumberDTO.toString());
        try {
            lumberService.reserveLumber(lumberDTO);
        } catch (ServiceLayerException e) {
            throw new ResourceNotFoundException();
        }
    }*/

    @RequestMapping(value="/createLumber", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create Lumber")
    public void createLumber(@RequestBody LumberDTO lumberDTO) throws ResourceNotFoundException {
        LOG.debug("called createLumber, {}", lumberDTO.toString());
        try {
            lumberService.addLumber(lumberDTO);
        } catch (ServiceLayerException e) {
            throw new ResourceNotFoundException();
        }
    }

}
