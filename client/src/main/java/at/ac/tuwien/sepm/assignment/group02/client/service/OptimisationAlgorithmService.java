package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Timber;

import java.util.ArrayList;

public interface OptimisationAlgorithmService {

    /**
     * 2.3 Schnittbild Optimierung
     * 2.3.4 Implementieren des Branch des Optimierungsalgorithmus
     * 2.3.5 Implementierung des Bound des Optimierungsalgorithmus
     *
     * 2.4 Anzeigen Ergebnis von Algorithmus + Erstellung neuer Aufgaben für Kranfahrer
     * 2.4.3 Impementieren der Anzeige des Schnittbilds
     * 2.4.4 (rest/AssignmentController) Neue Aufgabe für Kranfahrer erstellen.
     */

    public ArrayList<Task> getSelectedTasksMock();

    public Timber getSelectedTimberMock();

}
