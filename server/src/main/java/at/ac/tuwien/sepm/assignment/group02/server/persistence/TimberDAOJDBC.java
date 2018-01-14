package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.server.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class TimberDAOJDBC implements TimberDAO{

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static Connection dbConnection;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    @Autowired
    public TimberDAOJDBC(Connection dbConnection) {
        TimberDAOJDBC.dbConnection = dbConnection;
    }

    @Override
    public void createTimber(Timber timber) throws PersistenceLayerException {
        LOG.debug("Creating new Timber");

        String selectSentence = "SELECT AMOUNT FROM TIMBER WHERE ID = ?";
        String updateSentence = "UPDATE TIMBER SET AMOUNT=? WHERE ID = ?";

        try{
            stmt = dbConnection.prepareStatement(selectSentence);
            stmt.setInt(1, timber.getBox_id());
            rs = stmt.executeQuery();

            rs.next();
            int currentAmount = rs.getInt(1);

            int newAmount = currentAmount + timber.getAmount();

            stmt = dbConnection.prepareStatement(updateSentence);
            stmt.setInt(1, newAmount);
            stmt.setInt(2, timber.getBox_id());
            stmt.execute();


        } catch(SQLException e) {
            LOG.error("SQL Exception: " + e.getMessage());
            throw new PersistenceLayerException("Database Error");
        }
        finally {
            closeStatement();
        }

    }

    @Override
    public void removeTimber(Timber timber) throws PersistenceLayerException {
        LOG.debug("called updateTimber");

        String selectSentence = "SELECT AMOUNT FROM TIMBER WHERE ID = ?";
        String updateSentence = "UPDATE TIMBER SET AMOUNT=? WHERE ID = ?";

        try{
            stmt = dbConnection.prepareStatement(selectSentence);
            stmt.setInt(1, timber.getBox_id());
            rs = stmt.executeQuery();

            rs.next();
            int currentAmount = rs.getInt(1);

            int newAmount = currentAmount - timber.getAmount();

            if(newAmount<0){
                LOG.warn("not enough timber to remove the requested amount");
                throw new PersistenceLayerException("Nicht ausreichend Rundholz im Lager.");
            }

            stmt = dbConnection.prepareStatement(updateSentence);
            stmt.setInt(1, newAmount);
            stmt.setInt(2, timber.getBox_id());
            stmt.execute();

        } catch(SQLException e) {
            LOG.error("SQL Exception: " + e.getMessage());
            throw new PersistenceLayerException("Database Error");
        }
        finally {
            closeStatement();
        }
    }

    @Override
    public int getNumberOfBoxes() throws PersistenceLayerException {
        int numberOfBoxes = 0;
        String countSentence = "SELECT COUNT(*) FROM TIMBER";

        try {
            stmt = dbConnection.prepareStatement(countSentence);
            rs = stmt.executeQuery();
            rs.next();
            numberOfBoxes = rs.getInt(1);
        } catch (SQLException e) {
            LOG.error("SQL Exception: " + e.getMessage());
            throw new PersistenceLayerException("Database Error");
        }
        finally {
            closeStatement();
        }
        return numberOfBoxes;
    }

    @Override
    public List<Timber> getAllBoxes() throws PersistenceLayerException {


        List<Timber> timberList = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            //connect to db
            ps = dbConnection.prepareStatement("SELECT * FROM Timber");
            rs = ps.executeQuery();

            while (rs.next()) {

                Timber currentTimber = new Timber();

                currentTimber.setBox_id(rs.getInt("ID"));
                currentTimber.setWood_type(rs.getString("wood_type"));
                currentTimber.setFestmeter(rs.getDouble("festmeter"));
                currentTimber.setAmount(rs.getInt("amount"));
                currentTimber.setLength(rs.getInt("length"));
                currentTimber.setQuality(rs.getString("quality"));
                currentTimber.setDiameter(rs.getInt("diameter"));
                currentTimber.setPrice(rs.getInt("price"));
                currentTimber.setLast_edited(rs.getString("last_edited"));


                timberList.add(currentTimber);
            }

            if (timberList.size() == 0) {
                //no open order was found
                throw new PersistenceLayerException("No open orders found");
            }

        } catch (SQLException e) {
            LOG.error("SQL Exception: " +  e.getMessage());
            throw new PersistenceLayerException("Database error");
        } finally {
            //close connections
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* ignored */}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { /* ignored */}
            }

        }
        return timberList;
    }

    @Override
    public List<Timber> getBoxesForTask(Task toCheck) throws PersistenceLayerException {

        List<String> qualities = convertLumberQualityToTimberQuality(toCheck.getQuality());
        List<Timber> boxList  = new ArrayList<>();
        String selectSentence = "SELECT * FROM TIMBER WHERE LENGTH = ? AND QUALITY = ? AND WOOD_TYPE LIKE ?";

        try{
            stmt = dbConnection.prepareStatement(selectSentence);
            stmt.setInt(1,toCheck.getLength());
            stmt.setString(3,toCheck.getWood_type() + "%");
            for(int i = 0; i < qualities.size();i++) {
                stmt.setString(2,qualities.get(i));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    Timber toAdd = new Timber();
                    toAdd.setBox_id(rs.getInt(1));
                    toAdd.setWood_type(rs.getString(2));
                    toAdd.setFestmeter(rs.getDouble(3));
                    toAdd.setAmount(rs.getInt(4));
                    toAdd.setMAX_AMOUNT(rs.getInt(5));
                    toAdd.setLength(rs.getInt(6));
                    toAdd.setQuality(rs.getString(7));
                    toAdd.setDiameter(rs.getInt(8));
                    toAdd.setPrice(rs.getInt(9));
                    toAdd.setLast_edited(rs.getString(10));
                    boxList.add(toAdd);
                }
            }
        } catch (SQLException e) {
            LOG.error("SQL Exception: " + e.getMessage());
            throw new PersistenceLayerException("Database Error");
        }
        return boxList;
    }

    private void closeStatement() throws PersistenceLayerException {
        try {
            if(stmt != null)
                stmt.close();
            if(rs!=null)
                rs.close();
        } catch (SQLException e) {
            LOG.error("SQL Exception: " + e.getMessage());
            throw new PersistenceLayerException("Database Error");
        }
    }

    private List<String> convertLumberQualityToTimberQuality(String quality) {
        List<String> temp = new ArrayList<>();
        if(quality.equals("O")) {
            temp.add("A");
        }
        if(quality.equals("I")) {
            temp.add("A");
            temp.add("B");
        }
        if(quality.equals("II")) {
            temp.add("A");
            temp.add("B");
            temp.add("C");
        }
        if(quality.equals("III")) {
            temp.add("B");
            temp.add("C");
            temp.add("CX");
        }
        if(quality.equals("IV")) {
            temp.add("C");
            temp.add("CX");
        }
        if(quality.equals("V")) {
            temp.add("CX");
        }
        if(quality.equals("O/III")) {
            temp.add("A");
            temp.add("B");
            temp.add("C");
            temp.add("CX");
        }
        if(quality.equals("III/IV") || quality.equals("III/V")) {
            temp.add("B");
            temp.add("C");
            temp.add("CX");
        }
        return temp;
    }
}
