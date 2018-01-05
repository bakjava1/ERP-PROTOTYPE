package at.ac.tuwien.sepm.assignment.group02.client.gui;

import at.ac.tuwien.sepm.assignment.group02.client.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.client.service.OrderService;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by raquelsima on 04.01.18.
 */
public class RechnungOverviewController {


    @FXML
    private TextField txt_billID;

    @FXML
    private TextField txt_billCostumerName;

    @FXML
    private TextField txt_billTaskAmount;

    @FXML
    private TextField txt_billAmount;

    @FXML
    private TextField txt_billGrossSum;

    @FXML
    TableView<Order> table_bill;
    private OrderService orderService;


    @FXML
    public void initialize(){


    }



}
