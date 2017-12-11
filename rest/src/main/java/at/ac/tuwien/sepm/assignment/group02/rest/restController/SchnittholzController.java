package at.ac.tuwien.sepm.assignment.group02.rest.restController;

import at.ac.tuwien.sepm.assignment.group02.rest.exceptions.EntityCreationException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.FilterDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.SchnittholzDTO;

import java.util.List;

/**
 * Created by raquelsima on 11.12.17.
 */
public interface SchnittholzController {

    /**
     * 3.2.4 Schnittholz ins Lager hinzufügen.
     * @param schnittholzDTO
     * @throws EntityCreationException
     */
    public void  createSchnittholz(SchnittholzDTO schnittholzDTO) throws EntityCreationException;

    /**
     * Schnittholz aus dem Schnittholzlager entfernen.
     * @param schnittholzDTO
     * @throws EntityCreationException
     */
    public void removeSchnittholz(SchnittholzDTO schnittholzDTO)  throws EntityCreationException;

    /**
     * 2.2.2 & 3.2.5  Hinzugefügtes   Schnittholz   bei   Bedarf   reservieren und als reserviert markieren
     * @param schnittholzDTO
     * @throws EntityCreationException
     */
    public void rserveSchnittholz(SchnittholzDTO schnittholzDTO) throws EntityCreationException;

    /**
     * 2.2.2 Schnittholz bearbeiten. Execute an update in the data base
     * @param schnittholzDTO
     * @throws EntityCreationException
     */
    public void updateSchnittholz(SchnittholzDTO schnittholzDTO)  throws EntityCreationException;

    /**
     * 2.1.2 Eine tabellarische Übersicht des vorhandenen Schnittholz anzeigen.
     * 2.1.3 Suchfunktionalität implementieren
     * @param filterDTO
     * @return a list of Schnittholzer
     * @throws EntityCreationException
     */
    public List<SchnittholzDTO> getAllSchnittholz(FilterDTO filterDTO) throws EntityCreationException;

    /**
     * get Schnitt specified by schnittID
     * @param schnittID
     * @return SchnittDTO specified by schnittID
     * @throws EntityCreationException
     */
    public SchnittholzDTO getSchnittholzByID(int schnittID) throws  EntityCreationException;
}
