package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.server.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;

import java.util.List;

/**
 * Persistence layer for timber management, connects to server on server
 * Saving entities on this layer
 */
public interface TimberDAO {

    /**
     * This method adds new round timber to the timber store.
     * New timber will be stored in box specified by {@code timber}
     * @author Philipp Klein
     * @param timber Timber entity to update / insert new timber
     * @throws PersistenceLayerException if could not execute update of box specified by {@code timber}
     */
    void createTimber(Timber timber) throws PersistenceLayerException;

    /**
     * This method removes timber from the round timber store.
     * @param timber the updated round timber entity
     * @throws PersistenceLayerException
     */
    void removeTimber(Timber timber) throws PersistenceLayerException;

    /**
     * This method returns the number of boxes currently existing in the database.
     * @return number of boxes
     * @throws PersistenceLayerException if could not get number of saved boxes
     */
    int getNumberOfBoxes() throws PersistenceLayerException;

    /**
     * This method returns all boxes currently saved.
     * @return list of all boxes
     * @throws PersistenceLayerException if could not return a list of all boxes
     */
    List<Timber> getAllBoxes() throws PersistenceLayerException;


    List<Timber> getBoxesForTask(Task toCheck) throws PersistenceLayerException;

}
