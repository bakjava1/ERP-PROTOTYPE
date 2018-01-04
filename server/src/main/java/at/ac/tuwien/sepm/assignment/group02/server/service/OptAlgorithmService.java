package at.ac.tuwien.sepm.assignment.group02.server.service;

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
    OptAlgorithmResult getOptAlgorithmResult(Task task) throws ServiceLayerException, PersistenceLayerException;

    List<Timber> getPossibleTimbers(Task task) throws PersistenceLayerException;

    List<Task> getPossibleTasks(Task task) throws PersistenceLayerException;

}
