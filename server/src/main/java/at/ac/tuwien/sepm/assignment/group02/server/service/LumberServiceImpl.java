package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.rest.converter.LumberConverter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.FilterDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.LumberDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Service
public class LumberServiceImpl implements LumberService {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static LumberDAO lumberManagementDAO;
    private static LumberConverter lumberConverter;

    @Autowired
    public LumberServiceImpl(LumberDAO lumberManagementDAO, LumberConverter lumberConverter){
        LumberServiceImpl.lumberManagementDAO = lumberManagementDAO;
        LumberServiceImpl.lumberConverter = lumberConverter;
    }


    @Override
    public LumberDTO getLumberById(int id) {

        try {
            return lumberConverter.convertPlainObjectToRestDTO(lumberManagementDAO.readLumberById(id));
        } catch (PersistenceLayerException e) {
            LOG.warn("helloWorldLumber Persistence Exception: {}",e.getMessage());
        }

        return null;

    }

    @Override
    public List<LumberDTO> getAllLumber(LumberDTO lumber) {
        List<Lumber> allLumber = null;
        List<LumberDTO> allLumberConverted = null;
        Lumber filter = lumberConverter.convertRestDTOToPlainObject(lumber);

        try{
            allLumber = lumberManagementDAO.getAllLumber(filter);

        } catch(PersistenceLayerException e) {
            LOG.error(e.getMessage());
        }

        if (allLumber!= null) {
            allLumberConverted = new ArrayList<>();

            for (int i = 0; i < allLumber.size(); i++) {
                allLumberConverted.add(lumberConverter.convertPlainObjectToRestDTO(allLumber.get(i)));
            }
        }

        return allLumberConverted;
    }

    @Override
    public void removeLumber(LumberDTO lumber) {

    }

    @Override
    public void reserveLumber(LumberDTO lumber) {

    }

    @Override
    public void addLumber(LumberDTO lumberDTO) {

        try {
            lumberManagementDAO.createLumber(lumberConverter.convertRestDTOToPlainObject(lumberDTO));
        } catch (PersistenceLayerException e) {
            LOG.warn("helloWorldLumber Persistence Exception: {}",e.getMessage());
        }

    }
}
