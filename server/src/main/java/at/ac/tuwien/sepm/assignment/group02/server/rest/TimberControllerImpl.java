package at.ac.tuwien.sepm.assignment.group02.server.rest;

import at.ac.tuwien.sepm.assignment.group02.rest.restController.TimberController;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;



import static at.ac.tuwien.sepm.assignment.group02.server.MainApplication.timberService;


@RestController
public class TimberControllerImpl implements TimberController{

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    @RequestMapping(value="/createTimber",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public void createTimber(@RequestBody TimberDTO timberDTO) {
        LOG.debug("Trying creation of Timber in box: " + timberDTO.getBox_id());

        timberService.addTimber(timberDTO);


    }

    @Override
    public void deleteTimber(TimberDTO timberDTO) {

    }

    @Override
    public TimberDTO getTimberById(int timber_id) {
        return null;
    }
}
