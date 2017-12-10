package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Filter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.util.List;

/**
 * Created by e0701149 on 20.11.17.
 */
@Component
public class LumberDAOJDBC implements LumberDAO {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static Connection dbConnection;


    @Autowired
    public LumberDAOJDBC(Connection dbConnection){
        LumberDAOJDBC.dbConnection = dbConnection;
    }


    @Override
    public void createLumber(Lumber lumber) throws PersistenceLayerException {
        LOG.debug("adding product to database: {}", lumber);

        String insertSQL =
                "INSERT INTO LUMBER " +
                        "(ID, NAME)" +
                        "VALUES" +
                        "(default,?)";

        try {
            PreparedStatement ps = dbConnection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,lumber.getContent());
            ps.executeUpdate();

            ResultSet generatedKey = ps.getGeneratedKeys();
            generatedKey.next();
            lumber.setId(generatedKey.getInt(1));
            LOG.debug("product.setId(generatedKey.getInt(1)) {}", lumber.getId());
            generatedKey.close();
            ps.close();

        } catch (SQLException e) {
            LOG.error("SQLException: {}", e.getMessage());
            throw new PersistenceLayerException(e.getMessage());
        }
    }

    @Override
    public Lumber readLumberById(int id) throws PersistenceLayerException {
        LOG.debug("retrieving lumber from database specified by id: {}", id);

        String readSQL = "SELECT " +
                "ID, NAME " +
                "FROM LUMBER WHERE ID = ? LIMIT 1";

        Lumber lumber = new Lumber(id,"");

        try {
            PreparedStatement ps = dbConnection.prepareStatement(readSQL);

            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            rs.next();
            lumber.setId(rs.getInt(1));
            lumber.setContent(rs.getString(2));
            rs.close();
            ps.close();

            LOG.debug("product retrieved: {}", lumber.toString());
            return lumber;

        } catch (SQLException e) {
            LOG.error("SQLException: {}", e.getMessage());
            throw new PersistenceLayerException(e.getMessage());
        }
    }

    @Override
    public void updateLumber(Lumber lumber) throws PersistenceLayerException {

    }

    @Override
    public void deleteLumber(int id) throws PersistenceLayerException {

    }

    @Override
    public List<Lumber> getAllLumber(Filter filter) throws PersistenceLayerException {
        return null;
    }

}
