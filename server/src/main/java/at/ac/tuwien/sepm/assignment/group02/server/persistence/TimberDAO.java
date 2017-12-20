package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.server.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;

public interface TimberDAO {

    /**
     * This method creates new round timber on persistence level.
     * @param timber Timber entity to create
     * @throws PersistenceLayerException
     */
    void createTimber(Timber timber) throws PersistenceLayerException;

    /**
     * This method updates a round timber record.
     * @param timber the updated round timber entity
     * @throws PersistenceLayerException
     */
    void updateTimber(Timber timber) throws PersistenceLayerException;

    /**
     * This method returns the number of boxes currently existing in the database.
     * @return number of boxes
     * @throws PersistenceLayerException
     */
    int getNumberOfBoxes() throws PersistenceLayerException;

}
