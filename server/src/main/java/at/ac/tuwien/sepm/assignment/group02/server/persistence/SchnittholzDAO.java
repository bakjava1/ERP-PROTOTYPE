package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Filter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Schnittholz;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLevelException;

import java.util.List;

/**
 * Created by raquelsima on 11.12.17.
 */
public interface SchnittholzDAO {

    /**
     * 3.2.4 SchnittholzDAO ins Lager hinzufügen.
     * @param schnittholz
     * @throws PersistenceLevelException
     */
    public void createSchnittholz(Schnittholz schnittholz) throws PersistenceLevelException;

    /**
     * retrieve one schnittholz
     * @param schnittID
     * @return  the schnittholz
     * @throws PersistenceLevelException
     */
    public Schnittholz readSchnittholzByID(int schnittID) throws  PersistenceLevelException;

    /**
     * 2.2.2 & 3.2.5 SchnittholzDAO als reserviert markieren.
     * @param schnittholz
     * @throws PersistenceLevelException
     */
    public void updateSchnittholz(Schnittholz schnittholz)  throws PersistenceLevelException;

    /**
     * Schnittholz delete from database
     * @param schnittholz
     * @throws PersistenceLevelException
     */
    public void deleteSchnittholz(Schnittholz schnittholz)  throws PersistenceLevelException;

    /**
     * 2.1.2. Eine   tabellarische   Übersicht   des   vorhandenen   Schnittholz   anzeigen.
     * 2.1.3. Suchfunktionalität   implementieren
     * @param filter
     * @return Returns a list of all lumber that matches the filter.
     * @throws PersistenceLevelException
     */
    public List<Schnittholz> getAllSchnittholz(Filter filter) throws PersistenceLevelException;
}
