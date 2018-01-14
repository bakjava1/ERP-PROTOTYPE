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

    //side task vertical (left and right)
    private int verticalCount;
    private double widthSideTaskVertical;
    private double heightSideTaskVertical;

    public OptimisationBuffer(){}

    public void setNewValues(double radius, int nachschnittAnzahl, int vorschnittAnzahl, double widthHauptware,
                             double heightHauptware, double biggerSize, double smallerSize,
                             int horizontalCount, double widthSideTaskHorizontal, double heightSideTaskHorizontal,
                             int verticalCount, double widthSideTaskVertical, double heightSideTaskVertical) {
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

        this.verticalCount = verticalCount;
        this.widthSideTaskVertical = widthSideTaskVertical;
        this.heightSideTaskVertical = heightSideTaskVertical;
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
}
