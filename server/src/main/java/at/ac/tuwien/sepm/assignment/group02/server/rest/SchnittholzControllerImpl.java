package at.ac.tuwien.sepm.assignment.group02.server.rest;


import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.FilterDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.SchnittholzDTO;
import at.ac.tuwien.sepm.assignment.group02.server.service.SchnittholzService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * Created by raquelsima on 11.12.17.
 */

@RestController

public class SchnittholzControllerImpl{

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

     static SchnittholzService schnittholzService;

    @Autowired
    public SchnittholzControllerImpl() {
        SchnittholzControllerImpl.schnittholzService=schnittholzService;
    }


    public void createSchnittholz(SchnittholzDTO schnittholzDTO){

    }

    public void removeSchnittholz(SchnittholzDTO schnittholzDTO){

    }

    public void rserveSchnittholz(SchnittholzDTO schnittholzDTO){

    }

    public void updateSchnittholz(SchnittholzDTO schnittholzDTO){

    }

    public List<SchnittholzDTO> getAllSchnittholz(FilterDTO filterDTO){
        return null;
    }

    public SchnittholzDTO getSchnittholzByID(int schnittID){
        return null;
    }
}
