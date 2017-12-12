package at.ac.tuwien.sepm.assignment.group02.client.rest;


import at.ac.tuwien.sepm.assignment.group02.rest.exceptions.EntityCreationException;
import at.ac.tuwien.sepm.assignment.group02.rest.restController.SchnittholzController;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.FilterDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.SchnittholzDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * Created by raquelsima on 11.12.17.
 */

@RestController
public class SchnittholzControllerImpl implements SchnittholzController{

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private RestTemplate restTemplate;


    public SchnittholzControllerImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public void createSchnittholz(SchnittholzDTO schnittholzDTO)  {

    }

    @Override
    public void removeSchnittholz(SchnittholzDTO schnittholzDTO) {

    }


    @Override
    public void reserveSchnittholz(SchnittholzDTO schnittholzDTO) {

    }

    @Override
    public void updateSchnittholz(SchnittholzDTO schnittholzDTO) {

    }

    @Override
    public List<SchnittholzDTO> getAllSchnittholz(FilterDTO filterDTO) {
        return null;
    }

    @Override
    public SchnittholzDTO getSchnittholzByID(int schnittID){
        return null;
    }
}
