package at.ac.tuwien.sepm.assignment.group02.client;

import at.ac.tuwien.sepm.assignment.group02.client.gui.LoginFXML;
import at.ac.tuwien.sepm.assignment.group02.client.gui.OfficeFXML;
import at.ac.tuwien.sepm.assignment.group02.client.service.LumberService;
import at.ac.tuwien.sepm.assignment.group02.client.service.LumberServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.client.service.OrderService;
import at.ac.tuwien.sepm.assignment.group02.client.service.OrderServiceImpl;
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

    @Override
    public void start(Stage primaryStage) throws Exception {

        // setup application
        primaryStage.setTitle("Manage Products");
        primaryStage.centerOnScreen();
        primaryStage.setOnCloseRequest(event -> LOG.debug("Application shutdown initiated"));

        /*
        // initiate controller
        LoginFXML loginFXML = new LoginFXML();

        // prepare fxml loader to inject controller
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        fxmlLoader.setControllerFactory(param -> param.isInstance(loginFXML) ? loginFXML : null);
        primaryStage.setScene(new Scene(fxmlLoader.load()));
        */
        //TODO undo
        OfficeFXML officeFXML = new OfficeFXML();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/office.fxml"));
        fxmlLoader.setControllerFactory(param -> param.isInstance(officeFXML) ? officeFXML : null);
        primaryStage.setScene(new Scene(fxmlLoader.load()));

        // show application
        primaryStage.show();
        primaryStage.toFront();

        LOG.debug("Application startup complete");
    }

    public static void main(String[] args) {
        LOG.debug("Application starting with arguments={}", (Object) args);
        Application.launch(MainApplication.class, args);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        LOG.debug("stop application");
    }
}
