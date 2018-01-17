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

    private SideTaskResult horizontalSideTaskResult;
    private SideTaskResult verticalSideTaskResult;

    public OptimisationBuffer(){}


    public void setNewMainOrderValues(double radius, int nachschnittAnzahl, int vorschnittAnzahl, double widthHauptware,
                             double heightHauptware, double biggerSize, double smallerSize) {
        this.radius = radius;
        this.nachschnittAnzahl = nachschnittAnzahl;
        this.vorschnittAnzahl = vorschnittAnzahl;
        this.widthHauptware = widthHauptware;
        this.heightHauptware = heightHauptware;
        this.biggerSize = biggerSize;
        this.smallerSize = smallerSize;

    }

    public SideTaskResult getHorizontalSideTaskResult() {
        return horizontalSideTaskResult;
    }

    public void setHorizontalSideTaskResult(SideTaskResult horizontalSideTaskResult) {
        this.horizontalSideTaskResult = horizontalSideTaskResult;
    }

    public SideTaskResult getVerticalSideTaskResult() {
        return verticalSideTaskResult;
    }

    public void setVerticalSideTaskResult(SideTaskResult verticalSideTaskResult) {
        this.verticalSideTaskResult = verticalSideTaskResult;
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


}
