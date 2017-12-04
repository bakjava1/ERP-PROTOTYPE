package at.ac.tuwien.sepm.assignment.group02.rest.restDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDTO {
    private int id;

    private Timestamp orderDate;
    boolean isPaid;

    public OrderDTO() {

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
}
