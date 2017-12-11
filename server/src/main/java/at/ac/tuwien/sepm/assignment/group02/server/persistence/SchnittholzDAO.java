package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Filter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Schnittholz;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;

import java.util.List;

/**
 * Created by raquelsima on 11.12.17.
 */
public interface SchnittholzDAO {

    /**
     * 3.2.4 SchnittholzDAO ins Lager hinzufügen.
     * @param schnittholz
     * @throws PersistenceLayerException
     */
    public void createSchnittholz(Schnittholz schnittholz) throws PersistenceLayerException;

    /**
     * retrieve one schnittholz
     * @param schnittID
     * @return  the schnittholz
     * @throws PersistenceLayerException
     */
    public Schnittholz readSchnittholzByID(int schnittID) throws  PersistenceLayerException;

    /**
     * 2.2.2 & 3.2.5 SchnittholzDAO als reserviert markieren.
     * @param schnittholz
     * @throws PersistenceLayerException
     */
    public void updateSchnittholz(Schnittholz schnittholz)  throws PersistenceLayerException;

    /**
     * Schnittholz delete from database
     * @param schnittholz
     * @throws PersistenceLayerException
     */
    public void deleteSchnittholz(Schnittholz schnittholz)  throws PersistenceLayerException;

    /**
     * 2.1.2. Eine   tabellarische   Übersicht   des   vorhandenen   Schnittholz   anzeigen.
     * 2.1.3. Suchfunktionalität   implementieren
     * @param filter
     * @return Returns a list of all lumber that matches the filter.
     * @throws PersistenceLayerException
     */
    public List<Schnittholz> getAllSchnittholz(Filter filter) throws PersistenceLayerException;
}
