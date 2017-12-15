package at.ac.tuwien.sepm.assignment.group02.client.gui;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.service.AssignmentService;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Assignment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.lang.invoke.MethodHandles;
import java.util.LinkedList;
import java.util.List;

@Controller
public class CraneOperatorFXML {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FXML
    private TableView<Assignment> table_assignment;

    @FXML
    private TableColumn col_assignmentNr;

    @FXML
    private TableColumn col_assignmentCreated;

    @FXML
    private TableColumn col_assignmentAmount;

    @FXML
    private TableColumn col_assignmentBoxID;


    private AssignmentService assignmentService;

    @Autowired
    public CraneOperatorFXML(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @FXML
    public void initialize() {
        table_assignment.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        col_assignmentNr.setCellValueFactory(new PropertyValueFactory("id"));
        col_assignmentCreated.setCellValueFactory(new PropertyValueFactory("creation_date"));
        col_assignmentAmount.setCellValueFactory(new PropertyValueFactory("amount"));
        col_assignmentBoxID.setCellValueFactory(new PropertyValueFactory("boxID"));

        ObservableList<Assignment> assignments = FXCollections.observableArrayList();
        table_assignment.setItems(assignments);
        updateTable();
    }

    private void updateTable() {
        List<Assignment> allOpenAssignments = new LinkedList<>();
        try {
            allOpenAssignments = assignmentService.getAllOpenAssignments();
        } catch (ServiceLayerException e) {
            LOG.warn("error while updating assignment table for crane operator");
        }

        ObservableList<Assignment> assignmentObservableList = FXCollections.observableArrayList();

        for(Assignment assignment : allOpenAssignments) {
            assignmentObservableList.add(assignment);
        }

        table_assignment.setItems(assignmentObservableList);
        table_assignment.refresh();
    }

}
