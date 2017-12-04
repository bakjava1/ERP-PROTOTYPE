package at.ac.tuwien.sepm.assignment.group02.client.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
    private int id;

    private Timestamp orderDate;
    boolean isPaid;

    public Order() {

    }

    public Order(int id, int taskNo, Timestamp orderDate) {
        this.id = id;
        this.orderDate = orderDate;
    }
}
