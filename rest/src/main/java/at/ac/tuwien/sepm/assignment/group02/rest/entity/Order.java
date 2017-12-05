package at.ac.tuwien.sepm.assignment.group02.rest.entity;

import java.sql.Timestamp;

public class Order {

    private int id;
    private Timestamp orderDate;
    private boolean isPaid;

    public Order() {
        this.id = -1;
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

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }
}
