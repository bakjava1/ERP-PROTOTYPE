package at.ac.tuwien.sepm.assignment.group02.server.service;


import at.ac.tuwien.sepm.assignment.group02.server.dao.Lumber;

/**
 * This interface defines all service layer methods needed for product handling.
 */
public interface LumberService {

    /**
     * Hello World get Lumber
     */
    Lumber getLumber(int id);

    /**
     * Hello World add Lumber
     */
    void addLumber(Lumber lumber);

}
