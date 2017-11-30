package at.ac.tuwien.sepm.assignment.group02.rest.restController;


import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;

public interface TimberController {

    /**
     * 1.8.2 Hinzufügen von neuem Rundholz
     */
    void createTimber(TimberDTO timber);

    /**
     * 3.2.3 Rundholz aus dem Lager entfernen.
     */
    void deleteTimber(TimberDTO timber);
}
