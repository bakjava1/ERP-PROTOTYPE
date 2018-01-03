package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.server.entity.*;
import java.util.ArrayList;
import java.util.List;

public class OptimisationAlgorithmServiceImpl {

    private static double schnittfuge = 4.2; //in mm TODO: in properties file or input?
    private static int maxStieleVorschnitt = 2; //in mm TODO: in properties file or input?

    private List<Timber> boxes;
    private Lumber mainOrder;
    private List<Lumber> secondaryOrder;
    private OptimisationBuffer buffer;


    private OptimisationResult r;


    public OptimisationAlgorithmServiceImpl(List<Timber> boxes, List<Lumber> secondaryOrder, Lumber mainOrder) {
        this.boxes = boxes;
        this.buffer = new OptimisationBuffer();
        this.secondaryOrder = secondaryOrder;
        this.mainOrder = mainOrder;
    }


    public void calculateCut() throws Exception{

        this.r = new OptimisationResult();

        if (mainOrder != null) {
            r.setMainOrder(mainOrder);

        }else{
            throw new Exception("Kein Hauptauftrag ausgewählt."); //TODO: passende Exception erstellen

        }

        double maxMainOrderWasteRelation = 0;

        for (int i = 0; i < boxes.size(); i++) {


            Timber currentBox = boxes.get(i);
            double minDiameter = currentBox.getDiameter();
            double radius = minDiameter / 2;

            double a = minDiameter / Math.sqrt(2); //Seitenlänge des Quadrates
            double height = mainOrder.getSize();
            double width = mainOrder.getWidth();

            double biggerSize = (width >= height ? width : height);
            double smallerSize = (width == biggerSize ? height : width);


            int nachschnittAnzahl = (int) Math.floor((a+schnittfuge)/(smallerSize+schnittfuge)); //abrunden
            int vorschnittAnzahl = (int) Math.floor((a+schnittfuge)/(biggerSize+schnittfuge));

            //check if there is enough space for the main order
            if (nachschnittAnzahl == 0 || vorschnittAnzahl == 0) {
                continue;
            }

            //max. n-stielig
            if (vorschnittAnzahl > maxStieleVorschnitt){
                vorschnittAnzahl = maxStieleVorschnitt;
            }


            double widthHauptware = (vorschnittAnzahl * (biggerSize + schnittfuge)) - schnittfuge;
            double heightHauptware = (nachschnittAnzahl * (smallerSize + schnittfuge)) - schnittfuge;



            //TODO: find best secondary Order
            for (Lumber currentOrder: secondaryOrder) {

            }




            double currentMainOrderA = vorschnittAnzahl*nachschnittAnzahl*smallerSize*biggerSize;
            double currentSecondaryOrderA = 0; // TODO: Fläche der Seitenware berechnen
            double currentCircleA = (Math.pow(radius,2)*Math.PI);
            double currentWasteA = currentCircleA - (currentMainOrderA+currentSecondaryOrderA);

            double currentMainOrderProportion = 100/currentCircleA*currentMainOrderA;
            double currentWasteProportion = 100/currentCircleA*currentWasteA;

            double currentMainOrderWastRelation = currentMainOrderProportion/currentWasteProportion;


            if (maxMainOrderWasteRelation <= currentMainOrderWastRelation){
                maxMainOrderWasteRelation = currentMainOrderWastRelation;

                r.setBox(currentBox);
               
                this.buffer.setNewValues(radius, nachschnittAnzahl, vorschnittAnzahl, widthHauptware, heightHauptware, biggerSize, smallerSize);

            }



        }

        if (r.getBox() != null){

            calculateMainOrderCoordinates();


        }else{
            throw new Exception("Keine geeignete Box gefunden."); //TODO: passende Exception erstellen
        }


    }


    private void calculateMainOrderCoordinates() throws Exception {

        double x = (buffer.getRadius() - (buffer.getWidthHauptware() / 2));
        double yCoordinate = buffer.getRadius() - (buffer.getHeightHauptware() / 2);

        List<Rectangle> rectangles = new ArrayList<>();

        for (int k = 0; k < buffer.getNachschnittAnzahl(); k++){
            double xCoordinate = x;

            for (int j = 0;j < buffer.getVorschnittAnzahl();j++){

                rectangles.add(new Rectangle(xCoordinate, yCoordinate, "green", buffer.getSmallerSize(), buffer.getBiggerSize()));
                xCoordinate += buffer.getBiggerSize() + schnittfuge;


            }

            yCoordinate += (buffer.getSmallerSize() + schnittfuge);

        }

        r.setRectangles(rectangles);

    }


    private void calculateSecondaryOrderCoordinates() throws Exception{
        //TODO: implement this.
    }


    public OptimisationResult getResult(){
        return this.r;
    }


}