package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Filter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Lumber;

import java.util.List;

/**
 * CONVERSION HAPPENS ON THIS LAYER
 * VALIDATION HAPPENS ON THIS LAYER
 */
public interface LumberService {

    /**
     * 2.1 Übersicht + Suchfunktion
     * 2.1.2 (rest/LumberController) Eine tabellarische Übersicht des vorhandenen Schnittholz anzeigen.
     * 2.1.3 (rest/LumberController) Suchfunktionalität implementieren
     * @return
     */
    List<Lumber> getAll(Lumber filter) throws InvalidInputException, ServiceLayerException;

    /**
     * 2.2 Reservierung Schnittholz
     * 2.2.1 Schnittholz aus einer Übersicht für Reservierung auswählen
     * 2.2.2 (rest/LumberController) Ausgewähltes Schnittholz als reserviert markieren.
     * 2.2.3 (rest/TaskController) Reserviertes Schnittholz dem Auftrag hinzufügen.
     * 2.2.4 Überprüfen ob Auftrag fertig ist.
     * @param lumber
     * @param quantity
     */
    void reserveLumber(Lumber lumber, int quantity) throws InvalidInputException, ServiceLayerException;


    // just an example
    Lumber getLumber(int id) throws InvalidInputException, ServiceLayerException;
}