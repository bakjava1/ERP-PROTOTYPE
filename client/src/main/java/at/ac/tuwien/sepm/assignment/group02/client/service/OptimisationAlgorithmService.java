package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.entity.OptAlgorithmResult;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;

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

    ArrayList<Task> getSelectedTasksMock();

    Timber getSelectedTimberMock();

    /**
     * starts the optimisation algorithm and returns the optimal result
     * for the selected task by the user
     * @param task task selected by user
     * @return optimal result including: 3 tasks, 1 timber and the cut view
     */
    OptAlgorithmResult getOptAlgorithmResult(Task task) throws PersistenceLayerException;

}
