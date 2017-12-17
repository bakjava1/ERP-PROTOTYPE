package at.ac.tuwien.sepm.assignment.group02.client.gui;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.service.AssignmentService;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.lang.invoke.MethodHandles;

@Controller
public class CraneOperatorFXML {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private AssignmentService assignmentService;

    @FXML
    public Label crane_label;

    @Autowired
    public CraneOperatorFXML(AssignmentService assignmentService) {
        LOG.debug("called constructor CraneOperatorFXML");
        this.assignmentService = assignmentService;
    }

    @FXML
    public void setDone() {
        LOG.info("setDone button pressed");

        //TODO pick the assignmentDTO from a list of existing assignments
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setId(1);
        assignmentDTO.setBox_id(1);
        assignmentDTO.setAmount(1);

        // create a thread and task to prevent ui from freezing
        new Thread(new Task<Integer>() {

            @Override
            protected Integer call() {
                LOG.debug("setDone thread called");
                try {
                    assignmentService.setDone(assignmentDTO);
                } catch (ServiceLayerException e) {
                    LOG.warn(e.getMessage().trim());
                    AlertBuilder alertBuilder = new AlertBuilder();
                    alertBuilder.showErrorAlert("An Error occured", "Assignment Service", "Not possible to set assignment as done.");
                }
                return 1;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                LOG.debug("set done succeeded with value {}", getValue());

                //TODO update List of Assignments
                crane_label.setText("Success Crane Controller");
            }

            @Override
            protected void failed() {
                super.failed();
                LOG.debug("failed to set assignment done: {}", getException());

                AlertBuilder alertBuilder = new AlertBuilder();
                alertBuilder.showErrorAlert("An Error occured", "Assignment Service", "Not possible to set assignment as done.");

            }

        }, "setDone").start();
    }
}
