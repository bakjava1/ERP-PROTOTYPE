package at.ac.tuwien.sepm.assignment.group02.client.gui;

import at.ac.tuwien.sepm.assignment.group02.client.dao.Lumber;
import javafx.fxml.FXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.ResourceAccessException;

import java.lang.invoke.MethodHandles;

import static at.ac.tuwien.sepm.assignment.group02.client.MainApplication.resourceController;

public class LoginFXML {
    public static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FXML
    public void startHelloWorld() {
        LOG.info("called startHelloWorld");

        try {

            Lumber lumber = resourceController.getLumber(1);
            LOG.debug("ID: " + lumber.getId());
            LOG.debug("Name: " + lumber.getContent());

        } catch (ResourceAccessException e){
            LOG.warn("ResourceAccessException: {}", e.getMessage());
        }
    }

}
