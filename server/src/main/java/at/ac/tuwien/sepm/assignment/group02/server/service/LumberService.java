package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.FilterDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;

import java.sql.SQLException;
import java.util.List;

/**
 * This interface defines all service layer methods needed for lumber handling.
 *
 * Conversion between DTO and entity happens on this layer.
 *
 * Service Layer gets LumberDTO objects from rest Layer
 * and forwards Lumber entity objects to persistence Layer.
 */
public interface LumberService {

    /**
     * 3.2.4 Schnittholz ins Lager hinzufügen.
     */
    void addLumber(LumberDTO lumberDTO) throws ServiceLayerException;

    /**
     * 2.2.2 Schnittholz bearbeiten
     * @param lumberDTO
     * @throws ServiceLayerException
     */
    public void updateLumber(LumberDTO lumberDTO) throws ServiceLayerException, SQLException;

    /**
     * 2.1.2 Eine tabellarische Übersicht des vorhandenen Schnittholz anzeigen.
     * 2.1.3 Suchfunktionalität implementieren
     */
    List<LumberDTO> getAllLumber(FilterDTO filter) throws ServiceLayerException;

    /**
     * 2.2.2 & 3.2.5 Schnittholz als reserviert markieren.
     */
    void reserveLumber(LumberDTO lumberDTO) throws ServiceLayerException;

    /**
     * Schnittholz aus dem Schnittholzlager entfernen.
     */
    void removeLumber(LumberDTO lumberDTO) throws ServiceLayerException, SQLException;

    /**
     * Hello World!
     * get lumber specified by id
     * @param id int id of lumber to get
     * @return LumberDTO specified by id
     */
    LumberDTO getLumberById(int id) throws ServiceLayerException;

}
