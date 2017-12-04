package at.ac.tuwien.sepm.assignment.group02.server.rest;

import at.ac.tuwien.sepm.assignment.group02.rest.restController.LumberController;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.FilterDTO;
import at.ac.tuwien.sepm.assignment.group02.server.MainApplication;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.List;


@RestController
public class LumberControllerImpl implements LumberController {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * Hello World example
     * get lumber specified by id
     */
    @Override
    @RequestMapping(value="/getLumberById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LumberDTO getLumberById(@PathVariable(value = "id") int id) {

        LOG.debug("called helloWorldLumber2");

        return MainApplication.lumberService.getLumber(id);
    }



    @Override
    public List<LumberDTO> getAllLumber(FilterDTO filter) {
        return null;
    }

    @Override
    public void removeLumber(LumberDTO lumber) {

    }

    @Override
    public void reserveLumber(LumberDTO lumber) {

    }

    @Override
    public void createLumber(LumberDTO lumber) {

    }

}
