package at.ac.tuwien.sepm.assignment.group02.server.rest;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OptAlgorithmResultDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.OptimisationAlgorithmException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ResourceNotFoundException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.service.OptAlgorithmService;
import at.ac.tuwien.sepm.assignment.group02.server.service.OptAlgorithmServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.server.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;

@RestController
@Api(value= "Optimisation Algorithm Controller")
public class OptAlgorithmControllerImpl {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static OptAlgorithmService optAlgorithmService;

     /*TODO Autowire

    @Autowired
    public OptAlgorithmControllerImpl(OptAlgorithmService optAlgorithmService){
        OptAlgorithmControllerImpl.optAlgorithmService = optAlgorithmService;

    }
      */

    @CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" ) // TODO ADDED 11.06 is this okay? enabling cors
    @RequestMapping(value="/getOptAlgorithmResult",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "get optimisation algorithm result")
    public OptAlgorithmResultDTO getOptAlgorithmResult(@RequestBody TaskDTO taskDTO) throws ResourceNotFoundException, OptimisationAlgorithmException {
        optAlgorithmService = new OptAlgorithmServiceImpl();
        LOG.debug("getOptAlgorithmResult: {}", taskDTO.toString());


        OptAlgorithmResultDTO optAlgorithmResultDTO = null;
        try {
            optAlgorithmResultDTO = optAlgorithmService.getOptAlgorithmResult(taskDTO);

        } catch (ServiceLayerException e) {
            throw new ResourceNotFoundException("failed to get optimisation algorithm result."+ e.getMessage());
        } catch (PersistenceLayerException e) {
            throw new ResourceNotFoundException("failed to get optimisation algorithm result."+ e.getMessage());
        } catch (OptimisationAlgorithmException e) {
            throw new OptimisationAlgorithmException(e.getMessage());
        }

        return optAlgorithmResultDTO;
    }


}
