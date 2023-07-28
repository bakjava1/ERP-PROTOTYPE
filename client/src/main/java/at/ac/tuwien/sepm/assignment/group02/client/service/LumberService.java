package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.entity.Lumber;
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
     * @param lumber lumber to reserve
     * @param quantity defining the number of lumber to reserve
     * @param taskDTO task to reserve lumber for
     * @throws ServiceLayerException if lumber can't be reserved
     */
    void reserveLumber(Lumber lumber, int quantity, TaskDTO taskDTO) throws ServiceLayerException;

    /**
     * reserve lumber for algorithm
     * @param lumber lumber to reserve
     * @param quantity defining the number of lumber to reserve
     * @param taskDTO task to reserve lumber for
     * @throws ServiceLayerException if lumber can't be reserved
     */
    void reserveLumberAlg(Lumber lumber, int quantity, TaskDTO taskDTO) throws ServiceLayerException;

    /**
     * remove lumber from the store
     * @param lumber lumber to remove
     * @throws ServiceLayerException if lumber can't be removed
     */
    void deleteLumber(Lumber lumber) throws ServiceLayerException;

    /*
     * update a lumber
     * @param lumber lumber with updated values
     * @throws ServiceLayerException if lumber can't be updated

    void updateLumber(Lumber lumber) throws ServiceLayerException;
    */

    /**
     * get lumber by id
     * @param id id of the lumber to be returned
     * @return lumber with given id
     * @throws ServiceLayerException if lumber can't be returned
     */
    Lumber getLumber(int id) throws ServiceLayerException;

}