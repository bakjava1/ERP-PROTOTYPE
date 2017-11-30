package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.rest.converter.LumberConverter;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLevelException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.LumberManagementDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class LumberServiceImpl implements LumberService {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static LumberManagementDAO lumberManagementDAO;
    private static LumberConverter lumberConverter = new LumberConverter();

    public LumberServiceImpl(LumberManagementDAO lumberManagementDAO){

        LumberServiceImpl.lumberManagementDAO = lumberManagementDAO;
    }


    @Override
    public LumberDTO getLumber(int id) {

        try {
            return lumberConverter.convertPlainObjectToRestDTO(lumberManagementDAO.readLumberById(id));
        } catch (PersistenceLevelException e) {
            LOG.warn("helloWorldLumber Persistence Exception: {}",e.getMessage());
        }

        return null;

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
