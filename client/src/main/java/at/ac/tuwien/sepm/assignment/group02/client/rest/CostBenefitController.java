package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;

import java.util.List;

public interface CostBenefitController {

    /**
     * cost Value Function
     * @param taskList represents Tasks to be evaluated
     * @return double containing evaluation
     * @throws PersistenceLayerException if an Error at server occurs
     * @inv taskList is validated
     */
    double costValueFunction(List<TaskDTO> taskList) throws PersistenceLayerException;

}
