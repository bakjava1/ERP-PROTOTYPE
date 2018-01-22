package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OptAlgorithmResultDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.RectangleDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;
import at.ac.tuwien.sepm.assignment.group02.server.converter.OptAlgorithmConverter;
import at.ac.tuwien.sepm.assignment.group02.server.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.server.converter.TimberConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.OptimisationBuffer;
import at.ac.tuwien.sepm.assignment.group02.server.entity.SideTaskResult;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.OptimisationAlgorithmException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TaskDAO;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TimberDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import static java.lang.Math.*;


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
    private List<SideTaskResult> horizontalSideTask = new ArrayList<>();
    private List<SideTaskResult> verticalSideTask = new ArrayList<>();

    private InputStream inputStream;
    private double SCHNITTFUGE;
    private int MAX_STIELE_VORSCHNITT;

    private TimberDTO currentTimber;
    private TaskDTO mainTask;
    private List<TaskDTO> possibleSideTasksVertical, possibleSideTasksHorizontal;

    private int upperBound, lowerBound;
    private int vorschnittAnzahl, nachschnittAnzahl;
    private double biggerSize, smallerSize, widthMainTask, heightMainTask;

    private double mainTaskArea, currentCircleArea, currentDiameter, currentRadius;
    private double minWaste = Double.POSITIVE_INFINITY;
    private double maxAreaSideTaskHorizontal, maxAreaSideTaskVertical, horizontalMaxA, verticalMaxA = Double.MIN_VALUE;
    long startTime = System.currentTimeMillis();

    public OptAlgorithmServiceImpl(){}

    @Autowired
    public OptAlgorithmServiceImpl(TimberDAO timberDAO, TaskDAO taskDAO, OptAlgorithmConverter optAlgorithmConverter, TimberConverter timberConverter, TaskConverter taskConverter) {
        OptAlgorithmServiceImpl.timberDAO = timberDAO;
        OptAlgorithmServiceImpl.taskDAO = taskDAO;
        OptAlgorithmServiceImpl.taskConverter = taskConverter;
        OptAlgorithmServiceImpl.timberConverter = timberConverter;
        OptAlgorithmServiceImpl.optAlgorithmConverter = optAlgorithmConverter;
    }

    @Override
    public OptAlgorithmResultDTO getOptAlgorithmResult(TaskDTO task) throws PersistenceLayerException, OptimisationAlgorithmException, ServiceLayerException {
        try {
            Properties properties = new Properties();
            inputStream = new FileInputStream(OptAlgorithmService.class.getClassLoader().getResource("algorithm.properties").getFile());
            properties.load(inputStream);
            SCHNITTFUGE = Double.parseDouble(properties.getProperty("SCHNITTFUGE"));
            MAX_STIELE_VORSCHNITT = Integer.parseInt(properties.getProperty("MAX_STIELE_VORSCHNITT"));
            inputStream.close();
        } catch (IOException e) {
            LOG.error("Properties file for optimisation algorithm not found: {}", e.getMessage());
            throw new ServiceLayerException("Properties Datei für Optimierungsalgorithmus nicht gefunden: " + e.getMessage());
        }

        mainTask = task;

        List<TimberDTO> possibleTimbers = getPossibleTimbers(mainTask);
        if(possibleTimbers.isEmpty()){
            LOG.debug("Optimierungsalgorithmus: Keine geeignete Box gefunden.");
            throw new OptimisationAlgorithmException("Keine geeignete Box gefunden.");
        }

        possibleSideTasksVertical = getPossibleTasks("vertical", mainTask);
        possibleSideTasksHorizontal = getPossibleTasks("horizontal", mainTask);

        for(TimberDTO timber : possibleTimbers) {
                calculateCut(timber);
        }

        if (optAlgorithmResult.getTimberResult() != null){
            setColors();
            calculateRectangleCoordinates();
        }else{
            LOG.debug("Optimierungsalgorithmus: Keine geeignete Box gefunden.");
            throw new OptimisationAlgorithmException("Keine geeignete Box gefunden.");
        }

        return optAlgorithmResult;
    }

    @Override
    public List<TimberDTO> getPossibleTimbers(TaskDTO mainTask) throws PersistenceLayerException {
        List<TimberDTO> possibleTimbers = new ArrayList<>();
        List<TimberDTO> timbers =  new ArrayList<>();

        List<Timber> timbersToConvert = timberDAO.getBoxesForTask(taskConverter.convertRestDTOToPlainObject(mainTask));
        List <String> possibleQualities = convertLumberQualityToTimberQuality(mainTask.getQuality());

        for (Timber timber: timbersToConvert) {
            timbers.add(timberConverter.convertPlainObjectToRestDTO(timber));
        }

        for(TimberDTO timber : timbers){
            //compare wood type
            String timberWoodType = timber.getWood_type().toLowerCase();
            String mainTaskWoodType = mainTask.getWood_type().toLowerCase();
            if(timberWoodType.equals("laerche")) {
                timberWoodType = timberWoodType.substring(0,3);
            } else {
                timberWoodType = timberWoodType.substring(0,2);
            }
            if (timberWoodType.equals(mainTaskWoodType)) {
                //area of main task is smaller than area of timber
                if((mainTask.getSize()*mainTask.getWidth())< pow(timber.getDiameter()/2,2)*Math.PI){
                    //length of main task = length of timber
                    if(mainTask.getLength()==timber.getLength()){
                        //TODO mainTask.getQuantity() <= timber.getAmount ?

                        //quality of main task is not better than best possible quality from timber
                        possibleTimbers.add(timber);


                    }
                }
            }
        }
        return possibleTimbers;
    }

    @Override
    public List<TaskDTO> getPossibleTasks(String direction, TaskDTO mainTask) throws PersistenceLayerException {
        List<TaskDTO> possibleTasks = new ArrayList<>();

        List<Task> tasks = taskDAO.getAllOpenTasks();


        for (Task task : tasks) {
                //compare wood type
                if (task.getWood_type().toLowerCase().equals(mainTask.getWood_type().toLowerCase())) {
                    //length of side task is not bigger than length of main task
                    if (task.getLength() == mainTask.getLength()) {
                        //quality of side task is not better than quality of main task
                        if (task.getQuality().toLowerCase().equals(mainTask.getQuality().toLowerCase())) {
                            //horizontal tasks need the same width as main task
                            if (direction.equals("horizontal") && task.getWidth() == mainTask.getWidth()) {
                                possibleTasks.add(taskConverter.convertPlainObjectToRestDTO(task));
                            } else if (direction.equals("vertical") && task.getId() != mainTask.getId()) {
                                //main task is not allowed as side task
                                possibleTasks.add(taskConverter.convertPlainObjectToRestDTO(task));
                            }
                        }
                    }
                }
        }

        return possibleTasks;
    }

    private void calculateCut(TimberDTO timber) throws OptimisationAlgorithmException {
        LOG.debug("calculation cut for box number: {}", timber.getBox_id());

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
        calculateVerticalSideTasks(0, new ArrayList<>(),currentRadius + (widthMainTask / 2) +SCHNITTFUGE,  1);

        if (verticalSideTask.isEmpty()){
            throw new OptimisationAlgorithmException("Es ist keine passende vertikale Seitenware verfügbar.");
        }


        //horizontal sidetask
        horizontalMaxA = widthMainTask * (currentRadius-(heightMainTask/2))*2;
        calculateHorizontalSideTasks(0, new ArrayList<>(),currentRadius + (heightMainTask / 2) +SCHNITTFUGE,  1);


        double currentWaste =  currentCircleArea - mainTaskArea - maxAreaSideTaskHorizontal - maxAreaSideTaskVertical;

        if (currentWaste < minWaste){
            minWaste = currentWaste;

            optimisationBuffer.setHorizontalSideTaskResult(new ArrayList<>(horizontalSideTask));
            optimisationBuffer.setVerticalSideTaskResult(new ArrayList<>(verticalSideTask));
            optimisationBuffer.setNewMainOrderValues(currentRadius, nachschnittAnzahl, vorschnittAnzahl, widthMainTask, heightMainTask, biggerSize, smallerSize);

            ArrayList<TaskDTO> taskResult = new ArrayList<>();
            taskResult.add(mainTask);

            clearResultAmount(horizontalSideTask);
            clearResultAmount(verticalSideTask);

            for(SideTaskResult sideTaskResult : horizontalSideTask) {
                    sideTaskResult.getTask().setAlgorithmResultAmount(sideTaskResult.getTask().getAlgorithmResultAmount() + sideTaskResult.getCount() * 2);
                    if (!taskResult.contains(sideTaskResult.getTask())) {
                        taskResult.add(sideTaskResult.getTask());
                    }
            }

            for(SideTaskResult sideTaskResult : verticalSideTask) {
                sideTaskResult.getTask().setAlgorithmResultAmount(sideTaskResult.getTask().getAlgorithmResultAmount() + sideTaskResult.getCount()*2);
                if(!taskResult.contains(sideTaskResult.getTask())) {
                    taskResult.add(sideTaskResult.getTask());
                }
            }

            if(horizontalSideTask.contains(mainTask)) {
                int taken_amount = 0;
                for (TaskDTO task : taskResult) {
                    if (task.getId() == mainTask.getId()) {
                        taken_amount += task.getAlgorithmResultAmount();
                    }
                }
                currentTimber.setTaken_amount((int) Math.ceil((double) (mainTask.getQuantity() - mainTask.getProduced_quantity()) / taken_amount));
            } else {
                currentTimber.setTaken_amount((int) Math.ceil((double) (mainTask.getQuantity() - mainTask.getProduced_quantity()) / (nachschnittAnzahl * vorschnittAnzahl)));
            }
            optAlgorithmResult.setTimberResult(currentTimber);

            optAlgorithmResult.setTaskResult(taskResult);
        }

        maxAreaSideTaskHorizontal = Double.MIN_VALUE;
        horizontalMaxA = Double.MIN_VALUE;
        maxAreaSideTaskVertical = Double.MIN_VALUE;
        verticalMaxA = Double.MIN_VALUE;
        verticalSideTask.clear();
        horizontalSideTask.clear();
    }

    private void clearResultAmount(List<SideTaskResult> taskResult) {
        for(SideTaskResult sideTaskResult : taskResult) {
            sideTaskResult.getTask().setAlgorithmResultAmount(0);
        }
    }


    private void calculateRectangleCoordinates(){
        int colorIndex = 0;
        List<RectangleDTO> rectangles = new ArrayList<>();

        //main task
        double x = (optimisationBuffer.getRadius() - (optimisationBuffer.getWidthHauptware() / 2));
        double yCoordinate = optimisationBuffer.getRadius() - (optimisationBuffer.getHeightHauptware() / 2);

        double xCoordinate;
        for (int k = 0; k < optimisationBuffer.getNachschnittAnzahl(); k++){
            xCoordinate = x;

            for (int j = 0; j < optimisationBuffer.getVorschnittAnzahl(); j++){

                rectangles.add(new RectangleDTO(xCoordinate, yCoordinate, "#2D8A41", optimisationBuffer.getSmallerSize(), optimisationBuffer.getBiggerSize()));
                xCoordinate += optimisationBuffer.getBiggerSize() + SCHNITTFUGE;
                mainTask.setAlgorithmResultAmount(mainTask.getAlgorithmResultAmount() + 1);
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
                xCoordinateRight = xCoordinateRight + optimisationBuffer.getVerticalSideTaskResult().get(k-1).getSmallerSize() + SCHNITTFUGE;
                xCoordinateLeft = xCoordinateLeft - SCHNITTFUGE - optimisationBuffer.getVerticalSideTaskResult().get(k).getSmallerSize();
            }


            yCoordinate = optimisationBuffer.getVerticalSideTaskResult().get(k).getY() + (optimisationBuffer.getVerticalSideTaskResult().get(k).getMaxHeight() - optimisationBuffer.getVerticalSideTaskResult().get(k).getHeightSideTask()) / 2;
            //yCoordinate = currentRadius - heightMainTask/2;

        for(int i = 0; i < optimisationBuffer.getVerticalSideTaskResult().get(k).getCount(); i++) {

            rectangles.add(new RectangleDTO(xCoordinateLeft, yCoordinate, optimisationBuffer.getVerticalSideTaskResult().get(k).getColor(), optimisationBuffer.getVerticalSideTaskResult().get(k).getBiggerSize(), optimisationBuffer.getVerticalSideTaskResult().get(k).getSmallerSize()));
            rectangles.add(new RectangleDTO(xCoordinateRight, yCoordinate, optimisationBuffer.getVerticalSideTaskResult().get(k).getColor(), optimisationBuffer.getVerticalSideTaskResult().get(k).getBiggerSize(), optimisationBuffer.getVerticalSideTaskResult().get(k).getSmallerSize()));
            yCoordinate += (optimisationBuffer.getVerticalSideTaskResult().get(k).getBiggerSize() + SCHNITTFUGE);}
        }



        for (int k = 0; k < optimisationBuffer.getHorizontalSideTaskResult().size(); k++) {

            //side task horizontal
            xCoordinate = optimisationBuffer.getHorizontalSideTaskResult().get(k).getX() + (optimisationBuffer.getBiggerSize()-optimisationBuffer.getHorizontalSideTaskResult().get(k).getBiggerSize())/2;
            double yCoordinateBottom = optimisationBuffer.getHorizontalSideTaskResult().get(k).getY();
            //double yCoordinateTop = currentRadius - heightMainTask/2 - SCHNITTFUGE * optimisationBuffer.getNachschnittAnzahl() - SCHNITTFUGE;
            double yCoordinateTop = (optimisationBuffer.getRadius()*2) - optimisationBuffer.getHorizontalSideTaskResult().get(k).getY() -  optimisationBuffer.getHorizontalSideTaskResult().get(k).getSmallerSize();
            //double yCoordinateBottom = optimisationBuffer.getHorizontalSideTaskResult().get(k).getY();

            for(int i = 0; i < optimisationBuffer.getHorizontalSideTaskResult().get(k).getCount(); i++) {

                rectangles.add(new RectangleDTO(xCoordinate, yCoordinateTop,optimisationBuffer.getHorizontalSideTaskResult().get(k).getColor() , optimisationBuffer.getHorizontalSideTaskResult().get(k).getSmallerSize(), optimisationBuffer.getHorizontalSideTaskResult().get(k).getBiggerSize()));
                rectangles.add(new RectangleDTO(xCoordinate, yCoordinateBottom, optimisationBuffer.getHorizontalSideTaskResult().get(k).getColor(), optimisationBuffer.getHorizontalSideTaskResult().get(k).getSmallerSize(), optimisationBuffer.getHorizontalSideTaskResult().get(k).getBiggerSize()));
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

                for (int i = 0; i < possibleSideTasksHorizontal.size(); i++) {
                    TaskDTO sideTaskHorizontal = possibleSideTasksHorizontal.get(i);
                    int horizontalAmount =  calculateHorizontalAmount(sideTaskHorizontal, currY);


                    //x Start-Koordinate für hor-seitenware nicht immer x(links/oben) von hauptware
                    // Math.floor((vorschnittAnzahl-horizontalAmount)/2)*mainTask.getWidth()  : wieviele stiele muss ich nach rechts rücken?
                    double xCoordinateSideTaskHorizontal = (currentRadius - widthMainTask / 2) + (Math.floor((vorschnittAnzahl - horizontalAmount) / 2) * (biggerSize + SCHNITTFUGE));
                    double widthSideTaskHorizontal = horizontalAmount * (sideTaskHorizontal.getWidth() + SCHNITTFUGE) - SCHNITTFUGE;

                    SideTaskResult x = new SideTaskResult(sideTaskHorizontal, horizontalAmount, widthSideTaskHorizontal, sideTaskHorizontal.getSize(), xCoordinateSideTaskHorizontal,
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
        }

        return horizontalCount;
    }

    //horizontal = top and bottom; vertical = left and right
    private void calculateVerticalSideTasks(double currArea, List<SideTaskResult> currSideTask,  double currX, double currVerticalAmount) {
        if (currArea <= verticalMaxA) {
            if (currVerticalAmount > 0 ) {
                if (currArea > maxAreaSideTaskVertical) {
                    maxAreaSideTaskVertical = currArea;
                    verticalSideTask = new ArrayList<>(currSideTask);
                }


                for (int i = 0; i < possibleSideTasksVertical.size(); i++) {
                    TaskDTO sideTaskVertical= possibleSideTasksVertical.get(i);

                    //y^2 - 2*y*radius + (radius^2 - (r*r - (xCoordinate - radius)^2)) = 0
                    //a = 1 (unser y in der quadratischen gleichung ist immer 1)
                    //b = -2*radius
                    //c = radius^2 - (radius^2 - ((xCoordinate - radius)^2)) = (xCoordinate - radius)^2)

                    //y1 = (-b + sqrt(b^2 - 4*a*c))/2*a
                    //y2 = (-b - sqrt(b^2 - 4*a*c))/2*a

                    double a = 1;
                    double b = 2 * currentRadius * -1;
                    double c = pow((currX+sideTaskVertical.getSize() - currentRadius), 2);

                    double y1 = (-b + sqrt(b * b - 4 * a * c)) / 2 * a;
                    double y2 = (-b - sqrt(b * b - 4 * a * c)) / 2 * a;

                    //y1 - y2
                    double maxHeightSideTaskVertical;
                    if (y1 > y2) {
                        maxHeightSideTaskVertical = y1 - y2;
                    } else {
                        maxHeightSideTaskVertical = y2 - y1;
                    }

                    int verticalAmount = (int) floor((maxHeightSideTaskVertical + SCHNITTFUGE) / (sideTaskVertical.getWidth() + SCHNITTFUGE));

                    double heightSideTaskVertical = verticalAmount * (sideTaskVertical.getWidth() + SCHNITTFUGE) - SCHNITTFUGE;



                    //bound
                    //momentane fläche + restplatz * prozentsatz
                    //TODO change U

                    double U = currArea +sideTaskVertical.getSize()*sideTaskVertical.getWidth()*verticalAmount*2 +  (sideTaskVertical.getSize()*sideTaskVertical.getWidth()) * (verticalMaxA - currArea)/(sideTaskVertical.getSize()*sideTaskVertical.getWidth());

                    if (U > maxAreaSideTaskVertical ) {
                        SideTaskResult x = new SideTaskResult(sideTaskVertical, verticalAmount, sideTaskVertical.getSize(), heightSideTaskVertical, currX, y2, sideTaskVertical.getSize(), sideTaskVertical.getWidth(), maxHeightSideTaskVertical, false);

                        currSideTask.add(x);
                        calculateVerticalSideTasks(currArea + (sideTaskVertical.getSize() * sideTaskVertical.getWidth() * 2 * verticalAmount), currSideTask, currX + sideTaskVertical.getSize() + SCHNITTFUGE, verticalAmount);
                        currSideTask.remove(x);
                    }
                }
            }
        }
    }

    private void setColors(){
        int colorIndex = 0;
        HashMap<Integer, String> foundTasks = new HashMap<>();

        List<SideTaskResult> allSideTasks = new ArrayList<>();
        allSideTasks.addAll(optimisationBuffer.getHorizontalSideTaskResult());
        allSideTasks.addAll(optimisationBuffer.getVerticalSideTaskResult());


        for (SideTaskResult task: allSideTasks) {
            int key = task.getTask().getId();

            if (!foundTasks.containsKey(key)) {
                if (key != mainTask.getId()) {

                    switch (colorIndex++ % 9) {
                        case 0:
                            //color = "blue";
                            task.setColor("#2891D9");
                            foundTasks.put(key, "#2891D9");
                            break;
                        case 1:
                            //color = "red";
                            task.setColor("#DE7167");
                            foundTasks.put(key, "#DE7167");
                            break;
                        case 2:
                            //color = "indigo";
                            task.setColor("#9575cd");
                            foundTasks.put(key, "#9575cd");
                            break;
                        case 3:
                            //color = "lime";
                            task.setColor("#afb42b");
                            foundTasks.put(key, "#afb42b");
                            break;
                        case 4:
                            //color = "teal";
                            task.setColor("#00897b");
                            foundTasks.put(key, "#00897b");
                            break;
                        case 5:
                            //color = "cyan";
                            task.setColor("#00acc1");
                            foundTasks.put(key, "#00acc1");
                            break;
                        case 6:
                            //color = "purple";
                            task.setColor("#ba68c8");
                            foundTasks.put(key, "#ba68c8");
                            break;
                        case 7:
                            //color = "yellow";
                            task.setColor("#ffff72");
                            foundTasks.put(key, "#ffff72");
                            break;
                        case 8:
                            //color = "orange";
                            task.setColor("#ffb74d");
                            foundTasks.put(key, "#ffb74d");
                            break;
                        default:
                            break;
                    }

                } else {
                    //color = "green";
                    task.setColor("#2D8A41");
                    foundTasks.put(key, "#2D8A41");
                }
            }else{
                task.setColor(foundTasks.get(key));
            }



        }


    }

    private List<String> convertLumberQualityToTimberQuality(String quality) {
        List<String> temp = new ArrayList<>();
        if(quality.equals("O")) {
            temp.add("A");
        }
        if(quality.equals("I")) {
            temp.add("A");
            temp.add("B");
        }
        if(quality.equals("II")) {
            temp.add("A");
            temp.add("B");
            temp.add("C");
        }
        if(quality.equals("III")) {
            temp.add("B");
            temp.add("C");
            temp.add("CX");
        }
        if(quality.equals("IV")) {
            temp.add("C");
            temp.add("CX");
        }
        if(quality.equals("V")) {
            temp.add("CX");
        }
        if(quality.equals("O/III")) {
            temp.add("A");
            temp.add("B");
            temp.add("C");
            temp.add("CX");
        }
        if(quality.equals("III/IV") || quality.equals("III/V")) {
            temp.add("B");
            temp.add("C");
            temp.add("CX");
        }
        return temp;
    }
}
