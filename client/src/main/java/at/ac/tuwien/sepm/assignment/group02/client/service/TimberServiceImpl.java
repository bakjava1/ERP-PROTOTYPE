package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.TimberController;
import at.ac.tuwien.sepm.assignment.group02.rest.converter.TimberConverter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
public class TimberServiceImpl implements TimberService{

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static TimberController timberController;
    private static TimberConverter timberConverter;

    @Autowired
    public TimberServiceImpl(TimberController timberController, TimberConverter timberConverter){
        TimberServiceImpl.timberController = timberController;
        TimberServiceImpl.timberConverter = timberConverter;
    }

    @Override
    public void addTimber(Timber timber) throws ServiceLayerException{

        TimberConverter timberConverter = new TimberConverter();

        TimberDTO timberDTO = timberConverter.convertPlainObjectToRestDTO(timber);
        try {
            timberController.createTimber(timberDTO);
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
        }
    }

    @Override
    public int getNumberOfBoxes() throws ServiceLayerException {
        try {
            return timberController.getNumberOfBoxes();
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
        }
        return 0;
    }
}
