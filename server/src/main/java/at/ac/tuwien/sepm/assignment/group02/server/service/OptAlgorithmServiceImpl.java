package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.server.converter.OptAlgorithmConverter;
import at.ac.tuwien.sepm.assignment.group02.server.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.*;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TaskDAO;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TimberDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

public class OptAlgorithmServiceImpl implements OptAlgorithmService{

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static TaskDAO taskDAO;
    private static TimberDAO timberDAO;
    private static OptAlgorithmConverter optAlgorithmConverter;
    private static TaskConverter taskConverter;

    private OptAlgorithmResult optAlgorithmResult= new OptAlgorithmResult();
    private OptimisationBuffer optBuffer = new OptimisationBuffer();

    private static final double SCHNITTFUGE = 4.2; //in mm TODO: in properties file or input?
    private static final int MAX_STIELE_VORSCHNITT = 2; //in mm TODO: in properties file or input?


    @Autowired
    public OptAlgorithmServiceImpl(TimberDAO timberDAO, TaskDAO taskDAO, OptAlgorithmConverter optAlgorithmConverter, TaskConverter taskConverter) {
        OptAlgorithmServiceImpl.timberDAO = timberDAO;
        OptAlgorithmServiceImpl.taskDAO = taskDAO;
        OptAlgorithmServiceImpl.taskConverter = taskConverter;
        OptAlgorithmServiceImpl.optAlgorithmConverter = optAlgorithmConverter;
    }

    @Override
    public OptAlgorithmResult getOptAlgorithmResult(Task mainTask) throws PersistenceLayerException {

        List<Timber> possibleTimbers = getPossibleTimbers(mainTask);
        if(possibleTimbers.isEmpty()){
            //throw exception or set boolean
            return null;
        }
        List<Task> possibleTasks = getPossibleTasks(mainTask);
        if(possibleTasks.isEmpty()){
            //create cut view and return optAlgorithmResult without any side tasks
            //no need to execute optimisation algorithm because no side tasks can be optimised
        }

        for(Timber timber : possibleTimbers) {
            try {
                calculateCut(mainTask, timber);
            } catch (Exception e) {
                e.printStackTrace();
                //TODO
            }
        }
        if (optAlgorithmResult.getTimberResult() != null){
                calculateMainOrderCoordinates();
        }else{
            System.out.println("Keine geeignete Box gefunden."); //TODO: passende Exception erstellen
        }
        return optAlgorithmResult;
    }

    @Override
    public List<Timber> getPossibleTimbers(Task mainTask) throws PersistenceLayerException {
        List<Timber> possibleTimbers = new ArrayList<>();
            List<Timber> timbers = timberDAO.getAllBoxes();
            for(Timber timber : timbers){
                //compare wood type
                if (timber.getWood_type().toLowerCase().equals(mainTask.getWood_type().toLowerCase())) {
                    //area of main task is smaller than area of timber
                    if((mainTask.getSize()*mainTask.getWidth())<Math.pow(timber.getDiameter()/2,2)*Math.PI){
                        //length of main task = length of timber
                        if(mainTask.getLength()==timber.getLength()){
                            //TODO quality
                            //quality of main task is not better than best possible quality from timber

                            possibleTimbers.add(timber);
                        }
                    }
                }
            }
        return possibleTimbers;
    }

    @Override
    public List<Task> getPossibleTasks(Task mainTask) throws PersistenceLayerException {
        List<Task> possibleTasks = new ArrayList<>();
            List<Task> tasks = taskDAO.getAllOpenTasks();
            for(Task task : tasks){
                //compare wood type
                if (task.getWood_type().toLowerCase().equals(mainTask.getWood_type().toLowerCase())) {
                    //length of side task is not bigger than length of main task
                    if(task.getLength()==mainTask.getLength()){
                        //quality of side task is not better than quality of main task
                        //TODO quality review
                        if(task.getQuality().toLowerCase().equals(mainTask.getQuality().toLowerCase())){
                            possibleTasks.add(task);
                        }
                    }

                }
            }
        return possibleTasks;
    }

    public void calculateCut(Task mainTask, Timber timber) throws Exception{



            double maxMainOrderWasteRelation = 0;

            double minDiameter = timber.getDiameter();
            double radius = minDiameter / 2;

            double a = minDiameter / Math.sqrt(2); //Seitenlänge des Quadrates
            double height = mainTask.getSize();
            double width = mainTask.getWidth();

            double biggerSize = (width >= height ? width : height);
            double smallerSize = (width == biggerSize ? height : width);


            int nachschnittAnzahl = (int) Math.floor((a+SCHNITTFUGE)/(smallerSize+SCHNITTFUGE)); //abrunden
            int vorschnittAnzahl = (int) Math.floor((a+SCHNITTFUGE)/(biggerSize+SCHNITTFUGE));

            //check if there is enough space for the main order
            if (nachschnittAnzahl == 0 || vorschnittAnzahl == 0) {
                return;
            }

            //max. n-stielig
            if (vorschnittAnzahl > MAX_STIELE_VORSCHNITT){
                vorschnittAnzahl = MAX_STIELE_VORSCHNITT;
            }


            double widthHauptware = (vorschnittAnzahl * (biggerSize + SCHNITTFUGE)) - SCHNITTFUGE;
            double heightHauptware = (nachschnittAnzahl * (smallerSize + SCHNITTFUGE)) - SCHNITTFUGE;


//
//            //TODO: find best secondary Order
//            for (Lumber currentOrder: secondaryOrder) {
//
//            }

            double currentMainOrderA = vorschnittAnzahl*nachschnittAnzahl*smallerSize*biggerSize;
            double currentSecondaryOrderA = 0; // TODO: Fläche der Seitenware berechnen
            double currentCircleA = (Math.pow(radius,2)*Math.PI);
            double currentWasteA = currentCircleA - (currentMainOrderA+currentSecondaryOrderA);

            double currentMainOrderProportion = 100/currentCircleA*currentMainOrderA;
            double currentWasteProportion = 100/currentCircleA*currentWasteA;

            double currentMainOrderWastRelation = currentMainOrderProportion/currentWasteProportion;


            if (maxMainOrderWasteRelation <= currentMainOrderWastRelation){
                maxMainOrderWasteRelation = currentMainOrderWastRelation;

                optAlgorithmResult.setTimberResult(timber);

                optBuffer.setNewValues(radius, nachschnittAnzahl, vorschnittAnzahl, widthHauptware, heightHauptware, biggerSize, smallerSize);

            }

    }


    private void calculateMainOrderCoordinates(){

        double x = (optBuffer.getRadius() - (optBuffer.getWidthHauptware() / 2));
        double yCoordinate = optBuffer.getRadius() - (optBuffer.getHeightHauptware() / 2);

        List<Rectangle> rectangles = new ArrayList<>();

        for (int k = 0; k < optBuffer.getNachschnittAnzahl(); k++){
            double xCoordinate = x;

            for (int j = 0;j < optBuffer.getVorschnittAnzahl();j++){

                rectangles.add(new Rectangle(xCoordinate, yCoordinate, "green", optBuffer.getSmallerSize(), optBuffer.getBiggerSize()));
                xCoordinate += optBuffer.getBiggerSize() + SCHNITTFUGE;


            }

            yCoordinate += (optBuffer.getSmallerSize() + SCHNITTFUGE);

        }

        //TODO set image or rectangles
        //optAlgorithmResult.setRectangles(rectangles);

    }
}
