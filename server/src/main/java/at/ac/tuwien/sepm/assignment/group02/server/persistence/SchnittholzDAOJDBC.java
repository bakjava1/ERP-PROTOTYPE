package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Filter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Schnittholz;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLevelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.util.List;

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
    public void createSchnittholz(Schnittholz schnittholz) throws PersistenceLevelException {

    }

    @Override
    public Schnittholz readSchnittholzByID(int schnittID) throws PersistenceLevelException {
        return null;
    }

    @Override
    public void updateSchnittholz(Schnittholz schnittholz) throws PersistenceLevelException {

    }

    @Override
    public void deleteSchnittholz(Schnittholz schnittholz) throws PersistenceLevelException {

    }

    @Override
    public List<Schnittholz> getAllSchnittholz(Filter filter) throws PersistenceLevelException {
        return null;
    }
}
