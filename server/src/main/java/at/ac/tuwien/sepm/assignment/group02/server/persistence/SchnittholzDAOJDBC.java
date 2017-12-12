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
                ps.setString(1, String.valueOf(schnittholz));
                ps.executeUpdate();

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
    public void deleteSchnittholz(Schnittholz schnittholz) throws PersistenceLayerException {

    }

    @Override
    public List<Schnittholz> getAllSchnittholz(Filter filter) throws PersistenceLayerException {
        return null;
    }
}
