package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;

import java.util.List;

public interface CostBenefitService {
    /**
     * estimate costs/use for orders
     * estimate the planned algorithm tho the costs and use
     */
    double costValueFunction(List<Task> taskList) throws ServiceLayerException;
}
