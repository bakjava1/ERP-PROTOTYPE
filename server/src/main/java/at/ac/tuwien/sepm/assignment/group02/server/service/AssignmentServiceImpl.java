package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import at.ac.tuwien.sepm.assignment.group02.server.converter.AssignmentConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Assignment;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.EntityNotFoundException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.AssignmentDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.LinkedList;
import java.util.List;

@Service
public class AssignmentServiceImpl implements AssignmentService {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static AssignmentDAO assignmentManagementDAO;
    private static AssignmentConverter assignmentConverter;

    @Autowired
    public AssignmentServiceImpl(AssignmentDAO assignmentManagementDAO, AssignmentConverter assignmentConverter) {
        AssignmentServiceImpl.assignmentManagementDAO = assignmentManagementDAO;
        AssignmentServiceImpl.assignmentConverter = assignmentConverter;
    }

    @Override
    public void addAssignment(AssignmentDTO assignmentDTO) throws ServiceLayerException {

    }

    @Override
    public List<AssignmentDTO> getAllOpenAssignments() throws ServiceLayerException {
        LOG.debug("get all open assignments called in server service layer");

        List<Assignment> allOpenAssignments = new LinkedList<>();
        List<AssignmentDTO> allOpenAssignmentsConverted = new LinkedList<>();

        try {
            allOpenAssignments = assignmentManagementDAO.getAllAssignments();
        } catch (PersistenceLayerException e) {
            LOG.warn("error while getting all open assignments in server persistence layer", e.getMessage());
        }

        for(Assignment assignment : allOpenAssignments) {
            allOpenAssignmentsConverted.add(assignmentConverter.convertPlainObjectToRestDTO(assignment));
        }

        return allOpenAssignmentsConverted;
    }

    @Override
    public void setDone(AssignmentDTO assignmentDTO) throws ServiceLayerException {
        LOG.debug("called setDone");
        Assignment toUpdate = assignmentConverter.convertRestDTOToPlainObject(assignmentDTO);
        try {
            assignmentManagementDAO.updateAssignment(toUpdate);
        } catch (EntityNotFoundException e){
            LOG.error("Entity Not Found: " + e.getMessage());
            throw new ServiceLayerException("entity not found");
        } catch(PersistenceLayerException e) {
            LOG.error("Database Problems: " + e.getMessage());
            throw new ServiceLayerException("database problem.");
        }
    }
}
