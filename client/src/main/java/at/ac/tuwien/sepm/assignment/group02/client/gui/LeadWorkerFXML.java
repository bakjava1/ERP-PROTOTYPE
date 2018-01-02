package at.ac.tuwien.sepm.assignment.group02.client.gui;

import at.ac.tuwien.sepm.assignment.group02.client.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.client.entity.UnvalidatedLumber;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.service.LumberService;
import at.ac.tuwien.sepm.assignment.group02.client.service.TaskService;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class LeadWorkerFXML {

    public static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FXML
    private AnchorPane ap;

    @FXML
    private TextField tf_description;

    @FXML
    private TextField tf_strength;

    @FXML
    private TextField tf_length;

    @FXML
    private TextField tf_width;

    @FXML
    private ChoiceBox cb_finishing;

    @FXML
    private ChoiceBox cb_quality;

    @FXML
    private ChoiceBox cb_wood_type;

    @FXML
    private TextField tf_quantity;

    @FXML
    private TableColumn col_reserved_quantity;

    @FXML
    private TableColumn col_quantity;

    @FXML
    private TableColumn col_length;

    @FXML
    private TableColumn col_width;

    @FXML
    private TableColumn col_size;

    @FXML
    private TableColumn col_quality;

    @FXML
    private TableColumn col_wood_type;

    @FXML
    private TableColumn col_finishing;

    @FXML
    private TableColumn col_description;

    @FXML
    TableView<Lumber> table_lumber;

    @FXML
    private TableColumn task_col_order_id;

    @FXML
    private TableColumn task_col_produced_quantity;

    @FXML
    private TableColumn task_col_quantity;

    @FXML
    private TableColumn task_col_length;

    @FXML
    private TableColumn task_col_width;

    @FXML
    private TableColumn task_col_size;

    @FXML
    private TableColumn task_col_quality;

    @FXML
    private TableColumn task_col_wood_type;

    @FXML
    private TableColumn task_col_finishing;

    @FXML
    private TableColumn task_col_description;

    @FXML
    TableView<TaskDTO> table_task;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tab_task;

    @FXML
    private Tab tab_lumber;

    private LumberService lumberService;
    private TaskService taskService;

    private TaskDTO selectedTask;

    @Autowired
    public LeadWorkerFXML(LumberService lumberService, TaskService taskService){

        this.lumberService = lumberService;
        this.taskService = taskService;

    }

    @FXML
    void initialize() {

        tabPane = new TabPane();
        tabPane.getTabs().add(tab_task);
        tabPane.getTabs().add(tab_lumber);

        col_description.setCellValueFactory(new PropertyValueFactory("description"));
        col_finishing.setCellValueFactory(new PropertyValueFactory("finishing"));
        col_wood_type.setCellValueFactory(new PropertyValueFactory("wood_type"));
        col_quality.setCellValueFactory(new PropertyValueFactory("quality"));
        col_size.setCellValueFactory(new PropertyValueFactory("size"));
        col_width.setCellValueFactory(new PropertyValueFactory("width"));
        col_length.setCellValueFactory(new PropertyValueFactory("length"));
        col_quantity.setCellValueFactory(new PropertyValueFactory("quantity"));
        col_reserved_quantity.setCellValueFactory(new PropertyValueFactory("reserved_quantity"));

        cb_finishing.setItems(FXCollections.observableArrayList("keine Angabe", "roh", "gehobelt", "besäumt", "prismiert", "trocken","lutro","frisch", "imprägniert"));
        cb_wood_type.setItems(FXCollections.observableArrayList("keine Angabe", "Fi", "Ta", "Lä", "Ki", "Zi"));
        cb_quality.setItems(FXCollections.observableArrayList("keine Angabe", "O","I","II","III","IV","V", "O/III", "III/IV", "III/V"));

        cb_finishing.getSelectionModel().selectFirst();
        cb_wood_type.getSelectionModel().selectFirst();
        cb_quality.getSelectionModel().selectFirst();


        task_col_order_id.setCellValueFactory(new PropertyValueFactory("order_id"));
        task_col_description.setCellValueFactory(new PropertyValueFactory("description"));
        task_col_finishing.setCellValueFactory(new PropertyValueFactory("finishing"));
        task_col_wood_type.setCellValueFactory(new PropertyValueFactory("wood_type"));
        task_col_quality.setCellValueFactory(new PropertyValueFactory("quality"));
        task_col_size.setCellValueFactory(new PropertyValueFactory("size"));
        task_col_width.setCellValueFactory(new PropertyValueFactory("width"));
        task_col_length.setCellValueFactory(new PropertyValueFactory("length"));
        task_col_quantity.setCellValueFactory(new PropertyValueFactory("quantity"));
        task_col_produced_quantity.setCellValueFactory(new PropertyValueFactory("produced_quantity"));


        //initial lumber overview
        onSearchButtonClicked();

        initializeTaskTable();
        updateTaskTable();

    }

    private void updateTaskTable() {

        List<TaskDTO> allOpenTasks = null;

        try{
            allOpenTasks = taskService.getAllOpenTasks();
        } catch (ServiceLayerException e){
            LOG.warn(e.getMessage());
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.showInformationAlert("Übersicht Aufträge",
                    "Übersicht Aufträge", "Fehler bei Erstellung der Auftragsübersicht.");
        }

        if(allOpenTasks != null){
            ObservableList<TaskDTO> openTasksForTable = FXCollections.observableArrayList();
            for (TaskDTO task: allOpenTasks) {
                openTasksForTable.add(task);
            }
            table_task.setItems(openTasksForTable);
            table_task.refresh();
        } else {
            table_task.refresh();
        }

    }

    @FXML
    private void initializeTaskTable(){

        // single row can be selected
        table_task.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // set row factory in order to create the context menu
        table_task.setRowFactory(
                new Callback<TableView<TaskDTO>, TableRow<TaskDTO>>() {
                    @Override
                    public TableRow<TaskDTO> call(TableView<TaskDTO> tableView) {
                        final TableRow<TaskDTO> row = new TableRow<>();
                        final ContextMenu rowMenu = new ContextMenu();

                        MenuItem reserveLumber = new MenuItem("Schnittholz hinzufügen");
                        reserveLumber.setOnAction((ActionEvent event) -> {
                            LOG.debug("selected product: {}",row.getItem().toString());
                            selectedTask = table_task.getSelectionModel().getSelectedItem();

                            tabPane.getSelectionModel().select(tab_lumber);

                            table_task.getSelectionModel().clearSelection();
                        });

                        rowMenu.getItems().addAll(reserveLumber);


                        // only display context menu for non-null items:
                        row.contextMenuProperty().bind(Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                .then(rowMenu)
                                .otherwise((ContextMenu)null));
                        return row;
                    }
                });
    }

    @FXML
    public void onSearchButtonClicked() {
        UnvalidatedLumber filter = new UnvalidatedLumber();
        List<Lumber> allLumber = null;

        filter.setDescription(tf_description.getText().trim());
        filter.setFinishing(cb_finishing.getSelectionModel().getSelectedItem().toString().trim());
        filter.setWood_type(cb_wood_type.getSelectionModel().getSelectedItem().toString().trim());
        filter.setQuality(cb_quality.getSelectionModel().getSelectedItem().toString().trim());
        filter.setSize(tf_strength.getText().trim());
        filter.setWidth(tf_width.getText().trim());
        filter.setLength(tf_length.getText().trim());


        try {
            allLumber = lumberService.getAll(filter);
        } catch (InvalidInputException e) {
            LOG.warn(e.getMessage());
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Eingabe nicht korrekt");
            error.setHeaderText(null);
            error.setContentText(e.getMessage());
            error.showAndWait();
        } catch (ServiceLayerException e) {
            LOG.warn(e.getMessage());
        }

        if (allLumber != null) {
            ObservableList<Lumber> lumberForTable = FXCollections.observableArrayList();

            for (Lumber lumber: allLumber) {
                lumberForTable.add(lumber);
            }

            table_lumber.setItems(lumberForTable);

            table_lumber.refresh();
        } else {
            table_lumber.refresh();
        }

    }


    @FXML
    public void onReserveButtonClicked(){
        LOG.info("onReserveButtonClicked clicked");

        if(selectedTask == null){
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.showInformationAlert("Schnittholz-Reservierung",
                    "Schnittholz-Reservierung", "Bitte wählen Sie einen Auftrag zur Reservierung aus!");
            tabPane.getSelectionModel().select(tab_task);
            return;
        }

        // get the selected lumberDTO from the table
        if(table_lumber.getSelectionModel().getSelectedItem() == null) {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.showInformationAlert("Schnittholz-Reservierung",
                    "Schnittholz-Reservierung", "Bitte wählen Sie ein Schnittholz zur Reservierung aus!");
            return;
        }

        Lumber lumber = table_lumber.getSelectionModel().getSelectedItem();
        LOG.debug("selected lumber: {}", lumber.toString());

        // get the quantity of lumber to reserve from the textfield
        int quantity;
        if( tf_quantity.getText().isEmpty() || tf_quantity.getText().length() < 1 ) {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.showInformationAlert("Schnittholz-Reservierung",
                    "Schnittholz-Reservierung", "Bitte definieren Sie die Menge an Schnittholz, die reserviert werden soll!");
            return;
        } else {
            if (tf_quantity.getText().trim().matches("^\\d+$")){
                quantity = Integer.parseInt(tf_quantity.getText().trim());
            } else {
                AlertBuilder alertBuilder = new AlertBuilder();
                alertBuilder.showInformationAlert("Schnittholz-Reservierung",
                        "Schnittholz-Reservierung", "Bitte definieren Sie eine positive ganze Zahl als Reservierungsmenge!");

                return;
            }
        }

        final int qu = quantity;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Schnittholz (Id:"+lumber.getId()+", "+lumber.getDescription()+", Menge: "+qu+") dem Auftrag (Id: "+selectedTask.getId()+", "+selectedTask.getDescription()+") hinzufügen?",
                ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {

            new Thread(new Task<>() {
                @Override
                protected Object call() throws Exception {
                    LOG.debug("reserve-lumber thread called");

                    lumberService.reserveLumber(lumber, qu, selectedTask);

                    return 1;
                }

                @Override
                protected void succeeded() {
                    super.succeeded();
                    LOG.debug("reserve-lumber succeeded with value {}", getValue());
                    table_lumber.getSelectionModel().clearSelection();
                    tf_quantity.setText("");

                    //refresh tables
                    onSearchButtonClicked();
                    updateTaskTable();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION,
                            "Schnittholz wurde dem Auftrag erfolgreich hinzugefügt.");
                    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    alert.show();

                    //change tab
                    tabPane.getSelectionModel().select(tab_task);
                }

                @Override
                protected void failed() {
                    super.failed();
                    LOG.debug("reserve-lumber failed with exception:", getException().getMessage());
                    table_lumber.getSelectionModel().clearSelection();
                    tf_quantity.setText("");

                    Alert alert = new Alert(Alert.AlertType.WARNING,
                            "Schnittholz-Reservierung wurde nicht durchgeführt. "+ getException().getMessage());
                    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    alert.show();

                    //refresh tables
                    onSearchButtonClicked();
                    updateTaskTable();
                    //change tab
                    tabPane.getSelectionModel().select(tab_task);

                }
            }, "reserve-lumber").start();

        }
    }

    @FXML
    public void optimisationBtnClicked() {
        LOG.info("optimisationBtn clicked");

        new Thread(new Task<Object>() {
            @Override
            protected Object call() throws Exception {

                //TODO not able to debug autowiring not working
                /*TimeUnit.SECONDS.sleep(6);
                try {
                    Stage stage = new Stage();
                    stage.setTitle("Optimierungsalgorithmus");

                    context = new AnnotationConfigApplicationContext();
                    context.getBeanFactory().registerSingleton("stage", (Stage) ap.getScene().getWindow());
                    context.scan("at.ac.tuwien.sepm.assignment.group02.client");
                    context.refresh();
                    context.start();

                    ExampleQSE_SpringFXMLLoader fxmlLoader = context.getBean(ExampleQSE_SpringFXMLLoader.class);

                    stage.setScene(new Scene((Parent) fxmlLoader.load("/fxml/optimisation.fxml"), 950, 680));
                    stage.centerOnScreen();
                    stage.show();

                } catch (IOException e) {
                    LOG.error(e.getMessage());

                }*/

                TimeUnit.SECONDS.sleep(6);

                return null;
            }

            @Override
            protected void succeeded(){
                try {

                    OptimisationFXML optimisationFXML    = new OptimisationFXML();
                    FXMLLoader fxmlLoader = new FXMLLoader(OfficeFXML.class.getResource("/fxml/optimisation.fxml"));
                    fxmlLoader.setControllerFactory(param -> param.isInstance(optimisationFXML) ? optimisationFXML : null);

                    Stage stage = new Stage();
                    stage.setTitle("Optimierungsalgorithmus");
                    stage.setWidth(950);
                    stage.setHeight(680);
                    stage.centerOnScreen();
                    stage.setScene(new Scene(fxmlLoader.load()));
                    stage.show();

                } catch (IOException e) {
                    LOG.error(e.getMessage());

                }

            }

            @Override
            protected void failed(){

            }
        }, "optimisation-algorithm").start();


    }
}
