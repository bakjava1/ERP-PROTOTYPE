package at.ac.tuwien.sepm.assignment.group02.client.gui;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Timber;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

import static at.ac.tuwien.sepm.assignment.group02.client.MainApplication.orderService;
import static at.ac.tuwien.sepm.assignment.group02.client.MainApplication.timberService;


public class OfficeFXML {
    public static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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
        Order newOrder = new Order();
        newOrder.setCustomerName(tf_order_customername.getText());
        newOrder.setCustomerAddress(tf_order_customeraddress.getText());
        newOrder.setCustomerUID(tf_order_customerUID.getText());
        LOG.debug(newOrder.toString());
        /*try {
            orderService.addOrder(newOrder,null);
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
        }*/

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
    }
}
