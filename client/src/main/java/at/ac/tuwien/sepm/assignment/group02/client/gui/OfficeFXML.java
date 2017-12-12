package at.ac.tuwien.sepm.assignment.group02.client.gui;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.service.OrderService;
import at.ac.tuwien.sepm.assignment.group02.client.service.TaskService;
import at.ac.tuwien.sepm.assignment.group02.client.service.TimberService;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Timber;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Controller
public class OfficeFXML {
    public static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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

    @Autowired
    public OfficeFXML(OrderService orderService, TimberService timberService, TaskService taskService){
        this.orderService = orderService;
        this.timberService = timberService;
        this.taskService = taskService;
    }


    @FXML
    void initialize() {
        col_orderID.setCellValueFactory(new PropertyValueFactory("ID"));
        table_openOrder.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        updateTable();
    }

    @FXML
    public void deleteOrder() {
        LOG.trace("called deleteOrder");

        Order order = new Order();

        if(table_openOrder.getSelectionModel().getSelectedItem() != null) {
            order.setID(table_openOrder.getSelectionModel().getSelectedItem().getID());

            Task task = new Task();
            task.setOrderID(order.getID());

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
        Order newOrder = new Order();
        try {
            orderService.addOrder(newOrder,null);
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
        Timber timber = new Timber(cb_timber_box.getSelectionModel().getSelectedIndex()+1, Integer.parseInt(tf_timber_amount.getText()));
        try {
            timberService.addTimber(timber);
        } catch (InvalidInputException e) {
            LOG.error("Invalid Input Error: " + e.getMessage());
        } catch (ServiceLayerException e) {
            LOG.warn(e.getMessage());
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

}
