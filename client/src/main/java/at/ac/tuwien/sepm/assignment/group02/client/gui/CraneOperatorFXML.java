package at.ac.tuwien.sepm.assignment.group02.client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
public class CraneOperatorFXML {

    @FXML
    public Label crane_label;

    @FXML
    public void testCraneController() {
        crane_label.setText("Success Crane Controller");
    }
}
