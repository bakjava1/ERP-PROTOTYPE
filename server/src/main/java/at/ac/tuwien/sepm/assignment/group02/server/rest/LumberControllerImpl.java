package at.ac.tuwien.sepm.assignment.group02.server.rest;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
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
    public LumberControllerImpl(LumberService lumberService) {
        LumberControllerImpl.lumberService = lumberService;
    }

    @RequestMapping(value="/updateLumber", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update Lumber")
    public void updateLumber(@RequestBody LumberDTO lumberDTO) throws ServiceLayerException {
        LOG.debug("Updating the Lumber  with id: " + lumberDTO.getId());
        lumberService.updateLumber(lumberDTO);
    }

    @RequestMapping(value="/deleteLumber", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Remove Lumber")
    public void removeLumber(@RequestBody LumberDTO lumberDTO) throws ServiceLayerException {
        LOG.debug("removing lumber " + lumberDTO.getId());
        lumberService.removeLumber(lumberDTO);
    }

    /**
     * get lumber by id
     * @param id the id of the lumber object
     * @return the lumber object specified by the id
     * @throws ResourceNotFoundException if no lumber object with given id was found
     */
    @RequestMapping(value="/getLumberById/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get Lumber By Id")
    public LumberDTO getLumberById(@PathVariable(value = "id") int id) throws ServiceLayerException {
        LOG.debug("called getLumberById");
        return lumberService.getLumberById(id);
    }


    @RequestMapping(value="/getAllLumber",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "get all lumber")
    public List<LumberDTO> getAllLumber(@RequestBody LumberDTO filter) throws ServiceLayerException {
        LOG.debug("Get all lumber");
        return lumberService.getAllLumber(filter);
    }


    @RequestMapping(value="/reserveLumber", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Reserve Lumber")
    public void reserveLumber(@RequestBody LumberDTO lumberDTO) throws ServiceLayerException {
        LOG.debug("called reserveLumber, {}", lumberDTO.toString());
        lumberService.reserveLumber(lumberDTO);
    }

    @RequestMapping(value="/createLumber", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create Lumber")
    public void createLumber(@RequestBody LumberDTO lumberDTO) throws ServiceLayerException {
        LOG.debug("called createLumber, {}", lumberDTO.toString());
        lumberService.addLumber(lumberDTO);
    }

}
