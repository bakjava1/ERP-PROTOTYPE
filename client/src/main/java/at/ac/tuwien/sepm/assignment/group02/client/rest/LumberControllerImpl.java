package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.FilterDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements interface from rest/restController
 */

@RestController
public class LumberControllerImpl implements LumberController {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private RestTemplate restTemplate;

    @Autowired
    public LumberControllerImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }


    @Override
    public List<LumberDTO> getAllLumber(@RequestBody LumberDTO filter) {
        LOG.debug("get lumber");

        List<LumberDTO> lumberList = new ArrayList<>();
        LumberDTO[] lumberArray = restTemplate.postForObject("http://localhost:8080/getAllLumber", filter, LumberDTO[].class);


        for (int i = 0; lumberArray!= null && i < lumberArray.length; i++) {
            lumberList.add(lumberArray[i]);
        }


        return lumberList;

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

    /**
     * HELLO WORLD example
     */
    @Override
    public LumberDTO getLumberById(@RequestParam(value="id", defaultValue="0") int id) {
        LOG.debug("called getLumber");

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(
                "http://localhost:8080/getLumberById/{id}",
                LumberDTO.class, id);
    }
}