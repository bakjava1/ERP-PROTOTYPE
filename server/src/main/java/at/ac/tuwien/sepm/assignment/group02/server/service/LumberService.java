package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;

import java.util.List;

/**
 * This interface defines all service layer methods needed for lumber handling.
 *
 * Conversion between DTO and entity happens on this layer.
 *
 * Service Layer gets LumberDTO objects from rest Layer
 * and forwards Lumber entity objects to persistence Layer.
 */
public interface LumberService {

    /**
     * update an existing lumber
     * @param lumberDTO
     * @throws ServiceLayerException
     */
    void updateLumber(LumberDTO lumberDTO) throws ServiceLayerException;

    /**
     * retrieve all existing lumbers
     * @param filter an object of Class LumberDTO with search paramaters for the requested list
     * @return a list of all lumber that matches the filter.
     * @throws ServiceLayerException if the database is not available for the persistence layer
     */
    List<LumberDTO> getAllLumber(LumberDTO filter) throws ServiceLayerException;

    /**
     * delete a lumber from lumber store
     * @param lumberDTO
     * @throws ServiceLayerException
     */
    void removeLumber(LumberDTO lumberDTO) throws ServiceLayerException;

    /**
     * mark a lumber as reserved
     * @param lumberDTO
     * @throws ServiceLayerException
     */
    void reserveLumber(LumberDTO lumberDTO) throws ServiceLayerException;

    /**
     * insert a new lumber in the lumber store
     * @param lumber
     * @throws ServiceLayerException
     */
    int addLumber(LumberDTO lumber) throws ServiceLayerException;

    /**
     * Hello World!
     * get lumber specified by id
     * @param id int id of lumber to get
     * @return LumberDTO specified by id
     */
    LumberDTO getLumberById(int id) throws ServiceLayerException;

}
