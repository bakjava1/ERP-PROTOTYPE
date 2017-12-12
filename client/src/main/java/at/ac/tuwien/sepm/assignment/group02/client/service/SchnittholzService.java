package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Filter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Schnittholz;


import java.util.List;

/**
 * Created by raquelsima on 11.12.17.
 */
public interface SchnittholzService {

    /**
     * 3.2.4 Schnittholz ins Lager hinzufügen.
     * @param schnittholz
     * @throws InvalidInputException
     */
    public void  createSchnittholz(Schnittholz schnittholz) throws InvalidInputException;

    /**
     * Schnittholz aus dem Schnittholzlager entfernen.
     * @param schnittholz
     * @throws InvalidInputException
     */
    public void removeSchnittholz(Schnittholz schnittholz)  throws InvalidInputException;

    /**
     * 2.2.2 & 3.2.5  Hinzugefügtes   Schnittholz   bei   Bedarf   reservieren und als reserviert markieren
     * @param schnittholz
     * @param quantity
     * @throws InvalidInputException
     */
    public void rserveSchnittholz(Schnittholz schnittholz, int quantity) throws InvalidInputException;

    /**
     * 2.2.2 Schnittholz bearbeiten. Execute an update in the data base
     * @param schnittholz
     * @throws InvalidInputException
     */
    public void updateSchnittholz(Schnittholz schnittholz)  throws InvalidInputException;

    /**
     * 2.1.2 Eine tabellarische Übersicht des vorhandenen Schnittholz anzeigen.
     * 2.1.3 Suchfunktionalität implementieren
     * @param filter
     * @return a list of Schnittholzer
     * @throws InvalidInputException
     */
    public List<Schnittholz> getAllSchnittholz(Filter filter) throws InvalidInputException;

    /**
     * get Schnitt specified by schnittID
     * @param schnittID
     * @return Schnittholz specified by schnittID
     * @throws InvalidInputException
     */
    public Schnittholz getSchnittholzByID(int schnittID) throws  InvalidInputException;
}
