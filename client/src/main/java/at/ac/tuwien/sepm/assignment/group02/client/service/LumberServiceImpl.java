package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.LumberController;
import at.ac.tuwien.sepm.assignment.group02.client.rest.OrderController;
import at.ac.tuwien.sepm.assignment.group02.rest.converter.LumberConverter;
import at.ac.tuwien.sepm.assignment.group02.rest.converter.OrderConverter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Filter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Schnittholz;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.SchnittholzDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.LinkedList;
import java.util.List;

@Service
public class LumberServiceImpl implements LumberService {


    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    private static LumberController lumberController;
    private static LumberConverter lumberConverter;

    @Autowired
    public LumberServiceImpl (LumberController lumberController, LumberConverter lumberConverter){
        LumberServiceImpl.lumberController = lumberController;
        LumberServiceImpl.lumberConverter = lumberConverter;
    }

    /**
     * HELLO WORLD example
     *
     * @param id
     * @return
     */
    @Override
    public Lumber getLumber(int id) {

        LumberConverter lumberConverter = new LumberConverter();

        LumberDTO lumberDTO = null;
        try {
            lumberDTO = lumberController.getLumberById(id);
        } catch (PersistenceLayerException e) {
            e.printStackTrace();
        }
        Lumber lumber = lumberConverter.convertRestDTOToPlainObject(lumberDTO);

        return lumber;

    }


    @Override
    public List<Lumber> getAll(Lumber filter) {

        LOG.debug("getAllSchnittholz called");
        List<LumberDTO> allLumber = null;

        LumberDTO filterDTO = lumberConverter.convertPlainObjectToRestDTO(filter);
        try {
            allLumber = lumberController.getAllLumber(filterDTO);
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
        }

        List<Lumber> allLumberConverted = new LinkedList<>();


        for (LumberDTO lumber: allLumber) {
            allLumberConverted.add(lumberConverter.convertRestDTOToPlainObject(lumber));
        }

        return allLumberConverted;
    }

    @Override
    public void reserveLumber(Lumber lumber, int quantity) {

    }


}
