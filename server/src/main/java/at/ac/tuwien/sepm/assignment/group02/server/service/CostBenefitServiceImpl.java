package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.server.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.LumberDAO;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TimberDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class CostBenefitServiceImpl implements CostBenefitService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private TaskConverter taskConverter;
    private LumberDAO lumberDAO;
    private TimberDAO timberDAO;

    @Autowired
    public CostBenefitServiceImpl(TaskConverter taskConverter,LumberDAO lumberDAO,TimberDAO timberDAO) {
        this.taskConverter = taskConverter;
        this.lumberDAO = lumberDAO;
        this.timberDAO = timberDAO;
    }

    @Override
    public double costValueFunction(List<TaskDTO> taskList) throws ServiceLayerException {
        LOG.info("Cost Benefit Evaluation started");
        if(taskList == null) {
            LOG.error("No List given");
            throw new ServiceLayerException("No List given");
        }
        if(taskList.size() == 0) {
            LOG.error("Empty List given");
            throw new ServiceLayerException("Empty List given");
        }
        List<Task> toEvaluate = new ArrayList<>();
        for(int i = 0; i < taskList.size();i++) {
            toEvaluate.add(taskConverter.convertRestDTOToPlainObject(taskList.get(i)));
        }
        double summe = 0.0;
        for(int i  = 0; i < toEvaluate.size();i++) {
            try {
                int aviable = lumberDAO.getLumberCountForTask(toEvaluate.get(i));
                LOG.debug("Aviable for Task " + i + ": " + aviable);
                if(toEvaluate.get(i).getQuantity() <= aviable) {
                    LOG.debug("Lumber Quantity " + toEvaluate.get(i).getQuantity() + " completely aviable in Storage -> + " + toEvaluate.get(i).getPrice());
                    double fromCentToEuro = centToEuro(toEvaluate.get(i).getPrice());
                    summe += fromCentToEuro;
                } else {
                    LOG.debug("Lumber Quantity " + toEvaluate.get(i).getQuantity() + " not completely aviable in Storage");
                    int toBeProduced = toEvaluate.get(i).getQuantity() - aviable;
                    LOG.debug(toBeProduced + " Lumber are left to produce");
                    double height = (double) toEvaluate.get(i).getSize() / (double) 1000;
                    double width = (double) toEvaluate.get(i).getWidth() / (double) 1000;
                    double length = (double) toEvaluate.get(i).getLength() / (double) 1000;
                    double volumeLumber = height * width * length;
                    LOG.debug("Volume of one Lumber left to produce: " + volumeLumber);
                    Properties properties = new Properties();
                    InputStream input = null;
                    double cost_riftsaw = -1;
                    try {
                        input = new FileInputStream(System.getProperty("user.home")+"/cost.properties");
                        properties.load(input);
                        cost_riftsaw = Double.parseDouble(properties.getProperty("cost/bankmeter/riftsaw"));
                    } catch(IOException e) {
                        LOG.error("Could not load Properties File for Costs\nReason: " + e.getMessage());
                        throw new ServiceLayerException("Could not load Properties File for Costs\nReason: " + e.getMessage());
                    }
                    double optimalProduceCost = -1.0;
                    List<Timber> possibleBoxes = timberDAO.getBoxesForTask(toEvaluate.get(i));
                    if(possibleBoxes.size() == 0) {
                        LOG.error("Lumber with Values length: " + toEvaluate.get(i).getLength() + " width: " + toEvaluate.get(i).getWidth() + " size: " + toEvaluate.get(i).getSize() + " quality : " + toEvaluate.get(i).getQuality() + " can not be produced");
                        throw new ServiceLayerException("Lumber with Values length: " + toEvaluate.get(i).getLength() + " width: " + toEvaluate.get(i).getWidth() + " size: " + toEvaluate.get(i).getSize() + " quality : " + toEvaluate.get(i).getQuality() + " can not be produced");
                    }
                    for(int j = 0; j < possibleBoxes.size();j++) {
                        Timber temp = possibleBoxes.get(j);
                        String searchSentence = "";
                        searchSentence += temp.getWood_type() +  "/";
                        searchSentence += temp.getLength() + "/";
                        searchSentence += temp.getQuality() + "/";
                        searchSentence += temp.getDiameter();
                        double price = Double.parseDouble(properties.getProperty(searchSentence));
                        double d = Math.floor( (double) temp.getDiameter() / (double) 10);
                        double l = (double) temp.getLength() / (double) 1000;
                        double bankmeter = (Math.PI / 4) * Math.pow(d,2) * l * Math.pow(10,-4);
                        BigDecimal bd = new BigDecimal(bankmeter);
                        bd = bd.setScale(4, RoundingMode.HALF_UP);
                        bankmeter = bd.doubleValue();
                        bankmeter *= 0.6;
                        LOG.debug("Aviable Bankmeter Count (60%) for Box " + temp.getBox_id() + ": " + bankmeter);
                        double countOneLumber = bankmeter / volumeLumber;
                        countOneLumber = Math.floor(countOneLumber);
                        LOG.debug("In one Timber " + countOneLumber + " Lumber could be produced");
                        if(countOneLumber > 0) {
                            double lumberCount = (double) toBeProduced / (double) countOneLumber;
                            lumberCount = Math.ceil(lumberCount);
                            double produceCost = lumberCount * price + (bankmeter * lumberCount) * cost_riftsaw;
                            LOG.debug("The Cost for Production would be: " + produceCost);
                            if(optimalProduceCost == -1.0) {
                                optimalProduceCost = produceCost;
                            } else if(optimalProduceCost > produceCost) {
                                optimalProduceCost = produceCost;
                            }
                        }
                    }
                    if (input != null) {
                        try {
                            input.close();
                        } catch (IOException e) {
                            LOG.error("Could not close InputStream\nReason: " + e.getMessage());
                        }
                    }
                    if(optimalProduceCost == -1) {
                        LOG.error("Lumber with Values length: " + toEvaluate.get(i).getLength() + " width: " + toEvaluate.get(i).getWidth() + " size: " + toEvaluate.get(i).getSize() + " can not be produced");
                        throw new ServiceLayerException("Lumber with Values length: " + toEvaluate.get(i).getLength() + " width: " + toEvaluate.get(i).getWidth() + " size: " + toEvaluate.get(i).getSize() + " can not be produced");
                    } else {
                        double fromCentToEuro = centToEuro(toEvaluate.get(i).getPrice());
                        double difference = fromCentToEuro - optimalProduceCost;
                        LOG.debug("Optimal Production Costs are: " + optimalProduceCost + " Resulting in: " + difference + " €");
                        summe += difference;
                    }
                }
            } catch(PersistenceLayerException e) {
                LOG.error("Database Problems, Reason: " + e.getMessage());
                throw new ServiceLayerException("Database Error");
            }
            LOG.debug("Current Sum: " + summe);
        }
        BigDecimal bd = new BigDecimal(summe);
        bd = bd.setScale(4, RoundingMode.HALF_UP);
        summe = bd.doubleValue();
        return summe;
    }

    private double centToEuro(int price) {
        double result = 0.0;
        result = Math.floor((double) price / (double) 100);
        result += ((double) price % (double) 100) / (double) 100;
        return result;
    }
}
