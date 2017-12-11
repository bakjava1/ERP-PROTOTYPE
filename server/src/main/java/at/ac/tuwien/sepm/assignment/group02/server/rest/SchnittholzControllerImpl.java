package at.ac.tuwien.sepm.assignment.group02.server.rest;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.FilterDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.SchnittholzDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.EntityCreationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * Created by raquelsima on 11.12.17.
 */
public class SchnittholzControllerImpl{

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    public void createSchnittholz(SchnittholzDTO schnittholzDTO) throws EntityCreationException {

    }

    public void removeSchnittholz(SchnittholzDTO schnittholzDTO) throws EntityCreationException {

    }

    public void rserveSchnittholz(SchnittholzDTO schnittholzDTO) throws EntityCreationException {

    }

    public void updateSchnittholz(SchnittholzDTO schnittholzDTO) throws EntityCreationException {

    }

    public List<SchnittholzDTO> getAllSchnittholz(FilterDTO filterDTO) throws EntityCreationException {
        return null;
    }

    public SchnittholzDTO getSchnittholzByID(int schnittID) throws EntityCreationException {
        return null;
    }
}
