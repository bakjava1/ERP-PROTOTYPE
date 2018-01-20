package at.ac.tuwien.sepm.assignment.group02.client.gui;

import at.ac.tuwien.sepm.assignment.group02.client.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.OptimisationAlgorithmException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.service.AssignmentService;
import at.ac.tuwien.sepm.assignment.group02.client.service.LumberService;
import at.ac.tuwien.sepm.assignment.group02.client.service.OptimisationAlgorithmService;
import at.ac.tuwien.sepm.assignment.group02.client.service.TaskService;
import at.ac.tuwien.sepm.assignment.group02.client.util.AlertBuilder;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.FilterDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OptAlgorithmResultDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.util.List;


@Controller
public class LeadWorkerFXML {

    public static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private AlertBuilder alertBuilder;

    @FXML
    private TextField tf_description;

    @FXML
    private TextField tf_strength;

    @FXML
    private TextField tf_length;

    @FXML
    private TextField tf_width;

    @FXML
    private ComboBox<String> cb_finishing;

    @FXML
    private ComboBox<String> cb_quality;

    @FXML
    private ComboBox<String> cb_wood_type;

    @FXML
    private TextField tf_quantity;

    @FXML
    private TableColumn<Lumber, Integer> col_reserved_quantity;

    @FXML
    private TableColumn<Lumber, Integer> col_quantity;

    @FXML
    private TableColumn<Lumber, Integer> col_length;

    @FXML
    private TableColumn<Lumber, Integer> col_width;

    @FXML
    private TableColumn<Lumber, Integer> col_size;

    @FXML
    private TableColumn<Lumber, String> col_quality;

    @FXML
    private TableColumn<Lumber, String> col_wood_type;

    @FXML
    private TableColumn<Lumber, String> col_finishing;

    @FXML
    private TableColumn<Lumber, String> col_description;

    @FXML
    private TableView<Lumber> table_lumber;

    @FXML
    private Button btn_opt_alg;
    @FXML
    private Button btn_reserve;
    @FXML
    private Button btn_resetSearch;

    @FXML
    private TableColumn<TaskDTO, Integer> task_col_order_id;

    @FXML
    private TableColumn<TaskDTO, Integer> task_col_produced_quantity;

    @FXML
    private TableColumn<TaskDTO, Integer> task_col_quantity;

    @FXML
    private TableColumn<TaskDTO, Integer> task_col_length;

    @FXML
    private TableColumn<TaskDTO, Integer> task_col_width;

    @FXML
    private TableColumn<TaskDTO, Integer> task_col_size;

    @FXML
    private TableColumn<TaskDTO, String> task_col_quality;

    @FXML
    private TableColumn<TaskDTO, String> task_col_wood_type;

    @FXML
    private TableColumn<TaskDTO, String> task_col_finishing;

    @FXML
    private TableColumn<TaskDTO, String> task_col_description;

    @FXML
    private TableColumn<TaskDTO, Boolean> task_col_done;

    @FXML
    private TableColumn<TaskDTO, Boolean> task_col_in_progress;

    @FXML
    TableView<TaskDTO> table_task;

    @FXML
    private TabPane tabPane;

    private LumberService lumberService;
    private OptimisationFXML optimisationFXML;
    private TaskService taskService;
    private AssignmentService assignmentService;
    private OptimisationAlgorithmService optimisationAlgorithmService;

    private TaskDTO selectedTask;

    @Autowired
    public LeadWorkerFXML(LumberService lumberService, OptimisationFXML optimisationFXML, TaskService taskService, AssignmentService assignmentService, OptimisationAlgorithmService optimisationAlgorithmService){

        this.lumberService = lumberService;
        this.optimisationFXML = optimisationFXML;
        this.taskService = taskService;
        this.assignmentService = assignmentService;
        this.optimisationAlgorithmService = optimisationAlgorithmService;

    }

    @FXML
    void initialize() {

        col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_finishing.setCellValueFactory(new PropertyValueFactory<>("finishing"));
        col_wood_type.setCellValueFactory(new PropertyValueFactory<>("wood_type"));
        col_quality.setCellValueFactory(new PropertyValueFactory<>("quality"));
        col_size.setCellValueFactory(new PropertyValueFactory<>("size"));
        col_width.setCellValueFactory(new PropertyValueFactory<>("width"));
        col_length.setCellValueFactory(new PropertyValueFactory<>("length"));
        col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_reserved_quantity.setCellValueFactory(new PropertyValueFactory<>("reserved_quantity"));

        cb_finishing.setItems(FXCollections.observableArrayList("keine Angabe", "roh", "gehobelt", "besäumt", "prismiert", "trocken","lutro","frisch", "imprägniert"));
        cb_wood_type.setItems(FXCollections.observableArrayList("keine Angabe", "Fi", "Ta", "Lä", "Ki", "Zi"));
        cb_quality.setItems(FXCollections.observableArrayList("keine Angabe", "O","I","II","III","IV","V", "O/III", "III/IV", "III/V"));

        cb_finishing.getSelectionModel().selectFirst();
        cb_wood_type.getSelectionModel().selectFirst();
        cb_quality.getSelectionModel().selectFirst();

        //hide button "Reservieren" (only show it after task is selected)
        btn_reserve.setVisible(false);

        tf_length.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    tf_length.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        tf_quantity.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    tf_quantity.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        tf_strength.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    tf_strength.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        tf_width.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    tf_width.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        //add image to reset search parameters button
        URL imgpath = LeadWorkerFXML.class
                .getClassLoader()
                .getResource("reset_search.png");
        if(imgpath!=null) {
            ImageView imageView = new ImageView(new Image(imgpath.toString()));
            imageView.setFitWidth(30.00);
            imageView.setPreserveRatio(true);
            btn_resetSearch.setGraphic(imageView);
        } else {
            btn_resetSearch.setText("Reset");
        }
        //initial lumber overview
        onSearchButtonClicked();

        initializeTaskTable();
        updateTaskTable();

    }

    private void initializeTaskTable(){

        task_col_order_id.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        task_col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        task_col_finishing.setCellValueFactory(new PropertyValueFactory<>("finishing"));
        task_col_wood_type.setCellValueFactory(new PropertyValueFactory<>("wood_type"));
        task_col_quality.setCellValueFactory(new PropertyValueFactory<>("quality"));
        task_col_size.setCellValueFactory(new PropertyValueFactory<>("size"));
        task_col_width.setCellValueFactory(new PropertyValueFactory<>("width"));
        task_col_length.setCellValueFactory(new PropertyValueFactory<>("length"));
        task_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        task_col_produced_quantity.setCellValueFactory(new PropertyValueFactory<>("produced_quantity"));
        task_col_done.setCellValueFactory(new PropertyValueFactory<>("done"));
        task_col_in_progress.setCellValueFactory(new PropertyValueFactory<>("in_progress"));

        Task<Integer> task = new Task<>() {
            @Override
            protected Integer call() throws Exception {
                while(true){
                    if(isCancelled()) break;
                    Thread.sleep(5000);
                    int selected_index = table_task.getSelectionModel().getSelectedIndex();
                    updateTaskTable();
                    table_task.getSelectionModel().select(selected_index);
                }
                return 1;
            }
        };

        //start the auto-refresh task
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

    private void updateTaskTable() {
        LOG.debug("called updateTaskTable");

        List<TaskDTO> allOpenTasks = null;

        try{
            allOpenTasks = taskService.getAllOpenTasks();
        } catch (ServiceLayerException e){
            LOG.warn(e.getMessage());
            alertBuilder.showErrorAlert("Übersicht Aufträge",
                    null, "Fehler bei Erstellung der Auftragsübersicht. "+ e.getMessage());
        }

        if(allOpenTasks != null){
            ObservableList<TaskDTO> openTasksForTable = FXCollections.observableArrayList();
            openTasksForTable.addAll(allOpenTasks);
            table_task.setItems(openTasksForTable);
            table_task.refresh();
        } else {
            table_task.refresh();
        }

        // single row can be selected
        table_task.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // set row factory in order to create the context menu and set row color
        table_task.setRowFactory(
                new Callback<>() {
                    @Override
                    public TableRow<TaskDTO> call(TableView<TaskDTO> tableView) {
                        final TableRow<TaskDTO> row = new TableRow<>() {
                            @Override
                            protected void updateItem(TaskDTO taskDTO, boolean empty){
                                super.updateItem(taskDTO, empty);

                                if (taskDTO == null) {
                                    setStyle("");
                                } else if (taskDTO.isDone()) {
                                    setStyle("-fx-background-color:#b0eeb0;");
                                    if(isSelected()) setStyle("-fx-background-color: lightslategray;");
                                } else if (taskDTO.isIn_progress()) {
                                    setStyle("-fx-background-color:#ffff80;");
                                    if(isSelected()) setStyle("-fx-background-color: lightslategray;");
                                } else {
                                    setStyle("");
                                }

                            }
                        };
                        LOG.trace(row.getId());
                        return row;
                    }
                });

        table_task.refresh();
    }

    @FXML
    public void createAssignmentButtonClicked(){

        TaskDTO taskDTO = table_task.getSelectionModel().getSelectedItem();

        if(taskDTO == null || taskDTO.isDone()) {
            alertBuilder.showInformationAlert("Schnittholz-Produktion",
                    null, "Bitte wählen Sie einen nicht abgeschlossenen Auftrag zur Produktion aus!");
            return;
        }

        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setTask_id(taskDTO.getId());
        assignmentDTO.setDone(false);

        //TODO opt_alg
        int box_id=2;
        int amount=(taskDTO.getQuantity()-taskDTO.getProduced_quantity());

        assignmentDTO.setBox_id(box_id);
        assignmentDTO.setAmount(amount);

        boolean confirmed = alertBuilder.showConfirmationAlert("Schnittholz-Produktion",
                null, "Möchten Sie die Produktion von "+assignmentDTO.getAmount()+" Schnitthölzer in Auftrag geben?");
        if(confirmed) {
            new Thread(new Task<>() {
                @Override
                protected Object call() throws Exception {
                    LOG.debug("create-assignment thread called");
                    assignmentService.createAssignment(assignmentDTO);
                    return null;
                }

                @Override
                protected void succeeded() {
                    super.succeeded();
                    LOG.debug("create-assignment succeeded with value: ", getValue());
                    alertBuilder.showInformationAlert("Schnittholz-Produktion",
                            null, "Schnittholz Produktion wurde erfolgreich in Auftrag gegeben.");
                    updateTaskTable();
                    table_task.getSelectionModel().clearSelection();
                }

                @Override
                protected void failed() {
                    super.failed();
                    LOG.debug("create-assignment failed with exception:", getException().getMessage());

                    alertBuilder.showErrorAlert("Schnittholz-Produktion",
                            null, "Schnittholz Produktion wurde nicht in Auftrag gegeben. " + getException().getMessage());

                    table_task.getSelectionModel().clearSelection();
                }
            }, "create-assignment").start();
        }
    }

    @FXML
    public void resetSearchClicked(){
        tf_description.setText("");
        cb_finishing.getSelectionModel().select(0);
        cb_wood_type.getSelectionModel().select(0);
        cb_quality.getSelectionModel().select(0);
        tf_strength.setText("");
        tf_width.setText("");
        tf_length.setText("");
    }

    @FXML
    public void addLumberButtonClicked(){

        selectedTask = table_task.getSelectionModel().getSelectedItem();

        if(selectedTask == null || selectedTask.isDone()) {
            alertBuilder.showInformationAlert("Schnittholz-Reservierung",
                    null, "Bitte wählen Sie einen nicht abgeschlossenen Auftrag zur Reservierung aus!");
            tabPane.getSelectionModel().clearAndSelect(0);
            return;
        }

        // set the textfield quantity of lumber to reserve to the needed amount of lumber
        tf_quantity.setText(""+(selectedTask.getQuantity()-selectedTask.getProduced_quantity()));
        // make the reserve button visible
        btn_reserve.setVisible(true);

        // reset all search fields
        resetSearchClicked();

        // fill search fields with the values of the selected task
        tf_description.setText(selectedTask.getDescription());
        cb_finishing.getSelectionModel().select(selectedTask.getFinishing());
        cb_wood_type.getSelectionModel().select(selectedTask.getWood_type());
        cb_quality.getSelectionModel().select(selectedTask.getQuality());
        tf_strength.setText(selectedTask.getSize()+"");
        tf_width.setText(selectedTask.getWidth()+"");
        tf_length.setText(selectedTask.getLength()+"");

        // set the search properties to the task properties
        FilterDTO filterDTO = new FilterDTO();
        filterDTO.setDescription(selectedTask.getDescription());
        filterDTO.setFinishing(selectedTask.getFinishing());
        filterDTO.setWood_type(selectedTask.getWood_type());
        filterDTO.setQuality(selectedTask.getQuality());
        filterDTO.setSize(selectedTask.getSize()+"");
        filterDTO.setWidth(selectedTask.getWidth()+"");
        filterDTO.setLength(selectedTask.getLength()+"");

        // execute lumber search with task-filter
        executeSearch(filterDTO);

        // change tab to lumber overview
        tabPane.getSelectionModel().clearAndSelect(1);
    }

    @FXML
    public void onSearchButtonClicked() {

        FilterDTO filterDTO = new FilterDTO();

        filterDTO.setDescription(tf_description.getText().trim());
        filterDTO.setFinishing(cb_finishing.getSelectionModel().getSelectedItem().trim());
        filterDTO.setWood_type(cb_wood_type.getSelectionModel().getSelectedItem().trim());
        filterDTO.setQuality(cb_quality.getSelectionModel().getSelectedItem().trim());
        filterDTO.setSize(tf_strength.getText().trim());
        filterDTO.setWidth(tf_width.getText().trim());
        filterDTO.setLength(tf_length.getText().trim());

        executeSearch(filterDTO);

    }

    private void executeSearch(FilterDTO filterDTO){

        Task task = new Task<>() {
            @Override
            protected List<Lumber> call() throws Exception {
                LOG.debug("search-lumber thread called");
                return lumberService.getAll(filterDTO);
            }

            @Override
            protected void failed() {
                super.failed();
                Throwable e = getException();
                LOG.warn("search-lumber failed with exception:", getException().getMessage());
                if (e instanceof InvalidInputException){
                    alertBuilder.showErrorAlert("Schnittholz-Suche", null, "Bitte korrigieren Sie Ihre Eingabe! " + e.getMessage());
                } else {
                    alertBuilder.showErrorAlert("Schnittholz-Suche", null, "Bei der Schnittholz Suche ist ein Problem aufgetreten: " + e.getMessage());
                }
            }
        };

        task.setOnSucceeded((e) ->  {
            Object obj = task.getValue();
            if(obj instanceof List<?>) {
                List<Lumber> allLumber = (List<Lumber>) obj;
                //if (allLumber != null) {
                    ObservableList<Lumber> lumberForTable = FXCollections.observableArrayList();
                    lumberForTable.addAll(allLumber);
                    table_lumber.setItems(lumberForTable);
                    table_lumber.refresh();
                //}
            } else {
                // no list was returned
                table_lumber.refresh();
            }
        });

        Thread t = new Thread(task,"search-lumber");
        t.start();
    }


    @FXML
    public void onReserveButtonClicked(){
        LOG.info("onReserveButtonClicked clicked");

        if(selectedTask == null || selectedTask.isDone()) {
            alertBuilder.showInformationAlert("Schnittholz-Reservierung",
                    null, "Bitte wählen Sie einen nicht abgeschlossenen Auftrag zur Reservierung aus!");
            tabPane.getSelectionModel().clearAndSelect(0);
            return;
        }

        if(selectedTask.isIn_progress()) {
            alertBuilder.showInformationAlert("Schnittholz-Reservierung",
                    null, "Der ausgewählte Auftrag ist bereits in Produktion!");
            tabPane.getSelectionModel().clearAndSelect(0);
            return;
        }

        // get the selected lumberDTO from the table
        if(table_lumber.getSelectionModel().getSelectedItem() == null) {
            alertBuilder.showInformationAlert("Schnittholz-Reservierung",
                    null, "Bitte wählen Sie ein Schnittholz zur Reservierung aus!");
            return;
        }

        Lumber lumber = table_lumber.getSelectionModel().getSelectedItem();
        LOG.debug("selected lumber: {}", lumber.toString());

        int quantity;

        // get the entered quantity from the text field
        if( tf_quantity.getText().isEmpty() || tf_quantity.getText().length() < 1 ) {
            alertBuilder.showInformationAlert("Schnittholz-Reservierung",
                    null, "Bitte definieren Sie die Menge an Schnittholz, die reserviert werden soll!");
            return;
        } else {
            if (tf_quantity.getText().trim().matches("^\\d+$")){
                quantity = Integer.parseInt(tf_quantity.getText().trim());
            } else {
                alertBuilder.showInformationAlert("Schnittholz-Reservierung",
                        null, "Bitte definieren Sie eine positive ganze Zahl als Reservierungsmenge!");
                return;
            }
        }

        final int qu = quantity;

        boolean confirmed = alertBuilder.showConfirmationAlert("Schnittholz-Reservierung",
                null,"Möchten Sie nachfolgende Menge an Schnittholz für den Auftrag reservieren?"+
                        "\n "+
                        "\nSchnittholz: "+lumber.getDescription()+", "+lumber.getFinishing()+", "+
                        lumber.getWood_type()+", "+lumber.getQuality()+", "+
                        lumber.getSize()+", "+lumber.getWidth()+", "+lumber.getLength()+
                        "\nMenge: "+qu+
                        "\n "+
                        "\nAuftrag: "+selectedTask.getDescription()+", "+selectedTask.getFinishing()+", "+
                        selectedTask.getWood_type()+", "+selectedTask.getQuality()+", "+
                        selectedTask.getSize()+", "+selectedTask.getWidth()+", "+selectedTask.getLength());
        if(confirmed) {
            Task task = new Task<>() {
                @Override
                protected Object call() throws Exception {
                    LOG.debug("reserve-lumber thread called");
                    lumberService.reserveLumber(lumber, qu, selectedTask);
                    return null;
                }

                @Override
                protected void succeeded() {
                    super.succeeded();
                    LOG.debug("reserve-lumber succeeded with value {}", getValue());
                    table_lumber.getSelectionModel().clearSelection();
                    tf_quantity.setText("");

                    alertBuilder.showInformationAlert("Schnittholz-Reservierung",
                            null, "Schnittholz wurde dem Auftrag erfolgreich hinzugefügt.");

                }

                @Override
                protected void failed() {
                    super.failed();
                    LOG.debug("reserve-lumber failed with exception:", getException().getMessage());
                    table_lumber.getSelectionModel().clearSelection();
                    tf_quantity.setText("");

                    alertBuilder.showErrorAlert("Schnittholz-Reservierung",
                            null, "Schnittholz-Reservierung wurde nicht durchgeführt. "+ getException().getMessage());

                }
            };

            new Thread(task,"reserve-lumber").start();

        } else {

            alertBuilder.showInformationAlert("Schnittholz-Reservierung",
                    null, "Reservierungsvorgang wurde abgebrochen.");
        }

        //refresh tables
        onSearchButtonClicked();
        updateTaskTable();

        // hide the reserve button
        btn_reserve.setVisible(false);

        //change tab
        tabPane.getSelectionModel().clearAndSelect(0);

        // reset the selected task
        selectedTask = null;
    }

    @FXML
    public void optimisationBtnClicked() {
        LOG.info("optimisationBtn clicked");

        btn_opt_alg.setDisable(true);

        final OptAlgorithmResultDTO[] bestResult = {null};


        new Thread(new Task<>() {
            @Override
            protected Object call() {


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

                TaskDTO task = table_task.getSelectionModel().getSelectedItem();
                if(!task.isDone()) {
                    try {
                        bestResult[0] = optimisationAlgorithmService.getOptAlgorithmResult(task);
                    } catch (PersistenceLayerException e) {
                        LOG.error("Fehler bei der Optimierung in der Persistenzschicht" + e.getMessage());
                        alertBuilder.showErrorAlert("Fehler", "Fehler in der Persistenzschicht", e.getMessage());
                    } catch (OptimisationAlgorithmException e) {
                        LOG.error("Fehler beim Optimierungsalgorithmus:" + e.getMessage());
                        alertBuilder.showErrorAlert("Fehler", "Fehler bei der Optimierung", e.getMessage());
                    }
                } else {
                    failed();
                }


                return null;
            }

            @Override
            protected void succeeded(){
                if (bestResult[0] != null) {

                    optimisationFXML.setData(bestResult[0]);

                    try {
                        btn_opt_alg.setDisable(false);


                        FXMLLoader fxmlLoader = new FXMLLoader(OfficeFXML.class.getResource("/fxml/optimisation.fxml"));
                        fxmlLoader.setControllerFactory(param -> param.isInstance(optimisationFXML) ? optimisationFXML : null);
                        Scene scene = new Scene(fxmlLoader.load(), 950, 680);

                        Stage stage = new Stage();
                        stage.setTitle("Optimierungsalgorithmus");
                        stage.setScene(scene);
                        stage.centerOnScreen();
                        stage.show();
                    } catch (IOException e) {
                        LOG.error(e.getMessage());
                    }
                }
            }

            @Override
            protected void failed(){
                alertBuilder.showInformationAlert("Information", "Auftrag ist bereits fertig!", "Bitte wählen Sie einen anderen Auftrag aus.");
                btn_opt_alg.setDisable(false);
            }
        }, "optimisation-algorithm").start();

    }
}
