package at.ac.tuwien.sepm.assignment.group02.client.gui;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.service.AssignmentService;
import at.ac.tuwien.sepm.assignment.group02.client.util.AlertBuilder;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
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
    private TableColumn col_assignmentDone;

    @FXML
    public void initialize() {

        initializeAssignmentTable();
        updateAssignmentTable();
    }

    private void initializeAssignmentTable() {
        table_assignment.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        col_assignmentNr.setCellValueFactory(new PropertyValueFactory("id"));
        col_assignmentCreated.setCellValueFactory(new PropertyValueFactory("creation_date"));
        col_assignmentAmount.setCellValueFactory(new PropertyValueFactory("amount"));
        col_assignmentBoxID.setCellValueFactory(new PropertyValueFactory("box_id"));
        col_assignmentDone.setCellValueFactory(new PropertyValueFactory("isDone"));

        ObservableList<AssignmentDTO> assignments = FXCollections.observableArrayList();
        table_assignment.setItems(assignments);

        Task<Integer> task = new Task<>() {
            @Override
            protected Integer call() throws Exception {
                while(true){
                    if(isCancelled()) break;
                    Thread.sleep(5000);
                    int selected_index = table_assignment.getSelectionModel().getSelectedIndex();
                    updateAssignmentTable();
                    table_assignment.getSelectionModel().select(selected_index);
                }
                return 1;
            }
        };

        //start the auto-refresh task
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

    private void updateAssignmentTable() {
        List<AssignmentDTO> allOpenAssignments = new LinkedList<>();
        try {
            allOpenAssignments = assignmentService.getAllAssignments();
        } catch (ServiceLayerException e) {
            LOG.warn("error while updating assignment table for crane operator");
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.showErrorAlert("Fehlermeldung", "Aufgaben-Service",
                    "Tabelle konnte nicht aktualisiert werden.");
        }

        ObservableList<AssignmentDTO> assignmentObservableList = FXCollections.observableArrayList();

        for(AssignmentDTO assignment : allOpenAssignments) {
            assignmentObservableList.add(assignment);
        }

        table_assignment.setItems(assignmentObservableList);

        // set row factory in order to create the context menu and set row color
        table_assignment.setRowFactory(
                new Callback<TableView<AssignmentDTO>, TableRow<AssignmentDTO>>() {
                    @Override
                    public TableRow<AssignmentDTO> call(TableView<AssignmentDTO> tableView) {

                        final TableRow<AssignmentDTO> row = new TableRow<>() {
                            @Override
                            protected void updateItem(AssignmentDTO assignmentDTO, boolean empty){
                                super.updateItem(assignmentDTO, empty);

                                if (assignmentDTO == null) {
                                    setStyle("");
                                } else if (assignmentDTO.isDone()) {
                                    setStyle("-fx-background-color: green;");
                                } else {
                                    setStyle("-fx-background-color: orange;");
                                }

                            }
                        };
                        return row;
                    }
                });

        table_assignment.refresh();
    }

    public void setDone() {
        LOG.info("setDone button pressed");

        // get the selected assignmentDTO from the table
        if(table_assignment.getSelectionModel().getSelectedItem() == null) {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.showInformationAlert("Information", "Aufgabe ", "Bitte wählen Sie eine Aufgabe aus!");
            return;
        }

        AssignmentDTO assignmentDTO = table_assignment.getSelectionModel().getSelectedItem();

        if(assignmentDTO.isDone()){
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.showInformationAlert("Information", "Aufgabe abschließen", "Diese Aufgabe ist bereits abgeschlossen.");
            return;
        }

        AlertBuilder alertBuilder = new AlertBuilder();
        boolean confirmed = alertBuilder.showConfirmationAlert("Aufgabe abschließen", null, "Möchten Sie die Aufgabe wirklich abschließen?");
        if(confirmed) {

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
                        alertBuilder.showErrorAlert("Fehlermeldung", "Aufgaben-Service", "Die Aufgabe konnte nicht als erledigt markiert werden.");
                    }
                    return 1;
                }

                @Override
                protected void succeeded() {
                    super.succeeded();
                    LOG.debug("set done succeeded with value {}", getValue());
                    table_assignment.getSelectionModel().clearSelection();

                    AlertBuilder alertBuilder = new AlertBuilder();
                    alertBuilder.showInformationAlert("Information", "Aufgabe abgeschlossen", "Aufgabe " + assignmentDTO.getId() + " wurde als erledigt markiert.");

                    updateAssignmentTable();
                }

                @Override
                protected void failed() {
                    super.failed();
                    LOG.debug("failed to set assignment done: {}", getException());

                    AlertBuilder alertBuilder = new AlertBuilder();
                    alertBuilder.showErrorAlert("Fehlermeldung", "Aufgaben-Service", "Die Aufgabe konnte nicht als erledigt markiert werden.");

                }

            }, "setDone").start();
        }
    }

}
