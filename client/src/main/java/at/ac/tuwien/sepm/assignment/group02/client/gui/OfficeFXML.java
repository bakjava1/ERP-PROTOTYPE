package at.ac.tuwien.sepm.assignment.group02.client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OfficeFXML {

    @FXML
    public Label office_label;

    @FXML
    public void testOfficeController() {
        office_label.setText("Success Office Controller");
    }
}
