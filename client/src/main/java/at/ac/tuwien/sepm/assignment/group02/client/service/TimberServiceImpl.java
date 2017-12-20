package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.TimberController;
import at.ac.tuwien.sepm.assignment.group02.client.converter.TimberConverter;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.client.validation.ValidateInput;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
public class TimberServiceImpl implements TimberService{

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static TimberController timberController;
    private static TimberConverter timberConverter;
    private static ValidateInput<Timber> timberValidator;

    @Autowired
    public TimberServiceImpl(TimberController timberController, TimberConverter timberConverter, ValidateInput<Timber> timberValidator){
        TimberServiceImpl.timberController = timberController;
        TimberServiceImpl.timberConverter = timberConverter;
        TimberServiceImpl.timberValidator = timberValidator;
    }

    @Override
    public void addTimber(Timber timber) throws ServiceLayerException, InvalidInputException{

        try {
            if(timber.getBox_id()>timberController.getNumberOfBoxes())
                throw new InvalidInputException("Error in Timber Box: Box ID is bigger than number of boxes");
            timberValidator.isValid(timber);
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
            throw new ServiceLayerException(e.getMessage());
        }

        TimberDTO timberDTO = timberConverter.convertPlainObjectToRestDTO(timber);
        try {
            timberController.createTimber(timberDTO);
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public int getNumberOfBoxes() throws ServiceLayerException {
        try {
            return timberController.getNumberOfBoxes();
        } catch (PersistenceLayerException e) {
            LOG.error(e.getMessage());
            throw new ServiceLayerException(e.getMessage());
        }
    }
}
