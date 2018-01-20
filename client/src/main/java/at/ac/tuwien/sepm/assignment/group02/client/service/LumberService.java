package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.FilterDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;

import java.util.List;

public interface LumberService {

    /**
     * retrieve all lumber matching the filter
     * @param filterDTO an UnvalidatedLumber object with the parameter to be searched
     * @return list of searched lumber
     * @throws ServiceLayerException is thrown if an error occurs in the client persistence layer
     */
    List<Lumber> getAll(FilterDTO filterDTO) throws ServiceLayerException;

    /**
     * choose a lumber from the list and mark it as reserved
     * @param lumber
     * @param quantity
     * @throws InvalidInputException
     * @throws ServiceLayerException
     */
    void reserveLumber(Lumber lumber, int quantity, TaskDTO taskDTO) throws ServiceLayerException;

    /**
     * reserve lumber
     * @param lumber
     * @param quantity
     * @param taskDTO
     * @throws ServiceLayerException
     */
    void reserveLumberAlg(Lumber lumber, int quantity, TaskDTO taskDTO) throws ServiceLayerException;

    /**
     * delete a lumber from the store
     *
     * @param lumber
     * @throws ServiceLayerException
     */
    boolean deleteLumber(Lumber lumber) throws ServiceLayerException;

    /**
     * update a lumber
     *
     * @param lumber
     * @throws ServiceLayerException
     */
    void updateLumber(Lumber lumber) throws ServiceLayerException;

    /**
     * get an id of lumber
     *
     * @param id
     * @return an id of lumber
     * @throws InvalidInputException
     */
    Lumber getLumber(int id) throws ServiceLayerException;

    /**
     * add a reserved lumber to task
     *
     * @param id
     * @param quantity
     * @throws InvalidInputException
     * @throws ServiceLayerException
     */
    void addReservedLumberToTask(String id, String quantity) throws ServiceLayerException;

    /**
     * @param lumber
     * @return
     */
    public boolean lumberExists(Lumber lumber);



    /**
     * @param lumber
     */
    public void createLumber(Lumber lumber);
}