package at.ac.tuwien.sepm.assignment.group02.client.gui;

import at.ac.tuwien.sepm.assignment.group02.client.MainApplication;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.service.OrderService;
import at.ac.tuwien.sepm.assignment.group02.client.service.TaskService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by raquelsima on 10.01.18.
 */
public class RechnungOverviewController {
    public static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static Order currentOrder = new Order();
    private static List<Task> currentOrderTaskList = new ArrayList<>();
    private static int currentOrderIndex = 1;
    private static int currentOrderSum = 0;


    @FXML
    private Button bt_rechnungAnzeigen;
    @FXML
    private Label l_sumorders;

    @FXML
    private TableColumn col_taskQuantity;

    @FXML
    private TableColumn col_taskDescription;

    @FXML
    private TableColumn col_taskNr;
    @FXML
    private AnchorPane printPage;
    @FXML
    private TableColumn col_grossSum;

    @FXML
    private TableView<Task> task_table;
    @FXML
    private TableView<Order> bill_table;

    @FXML
    private TableColumn col_taskLength;

    @FXML
    private TableColumn col_taskWidth;

    @FXML
    private TableColumn col_taskSize;
    @FXML
    private TableColumn col_billID;

    @FXML
    private TableColumn col_billCostumerName;

    @FXML
    private TableColumn col_billTaskAmount;

    @FXML
    private TableColumn col_billAmount;
    @FXML
    private TableColumn col_billPrice;

    @FXML
    private TableColumn col_billGrossSum;
    @FXML
    private Label sumNet;
    @FXML
    private Label sumGross;
    @FXML
    private Label sumTax;
    @FXML
    private Label address;
    @FXML
    private Label nameL;
    @FXML
    private Label uid;
    @FXML
    private Label invoiceNumber;
    @FXML
    private Label date;

    private TaskService taskService;
    private OrderService orderService;


    @Autowired
    public RechnungOverviewController(TaskService taskService, OrderService orderService){
        this.taskService=taskService;
        this.orderService=orderService;
    }

@FXML
    public void initialize(){
    bill_table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    col_billID.setCellValueFactory(new PropertyValueFactory<Order, Integer>("ID"));
    col_billCostumerName.setCellValueFactory(new PropertyValueFactory<Order, String>("customerName"));
    col_billTaskAmount.setCellValueFactory(new PropertyValueFactory<Order, Integer>("taskAmount"));
    col_billAmount.setCellValueFactory(new PropertyValueFactory<Order, Integer>("quantity"));
    col_billGrossSum.setCellValueFactory(new PropertyValueFactory<Order, Integer>("grossAmount"));


    task_table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    col_taskNr.setCellValueFactory(new PropertyValueFactory<Task, Integer>("id"));
    col_taskQuantity.setCellValueFactory(new PropertyValueFactory<Task, Integer>("quantity"));
    col_taskDescription.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
    col_taskSize.setCellValueFactory(new PropertyValueFactory<Task, Integer>("size"));
    col_taskWidth.setCellValueFactory(new PropertyValueFactory<Task, Integer>("width"));
    col_taskLength.setCellValueFactory(new PropertyValueFactory<Task, Integer>("length"));



    ObservableList<Task> orderTasks = FXCollections.observableArrayList();
    task_table.setItems(orderTasks);
    ObservableList<Order> orders=FXCollections.observableArrayList();
    bill_table.setItems(orders);

    l_sumorders.setText(currentOrderSum + " €");
    updateBillTable();


}

    @FXML
    public void rechnungAnzeigen(ActionEvent actionEvent){

        LOG.info("RechnungAnzeigenBtn clicked");

        if (bill_table.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        Order order = bill_table.getSelectionModel().getSelectedItem();


        try {

           //  order=new Order();
            FXMLLoader fxmlLoader = new FXMLLoader(OfficeFXML.class.getResource("/fxml/rechnungOverview.fxml"));




            Stage stage = new Stage();
            stage.setTitle("Detail Ansicht Rechnung");
            stage.setWidth(680);
            stage.setHeight(900);
            stage.centerOnScreen();
            fxmlLoader.setController(this);

            stage.setScene(new Scene(fxmlLoader.load()));

            stage.show();
            nameL.setText(order.getCustomerName());
            address.setText(order.getCustomerAddress());
            uid.setText(order.getCustomerUID());
            date.setText(""+order.getInvoiceDate());
            sumNet.setText("€ " +order.getNetAmount());
            sumTax.setText("€ "+order.getTaxAmount());
            sumGross.setText("€ "+order.getGrossAmount());
            invoiceNumber.setText("Rechnung #"+order.getID());

            // falta la tablatask

            PrinterJob printerJob = PrinterJob.createPrinterJob();
            if (printerJob.showPrintDialog(stage)) {
                printerJob.printPage(printPage);
            }


        } catch (IOException e) {
            LOG.error(e.getMessage());

        }

    }



    private void updateBillTable() {

        List<Order> allClosed = null;
        ObservableList<Order> closedOrderForTable;

        try {
            allClosed = orderService.getAllClosed();
        } catch (ServiceLayerException e) {
            LOG.warn(e.getMessage());
        }

        if (allClosed != null) {

            closedOrderForTable = FXCollections.observableArrayList();

            for (Order bill: allClosed) {
                closedOrderForTable.add(bill);
            }

            bill_table.setItems(closedOrderForTable);
            bill_table.refresh();
        } else {
            bill_table.refresh();
        }

    }

}
