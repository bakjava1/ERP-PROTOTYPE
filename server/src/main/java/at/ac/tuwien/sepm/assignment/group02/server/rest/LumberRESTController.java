package at.ac.tuwien.sepm.assignment.group02.server.rest;

import at.ac.tuwien.sepm.assignment.group02.server.MainApplication;
import at.ac.tuwien.sepm.assignment.group02.server.dao.Lumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;


@RestController
public class LumberRESTController{

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @RequestMapping(value="/getLumberById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Lumber getLumberById(@PathVariable(value = "id") Integer id) {

        LOG.debug("called helloWorldLumber2");

        return MainApplication.lumberService.getLumber(id);
    }

}
