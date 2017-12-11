package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.FilterDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.SchnittholzDTO;

import java.util.List;


public interface SchnittholzController {

    /**
     * 3.2.4 Schnittholz ins Lager hinzufügen.
     * @param schnittholzDTO
     * @throws PersistenceLayerException
     */
    public void  createSchnittholz(SchnittholzDTO schnittholzDTO) throws PersistenceLayerException;

    /**
     * Schnittholz aus dem Schnittholzlager entfernen.
     * @param schnittholzDTO
     * @throws PersistenceLayerException
     */
    public void removeSchnittholz(SchnittholzDTO schnittholzDTO)  throws PersistenceLayerException;

    /**
     * 2.2.2 & 3.2.5  Hinzugefügtes   Schnittholz   bei   Bedarf   reservieren und als reserviert markieren
     * @param schnittholzDTO
     * @throws PersistenceLayerException
     */
    public void rserveSchnittholz(SchnittholzDTO schnittholzDTO) throws PersistenceLayerException;

    /**
     * 2.2.2 Schnittholz bearbeiten. Execute an update in the data base
     * @param schnittholzDTO
     * @throws PersistenceLayerException
     */
    public void updateSchnittholz(SchnittholzDTO schnittholzDTO)  throws PersistenceLayerException;

    /**
     * 2.1.2 Eine tabellarische Übersicht des vorhandenen Schnittholz anzeigen.
     * 2.1.3 Suchfunktionalität implementieren
     * @param filterDTO
     * @return a list of Schnittholzer
     * @throws PersistenceLayerException
     */
    public List<SchnittholzDTO> getAllSchnittholz(FilterDTO filterDTO) throws PersistenceLayerException;

    /**
     * get Schnitt specified by schnittID
     * @param schnittID
     * @return SchnittDTO specified by schnittID
     * @throws PersistenceLayerException
     */
    public SchnittholzDTO getSchnittholzByID(int schnittID) throws  PersistenceLayerException;
}
