package at.ac.tuwien.sepm.assignment.group02.client;

import at.ac.tuwien.sepm.assignment.group02.client.gui.*;
import at.ac.tuwien.sepm.assignment.group02.client.service.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public final class MainApplication extends Application {

    public static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static LumberService lumberService = new LumberServiceImpl();
    public static OrderService orderService = new OrderServiceImpl();
    public static TimberService timberService = new TimberServiceImpl();
    public static TaskService taskService = new TaskServiceImpl();

    public static boolean crane = false;
    public static boolean lead = false;
    public static boolean office = false;

    @Override
    public void start(Stage primaryStage) throws Exception {

        // setup application
        primaryStage.centerOnScreen();
        primaryStage.setOnCloseRequest(event -> LOG.debug("Application shutdown initiated"));

        // initiate controller, prepare fxml loader to inject controller
        FXMLLoader fxmlLoader = null;
        LeadWorkerFXML leadWorkerFXML = new LeadWorkerFXML();
        OfficeFXML officeFXML = new OfficeFXML();
        CraneOperatorFXML craneOperatorFXML = new CraneOperatorFXML();

        if(lead) {
            primaryStage.setTitle("Lead Worker");
            fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/lead.fxml"));
            fxmlLoader.setControllerFactory(param -> param.isInstance(leadWorkerFXML) ? leadWorkerFXML : null);
        }
        if(office) {
            primaryStage.setTitle("Office");
            fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/office.fxml"));
            fxmlLoader.setControllerFactory(param -> param.isInstance(officeFXML) ? officeFXML : null);
        }
        if(crane) {
            primaryStage.setTitle("Crane Operator");
            fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/crane.fxml"));
            fxmlLoader.setControllerFactory(param -> param.isInstance(craneOperatorFXML) ? craneOperatorFXML : null);
        }

        primaryStage.setScene(new Scene(fxmlLoader.load()));
        if(office) {
            officeFXML.initStage();
        }
        // show application
        primaryStage.show();
        primaryStage.toFront();

        LOG.debug("Application startup complete");
    }

    public static void main(String[] args) {
        LOG.debug("Application starting with arguments={}", (Object) args);
        if(args[0].equals("crane")) {
            //crane
            crane = true;
        } else if(args[0].equals("office")) {
            //office
            office = true;
        } else if(args[0].equals("lead")) {
            //lead
            lead = true;
        } else {
            //unknown client error
            return;
        }
        Application.launch(MainApplication.class, args);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        LOG.debug("stop application");
    }
}
