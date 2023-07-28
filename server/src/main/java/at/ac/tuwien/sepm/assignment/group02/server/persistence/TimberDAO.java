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
     * @inv timber is validated
     */
    void createTimber(Timber timber) throws PersistenceLayerException;

    /**
     * This method removes timber from the round timber store.
     * @param timber the updated round timber entity
     * @throws PersistenceLayerException
     * @inv timber is validated
     */
    void removeTimber(Timber timber) throws PersistenceLayerException;

    /**
     * This method returns the number of boxes currently existing in the database.
     * @return number of boxes
     * @throws PersistenceLayerException if could not get number of saved boxes
     * @inv timber is validated
     */
    int getNumberOfBoxes() throws PersistenceLayerException;

    /**
     * This method returns all boxes currently saved.
     * @return list of all boxes
     * @throws PersistenceLayerException if could not return a list of all boxes
     * @inv timber is validated
     */
    List<Timber> getAllBoxes() throws PersistenceLayerException;

    /**
     * Method returns Boxes fitting Requirements of Task
     * @param toCheck Task with requirements
     * @return List of Boxes , an empty list if no Box for Task could be found
     * @throws PersistenceLayerException if an error in the database occurs
     * @inv toCheck is validated
     */
    List<Timber> getBoxesForTask(Task toCheck) throws PersistenceLayerException;

}
