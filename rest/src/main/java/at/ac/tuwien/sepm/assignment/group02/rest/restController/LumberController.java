package at.ac.tuwien.sepm.assignment.group02.rest.restController;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.FilterDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;

import java.util.List;

public interface LumberController {

    /**
     * 2.1.2 Eine tabellarische Übersicht des vorhandenen Schnittholz anzeigen.
     * 2.1.3 Suchfunktionalität implementieren
     */
    List<LumberDTO> getAllLumber(FilterDTO filter);

    /**
     * Schnittholz aus dem Schnittholzlager entfernen.
     */
    void removeLumber(LumberDTO lumber);

    /**
     * 2.2.2 & 3.2.5 Schnittholz als reserviert markieren.
     */
    void reserveLumber(LumberDTO lumber);

    /**
     * 3.2.4 Schnittholz ins Lager hinzufügen.
     */
    void createLumber(LumberDTO lumber);

    /**
     * Hello World!
     * get lumber specified by id
     * @param id int id of lumber to get
     * @return LumberDTO specified by id
     */
    LumberDTO getLumberById(int id);

}
