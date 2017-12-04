package at.ac.tuwien.sepm.assignment.group02.client;

import at.ac.tuwien.sepm.assignment.group02.client.gui.*;
import at.ac.tuwien.sepm.assignment.group02.client.service.LumberService;
import at.ac.tuwien.sepm.assignment.group02.client.service.LumberServiceImpl;
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
    public static boolean crane = false;
    public static boolean lead = false;
    public static boolean office = false;


    @Override
    public void start(Stage primaryStage) throws Exception {

        // setup application
        primaryStage.setTitle("Manage Products");
        primaryStage.centerOnScreen();
        primaryStage.setOnCloseRequest(event -> LOG.debug("Application shutdown initiated"));

        // initiate controller, prepare fxml loader to inject controller
        FXMLLoader fxmlLoader = null;
        if(lead) {
            LeadWorkerFXML leadWorkerFXML = new LeadWorkerFXML();
            fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/lead.fxml"));
            fxmlLoader.setControllerFactory(param -> param.isInstance(leadWorkerFXML) ? leadWorkerFXML : null);
        }
        if(office) {
            OfficeFXML officeFXML = new OfficeFXML();
            fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/office.fxml"));
            fxmlLoader.setControllerFactory(param -> param.isInstance(officeFXML) ? officeFXML : null);
        }
        if(crane) {
            CraneOperatorFXML craneOperatorFXML = new CraneOperatorFXML();
            fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/crane.fxml"));
            fxmlLoader.setControllerFactory(param -> param.isInstance(craneOperatorFXML) ? craneOperatorFXML : null);
        }

        primaryStage.setScene(new Scene(fxmlLoader.load()));

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
