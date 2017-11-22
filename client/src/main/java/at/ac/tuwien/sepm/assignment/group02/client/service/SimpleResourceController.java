package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.dao.Lumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;

@RestController
public class SimpleResourceController implements ResourceController {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public SimpleResourceController(){}

    @Override
    public Lumber getLumber(@RequestParam(value="id", defaultValue="0") int id) {
        LOG.debug("called getLumber");

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(
                "http://localhost:8080/lumber",
                Lumber.class, 200);
    }
}