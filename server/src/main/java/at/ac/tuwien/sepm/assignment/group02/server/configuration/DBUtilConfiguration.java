package at.ac.tuwien.sepm.assignment.group02.server.configuration;

import at.ac.tuwien.sepm.assignment.group02.server.util.DBUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;

@Configuration
public class DBUtilConfiguration {

    @Bean
    public Connection getDBConnection() {
        return DBUtil.getConnection();
    }


}