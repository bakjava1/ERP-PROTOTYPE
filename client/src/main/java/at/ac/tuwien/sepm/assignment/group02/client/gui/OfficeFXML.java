package at.ac.tuwien.sepm.assignment.group02.client.gui;

import at.ac.tuwien.sepm.assignment.group02.client.entity.UnvalidatedTask;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Timber;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static at.ac.tuwien.sepm.assignment.group02.client.MainApplication.orderService;
import static at.ac.tuwien.sepm.assignment.group02.client.MainApplication.timberService;
import static at.ac.tuwien.sepm.assignment.group02.client.MainApplication.taskService;


public class OfficeFXML {

    public static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static Order currentOrder = new Order();
    private static List<Task> currentOrderTaskList = new ArrayList<>();
    private static int currentOrderIndex = 1;

    @FXML
    private TableColumn col_taskLength;

    @FXML
    private TableColumn col_taskWidth;

    @FXML
    private TableColumn col_taskSize;

    @FXML
    private TableColumn col_taskQuantity;

    @FXML
    private TableColumn col_taskDescription;

    @FXML
    private TableColumn col_taskNr;

    @FXML
    private TableView table_addedTask;

    @FXML
    private TextField tf_order_customerUID;

    @FXML
    private TextField tf_order_customeraddress;

    @FXML
    private TextField tf_order_customername;

    @FXML
    private Tab tab_timber;

    @FXML
    private Tab tab_order;

    @FXML
    private TextField selectedOrder;

    @FXML
    private TextField tf_timber_amount;

    @FXML
    private ChoiceBox cb_timber_box;

    @FXML
    private TableColumn col_orderID;

    @FXML
    TableView<Order> table_openOrder;



    @FXML
    void initialize() {
        col_orderID.setCellValueFactory(new PropertyValueFactory("ID"));

        col_taskNr.setCellValueFactory(
                new PropertyValueFactory<Task, Integer>("id")
        );
        col_taskDescription.setCellValueFactory(
                new PropertyValueFactory<Task, String>("description")
        );
        col_taskQuantity.setCellValueFactory(
                new PropertyValueFactory<Task, Integer>("quantity")
        );
        col_taskSize.setCellValueFactory(
                new PropertyValueFactory<Task, Integer>("size")
        );
        col_taskWidth.setCellValueFactory(
                new PropertyValueFactory<Task, Integer>("width")
        );
        col_taskLength.setCellValueFactory(
                new PropertyValueFactory<Task, Integer>("length")
        );

        ObservableList<Task> orderTasks = FXCollections.observableArrayList();
        table_addedTask.setItems(orderTasks);

        updateTable();
    }

    @FXML
    public void deleteOrder() {
        LOG.trace("called deleteOrder");

        //TODO create order correctly and use orderDTO for REST
        int selectedOrderID = Integer.parseInt(selectedOrder.getText());
        Order order = new Order();
        order.setID(selectedOrderID);

        try {
            orderService.deleteOrder(order);
        } catch (InvalidInputException e) {
            e.printStackTrace();
        }

        updateTable();
    }

    @FXML
    public void createOrder(ActionEvent actionEvent) {
        LOG.info("createOrder called");
        if(currentOrderTaskList.size() == 0) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("No Tasks added");
            error.setHeaderText(null);
            error.setContentText("There is not Task added to the Order");
            error.showAndWait();
            return;
        }
        currentOrder.setCustomerName(tf_order_customername.getText());
        currentOrder.setCustomerAddress(tf_order_customeraddress.getText());
        currentOrder.setCustomerUID(tf_order_customerUID.getText());
        LOG.debug(currentOrder.toString());
        try {
            orderService.addOrder(currentOrder,currentOrderTaskList);
            ObservableList<Task> orderTasks = FXCollections.observableArrayList();
            table_addedTask.setItems(orderTasks);
            currentOrder = new Order();
            currentOrderTaskList = new ArrayList<>();
            tf_order_customername.setText("");
            tf_order_customeraddress.setText("");
            tf_order_customerUID.setText("");
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("Creation successful");
            success.setHeaderText(null);
            success.setContentText("Order created successfully!");
            success.showAndWait();
        } catch (InvalidInputException e) {
            e.printStackTrace();
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Creation failed");
            error.setHeaderText(null);
            error.setContentText("Order Creation failed!");
            error.showAndWait();
        }

        updateTable();
    }


    private void updateTable() {

        List<Order> allOpen = orderService.getAllOpen();

        if (allOpen != null) {
            ObservableList<Order> openOrderForTable = FXCollections.observableArrayList();

            for (Order order: allOpen) {
                openOrderForTable.add(order);
            }

            table_openOrder.setItems(openOrderForTable);
            table_openOrder.refresh();
        } else {
            table_openOrder.refresh();
        }

    }

    @FXML
    public void addTimber(ActionEvent actionEvent){
        //TODO catch exception (InvalidInputException by Lucia)
        Timber timber = new Timber(cb_timber_box.getSelectionModel().getSelectedIndex()+1, Integer.parseInt(tf_timber_amount.getText()));
        try {
            timberService.addTimber(timber);
        } catch (InvalidInputException e) {
            LOG.error("Invalid Input Error: " + e.getMessage());
        }
    }

    //initialize the gui
    public void initStage() {
        cb_timber_box.setItems(FXCollections.observableArrayList("Box 1", "Box 2"));
        // force the field to be numeric only
        tf_timber_amount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    tf_timber_amount.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    public void addTaskToOrder(ActionEvent actionEvent) {
        Dialog<UnvalidatedTask> dialog = new Dialog<>();
        dialog.setTitle("Create Task");
        dialog.setHeaderText(null);
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);

        Label l_description =  new Label("Beschreibung: ");
        TextField description = new TextField("");
        HBox hb_description = new HBox();
        hb_description.getChildren().addAll(l_description, description);
        hb_description.setSpacing(10);

        Label l_finishing =  new Label("Ausführung:    ");
        TextField finishing = new TextField("");
        HBox hb_finishing = new HBox();
        hb_finishing.getChildren().addAll(l_finishing, finishing);
        hb_finishing.setSpacing(10);

        TextField wood_type = new TextField("");
        Label l_wood_type =  new Label("Holzart:           ");
        HBox hb_wood_type = new HBox();
        hb_wood_type.getChildren().addAll(l_wood_type, wood_type);
        hb_wood_type.setSpacing(10);

        TextField quality = new TextField("");
        Label l_quality =  new Label("Qualität:          ");
        HBox hb_quality = new HBox();
        hb_quality.getChildren().addAll(l_quality, quality);
        hb_quality.setSpacing(10);

        TextField size = new TextField("");
        Label l_size =  new Label("Dicke:              ");
        HBox hb_size = new HBox();
        hb_size.getChildren().addAll(l_size, size);
        hb_size.setSpacing(10);

        TextField width = new TextField("");
        Label l_width =  new Label("Breite:             ");
        HBox hb_width = new HBox();
        hb_width.getChildren().addAll(l_width, width);
        hb_width.setSpacing(10);

        TextField length = new TextField("");
        Label l_length =  new Label("Länge:             ");
        HBox hb_length = new HBox();
        hb_length.getChildren().addAll(l_length, length);
        hb_length.setSpacing(10);

        TextField quantity = new TextField("");
        Label l_quantity =  new Label("Menge:           ");
        HBox hb_quantity = new HBox();
        hb_quantity.getChildren().addAll(l_quantity, quantity);
        hb_quantity.setSpacing(10);

        dialogPane.setContent(new VBox(8,hb_description,hb_finishing,hb_wood_type,hb_quality,hb_size,hb_width,hb_length,hb_quantity));
        Platform.runLater(description::requestFocus);
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                return new UnvalidatedTask(description.getText(),finishing.getText(),wood_type.getText(),quality.getText(),size.getText(),width.getText(),length.getText(),quantity.getText());
            }
            return null;
        });
        Optional<UnvalidatedTask> unvalidatedResult = dialog.showAndWait();
        unvalidatedResult.ifPresent((UnvalidatedTask results) -> {
            try {
                Task toAdd = taskService.validateTaskInput(results);
                toAdd.setId(currentOrderIndex);
                currentOrderIndex++;
                currentOrderTaskList.add(toAdd);
                table_addedTask.getItems().add(toAdd);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successfully created Task");
                alert.setHeaderText(null);
                alert.setContentText("Task successfully created and added to your Order");
                alert.showAndWait();
            } catch(InvalidInputException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error creating Task");
                alert.setHeaderText(null);
                alert.setContentText("Error creating Task!\nReason: " + e.getMessage());
                alert.showAndWait();
            }
        });

    }
}
