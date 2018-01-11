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
                    double height = (double) toEvaluate.get(i).getSize() / (double) 1000;
                    double width = (double) toEvaluate.get(i).getWidth() / (double) 1000;
                    double length = (double) toEvaluate.get(i).getLength() / (double) 1000;
                    LOG.info("h: " + height + " w: " + width + " l: " + length);
                    double volumeLumber = height * width * length;
                    LOG.info("Volume of one Lumber: " + volumeLumber);
                    Properties properties = new Properties();
                    InputStream input = null;
                    int cost1 = -1;
                    int cost2 = -1;
                    try {
                        input = new FileInputStream("src/main/resources/cost.properties");
                        properties.load(input);
                        cost1 = Integer.parseInt(properties.getProperty("cost/bankmeter/riftsaw"));
                        cost2 = Integer.parseInt(properties.getProperty("cost/bankmeter/bandsaw"));
                    } catch(IOException e) {
                        LOG.error("Could not load Properties File for Costs\nReason: " + e.getMessage());
                        throw new ServiceLayerException("Could not load Properties File for Costs\nReason: " + e.getMessage());
                    }
                    LOG.info("cost riftsaw = " + cost1 + " cost bandsaw = " + cost2);
                    //TODO Produktionskostenrechnung
                    List<Timber> possibleBoxes = timberDAO.getBoxesForTask(null);
                    for(int j = 0; j < possibleBoxes.size();j++) {
                        Timber temp = possibleBoxes.get(j);
                        String searchSentence = "";
                        searchSentence += temp.getWood_type() +  "/";
                        searchSentence += temp.getLength() + "/";
                        searchSentence += temp.getQuality() + "/";
                        searchSentence += temp.getDiameter();
                        int price = Integer.parseInt(properties.getProperty(searchSentence));
                        LOG.info("Price for Box " + temp.getBox_id() + " is " + price);
                        boolean check = (price == temp.getPrice());
                        LOG.info("Price is equal to Price in Database: " + check);
                        double d = Math.floor( (double) temp.getDiameter() / (double) 10);
                        double l = (double) temp.getLength() / (double) 1000;
                        LOG.info("D = " + d + " L = " + l);
                        double bankmeter = (Math.PI / 4) * Math.pow(d,2) * l * Math.pow(10,-4);
                        BigDecimal bd = new BigDecimal(bankmeter);
                        bd = bd.setScale(4, RoundingMode.HALF_UP);
                        bankmeter = bd.doubleValue();
                        LOG.info("Bankmeters for Box " + temp.getBox_id() + " is " + bankmeter);
                        bankmeter *= 0.6;
                        LOG.info("Aviable Bankmeter Count (60%): " + bankmeter);
                        double countOneLumber = bankmeter / volumeLumber;
                        countOneLumber = Math.floor(countOneLumber);
                        LOG.info("In one Timber " + countOneLumber + " Lumber could be produced");
                        double lumberCount = (double) toBeProduced / (double) countOneLumber;
                        lumberCount = Math.ceil(lumberCount);
                        LOG.info(lumberCount + " Timber would be needed to produce needed Quantity of Lumber");
                        double produceCost = lumberCount * price + (bankmeter * lumberCount) * cost2 ;
                        LOG.info("The Cost for Production would be: " + produceCost);
                    }
                    if (input != null) {
                        try {
                            input.close();
                        } catch (IOException e) {
                            LOG.error("Could not close InputStream\nReason: " + e.getMessage());
                        }
                    }
                }
            } catch(PersistenceLayerException e) {
                LOG.error("Database Problems, Reason: " + e.getMessage());
                throw new ServiceLayerException("Database Error");
            }
        }
        return summe;
    }
}
