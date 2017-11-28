package at.ac.tuwien.sepm.assignment.group02.server.rest;

import at.ac.tuwien.sepm.assignment.group02.server.dao.Lumber;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.LumberManagementDAO;
import at.ac.tuwien.sepm.assignment.group02.server.service.LumberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;


@RestController
public class LumberRESTController{

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @RequestMapping(value="/getLumberById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Lumber helloWorldLumber2(@PathVariable(value = "id") Integer id) {
        LOG.debug("called helloWorldLumber2");
        return new Lumber(id,"hallohallo");
    }


    @RequestMapping("/lumber")
    public Lumber helloWorldLumber(@RequestParam(value="id", defaultValue="0") int id) {
        LOG.debug("called helloWorldLumber");
        return new Lumber(id, "hallo");
    }
}
