package at.ac.tuwien.sepm.assignment.group02.rest.restController;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;

import java.util.List;

public interface AssignmentController {

    /**
     * 2.4.4 Neue Aufgabe für Kranfahrer erstellen.
     */
    void createAssignment(AssignmentDTO assignmentDTO);

    /**
     * 3.1.2 Alle nicht erledigten Aufgaben anfordern.
     */
    List<AssignmentDTO> getAllOpenAssignments();

    /**
     * 3.2.2 Aufgabe als erledigt markieren.
     */
    void updateAssignment(AssignmentDTO assignmentDTO);

}
