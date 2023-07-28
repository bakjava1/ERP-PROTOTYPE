package at.ac.tuwien.sepm.assignment.group02.server.rest;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.service.TimberService;
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


@RestController
@Api(value="(Round) Timber Controller")
public class TimberControllerImpl {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static TimberService timberService;

    @Autowired
    public TimberControllerImpl(TimberService timberService){
        TimberControllerImpl.timberService = timberService;
    }


    @RequestMapping(value="/createTimber", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "create timber")
    public void createTimber(@RequestBody TimberDTO timberDTO) throws ServiceLayerException {
        LOG.debug("Trying creation of Timber in box: " + timberDTO.getBox_id());
        timberService.addTimber(timberDTO);
    }

    /*
    @RequestMapping(value="/deleteTimber", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "delete timber")
    public void deleteTimber(TimberDTO timberDTO) throws ResourceNotFoundException {
        LOG.debug("deleting timber " + timberDTO.toString());
        try {
            timberService.removeTimber(timberDTO);
        } catch (ServiceLayerException e) {
            LOG.error(e.getMessage());
            throw new ResourceNotFoundException("Failed to delete timber.");
        }
    }
    */

    /*
    @RequestMapping(value="/getTimberById/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get Timber By Id")
    public TimberDTO getTimberById(int timber_id) throws ResourceNotFoundException {
        LOG.debug("called getOrderById");
        try {
            return timberService.getTimberById(timber_id);
        } catch (ServiceLayerException e) {
            LOG.error(e.getMessage());
            throw new ResourceNotFoundException("failed to get order by id.");
        }
    }
    */

    @RequestMapping(value="/getNumberOfBoxes",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "get number of boxes")
    public int getNumberOfBoxes() throws ServiceLayerException {
        LOG.debug("Trying return number of boxes");
        return timberService.numberOfBoxes();
    }
}
