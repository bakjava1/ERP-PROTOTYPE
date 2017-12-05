package at.ac.tuwien.sepm.assignment.group02.rest.restDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDTO {

    private int id;
    private Timestamp orderDate;
    private boolean isPaid;

    public OrderDTO() {
        this.id = -1;
    }

    public OrderDTO(int id, Timestamp orderDate) {
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
