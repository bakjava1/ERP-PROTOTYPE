package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Timber;

/**
 * TimberDTO Management / RoundTimber Management
 *
 * Validation of timber on this layer
 * Service Layer for management of timber boxes on client
 */
public interface TimberService {

    /**
     * This method adds new round timber to the timber store.
     * @author Philipp Klein
     * @param timber Timber entity to create or update
     * @throws InvalidInputException    if box number is greater than number of boxes
     *                                  or amount of added timber is negative
     * @throws ServiceLayerException    if could not add timber to the timber store
     */
    void addTimber(Timber timber) throws ServiceLayerException;


    /**
     * Returns the number of boxes currently existing.
     * @author Philipp Klein
     * @return number of boxes
     * @throws ServiceLayerException if error getting number of boxes
     */
    int getNumberOfBoxes() throws ServiceLayerException;
}
