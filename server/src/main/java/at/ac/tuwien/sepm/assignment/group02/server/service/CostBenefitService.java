package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;

import java.util.List;

public interface CostBenefitService {

    int costValueFunction(List<TaskDTO> taskList) throws ServiceLayerException;

}
