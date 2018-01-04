package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.server.converter.TimberConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TimberDAO;
import at.ac.tuwien.sepm.assignment.group02.server.validation.ValidateTimber;
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
    private static ValidateTimber validateTimber;

    @Autowired
    public TimberServiceImpl(TimberDAO timberManagementDAO, TimberConverter timberConverter, ValidateTimber validateTimber) {
        TimberServiceImpl.timberManagementDAO = timberManagementDAO;
        TimberServiceImpl.timberConverter = timberConverter;
        TimberServiceImpl.validateTimber = validateTimber;
    }


    @Override
    public void addTimber(TimberDTO timberDTO) throws ServiceLayerException {

        Timber timber = timberConverter.convertRestDTOToPlainObject(timberDTO);
        validateTimber.isValid(timber);

        //TODO verify if amount is equal or less to box volume

        try {
            timberManagementDAO.createTimber(timber);
        } catch (PersistenceLayerException e) {
            LOG.error("Error while trying to create Object in Database");
            throw new ServiceLayerException("Failed Persistence");
        }
    }

    @Override
    public void removeTimberFromBox(int box_id, int amount_to_remove) throws ServiceLayerException {
        Timber timber = new Timber();
        timber.setBox_id(box_id);
        timber.setAmount(amount_to_remove);

        validateTimber.isValid(timber);

        //TODO verify if amount is equal or less to total amount in the box

        try {
            timberManagementDAO.removeTimber(timber);
        } catch (PersistenceLayerException e) {
            LOG.error("Error while trying to update timber in Database");
            throw new ServiceLayerException("Fehlermeldung. Rundholz konnte nicht bearbeitet werden.");
        }
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
