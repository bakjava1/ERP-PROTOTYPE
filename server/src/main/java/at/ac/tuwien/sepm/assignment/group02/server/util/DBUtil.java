package at.ac.tuwien.sepm.assignment.group02.server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by e0701149 on 20.11.17.
 */

public class DBUtil {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static Connection con = null;

    public static Connection getConnection(){
        LOG.debug("called getConnection");

        if (con == null) {
            con = openConnection();
        }
        return con;
    }

    private static Connection openConnection() {
        LOG.debug("called openConnection");

        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            LOG.error("ERROR: failed to load H2 JDBC driver.");
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection("jdbc:h2:~/smartholzDB;INIT=runscript from 'classpath:create.sql'",
                    "sa", "");
        } catch (SQLException e) {
            LOG.error("ERROR: SQLException{}",e);
            e.printStackTrace();
        }

        return connection;
    }

    public static void dropDB(){
//TODO
    }

    public static void initDB(String filepath){
//TODO
    }

    public static void closeConnection(){
        LOG.debug("called closeConnection");
        try{
            if(con!=null) {
                con.close();
                con = null;
            }
        } catch (SQLException e){
            LOG.error("ERROR: SQLException{}",e);
            e.printStackTrace();
        }
    }
}
