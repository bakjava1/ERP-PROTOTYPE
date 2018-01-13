package at.ac.tuwien.sepm.assignment.group02.client;

import at.ac.tuwien.sepm.assignment.group02.client.util.ExampleQSE_SpringFXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.lang.invoke.MethodHandles;

@SpringBootApplication
@ComponentScan(basePackages = {"at.ac.tuwien.sepm.assignment.group02.client"})
public class MainApplication extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static boolean crane = false;
    private static boolean lead = false;
    private static boolean office = false;

    private AnnotationConfigApplicationContext context;


    @Override
    public void start(Stage primaryStage) throws Exception {

        context = new AnnotationConfigApplicationContext();
        context.getBeanFactory().registerSingleton("primaryStage", primaryStage);
        context.scan("at.ac.tuwien.sepm.assignment.group02.client");
        context.refresh();
        context.start();

        // initiate controller, prepare fxml loader to inject controller
        ExampleQSE_SpringFXMLLoader fxmlLoader = context.getBean(ExampleQSE_SpringFXMLLoader.class);

        if(lead) {
            primaryStage.setTitle("Vorarbeiter");
           // primaryStage.setTitle("Lead Worker");

            primaryStage.setScene(new Scene((Parent) fxmlLoader.load("/fxml/lead.fxml"), 831, 634));

        }
        if(office) {
            primaryStage.setTitle("Büroangestellter");
            //primaryStage.setTitle("office");

            primaryStage.setScene(new Scene((Parent) fxmlLoader.load("/fxml/office.fxml"), 900, 550));
        }
        if(crane) {
            primaryStage.setTitle("Kranfahrer");
            //primaryStage.setTitle("Crame Operator");

            primaryStage.setScene(new Scene((Parent) fxmlLoader.load("/fxml/crane.fxml"), 1000, 600));
        }

        primaryStage.centerOnScreen();
        primaryStage.setOnCloseRequest(event -> LOG.debug("Application shutdown initiated"));

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
        context.stop();
        LOG.debug("stop application");
    }
}
