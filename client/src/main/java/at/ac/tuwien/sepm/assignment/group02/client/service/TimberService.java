package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Timber;

/**
 * TimberDTO Management / RoundTimber Management
 *
 * CONVERSION HAPPENS ON THIS LAYER
 * VALIDATION HAPPENS ON THIS LAYER
 */
public interface TimberService {

    /**
     * 1.8 Rundholz anlegen
     * 1.8.2 (rest/TimberController) Hinzufügen von neuem Rundholz
     * This method adds new round timber to the timber store.
     * (On server side this method might create a new timber record on persistence level,
     * or an existing timber record will be updated with the values of the timber parameter passed)
     * @param timber Timber entity to create or update
     */
    void addTimber(Timber timber) throws InvalidInputException, ServiceLayerException;

}
