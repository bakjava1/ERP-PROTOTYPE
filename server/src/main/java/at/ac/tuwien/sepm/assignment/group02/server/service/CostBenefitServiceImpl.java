package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.server.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.LumberDAO;
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

    private TaskConverter taskConverter;
    private LumberDAO lumberDAO;

    @Autowired
    public CostBenefitServiceImpl(TaskConverter taskConverter,LumberDAO lumberDAO) {
        this.taskConverter = taskConverter;
        this.lumberDAO = lumberDAO;
    }

    @Override
    public int costValueFunction(List<TaskDTO> taskList) throws ServiceLayerException {
        List<Task> toEvaluate = new ArrayList<>();
        for(int i = 0; i < taskList.size();i++) {
            toEvaluate.add(taskConverter.convertRestDTOToPlainObject(taskList.get(i)));
        }
        int summe = 0;
        for(int i  = 0; i < toEvaluate.size();i++) {
            try {
                int aviable = lumberDAO.getLumberCountForTask(toEvaluate.get(i));
                LOG.info("Aviable for Task " + i + ": " + aviable);
                if(toEvaluate.get(i).getQuantity() <= aviable) {
                    summe += toEvaluate.get(i).getPrice();
                } else {
                    //TODO Produktionskostenrechnung
                }
            } catch(PersistenceLayerException e) {
                LOG.error("Database Problems, Reason: " + e.getMessage());
                throw new ServiceLayerException("Database Error");
            }
        }
        return summe;
    }
}
