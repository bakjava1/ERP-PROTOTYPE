package at.ac.tuwien.sepm.assignment.group02.server.entity;

public class OptimisationBuffer {

    private double radius;

    //main task
    private int nachschnittAnzahl;
    private int vorschnittAnzahl;
    private double widthHauptware;
    private double heightHauptware;
    private double biggerSize;
    private double smallerSize;

    //side task horizontal (top and bottom)
    private int horizontalCount;
    private double widthSideTaskHorizontal;
    private double heightSideTaskHorizontal;
    private double xHorizontal;
    private double yHorizontal;
    private double smallerSizeHorizontal;
    private double biggerSizeHorizontal;

    //side task vertical (left and right)
    private int verticalCount;
    private double widthSideTaskVertical;
    private double heightSideTaskVertical;
    private double xVertical;
    private double yVertical;
    private double smallerSizeVertical;
    private double biggerSizeVertical;
    private double maxHeightSideTaskVertical;

    public OptimisationBuffer(){}

    public void setNewValues(double radius, int nachschnittAnzahl, int vorschnittAnzahl, double widthHauptware,
                             double heightHauptware, double biggerSize, double smallerSize,
                             int horizontalCount, double widthSideTaskHorizontal, double heightSideTaskHorizontal, double xHorizontal, double yHorizontal, double smallerSizeHorizontal, double biggerSizeHorizontal,
                             int verticalCount, double widthSideTaskVertical, double heightSideTaskVertical, double xVertical, double yVertical, double smallerSizeVertical, double biggerSizeVertical, double maxHeightSideTaskVertical) {
        this.radius = radius;
        this.nachschnittAnzahl = nachschnittAnzahl;
        this.vorschnittAnzahl = vorschnittAnzahl;
        this.widthHauptware = widthHauptware;
        this.heightHauptware = heightHauptware;
        this.biggerSize = biggerSize;
        this.smallerSize = smallerSize;

        this.horizontalCount = horizontalCount;
        this.widthSideTaskHorizontal = widthSideTaskHorizontal;
        this.heightSideTaskHorizontal = heightSideTaskHorizontal;
        this.xHorizontal = xHorizontal;
        this.yHorizontal = yHorizontal;
        this.smallerSizeHorizontal = smallerSizeHorizontal;
        this.biggerSizeHorizontal = biggerSizeHorizontal;

        this.verticalCount = verticalCount;
        this.widthSideTaskVertical = widthSideTaskVertical;
        this.heightSideTaskVertical = heightSideTaskVertical;
        this.xVertical = xVertical;
        this.yVertical = yVertical;
        this.smallerSizeVertical = smallerSizeVertical;
        this.biggerSizeVertical = biggerSizeVertical;
        this.maxHeightSideTaskVertical = maxHeightSideTaskVertical;
    }

    //TODO delete; for testing purpose only
    public void setNewValues(double radius, int nachschnittAnzahl, int vorschnittAnzahl, double widthHauptware,
                             double heightHauptware, double biggerSize, double smallerSize) {
        this.radius = radius;
        this.nachschnittAnzahl = nachschnittAnzahl;
        this.vorschnittAnzahl = vorschnittAnzahl;
        this.widthHauptware = widthHauptware;
        this.heightHauptware = heightHauptware;
        this.biggerSize = biggerSize;
        this.smallerSize = smallerSize;
    }


    public double getRadius() {
        return radius;
    }

    public int getNachschnittAnzahl() {
        return nachschnittAnzahl;
    }

    public int getVorschnittAnzahl() {
        return vorschnittAnzahl;
    }

    public double getWidthHauptware() {
        return widthHauptware;
    }

    public double getHeightHauptware() {
        return heightHauptware;
    }

    public double getBiggerSize() {
        return biggerSize;
    }

    public double getSmallerSize() {
        return smallerSize;
    }

    public int getHorizontalCount() {
        return horizontalCount;
    }

    public double getWidthSideTaskHorizontal() { return widthSideTaskHorizontal; }

    public double getHeightSideTaskHorizontal() { return heightSideTaskHorizontal; }

    public int getVerticalCount() { return verticalCount; }

    public double getWidthSideTaskVertical() { return widthSideTaskVertical; }

    public double getHeightSideTaskVertical() { return heightSideTaskVertical; }

    public double getxHorizontal() {
        return xHorizontal;
    }

    public double getyHorizontal() { return yHorizontal; }

    public double getxVertical() { return xVertical; }

    public double getyVertical() { return yVertical; }

    public double getSmallerSizeHorizontal() { return smallerSizeHorizontal; }

    public double getSmallerSizeVertical() { return smallerSizeVertical; }

    public double getBiggerSizeVertical() { return biggerSizeVertical; }

    public double getBiggerSizeHorizontal() { return biggerSizeHorizontal; }

    public double getMaxHeightSideTaskVertical() { return maxHeightSideTaskVertical; }
}
