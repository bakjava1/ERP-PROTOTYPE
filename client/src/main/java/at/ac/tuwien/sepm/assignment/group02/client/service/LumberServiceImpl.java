package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.LumberController;
import at.ac.tuwien.sepm.assignment.group02.rest.converter.LumberConverter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Filter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
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
    public List<Lumber> getAll(Filter filter) {
        return null;
    }

    @Override
    public void reserveLumber(Lumber lumber, int quantity) {

    }

    @Override
    public void deleteLumber(Lumber lumber) throws ServiceLayerException {

        LOG.debug("deleteLumber called: {}", lumber);
        LumberDTO lumberToDelete = lumberConverter.convertPlainObjectToRestDTO(lumber);
        try {
            lumberController.removeLumber(lumberToDelete);
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
        }
    }

    @Override
    public void updateLumber(Lumber lumber) throws ServiceLayerException {

        LOG.debug("updateLumber called: {}", lumber);
        LumberDTO toUpdate = lumberConverter.convertPlainObjectToRestDTO(lumber);
        try {
            lumberController.updateLumber(toUpdate);
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
        }

    }
}
