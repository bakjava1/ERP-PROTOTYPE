package at.ac.tuwien.sepm.assignment.group02.rest.restDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDTO {

    private int id;
    private Timestamp orderDate;
    private String customerName;
    private String customerAddress;
    private String customerUID;
    private boolean isPaid;
    private List<TaskDTO> taskList;

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

    public List<TaskDTO> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<TaskDTO> taskList) {
        this.taskList = taskList;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerUID() {
        return customerUID;
    }

    public void setCustomerUID(String customerUID) {
        this.customerUID = customerUID;
    }
}
