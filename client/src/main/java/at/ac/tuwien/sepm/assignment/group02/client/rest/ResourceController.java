package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.client.dao.Lumber;

/**
 * This interface defines all service layer methods needed for product handling.
 */
public interface ResourceController {

    /**
     * Hello World!
     * get lumber specified by id
     * @param id int id of lumber to get
     * @return Lumber specified by id
     */
    Lumber getLumber(int id);

}
