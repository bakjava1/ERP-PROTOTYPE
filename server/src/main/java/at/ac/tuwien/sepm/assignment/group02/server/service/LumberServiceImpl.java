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
    public Lumber helloWorldLumber(int id) {
        LumberRESTController restController = new LumberRESTController();

        boolean b = false;
        try {
            b = lumberManagementDAO.helloWorld();
        } catch (PersistenceLevelException e) {
            LOG.warn("helloWorldLumber Persistence Exception: {}",e.getMessage());
        }

        if(b)
            return restController.helloWorldLumber(id);
        else return restController.helloWorldLumber(id+1);

    }

}
