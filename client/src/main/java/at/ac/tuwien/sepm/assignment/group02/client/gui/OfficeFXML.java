package at.ac.tuwien.sepm.assignment.group02.client.gui;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

import static at.ac.tuwien.sepm.assignment.group02.client.MainApplication.orderService;

public class OfficeFXML {
    public static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FXML private TextField selectedOrder;
    @FXML private Button deleteOrderButton;

    @FXML
    public void deleteOrder() {
        LOG.trace("called deleteOrder");
 
        //TODO create order correctly and use orderDTO for REST
        int selectedOrderID = Integer.parseInt(selectedOrder.getText());
        Order order = new Order();
        order.setID(selectedOrderID);

        orderService.deleteOrder(order);
    }
}
