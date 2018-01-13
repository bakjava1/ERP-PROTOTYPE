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
     * @param box_id int defining the timber to be updated
     * @param amount_to_remove int number of round timber to remove
     * @throws ServiceLayerException if timber couldn't be updated/removed
     */
    void removeTimberFromBox(int box_id, int amount_to_remove) throws ServiceLayerException;

    /**
     * This method returns the number of boxes currently existing.
     * @return number of boxes
     * @throws ServiceLayerException if error getting number of boxes
     */
    int numberOfBoxes() throws ServiceLayerException;
}
