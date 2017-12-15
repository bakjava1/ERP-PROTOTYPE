package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.AssignmentController;
import at.ac.tuwien.sepm.assignment.group02.rest.converter.AssignmentConverter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Assignment;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
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

    private static AssignmentController assignmentController;
    private static AssignmentConverter assignmentConverter;

    @Autowired
    public AssignmentServiceImpl(AssignmentController assignmentController, AssignmentConverter assignmentConverter) {
        AssignmentServiceImpl.assignmentController = assignmentController;
        AssignmentServiceImpl.assignmentConverter = assignmentConverter;
    }


    @Override
    public void createAssignment(Assignment assignment) throws InvalidInputException, ServiceLayerException {

    }

    @Override
    public List<Assignment> getAllOpenAssignments() throws ServiceLayerException {
        LOG.debug("getAllOpenAssignments called in ");
        List<AssignmentDTO> allOpenAssignments = null;

        try {
            allOpenAssignments = assignmentController.getAllOpenAssignments();
        } catch (PersistenceLayerException e) {
            LOG.warn("Error while getting all open assignments in client service layer: ", e.getMessage());
        }

        List<Assignment> allOpenAssignmentsConverted = new LinkedList<>();

        for(AssignmentDTO assignmentDTO : allOpenAssignments) {
            allOpenAssignmentsConverted.add(assignmentConverter.convertRestDTOToPlainObject(assignmentDTO));
        }

        return allOpenAssignmentsConverted;
    }

    @Override
    public void setDone(Assignment assignment) throws InvalidInputException, ServiceLayerException {

    }
}
