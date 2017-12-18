package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.server.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ResourceNotFoundException;
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

/**
 * Created by e0701149 on 20.11.17.
 */
@Component
public class LumberDAOJDBC implements LumberDAO {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private Connection dbConnection;


    @Autowired
    public LumberDAOJDBC(Connection dbConnection){
        this.dbConnection = dbConnection;
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
                Lumber currentLumber = new Lumber();
                currentLumber.setDescription(rs.getString("description"));
                currentLumber.setFinishing(rs.getString("finishing"));
                currentLumber.setWood_type(rs.getString("wood_type"));
                currentLumber.setQuality(rs.getString("quality"));
                currentLumber.setSize(rs.getInt("size"));
                currentLumber.setWidth(rs.getInt("width"));
                currentLumber.setLength(rs.getInt("length"));
                currentLumber.setQuantity(rs.getInt("quantity"));
                currentLumber.setReserved_quantity(rs.getInt("reserved_quantity"));

                lumberList.add(currentLumber);
            }

            if (lumberList.size() == 0) {
                //no open order was found
                return null;
            }

        } catch (SQLException e) {
            throw new PersistenceLayerException("Database error");
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
            ps.setString(1,lumber.getDescription());
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
    public void updateLumber(Lumber lumber) throws PersistenceLayerException {
        LOG.debug("Entering update Lumber method with parameter:{}" ,lumber);
        checkIfLumberIsNull(lumber);

        try {
            dbConnection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String selectStatement = "SELECT QUANTITY,RESERVED_QUANTITY FROM LUMBER WHERE ID = ?";
        String updateLumber="UPDATE Lumber SET lager=?, description=?, finishing=?, wood_type=?, quality=?," +
                "size=?, length=?, width=?, quantity=?, reserved_quantity=?, delivered_quantity=?,all_reserved=?,all_delivered=? WHERE ID=?";

        try {
            PreparedStatement ps = dbConnection.prepareStatement(updateLumber);
            ResultSet rs= dbConnection.createStatement().executeQuery(selectStatement);
            ps.setInt(1,lumber.getId());

            int currentQuantity=-1;
            int currentReservedQuantity=-1;

            if(!rs.next()){
                LOG.debug("Lumber with id {} doesn't exist.",lumber.getId());
            }
            if(rs.next()) {
                currentQuantity = rs.getInt(1);
                currentReservedQuantity = rs.getInt(2);
            }
            if(lumber.getQuantity() >currentQuantity || lumber.getQuantity() < currentReservedQuantity) {

                throw new PersistenceLayerException(" ERROR: Trying to save a  negative value of lumber ");
            }else {
                if (lumber.getQuantity() > currentQuantity || lumber.getQuantity() > currentReservedQuantity){
                    throw new PersistenceLayerException(" UPDATE successfuly ");
                }

            }
            currentQuantity -= lumber.getQuantity();
            currentReservedQuantity -= lumber.getQuantity();


            ps = dbConnection.prepareStatement(updateLumber);
            ps.setInt(1, currentQuantity);
            ps.setInt(2,currentReservedQuantity);
            ps.setInt(3,lumber.getId());
            ps.executeUpdate();
           /* ps.setInt(1,lumber.getId());
            ps.setString(2,lumber.getLager());
            ps.setString(3,lumber.getDescription());
            ps.setString(4, lumber.getFinishing());
            ps.setString(5, lumber.getWood_type());
            ps.setString(6, lumber.getQuality());
            ps.setInt(7, lumber.getSize());
            ps.setInt(8, lumber.getLength());
            ps.setInt(9, lumber.getWidth());
            ps.setInt(10, lumber.getQuantity());
            ps.setInt(11, lumber.getReserved_quantity());
            ps.setInt(12, lumber.getDelivered_quantity());
            ps.setBoolean(13, lumber.isAll_reserved());
            ps.setBoolean(14, lumber.isAll_delivered());
            ps.executeUpdate();*/
            dbConnection.commit();
            LOG.debug("Successfuly updated lumber in the table Lumber {}", lumber);
            ps.close();

        }catch (SQLException e){
            LOG.error("SQLException: {}", e.getMessage());
            throw new PersistenceLayerException("Database error");
        }
        try {
            dbConnection.rollback();

    }catch (SQLException e1){
            LOG.error("SQLException: {}", e1.getMessage());
            throw new PersistenceLayerException("Database error");
        }
            }

    @Override
    public void deleteLumber(Lumber lumber) throws PersistenceLayerException {

        LOG.debug("deleting lumber number {} from database", lumber.getId());


        String selectStatement = "SELECT QUANTITY,RESERVED_QUANTITY FROM LUMBER WHERE ID = ?";
        String deleteLumber= "UPDATE LUMBER SET QUANTITY = ? , RESERVED_QUANTITY = ? WHERE ID = ?";

        try {
            PreparedStatement ps = dbConnection.prepareStatement(selectStatement);
            ps.setInt(1,lumber.getId());
            ResultSet rs = ps.executeQuery();
            int currentQuantity = -1;
            int currentReservedQuantity = -1;
            if(rs.next()) {
                currentQuantity = rs.getInt(1);
                currentReservedQuantity = rs.getInt(2);
            }
            if(lumber.getQuantity() > currentQuantity || lumber.getQuantity() > currentReservedQuantity) {
                throw new PersistenceLayerException("Trying to delete more Lumber than existing");
            }
            currentQuantity -= lumber.getQuantity();
            currentReservedQuantity -= lumber.getQuantity();

            ps = dbConnection.prepareStatement(deleteLumber);
            ps.setInt(1, currentQuantity);
            ps.setInt(2,currentReservedQuantity);
            ps.setInt(3,lumber.getId());
            ps.executeUpdate();

            ps.close();
        }catch (SQLException e){
            LOG.error("SQLException: {}", e.getMessage());
            throw new PersistenceLayerException("Database error");
        }
    }


    private void checkIfLumberIsNull(Lumber lumber) throws ResourceNotFoundException {
               if(lumber == null){
                        LOG.debug("Lumber is null.");
                        throw new ResourceNotFoundException("Lumber can't be null.");
                    }
            }
    }


