package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.entity.UnvalidatedLumber;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Lumber;

import java.util.List;

/**
 * CONVERSION HAPPENS ON THIS LAYER
 * VALIDATION HAPPENS ON THIS LAYER
 */
public interface LumberService {

    /**
     * retrieve all lumbers
     * @param filter
     * @return list of searched lumber
     * @param filter an UnvalidatedLumber object with the parameter to be searched
     * @throws InvalidInputException if the search parameters are not valid
     * @throws ServiceLayerException is thrown if an error occurs in the client persistence layer
     */
    List<Lumber> getAll(UnvalidatedLumber filter) throws InvalidInputException, ServiceLayerException;

    /**
     * choose a lumber from the list and mark it as reserved
     * @param lumber
     * @param quantity
     * @throws InvalidInputException
     * @throws ServiceLayerException
     */
    void reserveLumber(Lumber lumber, int quantity) throws InvalidInputException, ServiceLayerException;

    /**
     * delete a lumber from the store
     * @param lumber
     * @throws ServiceLayerException
     */
    public boolean deleteLumber(Lumber lumber) throws ServiceLayerException;

    /**
     * update a lumber
     * @param lumber
     * @throws ServiceLayerException
     */
    public void updateLumber(Lumber lumber) throws  ServiceLayerException;

    /**
     * retrieve all lumbers
     * @throws ServiceLayerException
     * @return a list of all Lumbers
     */
    List<Lumber>getAllLumber() throws ServiceLayerException;

    /**
     * get an id of lumber
     * @param id
     * @return an id of lumber
     * @throws InvalidInputException
     */
    Lumber getLumber(int id) throws InvalidInputException;

    /**
     * add a reserved lumber to task
     * @param id
     * @param quantity
     * @throws InvalidInputException
     * @throws ServiceLayerException
     */
    void addReservedLumberToTask(String id, String quantity) throws InvalidInputException, ServiceLayerException;

    /**
     *
     * @param lumber
     * @return false or true
     */
    public boolean lumberExists(Lumber lumber);

    /**
     *
     * @param lumber
     */
    public  void createLumber(Lumber lumber);

}