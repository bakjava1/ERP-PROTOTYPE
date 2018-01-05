package at.ac.tuwien.sepm.assignment.group02.server.entity;

import java.util.ArrayList;
import java.util.List;

public class OptimisationResult {

    private Timber box;
    private Lumber mainOrder;
    private List<Lumber> secondaryOrder;
    private List <Rectangle> rectangles;

    public OptimisationResult() {
        this.secondaryOrder = new ArrayList<>();
        this.rectangles = new ArrayList<>();
    }

    public Timber getBox() {
        return box;
    }

    public void setBox(Timber box) {
        this.box = box;
    }

    public Lumber getMainOrder() {
        return mainOrder;
    }

    public void setMainOrder(Lumber mainOrder) {
        this.mainOrder = mainOrder;
    }

    public List<Lumber> getSecondaryOrder() {
        return secondaryOrder;
    }

    public void setSecondaryOrder(List<Lumber> secondaryOrder) {
        this.secondaryOrder = secondaryOrder;
    }

    public List<Rectangle> getRectangles() {
        return rectangles;
    }

    public void setRectangles(List<Rectangle> rectangles) {
        this.rectangles = rectangles;
    }

    public void addRectangle(double xCoordinate,double yCoordinate, String green, double smallerSize, double biggerSize){
        rectangles.add(new Rectangle(xCoordinate, yCoordinate, "green", smallerSize, biggerSize));
    }
}
