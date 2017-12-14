package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Timber;
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
    public void updateTimber(Timber timber){

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

    private void closeStatement() throws PersistenceLayerException {
        try {
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            LOG.error("SQL Exception: " + e.getMessage());
            throw new PersistenceLayerException("Database Error");
        }
    }
}
