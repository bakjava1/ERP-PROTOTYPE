package at.ac.tuwien.sepm.assignment.group02.client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LeadWorkerFXML {

    @FXML
    public Label lead_label;

    @FXML
    public void testLeadController() {
        lead_label.setText("Success Lead Controller");
    }
}
