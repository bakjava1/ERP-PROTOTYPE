package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OptAlgorithmResultDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;
import at.ac.tuwien.sepm.assignment.group02.server.entity.OptAlgorithmResult;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;

import java.util.List;

public interface OptAlgorithmService {

    /**
     * implements the optimisation algorithm and returns the optimal result
     * for the selected task by the user
     * @param task task selected by user
     * @return optimal result including: 3 tasks, 1 timber and the cut view
     */
    OptAlgorithmResultDTO getOptAlgorithmResult(TaskDTO task) throws ServiceLayerException, PersistenceLayerException;

    List<TimberDTO> getPossibleTimbers(TaskDTO task) throws PersistenceLayerException;

    List<TaskDTO> getPossibleTasks(TaskDTO task) throws PersistenceLayerException;

}
