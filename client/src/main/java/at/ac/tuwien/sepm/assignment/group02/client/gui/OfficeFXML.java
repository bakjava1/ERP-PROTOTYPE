package at.ac.tuwien.sepm.assignment.group02.client.gui;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Timber;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

import static at.ac.tuwien.sepm.assignment.group02.client.MainApplication.orderService;
import static at.ac.tuwien.sepm.assignment.group02.client.MainApplication.timberService;


public class OfficeFXML {
    public static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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
    public void deleteOrder() {
        LOG.trace("called deleteOrder");

        //TODO create order correctly and use orderDTO for REST
        int selectedOrderID = Integer.parseInt(selectedOrder.getText());
        Order order = new Order();
        order.setID(selectedOrderID);

        orderService.deleteOrder(order);
    }

    @FXML
    public void createOrder(ActionEvent actionEvent) {
        Order newOrder = new Order();
        orderService.addOrder(newOrder,null);
    }

    @FXML
    public void addTimber(ActionEvent actionEvent){
        //TODO catch exception (InvalidInputException by Lucia)
        Timber timber = new Timber(cb_timber_box.getSelectionModel().getSelectedIndex()+1, Integer.parseInt(tf_timber_amount.getText()));
        timberService.addTimber(timber);
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
