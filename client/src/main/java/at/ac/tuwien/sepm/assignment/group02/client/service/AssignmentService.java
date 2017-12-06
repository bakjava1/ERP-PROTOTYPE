package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Assignment;

import java.util.List;

public interface AssignmentService {

    /**
     * 2.4.4 Neue Aufgabe für Kranfahrer erstellen.
     * @param assignment
     */
    void createAssignment(Assignment assignment) throws InvalidInputException;

    /**
     * 3.1 Aufgaben anzeigen
     * 3.1.2 (rest/AssignmentController) Eine tabellarische Übersicht der nicht erledigten Aufgaben anzeigen.
     * @return
     */
    List<Assignment> getAllOpenAssignments();

    /**
     * 3.2 Aufgaben abschließen
     * 3.2.2 (rest/AssignmentController) Aufgabe als erledigt markieren.
     * 3.2.3 (rest/TimberController) Rundholz aus dem Lager entfernen.
     * 3.2.4 (rest/LumberController) Schnittholz ins Lager hinzufügen.
     * 3.2.5 (rest/LumberController) Hinzugefügtes Schnittholz bei Bedarf reservieren.
     * 3.2.6 (rest/TaskController) Reserviertes Schnittholz dem Auftrag hinzufügen.
     * 3.2.7 Überprüfen ob Auftrag fertig ist (? => (rest/TaskController) getTaskById)
     * @param assignment
     */
    void setDone(Assignment assignment) throws InvalidInputException;

}
