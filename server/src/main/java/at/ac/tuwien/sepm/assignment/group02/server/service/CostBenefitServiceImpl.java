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
import java.math.BigDecimal;
import java.math.RoundingMode;
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
                    LOG.info("Complete needed Task Quantity is aviable in storage -> + " + toEvaluate.get(i).getPrice());
                    summe += toEvaluate.get(i).getPrice();
                } else {
                    LOG.info("Not complete Quantity is aviable in storage");
                    int toBeProduced = toEvaluate.get(i).getQuantity() - aviable;
                    LOG.info(toBeProduced + " are left to produce");
                    double d = Math.floor( (double) toEvaluate.get(i).getWidth() / (double) 10);
                    double l = (double) toEvaluate.get(i).getLength() / (double) 1000;
                    LOG.info("D = " + d + " L = " + l);
                    double bankmeter = (Math.PI / 4) * Math.pow(d,2) * l * Math.pow(10,-4);
                    BigDecimal bd = new BigDecimal(bankmeter);
                    bd = bd.setScale(4, RoundingMode.HALF_UP);
                    bankmeter = bd.doubleValue();
                    LOG.info("Bankmeters for one Product are: " + bankmeter);
                    double sum_bankmeter = bankmeter * toBeProduced;
                    LOG.info("Complete Bankmeter Count to be produced: " + sum_bankmeter);
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
