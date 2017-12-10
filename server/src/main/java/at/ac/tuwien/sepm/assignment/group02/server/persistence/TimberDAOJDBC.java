package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Timber;
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

    @Autowired
    public TimberDAOJDBC(Connection dbConnection) {
        TimberDAOJDBC.dbConnection = dbConnection;
    }

    @Override
    public void createTimber(Timber timber) {
        LOG.debug("Creating new Timber");

        String selectSentence = "SELECT AMOUNT FROM TIMBER WHERE ID = ?";
        String updateSentence = "UPDATE TIMBER SET AMOUNT=? WHERE ID = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            stmt = dbConnection.prepareStatement(selectSentence);
            stmt.setInt(1, timber.getBox_id());
            rs = stmt.executeQuery();

            rs.next();
            int currentAmount = rs.getInt(1);

            int newAmount = currentAmount + timber.getAmount();
            System.out.println("the new amount is " + newAmount);

            stmt = dbConnection.prepareStatement(updateSentence);
            stmt.setInt(1, newAmount);
            stmt.setInt(2, timber.getBox_id());
            stmt.execute();


        } catch(SQLException e) {

            LOG.error("SQL Exception: " + e.getMessage());

            //throw new PersistenceLayerException("Database Error");

        }
        finally {
            try {
                stmt.close();
                rs.close();
            } catch (SQLException e) {
                LOG.error("SQL Exception: " + e.getMessage());
            }
        }

    }

    @Override
    public void updateTimber(Timber timber){

    }
}
