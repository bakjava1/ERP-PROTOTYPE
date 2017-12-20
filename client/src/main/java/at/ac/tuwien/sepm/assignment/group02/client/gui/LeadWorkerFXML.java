package at.ac.tuwien.sepm.assignment.group02.client.gui;

import at.ac.tuwien.sepm.assignment.group02.client.entity.UnvalidatedLumber;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.service.LumberService;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Lumber;
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
import javafx.stage.Stage;
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


    private LumberService lumberService;

    @Autowired
    public LeadWorkerFXML(LumberService lumberService){

        this.lumberService = lumberService;
    }

    @FXML
    void initialize() {
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



    }


    @FXML
    public void onSearchButtonClicked(ActionEvent actionEvent) {
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
    public void onUpdateButtonClicked(ActionEvent actionEvent){

    }

    @FXML
    public void optimisationBtnClicked(ActionEvent actionEvent) {
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
