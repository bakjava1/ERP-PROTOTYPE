package at.ac.tuwien.sepm.assignment.group02.client.gui;

import at.ac.tuwien.sepm.assignment.group02.client.entity.OptAlgorithmResult;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.client.service.*;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OptAlgorithmResultDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
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
    private TableView<TaskDTO> tv_tasks;
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
    private TableView<TimberDTO> tv_timber;
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
    private static OptimisationAlgorithmService optimisationAlgorithmService;
    private OptAlgorithmResultDTO bestResult;

    @FXML
    void initialize() {



        tv_tasks.setSelectionModel(null);
        tc_task_amount.setCellValueFactory(new PropertyValueFactory("quantity"));
        tc_task_description.setCellValueFactory(new PropertyValueFactory("description"));
        tc_task_finishing.setCellValueFactory(new PropertyValueFactory("finishing"));
        tc_task_length.setCellValueFactory(new PropertyValueFactory("length"));
        tc_task_width.setCellValueFactory(new PropertyValueFactory("width"));
        tc_task_woodtype.setCellValueFactory(new PropertyValueFactory("wood_type"));

        tv_timber.setSelectionModel(null);
        tc_timber_boxNr.setCellValueFactory(new PropertyValueFactory("box_id"));
        tc_timber_diameter.setCellValueFactory(new PropertyValueFactory("diameter"));
        tc_timber_length.setCellValueFactory(new PropertyValueFactory("length"));
        tc_timber_quality.setCellValueFactory(new PropertyValueFactory("quality"));
        tc_timber_stored_amount.setCellValueFactory(new PropertyValueFactory("amount"));
        tc_timber_taken_amount.setCellValueFactory(new PropertyValueFactory("taken_amount"));


        updateData();

    }

    @Autowired
    public OptimisationFXML(OptimisationAlgorithmService optimisationAlgorithmService){
        OptimisationFXML.optimisationAlgorithmService = optimisationAlgorithmService;
    }

    //TODO delete this constructor and use autowired contructor
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

    public void setData(OptAlgorithmResultDTO bestResult){
        this.bestResult = bestResult;

    }
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

    private void updateData(){

        //fill tableview task

        /*
        ArrayList<TaskDTO> selectedTasks = bestResult.getTaskResult();

        ObservableList<TaskDTO> taskObservableList = FXCollections.observableArrayList();
        taskObservableList.addAll(selectedTasks);
        tv_tasks.setItems(taskObservableList);
        tv_tasks.refresh();

        //fill tableview timber
        */


        TimberDTO selectedTimber = bestResult.getTimberResult();

        ObservableList<TimberDTO> timberObservableList = FXCollections.observableArrayList();
        timberObservableList.add(selectedTimber);
        tv_timber.setItems(timberObservableList);
        tv_timber.refresh();


        Image image = SwingFXUtils.toFXImage(bestResult.getRenderedImage(), null);
        iv_cutaway_view.setImage(image);



    }


}
