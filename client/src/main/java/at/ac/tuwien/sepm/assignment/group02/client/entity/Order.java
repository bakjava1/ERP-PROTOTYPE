package at.ac.tuwien.sepm.assignment.group02.client.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

    private int id;
    private int invoiceNumber;
    private String invoiceDate;
    private String deliveryDate;
    private String customerName;
    private String customerAddress;
    private String customerUID;
    private String orderDate;
    private boolean isPaid;
    private List<Task> taskList;

    //prices in cent
    private int grossAmount;


    private int netAmount;
    private int taxAmount;

    public Order() {
        this.id = 0;
        this.orderDate = null;
        this.isPaid = false;
        this.taskList = new ArrayList<>();
    }

    public Order(int ID) {
        this.id = ID;
    }

    public void addTask(Task toAdd) { taskList.add(toAdd); }

    public void setID(int id) {
        this.id = id;
    }

    public int getID() {
        return this.id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
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

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public int getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(int grossAmount) {
        this.grossAmount = grossAmount;
    }

    public int getNetAmount() {
        return netAmount;
    }


    public void setNetAmount(int netAmount){
        this.netAmount=netAmount;
    }


    public int getTaxAmount() {

        return taxAmount;
    }

    public void setTaxAmount(int taxAmount) {
        this.taxAmount = taxAmount;
    }

    public int getQuantity() {
        int quantity = 0;

        for (Task task: taskList) {
            quantity+= task.getQuantity();
        }

        return quantity;
    }

    public int getTaskAmount() {
        if (taskList != null){
            return taskList.size();
        }

        return 0;
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
