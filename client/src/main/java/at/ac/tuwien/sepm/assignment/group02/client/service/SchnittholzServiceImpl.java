package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.SchnittholzControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Filter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Schnittholz;
import at.ac.tuwien.sepm.assignment.group02.rest.restController.SchnittholzController;

import java.util.List;

/**
 * Created by raquelsima on 11.12.17.
 */
public class SchnittholzServiceImpl implements SchnittholzService{

    public static SchnittholzController schnittholzController= new SchnittholzControllerImpl();


    @Override
    public void createSchnittholz(Schnittholz schnittholz) throws InvalidInputException {

    }

    @Override
    public void removeSchnittholz(Schnittholz schnittholz) throws InvalidInputException {

    }

    @Override
    public void rserveSchnittholz(Schnittholz schnittholz, int quantity) throws InvalidInputException {

    }

    @Override
    public void updateSchnittholz(Schnittholz schnittholz) throws InvalidInputException {

    }

    @Override
    public List<Schnittholz> getAllSchnittholz(Filter filter) throws InvalidInputException {
        return null;
    }

    @Override
    public Schnittholz getSchnittholzByID(int schnittID) throws InvalidInputException {
        return null;
    }
}
