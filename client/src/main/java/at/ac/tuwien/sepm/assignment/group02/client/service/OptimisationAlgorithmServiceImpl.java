package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.converter.OptAlgorithmConverter;
import at.ac.tuwien.sepm.assignment.group02.client.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.client.entity.OptAlgorithmResult;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Rectangle;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.OptAlgorithmController;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OptAlgorithmResultDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.RectangleDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;


@Service
public class OptimisationAlgorithmServiceImpl implements OptimisationAlgorithmService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static OptAlgorithmController optAlgorithmController;
    private static OptAlgorithmConverter optAlgorithmConverter;
    private static TaskConverter taskConverter;

    @Autowired
    public OptimisationAlgorithmServiceImpl (OptAlgorithmController optAlgorithmController, TaskConverter taskConverter, OptAlgorithmConverter optAlgorithmConverter){
        OptimisationAlgorithmServiceImpl.optAlgorithmController = optAlgorithmController;
        OptimisationAlgorithmServiceImpl.taskConverter = taskConverter;
        OptimisationAlgorithmServiceImpl.optAlgorithmConverter = optAlgorithmConverter;
    }

    //TODO delete this constructor and use autowired constructor
    public OptimisationAlgorithmServiceImpl(){}

    @Override
    public OptAlgorithmResultDTO getOptAlgorithmResult(TaskDTO task) throws PersistenceLayerException {


        OptAlgorithmResultDTO optAlgorithmResultDTO = optAlgorithmController.getOptAlgorithmResult(task);

        //OptAlgorithmResult optAlgorithmResult = optAlgorithmConverter.convertRestDTOToPlainObject(optAlgorithmResultDTO);

        renderImage(optAlgorithmResultDTO);


        return optAlgorithmResultDTO;
        //TODO check if optAlgorithmResult is empty and throw new exception to show user that no optimisation can be found
    }

    private void renderImage(OptAlgorithmResultDTO optAlgorithmResult){
        double diameter = optAlgorithmResult.getTimberResult().getDiameter();

        BufferedImage bimage = new BufferedImage((int)diameter,(int) diameter,
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = bimage.createGraphics();


        Color border = new Color(50, 50, 50);
        g2.setColor(border);

        Shape circle = new Ellipse2D.Double(0, 0, diameter, diameter);
        g2.draw(circle);



        for (RectangleDTO rectangle : optAlgorithmResult.getCutViewInRectangle()) {


            Shape rect = new java.awt.Rectangle((int)rectangle.getxCoordinate(), (int)rectangle.getyCoordinate(), (int)rectangle.getWidth(), (int)rectangle.getHeight());

            switch (rectangle.getColor()){
                case"green" :
                    g2.setColor(new Color(45, 138, 65));
                    g2.fill(rect);
                    g2.setColor(border);
                    g2.draw(rect);
                    break;
                case"red" :
                    g2.setColor(new Color(222, 113, 103));
                    g2.fill(rect);
                    g2.setColor(border);
                    g2.draw(rect);
                    break;
                case"blue" :
                    g2.setColor(new Color(40, 145, 217));
                    g2.fill(rect);
                    g2.setColor(border);
                    g2.draw(rect);
                    break;
                default:
                    break;

            }



        }
        g2.dispose();



        optAlgorithmResult.setRenderedImage(bimage);



    }
}
