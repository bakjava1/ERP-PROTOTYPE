package at.ac.tuwien.sepm.assignment.group02.client.gui;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import static at.ac.tuwien.sepm.assignment.group02.client.MainApplication.orderService;

public class OfficeFXML {

    @FXML
    public Label office_label;

    @FXML
    public void testOfficeController(ActionEvent actionEvent) {
        office_label.setText("Success Office Controller");
    }

    public void createOrder(ActionEvent actionEvent) {
        Order newOrder = new Order();
        orderService.addOrder(newOrder,null);
    }
}
