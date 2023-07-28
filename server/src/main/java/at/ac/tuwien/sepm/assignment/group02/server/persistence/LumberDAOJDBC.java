package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.FilterDTO;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.EntityNotFoundException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.sql.*;
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
    public List<Lumber> getAllLumber(FilterDTO filter) throws PersistenceLayerException {
        LOG.debug("Get all Lumber from database");

        String Query = "SELECT * FROM LUMBER";
        List<Lumber> lumberList = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        LOG.debug(filter.toString());

        boolean conditions = false;
        String description = filter.getDescription() == null? null :filter.getDescription().toLowerCase();
        String finishing = filter.getFinishing() == null? null: filter.getFinishing().toLowerCase();
        String wood_type = filter.getWood_type() == null? null: filter.getWood_type().toLowerCase();
        String quality = filter.getQuality() == null? null : filter.getQuality().toLowerCase();
        String strength = filter.getSize() == null? null : filter.getSize();
        String width = filter.getWidth() == null? null : filter.getWidth();
        String length = filter.getLength() == null? null : filter.getLength();

        if (description != null && !description.equals("")){
            Query += " WHERE (LOWER(description) LIKE '"+description+"%' OR LOWER(description) LIKE '%"+description+"')";
            conditions = true;

        }
        if (finishing != null && !finishing.equals("")){
            if (conditions) {
                Query += " AND (LOWER(finishing) LIKE '"+finishing+"%' OR LOWER(finishing) LIKE '%"+finishing+"')";
            }else{
                Query += " WHERE (LOWER(finishing) LIKE '"+finishing+"%' OR LOWER(finishing) LIKE '%"+finishing+"')";
                conditions = true;
            }
        }

        if (wood_type  != null && !wood_type.equals("")){
            if (conditions) {
                Query += " AND (LOWER(wood_type) LIKE '"+wood_type+"%' OR LOWER(wood_type) LIKE '%"+wood_type+"')";
            }else{
                Query += " WHERE (LOWER(wood_type) LIKE '"+wood_type+"%' OR LOWER(wood_type) LIKE '%"+wood_type+"')";
                conditions = true;
            }
        }

        if (quality != null && !quality.equals("")){
            if (conditions) {
                Query += " AND (LOWER(quality) LIKE '"+quality+"%' OR LOWER(quality) LIKE '%"+quality+"')";
            }else{
                Query += " WHERE (LOWER(quality) LIKE '"+quality+"%' OR LOWER(quality) LIKE '%"+quality+"')";
                conditions = true;
            }
        }

        if (strength != null && !strength.equals("")){
            if (conditions) {
                Query += " AND size = " + strength;
            }else{
                Query += " WHERE size = " + strength;
                conditions = true;
            }
        }

        if (width != null && !width.equals("")){
            if (conditions) {
                Query += " AND width = " + width;
            }else{
                Query += " WHERE width = " + width;
                conditions = true;
            }
        }
        if (length != null && !length.equals("")){
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
                Lumber lumber = new Lumber();
                lumber.setDescription(rs.getString("description"));
                lumber.setFinishing(rs.getString("finishing"));
                lumber.setWood_type(rs.getString("wood_type"));
                lumber.setQuality(rs.getString("quality"));
                lumber.setSize(rs.getInt("size"));
                lumber.setWidth(rs.getInt("width"));
                lumber.setLength(rs.getInt("length"));
                lumber.setQuantity(rs.getInt("quantity"));
                lumber.setReserved_quantity(rs.getInt("reserved_quantity"));
                lumber.setId(rs.getInt("id"));

                lumberList.add(lumber);
            }

            if (lumberList.size() == 0) {
                //no open order was found
                LOG.debug("lumberList.size == 0");
                return null;
            }

        } catch (SQLException e) {
            LOG.error("SQLException: ", e.getMessage());
            throw new PersistenceLayerException("Database error:"+ e.getMessage());
        }
        return lumberList;
    }

    @Override
    public int getLumberCountForTask(Task toCheck) throws PersistenceLayerException {
        String checkSentence = "SELECT SUM(QUANTITY) , SUM(RESERVED_QUANTITY) FROM LUMBER WHERE " +
                "LOWER(DESCRIPTION) = ? AND LOWER(FINISHING) = ? AND " +
                "WOOD_TYPE LIKE ? AND QUALITY LIKE ? AND " +
                "SIZE = ? AND WIDTH = ? AND LENGTH = ?";
        try {
            PreparedStatement stmt = dbConnection.prepareStatement(checkSentence);
            stmt.setString(1,toCheck.getDescription().toLowerCase());
            stmt.setString(2,toCheck.getFinishing().toLowerCase());
            stmt.setString(3,"%" + toCheck.getWood_type() + "%");
            stmt.setString(4,"%" + toCheck.getQuality() + "%");
            stmt.setInt(5,toCheck.getSize());
            stmt.setInt(6,toCheck.getWidth());
            stmt.setInt(7,toCheck.getLength());
            ResultSet rs = stmt.executeQuery();
            int result = 0;
            if(rs.next()) {
                result += rs.getInt(1);
                result -= rs.getInt(2);
            }
            return result;
        } catch(SQLException e) {
            LOG.error("Database Problem, Reason: " + e.getMessage());
            throw new PersistenceLayerException("Database Problems");
        }
    }

    @Override
    public int createLumber(Lumber lumber) throws PersistenceLayerException {
        LOG.debug("called createLumber: {}", lumber.toString());

        String insertSQL =
                "INSERT INTO LUMBER "+
                        "(ID, description, finishing, wood_type, quality, "+
                        "size, width, length, quantity, reserved_quantity) "+
                        "VALUES " +
                        "(default,?,?,?,?," +
                        "?,?,?,?,?) ";
        try {

            PreparedStatement ps = dbConnection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,lumber.getDescription());
            ps.setString(2, lumber.getFinishing());
            ps.setString(3, lumber.getWood_type());
            ps.setString(4, lumber.getQuality());
            ps.setInt(5, lumber.getSize());
            ps.setInt(6, lumber.getWidth());
            ps.setInt(7, lumber.getLength());
            ps.setInt(8, lumber.getQuantity());
            ps.setInt(9, lumber.getReserved_quantity());
            ps.executeUpdate();

            ResultSet generatedKey = ps.getGeneratedKeys();
            generatedKey.next();

            int newID = generatedKey.getInt(1);
            lumber.setId(newID);
            LOG.debug("product.setId(generatedKey.getInt(1)) {}", lumber.getId());
            generatedKey.close();
            ps.close();

            return newID;

        } catch (SQLException e) {
            LOG.error("SQLException: {}", e.getMessage());
            throw new PersistenceLayerException(e.getMessage());
        }
    }

    @Override
    public Lumber readLumberById(int id) throws PersistenceLayerException {
        LOG.debug("retrieving lumber from database specified by id: {}", id);

        String readSQL = "SELECT * FROM LUMBER WHERE ID = ? LIMIT 1";

        Lumber lumber = new Lumber();

        try {
            PreparedStatement ps = dbConnection.prepareStatement(readSQL);

            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            if(!rs.next()) throw new EntityNotFoundException("");

            lumber.setId(rs.getInt("id"));
            lumber.setDescription(rs.getString("description"));
            lumber.setFinishing(rs.getString("finishing"));
            lumber.setWood_type(rs.getString("wood_type"));
            lumber.setQuality(rs.getString("quality"));
            lumber.setSize(rs.getInt("size"));
            lumber.setWidth(rs.getInt("width"));
            lumber.setLength(rs.getInt("length"));
            lumber.setQuantity(rs.getInt("quantity"));
            lumber.setReserved_quantity(rs.getInt("reserved_quantity"));

            rs.close();
            ps.close();

            LOG.debug("lumber retrieved: {}", lumber.toString());
            return lumber;

        } catch (SQLException e) {
            LOG.error("SQLException: {}", e.getMessage());
            throw new PersistenceLayerException(e.getMessage());
        }
    }

    @Override
    public void updateLumber(Lumber lumber) throws PersistenceLayerException {
        LOG.debug("Entering update Lumber method with parameter:{}" ,lumber);

       // checkIfLumberIsNull(lumber);

        String updateLumber =    "UPDATE Lumber SET quantity=?, reserved_quantity=? WHERE ID=?";

        try {
            PreparedStatement ps = dbConnection.prepareStatement(updateLumber);
            ps.setInt(1, lumber.getQuantity());
            ps.setInt(2, lumber.getReserved_quantity());
            ps.setInt(3, lumber.getId());

            LOG.debug(ps.toString());

            int executeUpdate = ps.executeUpdate();
            LOG.debug("executeUpdate: {}", executeUpdate);

            ps.close();

            /*
            if(!rs.next()){
                LOG.debug("Lumber with id {} doesn't exist.",lumber.getId());
                throw new EntityNotFoundException("Lumber with id "+lumber.getId()+" doesn't exist.");
            }

            int currentQuantity = rs.getInt("quantity");
            int currentReservedQuantity = rs.getInt("reserved_quantity");

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

            dbConnection.commit();
            LOG.debug("Successfuly updated lumber in the table Lumber {}", lumber);
            ps.close();
*/
        } catch (SQLException e){
            LOG.error("SQLException: {}", e.getMessage());
            throw new PersistenceLayerException("Database error");
        }

    }

    @Override
    public void updateLumberAlg(Lumber lumber) {
        LOG.debug("Entering update Lumber method with parameter:{}" ,lumber);

        String updateLumber =    "UPDATE Lumber SET quantity=?, reserved_quantity=? WHERE ID=?";

        try {
            PreparedStatement ps = dbConnection.prepareStatement(updateLumber);
            ps.setInt(1, lumber.getQuantity());
            ps.setInt(2, lumber.getReserved_quantity());
            ps.setInt(3, lumber.getId());

            LOG.debug(ps.toString());

            int executeUpdate = ps.executeUpdate();
            LOG.debug("executeUpdate: {}", executeUpdate);

            ps.close();
        } catch (SQLException e){

        }

    }

    @Override
    public void deleteLumber(Lumber lumber) throws PersistenceLayerException {

        LOG.debug("deleting lumber number {} from database", lumber.getId());


        String selectStatement = "SELECT QUANTITY,RESERVED_QUANTITY FROM LUMBER WHERE ID = ?";
        String deleteLumber=    "UPDATE LUMBER SET QUANTITY = ? , RESERVED_QUANTITY = ? WHERE ID = ?";

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

    @Override
    public void removeLumber(int id, int removedQuantity) throws PersistenceLayerException {
        LOG.debug("removing lumber from database");


        String selectStatement = "SELECT QUANTITY, RESERVED_QUANTITY FROM LUMBER WHERE ID = ?";
        String removeStatement = "UPDATE LUMBER SET RESERVED_QUANTITY = ? WHERE ID = ?";

        try {
            PreparedStatement ps = dbConnection.prepareStatement(selectStatement    );
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int currentQuantity = rs.getInt(1);
            int currentReservedQuantity = rs.getInt(2);

            if(removedQuantity > (currentReservedQuantity)) {
                throw new PersistenceLayerException("Trying to remove more lumber than existing");
            }

            ps = dbConnection.prepareStatement(removeStatement);
            currentQuantity = currentQuantity - removedQuantity;
            currentReservedQuantity = currentReservedQuantity - removedQuantity;
            ps.setInt(1, currentReservedQuantity);
            ps.setInt(2, id);
            ps.executeUpdate();

            ps.close();
        }catch (SQLException e){
            LOG.error("SQLException: {}", e.getMessage());
            throw new PersistenceLayerException("Database error");
        }
    }
}



