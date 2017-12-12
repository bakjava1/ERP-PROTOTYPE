package at.ac.tuwien.sepm.assignment.group02.rest.entity;

public class Task {
    private int id;
    private int orderID;
    private String description;
    private String finishing;
    private String wood_type;
    private String quality;
    private int size;
    private int width;
    private int length;
    private int quantity;

    public Task() {this.id = -1;}

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getOrderID() {
        return this.orderID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFinishing(String finishing) {
        this.finishing = finishing;
    }

    public void setWood_type(String wood_type) {
        this.wood_type = wood_type;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return this.description;
    }

    public String getFinishing() {
        return finishing;
    }

    public String getWood_type() {
        return wood_type;
    }

    public String getQuality() {
        return quality;
    }

    public int getLength() {
        return length;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSize() {
        return size;
    }

    public int getWidth() {
        return width;
    }
}
