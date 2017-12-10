package at.ac.tuwien.sepm.assignment.group02.rest.entity;

public class Task {
    private int id;
    private int orderID;

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
}
