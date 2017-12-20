package at.ac.tuwien.sepm.assignment.group02.client.rest;


import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;

public interface TimberController {


    /**
     * This method adds new round timber to the round timber store.
     * On server side this might mean that a new record is inserted on persistence level,
     * or an existing record will be updated with the new timber object.
     * @param timberDTO
     * @throws PersistenceLayerException
     */
    void createTimber(TimberDTO timberDTO) throws PersistenceLayerException;

    /**
     * This method removes round timber from the round timber store.
     * @param timberDTO
     * @throws PersistenceLayerException
     */
    void deleteTimber(TimberDTO timberDTO) throws PersistenceLayerException;

    /**
     * get a timber by its id
     * @param timber_id
     * @return the id of timber
     * @throws PersistenceLayerException
     */
    TimberDTO getTimberById(int timber_id) throws PersistenceLayerException;

    /**
     * This method returns the number of boxes currently existing.
     * @return number of boxes
     * @throws PersistenceLayerException
     */
    int getNumberOfBoxes() throws PersistenceLayerException;
}
