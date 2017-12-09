package at.ac.tuwien.sepm.assignment.group02.rest.entity;

import java.sql.Timestamp;
import java.util.List;

public class Order {

    private int id;
    private String customerName;
    private String customerAddress;
    private String customerUID;
    private Timestamp orderDate;
    private boolean isPaid;
    private List<Task> taskList;

    public Order() {
        this.id = -1;
        this.orderDate = null;
        this.isPaid = false;
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


    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
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

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", customerUID='" + customerUID + '\'' +
                ", orderDate=" + orderDate +
                ", isPaid=" + isPaid +
                '}';
    }
}
