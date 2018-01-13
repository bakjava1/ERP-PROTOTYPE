package at.ac.tuwien.sepm.assignment.group02.rest.restDTO;

public class RectangleDTO {

    private double xCoordinate;
    private double yCoordinate;
    private String color;
    private double height;
    private double width;

    public RectangleDTO() {
    }

    public RectangleDTO(double xCoordinate, double yCoordinate, String color, double height, double width) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.color = color;
        this.height = height;
        this.width = width;
    }

    public double getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public double getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }
}
