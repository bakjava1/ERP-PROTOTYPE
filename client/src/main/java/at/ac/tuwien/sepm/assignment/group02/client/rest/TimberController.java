package at.ac.tuwien.sepm.assignment.group02.client.rest;


import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;

public interface TimberController {

    /**
     * 1.8.2 Hinzufügen von neuem Rundholz
     * This method adds new round timber to the round timber store.
     * On server side this might mean that a new record is inserted on persistence level,
     * or an existing record will be updated with the new timber object.
     */
    void createTimber(TimberDTO timberDTO) throws PersistenceLayerException;

    /**
     * 3.2.3 Rundholz aus dem Lager entfernen.
     * This method removes round timber from the round timber store.
     */
    void deleteTimber(TimberDTO timberDTO) throws PersistenceLayerException;

    TimberDTO getTimberById(int timber_id) throws PersistenceLayerException;
}
