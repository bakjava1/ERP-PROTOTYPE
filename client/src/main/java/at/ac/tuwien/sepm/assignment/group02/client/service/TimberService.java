package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Timber;

/**
 * TimberDTO Management / RoundTimber Management
 */
public interface TimberService {

    /**
     * 1.8 Rundholz anlegen
     * 1.8.2 (rest/TimberController) Hinzufügen von neuem Rundholz
     * @param timber
     */
    void addTimber(Timber timber);

}
