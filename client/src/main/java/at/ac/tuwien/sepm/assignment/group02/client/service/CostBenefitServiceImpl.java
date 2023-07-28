package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.CostBenefitController;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Service
public class CostBenefitServiceImpl implements CostBenefitService {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private CostBenefitController costBenefitController;
    private TaskConverter taskConverter;

    @Autowired
    public CostBenefitServiceImpl(CostBenefitController costBenefitController,TaskConverter taskConverter) {
        this.costBenefitController = costBenefitController;
        this.taskConverter = taskConverter;
    }

    @Override
    public double costValueFunction(List<Task> taskList) throws ServiceLayerException {
        LOG.info("Cost Value Function invoken");
        List<TaskDTO> toEvaluate = new ArrayList<>();
        for(int i = 0; i < taskList.size();i++) {
            toEvaluate.add(taskConverter.convertPlainObjectToRestDTO(taskList.get(i)));
        }
        double result = 0.0;
        try {
            result = costBenefitController.costValueFunction(toEvaluate);
        } catch(PersistenceLayerException e) {
            LOG.error("Error at Server: " + e.getMessage());
            throw new ServiceLayerException(e.getMessage());
        }
        return result;
    }
}
