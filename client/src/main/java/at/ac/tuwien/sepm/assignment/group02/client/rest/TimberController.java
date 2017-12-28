package at.ac.tuwien.sepm.assignment.group02.client.rest;


import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;

/**
 * Rest/Persistence layer for timber management, connects to server on client
 * Connection to server on this layer
 */
public interface TimberController {

    /**
     * This method adds new round timber to the round timber store.
     * @author Philipp Klein
     * @param timberDTO TimberDTO to update
     * @throws PersistenceLayerException if error in server or no connection to server
     */
    void createTimber(TimberDTO timberDTO) throws PersistenceLayerException;

    /**
     * This method removes round timber from the round timber store.
     * @param timberDTO timberDTO that will be removed/amount reduced
     * @throws PersistenceLayerException if error in server or no connection to server
     */
    void deleteTimber(TimberDTO timberDTO) throws PersistenceLayerException;

    /**
     * get a timber by its id
     * @param timber_id id of timber
     * @return timberDTO with {@code timber_id}
     * @throws PersistenceLayerException if error in server or no connection to server
     */
    TimberDTO getTimberById(int timber_id) throws PersistenceLayerException;

    /**
     * This method returns the number of boxes currently existing.
     * @author Philipp Klein
     * @return number of boxes
     * @throws PersistenceLayerException if error in server or no connection to server
     */
    int getNumberOfBoxes() throws PersistenceLayerException;
}
