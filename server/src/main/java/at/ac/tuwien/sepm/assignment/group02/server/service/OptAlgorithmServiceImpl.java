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

import static java.lang.Math.floor;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

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


    private Timber currentTimber;
    private Task mainTask;
    private List<Task> possibleSideTasks;

    private int upperBound, lowerBound;
    private int vorschnittAnzahl, nachschnittAnzahl;
    private double biggerSize, smallerSize, widthMainTask, heightMainTask;

    private double mainTaskArea, currentCircleArea, currentDiameter, currentRadius;
    private double minMainTaskWasteRelation, minWaste;


    @Autowired
    public OptAlgorithmServiceImpl(TimberDAO timberDAO, TaskDAO taskDAO, OptAlgorithmConverter optAlgorithmConverter, TaskConverter taskConverter) {
        OptAlgorithmServiceImpl.timberDAO = timberDAO;
        OptAlgorithmServiceImpl.taskDAO = taskDAO;
        OptAlgorithmServiceImpl.taskConverter = taskConverter;
        OptAlgorithmServiceImpl.optAlgorithmConverter = optAlgorithmConverter;
    }

    @Override
    public OptAlgorithmResult getOptAlgorithmResult(Task task) throws PersistenceLayerException {
        mainTask = task;

        List<Timber> possibleTimbers = getPossibleTimbers(mainTask);
        if(possibleTimbers.isEmpty()){
            //throw exception or set boolean
            return null;
        }


        possibleSideTasks = getPossibleTasks(mainTask);
        if(possibleSideTasks.isEmpty()){
            //create cut view and return optAlgorithmResult without any side tasks
            //no need to execute optimisation algorithm because no side tasks can be optimised
        }


        for(Timber timber : possibleTimbers) {
            try {
                calculateCut(timber);
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
                    if((mainTask.getSize()*mainTask.getWidth())< pow(timber.getDiameter()/2,2)*Math.PI){
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
                //main task is not allowed as side task
                if (task.getId() != mainTask.getId()) {
                    //compare wood type
                    if (task.getWood_type().toLowerCase().equals(mainTask.getWood_type().toLowerCase())) {
                        //length of side task is not bigger than length of main task
                        if (task.getLength() == mainTask.getLength()) {
                            //quality of side task is not better than quality of main task
                            //TODO quality review
                            if (task.getQuality().toLowerCase().equals(mainTask.getQuality().toLowerCase())) {
                                possibleTasks.add(task);
                            }
                        }

                    }
                }
            }
        return possibleTasks;
    }

    private void calculateCut(Timber timber) {
        currentTimber = timber;

        currentDiameter = currentTimber.getDiameter();
        currentRadius = currentDiameter / 2;

        double a = currentDiameter / sqrt(2); //Seitenlänge des Quadrates
        double height = mainTask.getSize();
        double width = mainTask.getWidth();

        biggerSize = (width >= height ? width : height);
        smallerSize = (width == biggerSize ? height : width);


        nachschnittAnzahl = (int) Math.floor((a+SCHNITTFUGE)/(smallerSize+SCHNITTFUGE)); //abrunden
        vorschnittAnzahl = (int) Math.floor((a+SCHNITTFUGE)/(biggerSize+SCHNITTFUGE));

        //check if there is enough space for the main order
        if (nachschnittAnzahl == 0 || vorschnittAnzahl == 0) {
            return;
        }

        //max. n-stielig
        if (vorschnittAnzahl > MAX_STIELE_VORSCHNITT){
            vorschnittAnzahl = MAX_STIELE_VORSCHNITT;
        }


        widthMainTask = (vorschnittAnzahl * (biggerSize + SCHNITTFUGE)) - SCHNITTFUGE;
        heightMainTask = (nachschnittAnzahl * (smallerSize + SCHNITTFUGE)) - SCHNITTFUGE;
        mainTaskArea = vorschnittAnzahl*nachschnittAnzahl*smallerSize*biggerSize;
        currentCircleArea = (pow(currentRadius,2)*Math.PI);


        //starting optimisation for current main task (checking every side tasks)
        calculateSideTasks(0,0);
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

    private void calculateSideTasks(int horizontalIndex, int verticalIndex) {
        Task sideTaskHorizontal = possibleSideTasks.get(horizontalIndex);
        Task sideTaskVertical = possibleSideTasks.get(verticalIndex);

        //r = Radius, xM = yM = radius bzw Kreismittelpunkt
        //r*r = (x - xM) * (x - xM) + (y - yM) * (y - yM)
        //y = sqrt(-(x-xM)^2+r^2)+yM
        double xCoordinateSideTaskHorizontal = ((currentRadius - widthMainTask)/2) - sideTaskHorizontal.getSize();
        double yCoordinateSideTaskHorizontal = sqrt(-pow((xCoordinateSideTaskHorizontal - currentRadius), 2) + pow(currentRadius, 2)) + currentRadius;
        double maxHeightSideTaskHorizontal = currentDiameter - ((currentDiameter - yCoordinateSideTaskHorizontal) * 2);

        //Anzahl der moeglichen Schnitthoelzer * Dicke * 2 (weil links und rechts)
        double horizontalCount = floor(maxHeightSideTaskHorizontal / sideTaskHorizontal.getWidth());
        double currentSideTaskVerticalArea = horizontalCount * sideTaskHorizontal.getSize() * 2;

        //TODO Schnittfuge auch bei side tasks vorhanden??



        double currentSideTaskHorizontalArea = 0.0;
        double currentWaste = currentCircleArea - (mainTaskArea + currentSideTaskVerticalArea + currentSideTaskHorizontalArea);


        //TODO notwendig? wir wollen nur waste bzw verschleiß minimieren
        /*double currentMainTaskProportion = mainTaskArea/currentCircleArea;
        double currentWasteProportion = currentWaste/currentCircleArea;

        double currentMainOrderWasteRelation = currentMainTaskProportion/currentWasteProportion;*/


        /*if (minMainTaskWasteRelation <= currentMainOrderWasteRelation){
            minMainTaskWasteRelation = currentMainOrderWasteRelation;

            optAlgorithmResult.setTimberResult(currentTimber);

            optBuffer.setNewValues(currentTimber.getDiameter()/2, nachschnittAnzahl, vorschnittAnzahl, widthMainTask, heightMainTask, biggerSize, smallerSize);
        }*/
        if (minWaste <= currentWaste) {
            minWaste = currentWaste;

            optAlgorithmResult.setTimberResult(currentTimber);


        } else if (verticalIndex < possibleSideTasks.size()) {
            calculateSideTasks(horizontalIndex, verticalIndex + 1);
        } else if (horizontalIndex < possibleSideTasks.size()){
            calculateSideTasks(horizontalIndex + 1, 0);
        }
    }
}
