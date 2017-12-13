package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;

public interface TimberService {

    /**
     * 1.8.2 Hinzufügen von neuem Rundholz
     * This method adds new round timber to the round timber store.
     * On server side this might mean that a new record is inserted on persistence level,
     * or an existing record will be updated with the new timber object.
     * @param timberDTO timberDTO to be added
     * @throws ServiceLayerException if timberDTO couldn't be added to the timber store
     */
    void addTimber(TimberDTO timberDTO) throws ServiceLayerException;

    /**
     * 3.2.3 Rundholz aus dem Lager entfernen.
     * This method removes round timber from the round timber store.
     * @param timberDTO timberDTO to be updated/removed
     * @throws ServiceLayerException if timberDTO couldn't be updated/removed
     */
    void updateTimber(TimberDTO timberDTO) throws ServiceLayerException;

    /**
     * This method returns the number of boxes currently existing.
     * @return number of boxes
     * @throws ServiceLayerException
     */
    int numberOfBoxes() throws ServiceLayerException;
}
