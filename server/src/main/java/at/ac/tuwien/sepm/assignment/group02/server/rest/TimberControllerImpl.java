package at.ac.tuwien.sepm.assignment.group02.server.rest;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.EntityCreationException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.EntityNotFoundException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.service.TimberService;
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
public class TimberControllerImpl {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static TimberService timberService;

    @Autowired
    public TimberControllerImpl(TimberService timberService) throws EntityCreationException {

        TimberControllerImpl.timberService = timberService;
    }

    @RequestMapping(value="/createTimber",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public void createTimber(@RequestBody TimberDTO timberDTO) throws EntityCreationException {
        LOG.debug("Trying creation of Timber in box: " + timberDTO.getBox_id());

        try {
            timberService.addTimber(timberDTO);
        } catch (ServiceLayerException e) {
            throw new EntityCreationException("failed to add timber.");
        }


    }

    public void deleteTimber(TimberDTO timberDTO) {

    }



    public TimberDTO getTimberById(int timber_id) {
        return null;
    }

    @RequestMapping(value="/getNumberOfBoxes",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public int getNumberOfBoxes() throws EntityNotFoundException{
        LOG.debug("Trying return number of boxes");

        try {
            return timberService.numberOfBoxes();
        } catch (ServiceLayerException e) {
            throw new EntityNotFoundException("failed to return number of boxes");
        }
    }
}
