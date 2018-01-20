package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.client.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.FilterDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;

import java.util.ArrayList;
import java.util.List;

public interface LumberController {

    /**
     * show an overview of all availables lumbers
     * @return list of searched lumber
     * @param filterDTO a FilterDTO object with the parameter to be searched
     * @throws PersistenceLayerException if the server is not available
     * @inv filter is validated
     */
    List<LumberDTO> getAllLumber(FilterDTO filterDTO) throws PersistenceLayerException;

    /**
     * remove lumber from the stock
     * @param lumberDTO lumber to remove
     * @throws PersistenceLayerException
     * @inv lumberDTO is validated
     */
    void removeLumber(LumberDTO lumberDTO) throws PersistenceLayerException;

    /**
     * marking a select lumber as reserved.
     * @param lumberDTO lumber to reserve
     * @throws PersistenceLayerException
     * @inv lumberDTO is validated
     */
    void reserveLumber(LumberDTO lumberDTO) throws PersistenceLayerException;

    /**
     * reserve additional lumber
     * @param lumberDTO lumber to reserve
     * @throws PersistenceLayerException
     * @inv lumberDTO is validated
     */
    void reserveLumberAlg(LumberDTO lumberDTO);

    /**
     * update a lumber and marking it  as reserved
     * @param lumberDTO
     * @throws PersistenceLayerException
     * @inv lumberDTO is validated
     */
    void updateLumber(LumberDTO lumberDTO) throws PersistenceLayerException, ServiceLayerException;

    /**
     * create a new lumber.
     * @param lumberDTO
     * @throws PersistenceLayerException
     * @inv lumberDTO is validated
     */
    void createLumber(LumberDTO lumberDTO) throws PersistenceLayerException;

    /**
     * get lumber specified by id
     * @param id int id of lumber to get
     * @return LumberDTO specified by id
     * @throws PersistenceLayerException
     */
    LumberDTO getLumberById(int id) throws PersistenceLayerException;

}
