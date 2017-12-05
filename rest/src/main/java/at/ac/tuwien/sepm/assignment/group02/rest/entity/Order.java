package at.ac.tuwien.sepm.assignment.group02.rest.entity;

import java.sql.Timestamp;

public class Order {
    private int id;

    private Timestamp orderDate;
    boolean isPaid;

    public Order() {

    }

    public Order(int id, Timestamp orderDate) {
        this.id = id;
        this.orderDate = orderDate;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getID() {
        return this.id;
    }
}
