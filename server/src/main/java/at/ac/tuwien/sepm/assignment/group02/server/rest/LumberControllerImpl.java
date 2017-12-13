package at.ac.tuwien.sepm.assignment.group02.server.rest;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
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
import java.sql.SQLException;
import java.util.List;


@RestController
public class LumberControllerImpl {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static LumberService lumberService;

    @Autowired
    public LumberControllerImpl(LumberService lumberService){

        LumberControllerImpl.lumberService = lumberService;
    }

    @RequestMapping(value="/updateLumber",method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateLumber(@RequestBody LumberDTO lumberDTO) throws EntityCreationException, SQLException {
        LOG.debug("Updating the Lumber  with id: " + lumberDTO.getID());

        try {
            lumberService.updateLumber(lumberDTO);
        }catch (ServiceLayerException e){
            LOG.error("Database Error:"+e.getMessage());
            throw  new EntityCreationException("Update failed");
        }
    }

    @RequestMapping(value="/deleteLumber",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
    public void removeLumber(@RequestBody LumberDTO lumberDTO) throws EntityNotFoundException, SQLException {
        LOG.debug("Deleting lumber " + lumberDTO.getID());
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


    @RequestMapping(value="/getAllLumber",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LumberDTO> getAllLumber(@RequestBody LumberDTO filter) throws EntityNotFoundException {
        LOG.debug("Get all lumber");
        try {
            return lumberService.getAllLumber(filter);
        } catch (ServiceLayerException e) {
            throw new EntityNotFoundException("failed to get all lumbers.");
        }

    }

    public void reserveLumber(LumberDTO lumber) {

    }

    public void createLumber(LumberDTO lumber) {

    }

}
