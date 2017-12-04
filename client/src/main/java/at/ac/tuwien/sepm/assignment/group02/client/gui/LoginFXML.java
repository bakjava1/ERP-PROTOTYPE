package at.ac.tuwien.sepm.assignment.group02.client.gui;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Lumber;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.ResourceAccessException;

import java.lang.invoke.MethodHandles;

import static at.ac.tuwien.sepm.assignment.group02.client.MainApplication.lumberService;

public class LoginFXML {
    public static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FXML
    public TextField tf_helloWorld;

    @FXML
    public Label label_helloWorld;


    @FXML
    public void startHelloWorld() {
        LOG.info("called startHelloWorld");

        String idString = tf_helloWorld.getText().trim();

        int id=0;

        if( idString.toString().matches("[0-9]*") ) {
            id = Integer.parseInt(idString.toString().trim());
        }

        try {
            Lumber lumber = lumberService.getLumber(id);
            LOG.debug("LumberDTO: " + lumber.toString());
            label_helloWorld.setText(lumber.toString());
        } catch (ResourceAccessException e){
            LOG.warn("ResourceAccessException: {}", e.getMessage());
        }
    }

}
