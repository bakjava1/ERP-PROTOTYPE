package at.ac.tuwien.sepm.assignment.group02.client.gui;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.service.AssignmentService;
import at.ac.tuwien.sepm.assignment.group02.client.util.AlertBuilder;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.lang.invoke.MethodHandles;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Controller
public class CraneOperatorFXML {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private AssignmentService assignmentService;

    private AssignmentDTO currentAssignment;

    @Autowired
    private AlertBuilder alertBuilder;

    @Autowired
    public CraneOperatorFXML(AssignmentService assignmentService) {
        LOG.debug("called constructor CraneOperatorFXML");
        this.assignmentService = assignmentService;
    }

    @FXML
    private TableView<AssignmentDTO> table_open_assignment;

    @FXML
    private TableColumn<AssignmentDTO,Integer> col_open_assignmentNr;

    @FXML
    private TableColumn<AssignmentDTO,String> col_open_assignmentCreated;

    @FXML
    private TableColumn<AssignmentDTO,Integer> col_open_assignmentAmount;

    @FXML
    private TableColumn<AssignmentDTO,Integer> col_open_assignmentBoxID;

    @FXML
    private TableView<AssignmentDTO> table_done_assignment;

    @FXML
    private TableColumn<AssignmentDTO,Integer> col_done_assignmentNr;

    @FXML
    private TableColumn<AssignmentDTO,String> col_done_assignmentCreated;

    @FXML
    private TableColumn<AssignmentDTO,Integer> col_done_assignmentAmount;

    @FXML
    private TableColumn<AssignmentDTO,Integer> col_done_assignmentBoxID;

    @FXML
    private Label currentAssignment_amount;
    @FXML
    private Label currentAssignment_box;
    @FXML
    private Label label_date;
    @FXML
    private Button btn_done;
    @FXML
    private Button btn_inProgress;
    @FXML
    private Button btn_inProgressAbort;

    @FXML
    public void initialize() {
        btn_done.setVisible(false);
        btn_inProgressAbort.setVisible(false);
        DateFormat dateFormat = new SimpleDateFormat("DD.MM.YY");
        Date date = new Date();

        label_date.setText(dateFormat.format(date));
        initializeAssignmentTable();
        updateAssignmentTable();

        deleteYesterdaysAssignments();
    }

    private void deleteYesterdaysAssignments(){
        try {
            assignmentService.cleanUpAssignments();
        } catch (ServiceLayerException e) {
            LOG.warn(e.getMessage());
        }
        updateAssignmentTable();
    }

    private void initializeAssignmentTable() {
        table_open_assignment.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        col_open_assignmentNr.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_open_assignmentCreated.setCellValueFactory(new PropertyValueFactory<>("creation_time"));
        col_open_assignmentAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        col_open_assignmentBoxID.setCellValueFactory(new PropertyValueFactory<>("box_id"));

        ObservableList<AssignmentDTO> assignments = FXCollections.observableArrayList();
        table_open_assignment.setItems(assignments);

        col_done_assignmentNr.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_done_assignmentCreated.setCellValueFactory(new PropertyValueFactory<>("creation_time"));
        col_done_assignmentAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        col_done_assignmentBoxID.setCellValueFactory(new PropertyValueFactory<>("box_id"));

        ObservableList<AssignmentDTO> assignments_done = FXCollections.observableArrayList();
        table_done_assignment.setItems(assignments_done);

        table_done_assignment.getSelectionModel().setSelectionMode(null);
        //table_done_assignment.setDisable(true);

        Task<Integer> task = new Task<>() {
            @Override
            protected Integer call() throws Exception {
                while(true){
                    if(isCancelled()) break;
                    Thread.sleep(5000);
                    int selected_index1 = table_open_assignment.getSelectionModel().getSelectedIndex();
                    int selected_index2 = table_done_assignment.getSelectionModel().getSelectedIndex();
                    updateAssignmentTable();
                    table_open_assignment.getSelectionModel().select(selected_index1);
                    table_done_assignment.getSelectionModel().select(selected_index2);
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
        List<AssignmentDTO> allDoneAssignments = new LinkedList<>();
        try {
            allOpenAssignments = assignmentService.getAllOpenAssignments();
            allDoneAssignments = assignmentService.getAllClosedAssignments();
        } catch (ServiceLayerException e) {
            LOG.warn("error while updating assignment table for crane operator");
            alertBuilder.showErrorAlert("Aufgaben-Service", null,
                    "Tabelle konnte nicht aktualisiert werden. "+ e.getMessage());
        }

        table_open_assignment.setItems(FXCollections.observableArrayList(allOpenAssignments));
        table_done_assignment.setItems(FXCollections.observableArrayList(allDoneAssignments));

        // set row factory in order to create context menu and set row color
        table_done_assignment.setRowFactory(
                new Callback<>() {
                    @Override
                    public TableRow<AssignmentDTO> call(TableView<AssignmentDTO> table_open_assignment) {

                        final TableRow<AssignmentDTO> row = new TableRow<>() {
                            @Override
                            protected void updateItem(AssignmentDTO assignmentDTO, boolean empty){
                                super.updateItem(assignmentDTO, empty);

                                if (assignmentDTO == null) {
                                    setStyle("");
                                } else if (assignmentDTO.isDone()) {
                                    setStyle("-fx-background-color:#b0eeb0;");
                                    if(isSelected()) setStyle("-fx-background-color: lightslategray;");
                                }

                            }
                        };
                        LOG.trace(row.getId());
                        return row;
                    }
                });

        table_open_assignment.refresh();
        table_done_assignment.refresh();
    }

    @FXML
    public void setInProgressButtonPressed() {
        LOG.info("setInProgressButtonPressed button pressed");
        AssignmentDTO assignmentDTO = this.currentAssignment;

        if (assignmentDTO == null){
            if (table_open_assignment.getSelectionModel().getSelectedItem() == null) {
                alertBuilder.showInformationAlert("Aufgaben-Service", null, "Bitte wählen Sie eine Aufgabe aus!");
                return;
            } else {
                assignmentDTO = table_open_assignment.getSelectionModel().getSelectedItem();
                table_open_assignment.getSelectionModel().clearSelection();
            }
        } else {
            return;
        }

        this.currentAssignment = assignmentDTO;

        currentAssignment_amount.setText("Anzahl: "+this.currentAssignment.getAmount()+"");
        currentAssignment_box.setText("Box Nr: "+this.currentAssignment.getBox_id());
        table_open_assignment.getSelectionModel().clearSelection();
        btn_done.setVisible(true);
        btn_inProgress.setVisible(false);
        btn_inProgressAbort.setVisible(true);
    }

    @FXML
    public void abortInProgressButtonPressed(){
        this.currentAssignment = null;
        btn_done.setVisible(false);
        btn_inProgress.setVisible(true);
        btn_inProgressAbort.setVisible(false);
        currentAssignment_amount.setText("");
        currentAssignment_box.setText("");
    }

    @FXML
    public void setDoneButtonPressed() {
        LOG.info("setDone button pressed");
        AssignmentDTO assignmentDTO = this.currentAssignment;

        // get the selected assignmentDTO from the table
        if (assignmentDTO == null) {
            alertBuilder.showInformationAlert("Aufgaben-Service", null, "Keine Aufgabe in Arbeit!");
            return;
        }

        setDone(assignmentDTO);
        this.currentAssignment = null;
    }

    private void setDone(AssignmentDTO assignmentDTO){

        if(assignmentDTO.isDone()){
            alertBuilder.showInformationAlert("Aufgaben-Service", null, "Diese Aufgabe ist bereits abgeschlossen.");
            return;
        }

        boolean confirmed = alertBuilder.showConfirmationAlert("Aufgaben-Service", null, "Möchten Sie die Aufgabe wirklich abschließen?");
        if(confirmed) {

            // create a thread and task to prevent ui from freezing
            Task task = new Task<Integer>() {

                @Override
                protected Integer call() throws Exception {
                    LOG.debug("set-done thread called");
                    assignmentService.setDone(assignmentDTO);
                    return 1;
                }

                @Override
                protected void succeeded() {
                    super.succeeded();
                    LOG.debug("set-done succeeded with value {}", getValue());
                    alertBuilder.showInformationAlert("Aufgaben-Service", null, "Aufgabe " + assignmentDTO.getId() + " wurde als erledigt markiert.");
                }

                @Override
                protected void failed() {
                    super.failed();
                    LOG.warn("set-done failed with exception: "+getException().getMessage().trim());
                    alertBuilder.showErrorAlert("Aufgaben-Service", null, "Es gab ein Problem beim Abschließen der Aufgabe. "+ getException().getMessage());
                }

            };

            Thread t = new Thread(task,"set-done");
            t.start();

            updateAssignmentTable();
            btn_done.setVisible(false);
            btn_inProgress.setVisible(true);
            btn_inProgressAbort.setVisible(false);
            currentAssignment_amount.setText("");
            currentAssignment_box.setText("");
        }
    }

}
