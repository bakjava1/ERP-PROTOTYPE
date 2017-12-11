package at.ac.tuwien.sepm.assignment.group02.server.rest;

import at.ac.tuwien.sepm.assignment.group02.rest.exceptions.EntityCreationException;
import at.ac.tuwien.sepm.assignment.group02.rest.restController.SchnittholzController;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.FilterDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.SchnittholzDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * Created by raquelsima on 11.12.17.
 */
public class SchnittholzControllerImpl implements SchnittholzController{

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void createSchnittholz(SchnittholzDTO schnittholzDTO) throws EntityCreationException {

    }

    @Override
    public void removeSchnittholz(SchnittholzDTO schnittholzDTO) throws EntityCreationException {

    }

    @Override
    public void rserveSchnittholz(SchnittholzDTO schnittholzDTO) throws EntityCreationException {

    }

    @Override
    public void updateSchnittholz(SchnittholzDTO schnittholzDTO) throws EntityCreationException {

    }

    @Override
    public List<SchnittholzDTO> getAllSchnittholz(FilterDTO filterDTO) throws EntityCreationException {
        return null;
    }

    @Override
    public SchnittholzDTO getSchnittholzByID(int schnittID) throws EntityCreationException {
        return null;
    }
}
