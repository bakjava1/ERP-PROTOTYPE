package at.ac.tuwien.sepm.assignment.group02.server;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.*;
import at.ac.tuwien.sepm.assignment.group02.server.service.*;
import at.ac.tuwien.sepm.assignment.group02.server.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.invoke.MethodHandles;

@SpringBootApplication
public class MainApplication {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static LumberService lumberService = new LumberServiceImpl(new LumberDAOJDBC(DBUtil.getConnection()));
    public static OrderService orderService = new OrderServiceImpl(new OrderDAOJDBC(DBUtil.getConnection()));
    public static TaskService taskService = new TaskServiceImpl(new TaskDAOJDBC(DBUtil.getConnection()));
    public static TimberService timberService = new TimberServiceImpl(new TimberDAOJDBC(DBUtil.getConnection()));

    /**
     * Main method begins execution of Java application
     * @param args passed arguments
     */
    public static void main(String[] args) {
        LOG.debug("Application starting with arguments={}", (Object) args);

        SpringApplication.run(MainApplication.class, args);

        lumberService.addLumber(new LumberDTO(1, "hallo"));
        lumberService.addLumber(new LumberDTO(2, "hallohallo"));
        lumberService.addLumber(new LumberDTO(3, "hallohallohallo"));
    }

    /*
    //TODO how to stop the server?
    public void stop() throws Exception {
        //DBUtil.dropTable();
        LOG.debug("Closing Database Connection");
        DBUtil.closeConnection();
    }
    */
}
