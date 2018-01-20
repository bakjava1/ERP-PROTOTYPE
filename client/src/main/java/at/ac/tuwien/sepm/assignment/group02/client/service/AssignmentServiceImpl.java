package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.AssignmentController;
import at.ac.tuwien.sepm.assignment.group02.client.validation.ValidateAssignmentDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class AssignmentServiceImpl implements AssignmentService {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private AssignmentController assignmentController;
    private ValidateAssignmentDTO validateAssignmentDTO;

    @Autowired
    public AssignmentServiceImpl(AssignmentController assignmentController, ValidateAssignmentDTO validateAssignmentDTO) {
        this.assignmentController = assignmentController;
        this.validateAssignmentDTO = validateAssignmentDTO;
    }

    @Override
    public void createAssignment(AssignmentDTO assignmentDTO) throws ServiceLayerException {
        LOG.debug("createAssignment called");
        validateAssignmentDTO.isValid(assignmentDTO);
        try {
            assignmentController.createAssignment(assignmentDTO);
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public List<AssignmentDTO> getAllOpenAssignments() throws ServiceLayerException {
        LOG.trace("getAllOpenAssignments called in ");
        List<AssignmentDTO> allOpenAssignments;

        try {
            allOpenAssignments = assignmentController.getAllOpenAssignments();
        } catch (PersistenceLayerException e) {
            LOG.warn("Error while getting all open assignments in client service layer: ", e.getMessage());
            throw new ServiceLayerException(e.getMessage());
        }

        return allOpenAssignments;
    }

    @Override
    public List<AssignmentDTO> getAllClosedAssignments() throws ServiceLayerException {
        LOG.trace("getAllClosedAssignments called");
        List<AssignmentDTO> allAssignments;

        try {
            allAssignments = assignmentController.getAllClosedAssignments();
        } catch (PersistenceLayerException e) {
            LOG.warn("Error while getting all open assignments in client service layer: ", e.getMessage());
            throw new ServiceLayerException(e.getMessage());
        }

        return allAssignments;
    }

    @Override
    public void setDone(AssignmentDTO assignmentDTO) throws ServiceLayerException {

        if(!assignmentDTO.isDone())
            assignmentDTO.setDone(true);
        else throw new ServiceLayerException("Diese Aufgabe ist bereits abgeschlossen.");

        // throws InvalidInputException
        validateAssignmentDTO.isValid(assignmentDTO);


        // 3.2.2 (rest/AssignmentController) Aufgabe als erledigt markieren.
        try {
            assignmentController.setDone(assignmentDTO);
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
            throw new ServiceLayerException(e.getMessage());
        }

    }

    @Override
    public void cleanUpAssignments() throws ServiceLayerException {
        try {
            assignmentController.cleanUpAssignments();
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
            throw new ServiceLayerException(e.getMessage());
        }
    }
}
