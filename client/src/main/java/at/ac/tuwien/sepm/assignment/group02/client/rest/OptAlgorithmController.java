package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.client.entity.OptAlgorithmResult;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;

public interface OptAlgorithmController {

    /**
     * starts the optimisation algorithm and returns the optimal result
     * for the selected task by the user
     * @param task selected task by the user
     * @return optimal result including: 3 tasks, 1 timber and the cut view
     */
    OptAlgorithmResult getOptAlgorithmResult(TaskDTO task) throws PersistenceLayerException;
}
