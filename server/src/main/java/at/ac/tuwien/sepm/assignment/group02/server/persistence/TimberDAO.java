package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;

public interface TimberDAO {

    /**
     * 1.8.2
     * This method creates new round timber on persistence level.
     * @param timber Timber entity to create
     */
    void createTimber(Timber timber) throws PersistenceLayerException;

    /**
     * 3.2.3
     * This method updates a round timber record.
     * @param timber the updated round timber entity
     */
    void updateTimber(Timber timber) throws PersistenceLayerException;

    /**
     * This method returns the number of boxes currently existing in the database.
     * @return number of boxes
     * @throws PersistenceLayerException
     */
    int getNumberOfBoxes() throws PersistenceLayerException;

}
