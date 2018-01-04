package at.ac.tuwien.sepm.assignment.group02.server.rest;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.server.entity.OptAlgorithmResult;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Task;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;

@RestController
@Api(value= "Optimisation Algorithm Controller")
public class OptAlgorithmControllerImpl {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    //TODO implement
    //private static OptAlgorithmService optAlgorithmService;

    @RequestMapping(value="/getOptAlgorithmResult", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "get optimisation algorithm result")
    public OptAlgorithmResult getOptAlgorithmResult(@RequestBody TaskDTO taskDTO) {
        LOG.debug("getOptAlgorithmResult: {}", taskDTO.toString());

        //TODO implement
        //return optAlgorithmService.getOptAlgorithmResult(taskDTO);
        return null;
    }
}
