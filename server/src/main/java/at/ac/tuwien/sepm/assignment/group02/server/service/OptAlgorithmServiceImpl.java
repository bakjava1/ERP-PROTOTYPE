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
    private List<SideTaskResult>  horizontalSideTask = new ArrayList<>();
    private List<SideTaskResult> verticalSideTask = new ArrayList<>();


    private static final double SCHNITTFUGE = 4.2; //in mm TODO: in properties file or input?
    private static final int MAX_STIELE_VORSCHNITT =    2; //in mm TODO: in properties file or input?

    public OptAlgorithmServiceImpl(){}

    private TimberDTO currentTimber;
    private TaskDTO mainTask;
    private List<TaskDTO> possibleSideTasks;

    private int upperBound, lowerBound;
    private int vorschnittAnzahl, nachschnittAnzahl;
    private double biggerSize, smallerSize, widthMainTask, heightMainTask;

    private double mainTaskArea, currentCircleArea, currentDiameter, currentRadius;
    private double minMainTaskWasteRelation,  minWaste = Double.POSITIVE_INFINITY;
    private double maxAreaSideTaskHorizontal, maxAreaSideTaskVertical, horizontalMaxA, verticalMaxA = Double.MIN_VALUE;



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
         //   if (timber.getWood_type().toLowerCase().equals(mainTask.getWood_type().toLowerCase())) {
                //area of main task is smaller than area of timber
                if((mainTask.getSize()*mainTask.getWidth())< pow(timber.getDiameter()/2,2)*Math.PI){
                    //length of main task = length of timber
                    if(mainTask.getLength()==timber.getLength()){
                        //TODO quality
                        //quality of main task is not better than best possible quality from timber

                        possibleTimbers.add(timber);
                    }
                }
            //}

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
            //if (task.getId() != mainTask.getId()) {
                //compare wood type
                //if (task.getWood_type().toLowerCase().equals(mainTask.getWood_type().toLowerCase())) {
                    //length of side task is not bigger than length of main task
                    if (task.getLength() == mainTask.getLength()) {
                        //quality of side task is not better than quality of main task
                        //TODO quality review
                        //if (task.getQuality().toLowerCase().equals(mainTask.getQuality().toLowerCase())) {
                            possibleTasks.add(taskConverter.convertPlainObjectToRestDTO(task));
                        //}

                    }
               // }
            //}
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


        //vertical sidetask
        verticalMaxA = heightMainTask * (currentRadius-(widthMainTask/2))*2;
        calculateVerticalSideTasks(0, new ArrayList<>(),currentRadius - (widthMainTask/2) - SCHNITTFUGE,  1);

        if (verticalSideTask.isEmpty()){
            //TODO fill default lumber - properties file?
        }


        //horizontal sidetask
        horizontalMaxA = widthMainTask * (currentRadius-(heightMainTask/2))*2;
        calculateHorizontalSideTasks(0, new ArrayList<>(),currentRadius + (heightMainTask / 2) +SCHNITTFUGE,  1);

        if (horizontalSideTask.isEmpty()){
            //TODO fill default lumber - properties file?

        }


        double currentWaste =  currentCircleArea - mainTaskArea - maxAreaSideTaskHorizontal - maxAreaSideTaskVertical;

        if (currentWaste < minWaste){
            minWaste = currentWaste;

            optimisationBuffer.setHorizontalSideTaskResult(new ArrayList<>(horizontalSideTask));
            optimisationBuffer.setVerticalSideTaskResult(new ArrayList<>(verticalSideTask));
            optimisationBuffer.setNewMainOrderValues(currentRadius, nachschnittAnzahl, vorschnittAnzahl, widthMainTask, heightMainTask, biggerSize, smallerSize);


            optAlgorithmResult.setTimberResult(currentTimber);
        }



        maxAreaSideTaskHorizontal = Double.MIN_VALUE;
        horizontalMaxA = Double.MIN_VALUE;
        maxAreaSideTaskVertical = Double.MIN_VALUE;
        verticalMaxA = Double.MIN_VALUE;
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

        double xCoordinateLeft = 0;
        double xCoordinateRight = 0;
        for (int k = 0; k < optimisationBuffer.getVerticalSideTaskResult().size(); k++) {
            //side task vertical
            if (xCoordinateLeft == 0){
                xCoordinateLeft = optimisationBuffer.getRadius() - (optimisationBuffer.getWidthHauptware() / 2) -
                        SCHNITTFUGE- optimisationBuffer.getVerticalSideTaskResult().get(k).getSmallerSize();
                xCoordinateRight = optimisationBuffer.getRadius() + (optimisationBuffer.getWidthHauptware() / 2) + SCHNITTFUGE;
            }else{
                xCoordinateRight = xCoordinateRight + optimisationBuffer.getVerticalSideTaskResult().get(k).getSmallerSize() + SCHNITTFUGE;
                xCoordinateLeft = xCoordinateLeft - SCHNITTFUGE - optimisationBuffer.getVerticalSideTaskResult().get(k).getSmallerSize();
            }


            yCoordinate = optimisationBuffer.getVerticalSideTaskResult().get(k).getY() + (optimisationBuffer.getVerticalSideTaskResult().get(k).getMaxHeight() - optimisationBuffer.getVerticalSideTaskResult().get(k).getHeightSideTask()) / 2;
            //yCoordinate = currentRadius - heightMainTask/2;

            for (int i = 0; i < optimisationBuffer.getVerticalSideTaskResult().get(k).getCount(); i++) {
                System.out.print("");
                rectangles.add(new RectangleDTO(xCoordinateLeft, yCoordinate, "red", optimisationBuffer.getVerticalSideTaskResult().get(k).getBiggerSize(), optimisationBuffer.getVerticalSideTaskResult().get(k).getSmallerSize()));
                rectangles.add(new RectangleDTO(xCoordinateRight, yCoordinate, "red", optimisationBuffer.getVerticalSideTaskResult().get(k).getBiggerSize(), optimisationBuffer.getVerticalSideTaskResult().get(k).getSmallerSize()));
                yCoordinate += (optimisationBuffer.getVerticalSideTaskResult().get(k).getBiggerSize() + SCHNITTFUGE);
            }
        }



        for (int k = 0; k < optimisationBuffer.getHorizontalSideTaskResult().size(); k++) {

            //side task horizontal
            xCoordinate = optimisationBuffer.getHorizontalSideTaskResult().get(k).getX() + (optimisationBuffer.getBiggerSize()-optimisationBuffer.getHorizontalSideTaskResult().get(k).getBiggerSize())/2;
            double yCoordinateBottom = optimisationBuffer.getHorizontalSideTaskResult().get(k).getY();
            //double yCoordinateTop = currentRadius - heightMainTask/2 - SCHNITTFUGE * optimisationBuffer.getNachschnittAnzahl() - SCHNITTFUGE;
            double yCoordinateTop = (optimisationBuffer.getRadius()*2) - optimisationBuffer.getHorizontalSideTaskResult().get(k).getY() -  optimisationBuffer.getHorizontalSideTaskResult().get(k).getSmallerSize();
            //double yCoordinateBottom = optimisationBuffer.getHorizontalSideTaskResult().get(k).getY();

            for(int i = 0; i < optimisationBuffer.getHorizontalSideTaskResult().get(k).getCount(); i++) {
                System.out.print("");
                rectangles.add(new RectangleDTO(xCoordinate, yCoordinateTop, "blue", optimisationBuffer.getHorizontalSideTaskResult().get(k).getSmallerSize(), optimisationBuffer.getHorizontalSideTaskResult().get(k).getBiggerSize()));
                rectangles.add(new RectangleDTO(xCoordinate, yCoordinateBottom, "blue", optimisationBuffer.getHorizontalSideTaskResult().get(k).getSmallerSize(), optimisationBuffer.getHorizontalSideTaskResult().get(k).getBiggerSize()));
                xCoordinate += (optimisationBuffer.getBiggerSize() + SCHNITTFUGE);
            }



        }


        optAlgorithmResult.setCutViewInRectangle(rectangles);
    }

    //horizontal = top and bottom; vertical = left and right
    private void calculateHorizontalSideTasks(double currArea, List<SideTaskResult> currSideTask,  double currY, double currHorizontalAmount) {


        if (currArea <= horizontalMaxA) {

            if (currHorizontalAmount > 0 ) {


                if (currArea > maxAreaSideTaskHorizontal) {

                    maxAreaSideTaskHorizontal = currArea;
                    horizontalSideTask = new ArrayList<>(currSideTask);

                }




                for (int i = 0; i < possibleSideTasks.size(); i++) {



                    TaskDTO sideTaskHorizontal = possibleSideTasks.get(i);
                    int horizontalAmount =  calculateHorizontalAmount(sideTaskHorizontal, currY);


                    //x Start-Koordinate für hor-seitenware nicht immer x(links/oben) von hauptware
                    // Math.floor((vorschnittAnzahl-horizontalAmount)/2)*mainTask.getWidth()  : wieviele stiele muss ich nach rechts rücken?
                    double xCoordinateSideTaskHorizontal = (currentRadius - widthMainTask / 2) + (Math.floor((vorschnittAnzahl - horizontalAmount) / 2) * (biggerSize + SCHNITTFUGE));

                    double widthSideTaskHorizontal = horizontalAmount * (sideTaskHorizontal.getWidth() + SCHNITTFUGE) - SCHNITTFUGE;



                    SideTaskResult x = new SideTaskResult(horizontalAmount, widthSideTaskHorizontal, sideTaskHorizontal.getSize(), xCoordinateSideTaskHorizontal,
                            currY, sideTaskHorizontal.getSize(), sideTaskHorizontal.getWidth(), 0, false);


                    currSideTask.add(x);


                    calculateHorizontalSideTasks(currArea + (sideTaskHorizontal.getSize() * sideTaskHorizontal.getWidth() * 2 * horizontalAmount), currSideTask,  currY + sideTaskHorizontal.getSize()+ SCHNITTFUGE , horizontalAmount);


                    currSideTask.remove(x);


                }


            }


        }



    }


    private int calculateHorizontalAmount (TaskDTO task, double currY){


        //y-bottom -> links/oben geht sich evtl aus, aber links unten vielleicht nicht -> daher für die Berechnung der Anzahl den Punkt links/unten
        currY += task.getSize();

        //x Werte durch Kreisgleichung und durch abc-formel ermittelt
        double x1 = (2 * currentRadius + Math.sqrt(Math.pow(2 * currentRadius, 2) - 4 * Math.pow(currY - currentRadius, 2))) / 2;
        double x2 = (2 * currentRadius - Math.sqrt(Math.pow(2 * currentRadius, 2) - 4 * Math.pow(currY - currentRadius, 2))) / 2;


        double maxWidthSideTaskHorizontal;
        if (x1 > x2) {
            maxWidthSideTaskHorizontal = x1 - x2;
        } else {
            maxWidthSideTaskHorizontal = x2 - x1;
        }


        int horizontalCount = 0;
        if (task.getWidth() <= mainTask.getWidth()) {

            //check ob es sich in der höhe ausgeht
            if (maxWidthSideTaskHorizontal >= widthMainTask) {
                horizontalCount = vorschnittAnzahl;
            } else {

                //volle vorschnittanzahl für horizontale seitenware nicht möglich
                horizontalCount = (int) floor((maxWidthSideTaskHorizontal+SCHNITTFUGE) / (biggerSize+SCHNITTFUGE));

                if (horizontalCount < 1 && maxWidthSideTaskHorizontal >= task.getWidth()) {

                    //wenn sich genau einer innerhalb der grenzen der hauptware ausgeht
                    horizontalCount = 1;

                }


                //symmetrisch verteilung auf horizontaler ebene:
                //wenn vorschnittanzahl = gerade -> nur gerade anzahl an horizontalcount möglich
                if (vorschnittAnzahl % 2 == 0){
                    if (horizontalCount %2 != 0) {
                        horizontalCount -= 1;
                    }
                }

            }

        } return horizontalCount;


    }

    //horizontal = top and bottom; vertical = left and right
    private void calculateVerticalSideTasks(double currArea, List<SideTaskResult> currSideTask,  double currX, double currVerticalAmount) {


        if (currArea <= verticalMaxA) {

            if (currVerticalAmount > 0 ) {


                if (currArea > maxAreaSideTaskVertical) {

                    maxAreaSideTaskVertical = currArea;
                    verticalSideTask = new ArrayList<>(currSideTask);


                }


                for (int i = 0; i < possibleSideTasks.size(); i++) {


                    TaskDTO sideTaskVertical = possibleSideTasks.get(i);

                    currX = currX - sideTaskVertical.getSize() - SCHNITTFUGE;


                    //y^2 - 2*y*radius + (radius^2 - (r*r - (xCoordinate - radius)^2)) = 0
                    //a = 1 (unser y in der quadratischen gleichung ist immer 1)
                    //b = -2*radius
                    //c = radius^2 - (radius^2 - ((xCoordinate - radius)^2)) = (xCoordinate - radius)^2)

                    //y1 = (-b + sqrt(b^2 - 4*a*c))/2*a
                    //y2 = (-b - sqrt(b^2 - 4*a*c))/2*a

                    double a = 1;
                    double b = 2*currentRadius*-1;
                    double c = pow((currX - currentRadius),2);

                    double y1 = (-b + sqrt(b*b - 4*a*c))/2*a;
                    double y2 = (-b - sqrt(b*b - 4*a*c))/2*a;

                    //y1 - y2
                    double maxHeightSideTaskVertical;
                    if(y1 > y2) {
                        maxHeightSideTaskVertical = y1 - y2;
                    } else {
                        maxHeightSideTaskVertical = y2 - y1;
                    }

                    int verticalAmount = (int) floor((maxHeightSideTaskVertical + SCHNITTFUGE) / (sideTaskVertical.getWidth() + SCHNITTFUGE));





                    double heightSideTaskVertical = verticalAmount * (sideTaskVertical.getWidth() + SCHNITTFUGE) - SCHNITTFUGE;



                    SideTaskResult x = new SideTaskResult(verticalAmount, sideTaskVertical.getSize(), heightSideTaskVertical, currX, y2, sideTaskVertical.getSize(), sideTaskVertical.getWidth(), maxHeightSideTaskVertical, false);


                    currSideTask.add(x);


                    calculateVerticalSideTasks(currArea + (sideTaskVertical.getSize() * sideTaskVertical.getWidth() * 2 * verticalAmount), currSideTask,  currX  , verticalAmount);


                    currSideTask.remove(x);
                }


            }


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
