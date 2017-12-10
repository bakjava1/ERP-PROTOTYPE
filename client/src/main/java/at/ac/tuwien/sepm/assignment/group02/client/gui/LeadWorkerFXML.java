package at.ac.tuwien.sepm.assignment.group02.client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
public class LeadWorkerFXML {

    @FXML
    public Label lead_label;

    @FXML
    public void testLeadController() {
        lead_label.setText("Success Lead Controller");
    }
}
