package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import at.ac.tuwien.sepm.assignment.group02.server.converter.LumberConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ResourceNotFoundException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.LumberDAO;
import at.ac.tuwien.sepm.assignment.group02.server.validation.ValidateLumber;
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
    private ValidateLumber validateLumber;

    @Autowired
    public LumberServiceImpl(LumberDAO lumberManagementDAO, LumberConverter lumberConverter, ValidateLumber validateLumber) {
        LumberServiceImpl.lumberManagementDAO = lumberManagementDAO;
        LumberServiceImpl.lumberConverter = lumberConverter;
        this.validateLumber = validateLumber;
    }


    @Override
    public LumberDTO getLumberById(int id) throws ServiceLayerException {

        try {
            return lumberConverter.convertPlainObjectToRestDTO(lumberManagementDAO.readLumberById(id));
        } catch (PersistenceLayerException e) {
            LOG.warn("helloWorldLumber Persistence Exception: {}", e.getMessage());
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public void addLumber(LumberDTO lumberDTO) throws ServiceLayerException {

        try {
            lumberManagementDAO.createLumber(lumberConverter.convertRestDTOToPlainObject(lumberDTO));
        } catch (PersistenceLayerException e) {
            LOG.warn("helloWorldLumber Persistence Exception: {}", e.getMessage());
            throw new ServiceLayerException(e.getMessage());
        }

    }

    @Override
    public List<LumberDTO> getAllLumber(LumberDTO lumber) throws ServiceLayerException {
        List<Lumber> allLumber = null;
        List<LumberDTO> allLumberConverted = null;
        Lumber filter = lumberConverter.convertRestDTOToPlainObject(lumber);

        try {
            allLumber = lumberManagementDAO.getAllLumber(filter);

        } catch (PersistenceLayerException e) {
            LOG.error(e.getMessage());
            throw new ServiceLayerException(e.getMessage());
        }

        if (allLumber != null) {
            allLumberConverted = new ArrayList<>();

            for (int i = 0; i < allLumber.size(); i++) {
                LumberDTO lumberDTO = lumberConverter.convertPlainObjectToRestDTO(allLumber.get(i));
                allLumberConverted.add(lumberDTO);
            }
        }

        return allLumberConverted;
    }

    @Override
    public void reserveLumber(LumberDTO lumberDTO) throws ServiceLayerException {

        Lumber lumber = lumberConverter.convertRestDTOToPlainObject(lumberDTO);
        validateLumber.isValid(lumber);

        try {
            Lumber existing_lumber = lumberManagementDAO.readLumberById(lumber.getId());
            LOG.debug("lumber reservation - existing lumber: {}", existing_lumber.toString());

            int toReserve = lumber.getQuantity();
            int existingQuantity = existing_lumber.getQuantity();
            int existingReservedQuantity = existing_lumber.getReserved_quantity();

            if(lumber.getQuantity() <= existingQuantity) {

                existing_lumber.setReserved_quantity( existingReservedQuantity + toReserve );
                existing_lumber.setQuantity( existingQuantity - toReserve );
                LOG.debug("lumber reservation - updated lumber: {}", existing_lumber.toString());
                lumberManagementDAO.updateLumber(existing_lumber);

            } else {
                throw new ServiceLayerException("Reservierte Menge an Schnittholz übersteigt vorhandene Menge.");
            }

        } catch (PersistenceLayerException e) {
            LOG.warn("Updating Lumber Persistence Exception: {}", e.getMessage());
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public void updateLumber(LumberDTO lumberDTO) throws ServiceLayerException {
        try {
            lumberManagementDAO.updateLumber(lumberConverter.convertRestDTOToPlainObject(lumberDTO));
        } catch (PersistenceLayerException e) {
            LOG.warn("Updating Lumber Persistence Exception: {}", e.getMessage());
            throw new ServiceLayerException(e.getMessage());
        }
    }


    @Override
    public void removeLumber(LumberDTO lumberDTO) throws ServiceLayerException {

        Lumber lumberToDelete = lumberConverter.convertRestDTOToPlainObject(lumberDTO);

        try {
            lumberManagementDAO.deleteLumber(lumberToDelete);
        } catch (PersistenceLayerException e) {
            LOG.error("Error while deleting an order");
            throw new ServiceLayerException(e.getMessage());
        }
    }

}
