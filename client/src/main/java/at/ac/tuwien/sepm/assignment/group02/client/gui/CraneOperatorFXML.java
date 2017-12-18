package at.ac.tuwien.sepm.assignment.group02.client.gui;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.service.AssignmentService;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.lang.invoke.MethodHandles;
import java.util.LinkedList;
import java.util.List;

@Controller
public class CraneOperatorFXML {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private AssignmentService assignmentService;

    @Autowired
    public CraneOperatorFXML(AssignmentService assignmentService) {
        LOG.debug("called constructor CraneOperatorFXML");
        this.assignmentService = assignmentService;
    }

    @FXML
    private TableView<AssignmentDTO> table_assignment;

    @FXML
    private TableColumn col_assignmentNr;

    @FXML
    private TableColumn col_assignmentCreated;

    @FXML
    private TableColumn col_assignmentAmount;

    @FXML
    private TableColumn col_assignmentBoxID;

    @FXML
    public void initialize() {
        table_assignment.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        col_assignmentNr.setCellValueFactory(new PropertyValueFactory("id"));
        col_assignmentCreated.setCellValueFactory(new PropertyValueFactory("creation_date"));
        col_assignmentAmount.setCellValueFactory(new PropertyValueFactory("amount"));
        col_assignmentBoxID.setCellValueFactory(new PropertyValueFactory("boxID"));

        ObservableList<AssignmentDTO> assignments = FXCollections.observableArrayList();
        table_assignment.setItems(assignments);
        updateTable();
    }

    private void updateTable() {
        List<AssignmentDTO> allOpenAssignments = new LinkedList<>();
        try {
            allOpenAssignments = assignmentService.getAllOpenAssignments();
        } catch (ServiceLayerException e) {
            LOG.warn("error while updating assignment table for crane operator");
        }

        ObservableList<AssignmentDTO> assignmentObservableList = FXCollections.observableArrayList();

        for(AssignmentDTO assignment : allOpenAssignments) {
            assignmentObservableList.add(assignment);
        }

        table_assignment.setItems(assignmentObservableList);
        table_assignment.refresh();
    }

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

                updateTable();
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
