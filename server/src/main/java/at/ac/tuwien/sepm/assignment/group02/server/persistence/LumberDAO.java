package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Filter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;

import java.sql.SQLException;
import java.util.List;

public interface LumberDAO {

    /**
     * hello world
     */
    Lumber readLumberById(int id) throws PersistenceLayerException;

    /**
     * 3.2.4 SchnittholzDAO ins Lager hinzufügen.
     */
    void createLumber(Lumber lumber) throws PersistenceLayerException;

    /**
     * 2.2.2 & 3.2.5 SchnittholzDAO als reserviert markieren.
     */
    void updateLumber(Lumber lumber) throws PersistenceLayerException, SQLException;

    void deleteLumber(Lumber lumber) throws PersistenceLayerException, SQLException;

    /**
     * 2.1.2 & 2.1.3
     * Returns a list of all lumber that matches the filter.
     */
    List<Lumber> getAllLumber(Lumber filter) throws PersistenceLayerException;

}
