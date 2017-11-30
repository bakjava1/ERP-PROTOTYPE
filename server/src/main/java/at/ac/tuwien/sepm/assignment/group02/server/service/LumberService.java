package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;

/**
 * This interface defines all service layer methods needed for product handling.
 */
public interface LumberService {

    /**
     * Hello World get LumberDTO
     */
    LumberDTO getLumber(int id);

    /**
     * Hello World add LumberDTO
     */
    void addLumber(LumberDTO lumber);

}
