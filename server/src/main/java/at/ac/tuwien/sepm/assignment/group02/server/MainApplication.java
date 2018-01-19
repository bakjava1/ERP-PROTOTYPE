package at.ac.tuwien.sepm.assignment.group02.server;

import at.ac.tuwien.sepm.assignment.group02.server.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PreDestroy;
import java.lang.invoke.MethodHandles;

@SpringBootApplication
@ComponentScan(basePackages = {"at.ac.tuwien.sepm.assignment.group02.server"})
@EnableSwagger2
public class MainApplication {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * Main method begins execution of Java application
     * @param args passed arguments
     */
    public static void main(String[] args) {
        LOG.debug("Application starting with arguments={}", (Object) args);
        DBUtil.setIsFinalDB(false);
        SpringApplication.run(MainApplication.class, args);
    }

    @PreDestroy
    public void stop() throws Exception {
        //DBUtil.dropTable();
        LOG.debug("Closing Database Connection");
        DBUtil.closeConnection();
    }

}
