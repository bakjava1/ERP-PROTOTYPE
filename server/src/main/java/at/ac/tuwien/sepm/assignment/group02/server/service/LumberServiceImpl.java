package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.rest.converter.LumberConverter;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.FilterDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLevelException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.LumberDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class LumberServiceImpl implements LumberService {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static LumberDAO lumberManagementDAO;
    private static LumberConverter lumberConverter = new LumberConverter();

    public LumberServiceImpl(LumberDAO lumberManagementDAO){

        LumberServiceImpl.lumberManagementDAO = lumberManagementDAO;
    }


    @Override
    public LumberDTO getLumberById(int id) {

        try {
            return lumberConverter.convertPlainObjectToRestDTO(lumberManagementDAO.readLumberById(id));
        } catch (PersistenceLevelException e) {
            LOG.warn("helloWorldLumber Persistence Exception: {}",e.getMessage());
        }

        return null;

    }

    @Override
    public List<LumberDTO> getAllLumber(FilterDTO filter) {
        return null;
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
        } catch (PersistenceLevelException e) {
            LOG.warn("helloWorldLumber Persistence Exception: {}",e.getMessage());
        }

    }
}
