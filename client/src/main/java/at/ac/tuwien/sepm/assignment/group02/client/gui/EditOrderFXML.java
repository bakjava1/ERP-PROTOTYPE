package at.ac.tuwien.sepm.assignment.group02.client.gui;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Order;
import javafx.event.ActionEvent;

import static at.ac.tuwien.sepm.assignment.group02.client.MainApplication.orderService;
import static at.ac.tuwien.sepm.assignment.group02.client.MainApplication.timberService;
import static at.ac.tuwien.sepm.assignment.group02.client.MainApplication.taskService;

public class EditOrderFXML {
    private Order selectedOrder;

    public void setSelectedOrder(Order selectedOrder) {
        this.selectedOrder = selectedOrder;
    }

    public void closeBtnClicked(ActionEvent actionEvent) {
    }

    public void saveBtnClicked(ActionEvent actionEvent) {
    }
}
