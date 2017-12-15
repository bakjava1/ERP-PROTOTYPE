package at.ac.tuwien.sepm.assignment.group02.client.gui;

import at.ac.tuwien.sepm.assignment.group02.client.entity.UnvalidatedTask;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.service.CostBenefitService;
import at.ac.tuwien.sepm.assignment.group02.client.service.OrderService;
import at.ac.tuwien.sepm.assignment.group02.client.service.TaskService;
import at.ac.tuwien.sepm.assignment.group02.client.service.TimberService;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Task;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class OfficeFXML {
    public static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static Order currentOrder = new Order();
    private static List<Task> currentOrderTaskList = new ArrayList<>();
    private static int currentOrderIndex = 1;
    private static int currentOrderSum = 0;

    @FXML
    private Label l_sumorders;

    @FXML
    private Label kn_result;

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
    private TextField tf_timber_amount;

    @FXML
    private ChoiceBox cb_timber_box;

    @FXML
    private TableColumn col_orderID;

    @FXML
    TableView<Order> table_openOrder;

    private OrderService orderService;
    private TimberService timberService;
    private TaskService taskService;
    private CostBenefitService costBenefitService;

    @Autowired
    public OfficeFXML(OrderService orderService, TimberService timberService, TaskService taskService, CostBenefitService costBenefitService){
        this.orderService = orderService;
        this.timberService = timberService;
        this.taskService = taskService;
        this.costBenefitService = costBenefitService;
    }


    @FXML
    void initialize() {
        table_openOrder.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

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
        l_sumorders.setText(currentOrderSum + " €");

        initTimberTab();
        updateTable();
    }

    @FXML
    public void deleteOrder() {
        LOG.trace("called deleteOrder");

        Order order = new Order();

        if(table_openOrder.getSelectionModel().getSelectedItem() != null) {
            order.setID(table_openOrder.getSelectionModel().getSelectedItem().getID());

            Task task = new Task();
            task.setOrder_id(order.getID());

            try {
                orderService.deleteOrder(order);
                taskService.deleteTask(task);
            } catch (InvalidInputException e) {
                //InvalidInputException is never thrown
                //the only user input is to select an order
                //LOG.warn(e.getMessage());
            } catch (ServiceLayerException e) {
                LOG.warn(e.getMessage());
            }
        } else {
            Alert noSelection = new Alert(Alert.AlertType.ERROR);
            noSelection.setTitle("Deletion failed");
            noSelection.setHeaderText(null);
            noSelection.setContentText("You have to choose an order to delete it!");
            noSelection.showAndWait();
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
            clearCurrentOrder(null);
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("Creation successful");
            success.setHeaderText(null);
            success.setContentText("Order created successfully!");
            success.showAndWait();
        } catch (InvalidInputException e) {
            LOG.warn(e.getMessage());
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Creation failed");
            error.setHeaderText(null);
            error.setContentText("Order Creation failed!");
            error.showAndWait();
        } catch (ServiceLayerException e) {
            LOG.warn(e.getMessage());
        }

        updateTable();
    }


    private void updateTable() {

        List<Order> allOpen = null;
        try {
            allOpen = orderService.getAllOpen();
        } catch (ServiceLayerException e) {
            LOG.warn(e.getMessage());
        }

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
        if(tf_timber_amount.getText().isEmpty() || cb_timber_box.getSelectionModel().getSelectedIndex()==-1){
            LOG.error("No Input");

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText("No Input");
            alert.setContentText("Please use only valid input!");
            alert.showAndWait();
        }
        else{
            int tes = cb_timber_box.getSelectionModel().getSelectedIndex();
            Timber timber = new Timber(cb_timber_box.getSelectionModel().getSelectedIndex()+1, Integer.parseInt(tf_timber_amount.getText()));
            try {
                timberService.addTimber(timber);
            } catch (InvalidInputException e) {
                LOG.error("Invalid Input Error: " + e.getMessage());
            } catch (ServiceLayerException e) {
                LOG.warn(e.getMessage());
            }
        }
    }

    //initialize the timberTab
    public void initTimberTab() {
        ObservableList<String> boxes = FXCollections.observableArrayList();
        int numberOfBoxes = 0;
        try {
            numberOfBoxes = timberService.getNumberOfBoxes();
        } catch (ServiceLayerException e) {
            LOG.warn(e.getMessage());
        }
        for(int i=0; i<numberOfBoxes; i++){
            boxes.add("Box " + (i+1));
        }

        cb_timber_box.setItems(boxes);
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
        size.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    size.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        Label l_size =  new Label("Dicke:              ");
        HBox hb_size = new HBox();
        hb_size.getChildren().addAll(l_size, size);
        hb_size.setSpacing(10);

        TextField width = new TextField("");
        width.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    width.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        Label l_width =  new Label("Breite:             ");
        HBox hb_width = new HBox();
        hb_width.getChildren().addAll(l_width, width);
        hb_width.setSpacing(10);

        TextField length = new TextField("");
        length.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    length.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        Label l_length =  new Label("Länge:             ");
        HBox hb_length = new HBox();
        hb_length.getChildren().addAll(l_length, length);
        hb_length.setSpacing(10);

        TextField quantity = new TextField("");
        quantity.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    quantity.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        Label l_quantity =  new Label("Menge:           ");
        HBox hb_quantity = new HBox();
        hb_quantity.getChildren().addAll(l_quantity, quantity);
        hb_quantity.setSpacing(10);

        TextField cost = new TextField("");
        cost.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    cost.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        Label l_cost =  new Label("Preis:                ");
        HBox hb_cost = new HBox();
        hb_cost.getChildren().addAll(l_cost, cost);
        hb_cost.setSpacing(10);

        dialogPane.setContent(new VBox(8,hb_description,hb_finishing,hb_wood_type,hb_quality,hb_size,hb_width,hb_length,hb_quantity,hb_cost));
        Platform.runLater(description::requestFocus);
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                return new UnvalidatedTask(description.getText(),finishing.getText(),wood_type.getText(),quality.getText(),size.getText(),width.getText(),length.getText(),quantity.getText(),cost.getText());
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
                currentOrderSum += toAdd.getPrice();
                l_sumorders.setText(currentOrderSum + " €");
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

    public void editOrderBtnClicked(ActionEvent actionEvent) {
        LOG.info("editOrderBtn clicked");

        //get the selected order
        Order selectedOrder = table_openOrder.getSelectionModel().getSelectedItem();

        if(selectedOrder!= null) {
            try {
                EditOrderFXML editOrderFXML = new EditOrderFXML();
                FXMLLoader fxmlLoader = new FXMLLoader(OfficeFXML.class.getResource("/fxml/edit_order.fxml"));
                fxmlLoader.setControllerFactory(param -> param.isInstance(editOrderFXML) ? editOrderFXML : null);

                Stage stage = new Stage();
                stage.setTitle("Edit Order");
                stage.setWidth(500);
                stage.setHeight(750);
                stage.centerOnScreen();
                stage.setScene(new Scene(fxmlLoader.load()));
                stage.show();
                editOrderFXML.setSelectedOrder(selectedOrder);


            } catch (IOException e) {
                LOG.error(e.getMessage());
            }
        }
    }

    public void invoiceBtnClicked(ActionEvent actionEvent) {
        LOG.info("invoice Button clicked");

        //get the selected order
        Order selectedOrder = table_openOrder.getSelectionModel().getSelectedItem();

        try {
            orderService.invoiceOrder(selectedOrder);
        } catch (InvalidInputException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error creating Invoice!");
            alert.setHeaderText(null);
            alert.setContentText("Error while trying to invoice Order!\nReason: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void clearCurrentOrder(ActionEvent actionEvent) {
        ObservableList<Task> orderTasks = FXCollections.observableArrayList();
        table_addedTask.setItems(orderTasks);
        currentOrder = new Order();
        currentOrderTaskList = new ArrayList<>();
        tf_order_customername.setText("");
        tf_order_customeraddress.setText("");
        tf_order_customerUID.setText("");
        currentOrderSum = 0;
        l_sumorders.setText(currentOrderSum + " €");
        kn_result.setText("");
    }

    public void initiateCostValueFunction(ActionEvent actionEvent) {
        if(currentOrderTaskList.size() == 0) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("No Tasks to evaluate");
            error.setHeaderText(null);
            error.setContentText("There is no Task added to the Order!\nPlease add Tasks to proceed to Cost/Benefit Function");
            error.showAndWait();
            return;
        }
        int randomized = costBenefitService.costValueFunctionStub(currentOrderSum);
        if(randomized < 0) {
            kn_result.setTextFill(Color.web("#dd0000"));
            kn_result.setText(randomized+"");
        } else {
            kn_result.setTextFill(Color.web("#00dd00"));
            kn_result.setText(randomized+"");
        }
    }
}
