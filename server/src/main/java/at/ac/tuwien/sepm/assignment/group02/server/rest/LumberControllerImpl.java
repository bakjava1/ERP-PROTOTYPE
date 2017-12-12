package at.ac.tuwien.sepm.assignment.group02.server.rest;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.FilterDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.EntityCreationException;
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


    public void createLumber(LumberDTO lumber) {

    }

    public void reserveLumber(LumberDTO lumber) {

    }

    public List<LumberDTO> getAllLumber(FilterDTO filter) {
        return null;
    }


    @RequestMapping(value="/updateLumber",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateLumber(@RequestBody LumberDTO lumberDTO) throws EntityCreationException{
        LOG.debug("Updating the Lumber  with id: " + lumberDTO.getId());

        try {
            lumberService.updateLumber(lumberDTO);
        }catch (ServiceLayerException e){
            LOG.error("Database Error:"+e.getMessage());
            throw  new EntityCreationException("Update failed");
        }
    }

    @RequestMapping(value="/deleteLumber",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public void removeLumber(@RequestBody LumberDTO lumberDTO) throws EntityNotFoundException {
        LOG.debug("Deleting lumber " + lumberDTO.getId());
        try {
            lumberService.removeLumber(lumberDTO);
        } catch (ServiceLayerException e) {
            throw new EntityNotFoundException("Failed to delete lumber.");
        }
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
}
