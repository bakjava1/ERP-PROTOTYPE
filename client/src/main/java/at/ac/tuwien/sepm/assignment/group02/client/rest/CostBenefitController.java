package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;

import java.util.List;

public interface CostBenefitController {

    double costValueFunction(List<TaskDTO> taskList) throws PersistenceLayerException;

}
