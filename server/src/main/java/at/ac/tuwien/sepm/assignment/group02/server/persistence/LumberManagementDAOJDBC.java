package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLevelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by e0701149 on 20.11.17.
 */
public class LumberManagementDAOJDBC implements LumberManagementDAO {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static Connection dbConnection;


    public LumberManagementDAOJDBC(Connection dbConnection){
        LumberManagementDAOJDBC.dbConnection = dbConnection;
    }


    @Override
    public boolean helloWorld() throws PersistenceLevelException {
        LOG.debug("called helloWorld");

        String helloWorldSQL =
                "SELECT * FROM LUMBER";

        boolean b = false;

        try {

            PreparedStatement ps = dbConnection.prepareStatement(helloWorldSQL);
            ResultSet rs = ps.executeQuery();
            b = rs.next();
            ps.close();

        } catch (SQLException e) {
            LOG.error("SQLException: {}", e.getMessage());
            throw new PersistenceLevelException(e.getMessage());
        }

        return b;

    }


}
