package at.ac.tuwien.sepm.assignment.group02.server.util;

import at.ac.tuwien.sepm.assignment.group02.server.MainApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by e0701149 on 20.11.17.
 */

public class DBUtil {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static Connection con = null;

    public static Connection getConnection(){
        LOG.debug("called getConnection");

        boolean isFinalDB = true;
        if (con == null) {
            con = openConnection();
            // insert test data to database
            initDB(isFinalDB);
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
            connection = DriverManager.getConnection("jdbc:h2:~/smartholzDB", "sa", "");
        } catch (SQLException e) {
            LOG.error("ERROR: SQLException{}",e);
            e.printStackTrace();
        }
        return connection;
    }

    private static void initDB(boolean isFinalDB){
        if(isFinalDB){
            //create final database
            LOG.debug("creating final database");
            executeSQLFile("server/src/main/resources/createFinalDB.sql");
        }
        else{
            //create test database
            LOG.debug("creating test database");
            executeSQLFile("server/src/main/resources/createTestDB.sql");
            //fill database with test data
            LOG.debug("filling test database with test data");
            executeSQLFile("server/src/main/resources/testData.sql");
        }
    }

    private static void executeSQLFile(String filepath){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filepath));
            String line = "";
            String command = "";
            while ((line = br.readLine()) != null) {
                for(char c : line.toCharArray()){
                    if(c != ';'){
                        command += c;
                    }
                    else{
                        command += c;
                        PreparedStatement stmt = con.prepareStatement(command);
                        stmt.execute();
                        stmt.close();
                        command = "";
                    }
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
