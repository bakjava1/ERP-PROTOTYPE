package at.ac.tuwien.sepm.assignment.group02.server.rest;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.FilterDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.EntityNotFoundException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.service.LumberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;


@RestController
public class LumberControllerImpl {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static LumberService lumberService;

    @Autowired
    public LumberControllerImpl(LumberService lumberService){
        LumberControllerImpl.lumberService = lumberService;
    }


    /**
     * Hello World example
     * get lumber specified by id
     */
    @RequestMapping(value="/getLumberById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LumberDTO getLumberById(@PathVariable(value = "id") int id) throws EntityNotFoundException {
        LOG.debug("called getLumberById");

        try {
            return lumberService.getLumberById(id);
        } catch (ServiceLayerException e) {
            throw new EntityNotFoundException("failed to get lumber.");
        }
    }



    @RequestMapping(value="/getAllLumber",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LumberDTO> getAllLumber(@RequestBody LumberDTO filter) throws EntityNotFoundException {
        LOG.debug("Get all lumber");
        try {
            return lumberService.getAllLumber(filter);
        } catch (ServiceLayerException e) {
            throw new EntityNotFoundException("failed to get all lumbers.");
        }

    }


    public void removeLumber(LumberDTO lumber) {

    }

    public void reserveLumber(LumberDTO lumber) {

    }

    public void createLumber(LumberDTO lumber) {

    }

}
