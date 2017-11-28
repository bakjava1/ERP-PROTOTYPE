package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.server.dao.Lumber;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLevelException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.LumberManagementDAO;
import at.ac.tuwien.sepm.assignment.group02.server.rest.LumberRESTController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class LumberServiceImpl implements LumberService {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static LumberManagementDAO lumberManagementDAO;

    public LumberServiceImpl(LumberManagementDAO lumberManagementDAO){

        LumberServiceImpl.lumberManagementDAO = lumberManagementDAO;
    }


    @Override
    public Lumber getLumber(int id) {

        try {
            return lumberManagementDAO.readLumberById(id);
        } catch (PersistenceLevelException e) {
            LOG.warn("helloWorldLumber Persistence Exception: {}",e.getMessage());
        }

        return null;

    }

    @Override
    public void addLumber(Lumber lumber) {

        try {
            lumberManagementDAO.createLumber(lumber);
        } catch (PersistenceLevelException e) {
            LOG.warn("helloWorldLumber Persistence Exception: {}",e.getMessage());
        }

    }

}
