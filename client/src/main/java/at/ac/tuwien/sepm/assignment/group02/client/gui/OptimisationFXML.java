package at.ac.tuwien.sepm.assignment.group02.client.gui;

import at.ac.tuwien.sepm.assignment.group02.client.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.client.service.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;

@Controller
public class OptimisationFXML {
    @FXML
    private Button btn_discard;
    @FXML
    private TableColumn tc_task_woodtype;
    @FXML
    private TableView<Task> tv_tasks;
    @FXML
    private TableColumn tc_task_description;
    @FXML
    private TableColumn tc_task_finishing;
    @FXML
    private TableColumn tc_task_length;
    @FXML
    private TableColumn tc_task_width;
    @FXML
    private TableColumn tc_task_amount;
    @FXML
    private TableView<Timber> tv_timber;
    @FXML
    private TableColumn tc_timber_boxNr;
    @FXML
    private TableColumn tc_timber_length;
    @FXML
    private TableColumn tc_timber_diameter;
    @FXML
    private TableColumn tc_timber_stored_amount;
    @FXML
    private TableColumn tc_timber_taken_amount;
    @FXML
    private TableColumn tc_timber_quality;
    @FXML
    private ImageView iv_cutaway_view;
    @FXML
    private Button btn_accept;

    private LumberService lumberService;
    private TimberService timberService;
    private TaskService taskService;
    private OptimisationAlgorithmService optimisationAlgorithmService;

    @FXML
    void initialize() {
        //fill tableview tasks
        tv_tasks.setSelectionModel(null);
        ArrayList<Task> selectedTasks = optimisationAlgorithmService.getSelectedTasksMock();
        tc_task_amount.setCellValueFactory(new PropertyValueFactory("quantity"));
        tc_task_description.setCellValueFactory(new PropertyValueFactory("description"));
        tc_task_finishing.setCellValueFactory(new PropertyValueFactory("finishing"));
        tc_task_length.setCellValueFactory(new PropertyValueFactory("length"));
        tc_task_width.setCellValueFactory(new PropertyValueFactory("width"));
        tc_task_woodtype.setCellValueFactory(new PropertyValueFactory("wood_type"));

        ObservableList<Task> taskObservableList = FXCollections.observableArrayList();
        taskObservableList.addAll(selectedTasks);
        tv_tasks.setItems(taskObservableList);
        tv_tasks.refresh();

        //fill tableview timber
        Timber selectedTimber = optimisationAlgorithmService.getSelectedTimberMock();
        tv_timber.setSelectionModel(null);
        tc_timber_boxNr.setCellValueFactory(new PropertyValueFactory("box_id"));
        tc_timber_diameter.setCellValueFactory(new PropertyValueFactory("diameter"));
        tc_timber_length.setCellValueFactory(new PropertyValueFactory("length"));
        tc_timber_quality.setCellValueFactory(new PropertyValueFactory("quality"));
        tc_timber_stored_amount.setCellValueFactory(new PropertyValueFactory("amount"));
        tc_timber_taken_amount.setCellValueFactory(new PropertyValueFactory("taken_amount"));

        ObservableList<Timber> timberObservableList = FXCollections.observableArrayList();
        timberObservableList.add(selectedTimber);
        tv_timber.setItems(timberObservableList);
        tv_timber.refresh();

        //image set in fxml file
    }

    public OptimisationFXML(){
        this.optimisationAlgorithmService = new OptimisationAlgorithmServiceImpl();
    }

    //TODO autowiring
    /*
    @Autowired
    public OptimisationFXML(LumberService lumberService, TimberService timberService, TaskService taskService, OptimisationAlgorithmService optimisationAlgorithmService){
        this.lumberService = lumberService;
        this.timberService = timberService;
        this.taskService = taskService;
        this.optimisationAlgorithmService = optimisationAlgorithmService;
    }
*/
    @FXML
    public void acceptBtnClicked(ActionEvent actionEvent) {
        // get a handle to the stage
        Stage stage = (Stage) btn_accept.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    public void discardBtnClicked(ActionEvent actionEvent) {
        // get a handle to the stage
        Stage stage = (Stage) btn_discard.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}
