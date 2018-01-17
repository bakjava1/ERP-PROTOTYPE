package at.ac.tuwien.sepm.assignment.group02.server.entity;

public class SideTaskResult {



    //side task horizontal (top and bottom)
    private int Count;
    private double area;
    private double widthSideTask;
    private double heightSideTask;
    private double x;
    private double y;
    private double smallerSize;
    private double biggerSize;
    private double maxHeight;
    private boolean isEmpty;



    public SideTaskResult(){}

    public SideTaskResult(SideTaskResult other) {
        Count = other.Count;
        this.area = other.area;
        this.widthSideTask = other.widthSideTask;
        this.heightSideTask = other.heightSideTask;
        this.x = other.x;
        this.y = other.y;
        this.smallerSize = other.smallerSize;
        this.biggerSize = other.biggerSize;
        this.maxHeight = other.maxHeight;
        this.isEmpty = other.isEmpty;
    }

    public void setNewResult(int count, double widthSideTask, double heightSideTask, double x, double y, double smallerSize, double biggerSize, double maxHeight, boolean isEmpty) {
        Count = count;
        this.widthSideTask = widthSideTask;
        this.heightSideTask = heightSideTask;
        this.x = x;
        this.y = y;
        this.smallerSize = smallerSize;
        this.biggerSize = biggerSize;
        this.maxHeight = maxHeight;
        this.isEmpty = isEmpty;
    }


    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }



    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getWidthSideTask() {
        return widthSideTask;
    }

    public void setWidthSideTask(double widthSideTask) {
        this.widthSideTask = widthSideTask;
    }

    public double getHeightSideTask() {
        return heightSideTask;
    }

    public void setHeightSideTask(double heightSideTask) {
        this.heightSideTask = heightSideTask;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getSmallerSize() {
        return smallerSize;
    }

    public void setSmallerSize(double smallerSize) {
        this.smallerSize = smallerSize;
    }

    public double getBiggerSize() {
        return biggerSize;
    }

    public void setBiggerSize(double biggerSize) {
        this.biggerSize = biggerSize;
    }

    public void clear(){
        Count = 0;
        area = 0;
        widthSideTask = 0;
        heightSideTask = 0;
        x = 0;
        y = 0;
        smallerSize = 0;
        biggerSize = 0;
        isEmpty = true;
    }

    public double getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(double maxHeight) {
        this.maxHeight = maxHeight;
    }

}
