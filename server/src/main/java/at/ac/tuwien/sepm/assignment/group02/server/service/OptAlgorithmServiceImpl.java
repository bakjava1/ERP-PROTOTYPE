package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OptAlgorithmResultDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.RectangleDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;
import at.ac.tuwien.sepm.assignment.group02.server.converter.OptAlgorithmConverter;
import at.ac.tuwien.sepm.assignment.group02.server.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.server.converter.TimberConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.*;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TaskDAO;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TimberDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.floor;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;


@Service

public class OptAlgorithmServiceImpl implements OptAlgorithmService{
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static TaskDAO taskDAO;
    private static TimberDAO timberDAO;
    private static OptAlgorithmConverter optAlgorithmConverter;
    private static TaskConverter taskConverter;
    private static TimberConverter timberConverter;

    private OptAlgorithmResultDTO optAlgorithmResult= new OptAlgorithmResultDTO();
    private OptimisationBuffer optimisationBuffer = new OptimisationBuffer();
    private SideTaskResult horizontalSideTask = new SideTaskResult();
    private SideTaskResult verticalSideTask = new SideTaskResult();


    private static final double SCHNITTFUGE = 4.2; //in mm TODO: in properties file or input?
    private static final int MAX_STIELE_VORSCHNITT = 10; //in mm TODO: in properties file or input?

    public OptAlgorithmServiceImpl(){}

    private TimberDTO currentTimber;
    private TaskDTO mainTask;
    private List<TaskDTO> possibleSideTasks;

    private int upperBound, lowerBound;
    private int vorschnittAnzahl, nachschnittAnzahl;
    private double biggerSize, smallerSize, widthMainTask, heightMainTask;

    private double mainTaskArea, currentCircleArea, currentDiameter, currentRadius;
    private double minMainTaskWasteRelation,  minWaste = Double.POSITIVE_INFINITY;
    private double maxAreaSideTaskHorizontal, maxAreaSideTaskVertical = Double.MIN_VALUE;



    @Autowired
    public OptAlgorithmServiceImpl(TimberDAO timberDAO, TaskDAO taskDAO, OptAlgorithmConverter optAlgorithmConverter, TimberConverter timberConverter, TaskConverter taskConverter) {
        OptAlgorithmServiceImpl.timberDAO = timberDAO;
        OptAlgorithmServiceImpl.taskDAO = taskDAO;
        OptAlgorithmServiceImpl.taskConverter = taskConverter;
        OptAlgorithmServiceImpl.timberConverter = timberConverter;
        OptAlgorithmServiceImpl.optAlgorithmConverter = optAlgorithmConverter;
    }

    @Override

    public OptAlgorithmResultDTO getOptAlgorithmResult(TaskDTO task) throws PersistenceLayerException {
        mainTask = task;


        List<TimberDTO> possibleTimbers = getPossibleTimbers(mainTask);
        if(possibleTimbers.isEmpty()){
            //throw exception or set boolean
            return null;
        }


        possibleSideTasks = getPossibleTasks(mainTask);
        if(possibleSideTasks.isEmpty()){
            //create cut view and return optAlgorithmResult without any side tasks
            //no need to execute optimisation algorithm because no side tasks can be optimised
        }


        for(TimberDTO timber : possibleTimbers) {
            try {
                calculateCut(timber);
            } catch (Exception e) {
                e.printStackTrace();
                //TODO
            }
        }
        if (optAlgorithmResult.getTimberResult() != null){

            calculateRectangleCoordinates();
        }else{
            System.out.println("Keine geeignete Box gefunden."); //TODO: passende Exception erstellen
        }
        return optAlgorithmResult;
    }

    @Override
    public List<TimberDTO> getPossibleTimbers(TaskDTO mainTask) throws PersistenceLayerException {
        List<TimberDTO> possibleTimbers = new ArrayList<>();
        List<TimberDTO> timbers =  new ArrayList<>();

        List<Timber> timbersToConvert = timberDAO.getAllBoxes();

        for (Timber timber: timbersToConvert) {
            timbers.add(timberConverter.convertPlainObjectToRestDTO(timber));
        }


        for(TimberDTO timber : timbers){
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

    //TODO zwei Methoden und zwei Listen; jeweils eine für vertikal=links/rechts und horizontal=oben/unten
    @Override
    public List<TaskDTO> getPossibleTasks(TaskDTO mainTask) throws PersistenceLayerException {
        List<TaskDTO> possibleTasks = new ArrayList<>();


        List<Task> tasks = taskDAO.getAllOpenTasks();
        for (Task task : tasks) {
            //main task is not allowed as side task
            if (task.getId() != mainTask.getId()) {
                //compare wood type
                if (task.getWood_type().toLowerCase().equals(mainTask.getWood_type().toLowerCase())) {
                    //length of side task is not bigger than length of main task
                    if (task.getLength() == mainTask.getLength()) {
                        //quality of side task is not better than quality of main task
                        //TODO quality review
                        //if (task.getQuality().toLowerCase().equals(mainTask.getQuality().toLowerCase())) {
                            possibleTasks.add(taskConverter.convertPlainObjectToRestDTO(task));
                        //}

                    }
                }
            }
        }

        return possibleTasks;
    }


    private void calculateCut(TimberDTO timber){
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


        /*
        //TODO delete; for testing purpose only
        double currentWaste = currentCircleArea - mainTaskArea;

        if(currentWaste <= minWaste) {
            minWaste = currentWaste;

            optAlgorithmResult.setTimberResult(timber);
            optimisationBuffer.setNewValues(currentRadius, nachschnittAnzahl, vorschnittAnzahl, widthMainTask, heightMainTask, biggerSize, smallerSize);
        }*/

        //starting optimisation for current main task (checking every side tasks)


        calculateVerticalSideTasks(0);

        if (verticalSideTask.isEmpty()){
            //TODO fill default lumber - properties file?
        }



        calculateHorizontalSideTasks(0);

        if (horizontalSideTask.isEmpty()){
            //TODO fill default lumber - properties file?
            System.out.println("-------------------------------------");
        }


        double currentWaste =  currentCircleArea - mainTaskArea - maxAreaSideTaskHorizontal - maxAreaSideTaskVertical;

        if (currentWaste < minWaste){
            minWaste = currentWaste;

            optimisationBuffer.setHorizontalSideTaskResult(new SideTaskResult(horizontalSideTask));
            optimisationBuffer.setVerticalSideTaskResult(new SideTaskResult(verticalSideTask));
            optimisationBuffer.setNewMainOrderValues(currentRadius, nachschnittAnzahl, vorschnittAnzahl, widthMainTask, heightMainTask, biggerSize, smallerSize);


            optAlgorithmResult.setTimberResult(currentTimber);
        }



        //horizontalSideTask.clear();
        //verticalSideTask.clear();
        maxAreaSideTaskHorizontal = Double.MIN_VALUE;
        maxAreaSideTaskVertical = Double.MIN_VALUE;
        verticalSideTask.clear();
        horizontalSideTask.clear();



    }


    private void calculateRectangleCoordinates(){
        List<RectangleDTO> rectangles = new ArrayList<>();

        //main task
        double x = (optimisationBuffer.getRadius() - (optimisationBuffer.getWidthHauptware() / 2));
        double yCoordinate = optimisationBuffer.getRadius() - (optimisationBuffer.getHeightHauptware() / 2);

        double xCoordinate;
        for (int k = 0; k < optimisationBuffer.getNachschnittAnzahl(); k++){
            xCoordinate = x;

            for (int j = 0; j < optimisationBuffer.getVorschnittAnzahl(); j++){
                rectangles.add(new RectangleDTO(xCoordinate, yCoordinate, "green", optimisationBuffer.getSmallerSize(), optimisationBuffer.getBiggerSize()));
                xCoordinate += optimisationBuffer.getBiggerSize() + SCHNITTFUGE;
            }

            yCoordinate += (optimisationBuffer.getSmallerSize() + SCHNITTFUGE);

        }


        //side task vertical
        double xCoordinateLeft = optimisationBuffer.getRadius() - (optimisationBuffer.getWidthHauptware()/2) - SCHNITTFUGE- optimisationBuffer.getVerticalSideTaskResult().getSmallerSize() ;
        double xCoordinateRight = optimisationBuffer.getRadius() + optimisationBuffer.getWidthHauptware()/2 + SCHNITTFUGE ;
        yCoordinate = optimisationBuffer.getVerticalSideTaskResult().getY() + (optimisationBuffer.getVerticalSideTaskResult().getMaxHeight() - optimisationBuffer.getVerticalSideTaskResult().getHeightSideTask())/2;
        //yCoordinate = currentRadius - heightMainTask/2;

        for(int i = 0; i < optimisationBuffer.getVerticalSideTaskResult().getCount(); i++) {
            System.out.print("");
            rectangles.add(new RectangleDTO(xCoordinateLeft, yCoordinate, "red", optimisationBuffer.getVerticalSideTaskResult().getBiggerSize(), optimisationBuffer.getVerticalSideTaskResult().getSmallerSize()));
            rectangles.add(new RectangleDTO(xCoordinateRight, yCoordinate, "red", optimisationBuffer.getVerticalSideTaskResult().getBiggerSize(), optimisationBuffer.getVerticalSideTaskResult().getSmallerSize()));
            yCoordinate += (optimisationBuffer.getVerticalSideTaskResult().getBiggerSize() + SCHNITTFUGE);
        }


        //side task horizontal
        xCoordinate = optimisationBuffer.getHorizontalSideTaskResult().getX() + (optimisationBuffer.getBiggerSize()-optimisationBuffer.getHorizontalSideTaskResult().getBiggerSize())/2;
        //double yCoordinateTop = currentRadius - heightMainTask/2 - SCHNITTFUGE * optimisationBuffer.getNachschnittAnzahl() - SCHNITTFUGE;
        double yCoordinateTop = optimisationBuffer.getRadius() - optimisationBuffer.getHeightHauptware()/2 - optimisationBuffer.getHorizontalSideTaskResult().getSmallerSize() - SCHNITTFUGE;
        double yCoordinateBottom = optimisationBuffer.getRadius() + optimisationBuffer.getHeightHauptware()/2  + SCHNITTFUGE;

        for(int i = 0; i < optimisationBuffer.getHorizontalSideTaskResult().getCount(); i++) {
            System.out.print("");
            rectangles.add(new RectangleDTO(xCoordinate, yCoordinateTop, "blue", optimisationBuffer.getHorizontalSideTaskResult().getSmallerSize(), optimisationBuffer.getHorizontalSideTaskResult().getBiggerSize()));
            rectangles.add(new RectangleDTO(xCoordinate, yCoordinateBottom, "blue", optimisationBuffer.getHorizontalSideTaskResult().getSmallerSize(), optimisationBuffer.getHorizontalSideTaskResult().getBiggerSize()));
            xCoordinate += (optimisationBuffer.getBiggerSize() + SCHNITTFUGE);
        }


        optAlgorithmResult.setCutViewInRectangle(rectangles);
    }

    //horizontal = top and bottom; vertical = left and right
    private void calculateHorizontalSideTasks(int horizontalIndex) {
        TaskDTO sideTaskHorizontal = possibleSideTasks.get(horizontalIndex);


        double yCoordinateSideTaskHorizontal = currentRadius + heightMainTask/2 + SCHNITTFUGE + sideTaskHorizontal.getSize();

        //x Werte durch Kreisgleichung und durch abc-formel ermittelt
        double x1 = (2*currentRadius + Math.sqrt(Math.pow(2*currentRadius,2) - 4*Math.pow(yCoordinateSideTaskHorizontal-currentRadius,2)))/2;
        double x2 = (2*currentRadius - Math.sqrt(Math.pow(2*currentRadius,2) - 4*Math.pow(yCoordinateSideTaskHorizontal-currentRadius,2)))/2;


        double maxWidthSideTaskHorizontal;
        if(x1 > x2) {
            maxWidthSideTaskHorizontal = x1 - x2;
        } else {
            maxWidthSideTaskHorizontal = x2 - x1;
        }


        int horizontalCount = 0;
        if(sideTaskHorizontal.getWidth() <= mainTask.getWidth()) {

            //check ob es sich in der höhe ausgeht
            if (maxWidthSideTaskHorizontal >= widthMainTask){
                horizontalCount = vorschnittAnzahl;
            }else{
                //wenn hor-seitenware eher nur in der mitte platz hat, oder garnicht
                horizontalCount =  (int) floor(maxWidthSideTaskHorizontal/biggerSize);

                if (horizontalCount < 1 && maxWidthSideTaskHorizontal >= sideTaskHorizontal.getWidth()){

                    //wenn sich genau einer innerhalb der grenzen der hauotware ausgeht
                    horizontalCount = 1;

                }

            }

        }

        if(horizontalCount > 0) {
            double smallerSizeHorizontal = sideTaskHorizontal.getSize();
            double biggerSizeHorizontal = sideTaskHorizontal.getWidth();
            double widthSideTaskHorizontal = horizontalCount * (sideTaskHorizontal.getWidth() + SCHNITTFUGE) - SCHNITTFUGE;
            double heightSideTaskHorizontal = sideTaskHorizontal.getSize();
            double sideTaskHorizontalArea = smallerSizeHorizontal * biggerSizeHorizontal * 2 * horizontalCount;

            //x Start-Koordinate für hor-seitenware nicht immer x(links/oben) von hauptware
            // Math.floor((vorschnittAnzahl-horizontalCount)/2)*mainTask.getWidth()  : wieviele stiele muss ich nach rechts rücken?
            double xCoordinateSideTaskHorizontal = (currentRadius- widthMainTask/2) + (Math.floor((vorschnittAnzahl-horizontalCount)/2)*(biggerSize+SCHNITTFUGE));

            if (sideTaskHorizontalArea > maxAreaSideTaskHorizontal) {

                maxAreaSideTaskHorizontal = sideTaskHorizontalArea;
                horizontalSideTask.setNewResult(horizontalCount, widthSideTaskHorizontal, heightSideTaskHorizontal, xCoordinateSideTaskHorizontal,
                        yCoordinateSideTaskHorizontal, smallerSizeHorizontal, biggerSizeHorizontal,0,false);

            }

        }


        if (horizontalIndex < possibleSideTasks.size() - 1) {
            calculateHorizontalSideTasks(horizontalIndex + 1);
        }


    }

    //horizontal = top and bottom; vertical = left and right
    private void calculateVerticalSideTasks(int verticalIndex) {
        TaskDTO sideTaskVertical = possibleSideTasks.get(verticalIndex);


        double xCoordinateSideTaskVertical = (currentRadius - (widthMainTask/2)) - sideTaskVertical.getSize() - SCHNITTFUGE;

        //y^2 - 2*y*radius + (radius^2 - (r*r - (xCoordinate - radius)^2)) = 0
        //a = 1 (unser y in der quadratischen gleichung ist immer 1)
        //b = -2*radius
        //c = radius^2 - (radius^2 - ((xCoordinate - radius)^2)) = (xCoordinate - radius)^2)

        //y1 = (-b + sqrt(b^2 - 4*a*c))/2*a
        //y2 = (-b - sqrt(b^2 - 4*a*c))/2*a

        double a = 1;
        double b = 2*currentRadius*-1;
        double c = pow((xCoordinateSideTaskVertical - currentRadius),2);

        double y1 = (-b + sqrt(b*b - 4*a*c))/2*a;
        double y2 = (-b - sqrt(b*b - 4*a*c))/2*a;

        //y1 - y2
        double maxHeightSideTaskVertical;
        if(y1 > y2) {
            maxHeightSideTaskVertical = y1 - y2;
        } else {
            maxHeightSideTaskVertical = y2 - y1;
        }

        int verticalCount = (int) floor((maxHeightSideTaskVertical + SCHNITTFUGE) / (sideTaskVertical.getWidth() + SCHNITTFUGE));



        if(verticalCount > 0) {
            double smallerSizeVertical = sideTaskVertical.getSize();
            double biggerSizeVertical = sideTaskVertical.getWidth();
            double heightSideTaskVertical = verticalCount * (sideTaskVertical.getWidth() + SCHNITTFUGE) - SCHNITTFUGE;
            double widthSideTaskVertical = sideTaskVertical.getSize();
            double sideTaskVerticalArea = smallerSizeVertical * biggerSizeVertical * verticalCount * 2;



            if (sideTaskVerticalArea > maxAreaSideTaskVertical) {

                maxAreaSideTaskVertical = sideTaskVerticalArea;

                //check if y1 is upper left corner
                //for drawing the rectangle shape
                if(y1 > y2) {
                    y1 = y2;
                }


                verticalSideTask.setNewResult(verticalCount, widthSideTaskVertical, heightSideTaskVertical, xCoordinateSideTaskVertical,
                        y1, smallerSizeVertical, biggerSizeVertical, maxHeightSideTaskVertical, false);

            }



        }

        if(verticalIndex < possibleSideTasks.size() - 1) {
            calculateVerticalSideTasks(verticalIndex + 1);
        }

    }






    //TODO notwendig? wir wollen nur den wert von waste bzw verschleiß minimieren
        /*double currentMainTaskProportion = mainTaskArea/currentCircleArea;
        double currentWasteProportion = currentWaste/currentCircleArea;

        double currentMainOrderWasteRelation = currentMainTaskProportion/currentWasteProportion;*/


        /*if (minMainTaskWasteRelation <= currentMainOrderWasteRelation){
            minMainTaskWasteRelation = currentMainOrderWasteRelation;

            optAlgorithmResult.setTimberResult(currentTimber);

            optimisationBuffer.setNewValues(currentTimber.getDiameter()/2, nachschnittAnzahl, vorschnittAnzahl, widthMainTask, heightMainTask, biggerSize, smallerSize);
        }*/

}
