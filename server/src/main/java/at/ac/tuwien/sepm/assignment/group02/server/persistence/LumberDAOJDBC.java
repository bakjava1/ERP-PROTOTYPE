package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Filter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static at.ac.tuwien.sepm.assignment.group02.server.util.DBUtil.getConnection;

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
    public List<Lumber> getAllLumber(Lumber filter) throws PersistenceLayerException {
        LOG.debug("Get all Lumber from database");


        String Query = "SELECT * FROM LUMBER";
        List<Lumber> lumberList = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;



        boolean conditions = false;
        String description = filter.getDescription();
        String finishing = filter.getFinishing();
        String wood_type = filter.getWood_type();
        String quality = filter.getQuality();
        int strength = filter.getSize();
        int width = filter.getWidth();
        int length = filter.getLength();

        if (description != null && !description.equals("")){
            Query += " WHERE description = '" + description+"'";
            conditions = true;

        }
        if (finishing != null && !finishing.equals("")){
            if (conditions) {
                Query += " AND finishing = '" + finishing+"'";
            }else{
                Query += " WHERE finishing = '" + finishing+"'";
                conditions = true;
            }
        }

        if (wood_type  != null && !wood_type.equals("")){
            if (conditions) {
                Query += " AND wood_type = '" + wood_type+"'";
            }else{
                Query += " WHERE wood_type = '" + wood_type+"'";
                conditions = true;
            }
        }

        if (quality != null && !quality.equals("")){
            if (conditions) {
                Query += " AND quality = '" + quality+"'";
            }else{
                Query += " WHERE quality = '" + quality+"'";
                conditions = true;
            }
        }

        if (strength != -1){
            if (conditions) {
                Query += " AND size = " + strength;
            }else{
                Query += " WHERE size = " + strength;
                conditions = true;
            }
        }

        if (width != -1){
            if (conditions) {
                Query += " AND width = " + width;
            }else{
                Query += " WHERE width = " + width;
                conditions = true;
            }
        }
        if (length != -1){
            if (conditions) {
                Query += " AND length = " + length;
            }else{
                Query += " WHERE length = " + length;
                conditions = true;
            }
        }





        try {

            //connect to db
            ps = dbConnection.prepareStatement(Query);
            rs = ps.executeQuery();

            while (rs.next()) {

                Lumber currentLumber = new Lumber(rs.getString("description"),rs.getString("finishing"),
                        rs.getString("wood_type"),rs.getString("quality"),
                        rs.getInt("size"),rs.getInt("width"),rs.getInt("length"),
                        rs.getInt("quantity"),rs.getInt("reserved_quantity"));


                lumberList.add(currentLumber);
            }

            if (lumberList.size() == 0) {
                //no open order was found
                return null;
            }

        } catch (SQLException e) {
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

        return lumberList;
    }

    @Override
    public void createLumber(Lumber lumber) throws PersistenceLayerException {
        /*LOG.debug("adding product to database: {}", lumber);

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
        */
    }

    @Override
    public Lumber readLumberById(int id) throws PersistenceLayerException {
        /*
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
        */
        return new Lumber();
    }

    @Override
    public void updateLumber(Lumber lumber) throws PersistenceLayerException, SQLException {

        /*
        LOG.debug("Entering update Lumber method with parameter" +lumber);

        dbConnection.setAutoCommit(false);

        String updateLumber="UPDATE LUMBER SET NAME=1 WHERE ID=?";

        try {
            PreparedStatement ps = dbConnection.prepareStatement(updateLumber);
            ps.setString(1,lumber.getContent());
            ps.executeUpdate();
            ps.close();

        }catch (SQLException e){
            LOG.error("SQLException: {}", e.getMessage());
            throw new PersistenceLayerException("Database error");
        } finally {
            if (getConnection() != null) {
                getConnection().close();
            }
        }

        */
    }

    @Override
    public void deleteLumber(Lumber lumber) throws PersistenceLayerException, SQLException {
        /*
        LOG.debug("deleting lumber number {} from database", lumber.getId());

        dbConnection.setAutoCommit(false);

        String deleteLumber= "DELETE FROM LUMBER WHERE ID=?";

        try {
            PreparedStatement ps = dbConnection.prepareStatement(deleteLumber);
            ps.setInt(1, lumber.getId());
            ps.executeUpdate();

            ps.close();
        }catch (SQLException e){
            LOG.error("SQLException: {}", e.getMessage());
            throw new PersistenceLayerException("Database error");
        } finally {
        if (getConnection() != null) {
            getConnection().close();
        }
    }
    */

    }

}
