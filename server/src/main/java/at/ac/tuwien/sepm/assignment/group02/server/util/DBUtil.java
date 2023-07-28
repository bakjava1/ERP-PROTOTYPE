package at.ac.tuwien.sepm.assignment.group02.server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.sql.*;


public class DBUtil {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static Connection con = null;

    private static boolean isFinalDB = false;

    public static void setIsFinalDB(boolean finalDB){
        isFinalDB = finalDB;
    }

    public static Connection getConnection(){
        LOG.debug("called getConnection");

        if (con == null) {
            if(isFinalDB){
                con = openFinalConnection();
                // insert test data to database
            }
            else{
                con = openTestConnection();
            }
            initDB(isFinalDB);
        }
        return con;
    }

    private static Connection openTestConnection() {
        LOG.debug("called openConnection");

        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            LOG.error("ERROR: failed to load H2 JDBC driver.");
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection("jdbc:h2:~/smartholzTestDB", "sa", "");
        } catch (SQLException e) {
            LOG.error("ERROR: SQLException{}",e);
            e.printStackTrace();
        }
        return connection;
    }

    private static Connection openFinalConnection() {
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

    public static void initDB(boolean isFinalDB) {
        if(isFinalDB){
            //create final database
            LOG.debug("creating final database");
            //executeSQLFile("server/src/main/resources/createFinalDB.sql");
            String filepath = DBUtil.class
                    .getClassLoader()
                    .getResource("createFinalDB.sql").getPath();
            executeSQLFile(filepath);
            fillFinalDatabaseIfEmpty();
        }
        else{
            //create test database
            LOG.debug("creating test database");
            //executeSQLFile("server/src/main/resources/createTestDB.sql");
            String filepath1 = DBUtil.class
                    .getClassLoader()
                    .getResource("createTestDB.sql").getPath();
            executeSQLFile(filepath1);

            //fill database with test data
            LOG.debug("filling test database with test data");
            //executeSQLFile("server/src/main/resources/testData.sql");
            String filepath2 = DBUtil.class
                    .getClassLoader()
                    .getResource("testData.sql").getPath();
            executeSQLFile(filepath2);
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

    private static void fillFinalDatabaseIfEmpty(){
        boolean alreadyFilled = false;
        try{
            String query = "SELECT 1 FROM ORDERS";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = null;

            rs = pst.executeQuery();

            if(rs.next()){
                alreadyFilled = true;
            }
            if(rs!=null){
                rs.close();
            }
            pst.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }

        if(!alreadyFilled) {
            LOG.debug("Start filling final database");
            String filepath = DBUtil.class
                    .getClassLoader()
                    .getResource("insertFinalData.sql").getPath();
            executeSQLFile(filepath);
            LOG.debug("Finished filling final database with");
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
