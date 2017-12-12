package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.FilterDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.SchnittholzDTO;

import java.util.List;

/**
 * Created by raquelsima on 11.12.17.
 */


public interface SchnittholzController {


    /**
     * 3.2.4 Schnittholz ins Lager hinzufügen.
     * @param schnittholzDTO
     * @throws PersistenceLayerException
     */
 public void createSchnittholz(SchnittholzDTO schnittholzDTO) throws PersistenceLayerException;

    /**
     *
     * @param schnittholzDTO
     * @throws PersistenceLayerException
     */
 public void removeSchnittholz(SchnittholzDTO schnittholzDTO) throws PersistenceLayerException;

    /**
     *
     * @param schnittholzDTO
     * @throws PersistenceLayerException
     */
 public void reserveSchnittholz(SchnittholzDTO schnittholzDTO) throws PersistenceLayerException;

    /**
     *
     * @param schnittholzDTO
     * @throws PersistenceLayerException
     */
 public void updateSchnittholz(SchnittholzDTO schnittholzDTO) throws PersistenceLayerException;

    /**
     * retrieve all of schnittholzer
     * @param filterDTO
     * @return a list of schnittholzer
     * @throws PersistenceLayerException
     */
 public List<SchnittholzDTO> getAllSchnittholz(FilterDTO filterDTO) throws PersistenceLayerException;

    /**
     * get schnittholz specified by id
     * @param schnittID
     * @return SchnittholzDTO specified by schnittID
     * @throws PersistenceLayerException
     */
 public SchnittholzDTO getSchnittholzByID(int schnittID) throws PersistenceLayerException;


}
