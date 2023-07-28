package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.FilterDTO;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;

import java.util.List;

public interface LumberDAO {

    /**
     * hello world
     */
    Lumber readLumberById(int id) throws PersistenceLayerException;

    /**
     * inserte a new lumber in lumber store
     * @param lumber
     * @throws PersistenceLayerException
     * @inv lumber is validated
     */
    int createLumber(Lumber lumber) throws PersistenceLayerException;

    /**
     * update an existing lumber
     * @param lumber
     * @throws PersistenceLayerException
     * @inv lumber is validated
     */
    void updateLumber(Lumber lumber) throws PersistenceLayerException;

    /**
     * update an existing lumber
     * @param lumber lumber to update
     */
    void updateLumberAlg(Lumber lumber);

    /**
     * delete lumber from the lumber store.
     * @param lumber
     * @throws PersistenceLayerException
     * @inv lumber is validated
     */
    void deleteLumber(Lumber lumber) throws PersistenceLayerException;

    /**
     * retrieve all lumber from data base
     * @param filter a object of Lumber with search parameters
     * @return  a list of all lumber that matches the filter.
     * @throws PersistenceLayerException if the database is not available
     * @inv filter is validated
     */
    List<Lumber> getAllLumber(FilterDTO filter) throws PersistenceLayerException;

    /**
     * gets the lumber amount which is not reserved and fits the needs of a task
     * @author Markus Fugger
     * @param toCheck task which needs lumber
     * @return how much lumber is aviable
     * @throws PersistenceLayerException if an sqlexception occurs
     * @inv toCheck is validated
     */
    int getLumberCountForTask(Task toCheck) throws PersistenceLayerException;


    void removeLumber(int id, int removedQuantity) throws PersistenceLayerException;
}
