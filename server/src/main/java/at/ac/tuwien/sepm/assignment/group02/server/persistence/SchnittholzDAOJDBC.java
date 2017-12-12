package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Filter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Schnittholz;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static at.ac.tuwien.sepm.assignment.group02.server.util.DBUtil.getConnection;

/**
 * Created by raquelsima on 11.12.17.
 */
public class SchnittholzDAOJDBC implements SchnittholzDAO {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static Connection dbConnection;


    public SchnittholzDAOJDBC(Connection dbConnection){

        SchnittholzDAOJDBC.dbConnection=dbConnection;
    }

    @Override
    public void createSchnittholz(Schnittholz schnittholz) throws PersistenceLayerException {

    }

    @Override
    public Schnittholz readSchnittholzByID(int schnittID) throws PersistenceLayerException {
        return null;
    }

    @Override
    public void updateSchnittholz(Schnittholz schnittholz) throws PersistenceLayerException, SQLException {


            dbConnection.setAutoCommit(false);

        String sql = "UPDATE SCHNITTHOLZ SET LAGER=?,DESCRIPTION=?,FINISHING=?,WOOD_TYPE=?,QUALITY=?," +
                "SIZE=?,WIDTH=?,LENGTH=?,QUANTITY=?,RESERVED_QUANTITY=?,DELIVERED_QUANTITY=?,ALL_RESERVED=?," +
                "ALL_DELIVERED=? WHERE WOOD_TYPE=?";
            try {


                PreparedStatement ps = getConnection().prepareStatement(sql);
                //ps.setString(1, String.valueOf(schnittholz));
                ps.setString(1,schnittholz.getLager());
                ps.setString(2,schnittholz.getDescription());
                ps.setString(3,schnittholz.getFinishing());
                ps.setString(4,schnittholz.getWood_type());
                ps.setString(5,schnittholz.getQuality());
                ps.setInt(6,schnittholz.getSize());
                ps.setInt(7,schnittholz.getWidth());
                ps.setInt(8,schnittholz.getLength());
                ps.setInt(9,schnittholz.getQuantity());
                ps.setInt(10,schnittholz.getReserved_quantity());
                ps.setInt(11,schnittholz.getDelivered_quantity());
                ps.setBoolean(12, schnittholz.isAll_reserved());
                ps.setBoolean(13,schnittholz.isAll_delivered());

                int i=ps.executeUpdate();
                if (i!=0){
                    LOG.info("UPDATED OK");
                }else {
                    LOG.info("UPDATED NOT OK");
                }

            } catch (Exception e) {
                e.printStackTrace();
                getConnection().rollback();
            } finally {
                if (getConnection() != null) {
                    getConnection().close();
                }
            }
        }

    @Override
    public void deleteSchnittholz(Schnittholz schnittholz) throws PersistenceLayerException, SQLException {
        LOG.debug("deleting lumber number {} from database", schnittholz.getSchnittID());

        dbConnection.setAutoCommit(false);

        String deleteLumber= "DELETE FROM SCHNITTHOLZ WHERE ID=?";

        try {
            PreparedStatement ps = dbConnection.prepareStatement(deleteLumber);
            ps.setInt(1, schnittholz.getSchnittID());
            int i=ps.executeUpdate();
            // ps.close();
            if(i!=0){
                LOG.info("DELETED OK");
            }else {
                LOG.info("DELETED NOT OK");
            }
        }catch (SQLException e){
            LOG.error("SQLException: {}", e.getMessage());
            throw new PersistenceLayerException("Database error");
        } finally {
            if (getConnection() != null) {
                getConnection().close();
            }
        }

    }

    @Override
    public List<Schnittholz> getAllSchnittholz(Filter filter) throws PersistenceLayerException {
        return null;
    }
}
