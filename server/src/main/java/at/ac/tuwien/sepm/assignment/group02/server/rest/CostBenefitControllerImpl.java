package at.ac.tuwien.sepm.assignment.group02.server.rest;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ResourceNotFoundException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.service.CostBenefitService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
public class CostBenefitControllerImpl {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private CostBenefitService costBenefitService;

    @Autowired
    public CostBenefitControllerImpl(CostBenefitService costBenefitService){
        this.costBenefitService = costBenefitService;
    }

    @RequestMapping(value="/costBenefit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "costBenefitEvaluation")
    public int costBenefit(@RequestBody List<TaskDTO> taskList) throws ResourceNotFoundException {
        LOG.debug("called costBenefit");

        try {
            return costBenefitService.costValueFunction(taskList);
        } catch (ServiceLayerException e) {
            throw new ResourceNotFoundException("Failed to execute Cost Benefit Service.");
        }
    }
}
