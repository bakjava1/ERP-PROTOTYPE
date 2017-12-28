package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;

/**
 * TimberDTO Management / RoundTimber Management
 *
 * Service Layer for management of timber boxes on server
 */
public interface TimberService {


    /**
     * This method adds new round timber to the timber store. A record is updated on persitence
     * level
     * @author Philipp Klein
     * @param timberDTO Timber entity to create or update
     * @throws ServiceLayerException    if added amount is negative
     *                                  or could not add timber to the timber store
     */
    void addTimber(TimberDTO timberDTO) throws ServiceLayerException;

    /**
     * This method removes round timber from the round timber store.
     * @param timberDTO timberDTO to be updated/removed
     * @throws ServiceLayerException if timberDTO couldn't be updated/removed
     */
    void updateTimber(TimberDTO timberDTO) throws ServiceLayerException;

    /**
     * This method returns the number of boxes currently existing.
     * @return number of boxes
     * @throws ServiceLayerException if error getting number of boxes
     */
    int numberOfBoxes() throws ServiceLayerException;
}
