package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.rest.converter.TimberConverter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLevelException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TimberDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class TimberServiceImpl implements TimberService{
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static TimberDAO timberManagementDAO;
    private static TimberConverter timberConverter = new TimberConverter();

    public TimberServiceImpl(TimberDAO timberManagementDAO) {
        TimberServiceImpl.timberManagementDAO = timberManagementDAO;
    }


    @Override
    public void addTimber(TimberDTO timberDTO) {

        Timber timber = timberConverter.convertRestDTOToPlainObject(timberDTO);

        try {
            timberManagementDAO.createTimber(timber);
        } catch (PersistenceLevelException e) {
            LOG.error("Error while trying to create Object in Database");
            //throw new ServiceDatabaseException("Failed Persistenz");

        }
    }

    @Override
    public void updateTimber(TimberDTO timberDTO) {

    }
}
