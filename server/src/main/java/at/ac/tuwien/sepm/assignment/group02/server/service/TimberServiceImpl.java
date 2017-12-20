package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.server.converter.TimberConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TimberDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
public class TimberServiceImpl implements TimberService{

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static TimberDAO timberManagementDAO;
    private static TimberConverter timberConverter;

    @Autowired
    public TimberServiceImpl(TimberDAO timberManagementDAO, TimberConverter timberConverter) {
        TimberServiceImpl.timberManagementDAO = timberManagementDAO;
        TimberServiceImpl.timberConverter = timberConverter;
    }


    @Override
    public void addTimber(TimberDTO timberDTO) throws ServiceLayerException {

        Timber timber = timberConverter.convertRestDTOToPlainObject(timberDTO);

        if(timber.getAmount()<0){
            throw new ServiceLayerException("Error: added amount is negative");
        }

        try {
            timberManagementDAO.createTimber(timber);
        } catch (PersistenceLayerException e) {
            LOG.error("Error while trying to create Object in Database");
            throw new ServiceLayerException("Failed Persistence");
        }
    }

    @Override
    public void updateTimber(TimberDTO timberDTO) throws ServiceLayerException {

    }

    @Override
    public int numberOfBoxes() throws ServiceLayerException {
        try {
            return timberManagementDAO.getNumberOfBoxes();
        } catch (PersistenceLayerException e) {
            LOG.error("Error while trying to get Number of Boxes");
            throw new ServiceLayerException("Failed Persistence");

        }
    }
}
