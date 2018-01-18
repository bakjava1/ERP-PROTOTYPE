package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.FilterDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.server.converter.AssignmentConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Assignment;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.*;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.AssignmentDAO;
import at.ac.tuwien.sepm.assignment.group02.server.validation.ValidateAssignment;
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
    private ValidateAssignment validateAssignment;



    private TimberService timberService;

    private LumberService lumberService;

    private TaskService taskService;

    @Autowired
    public AssignmentServiceImpl(AssignmentDAO assignmentManagementDAO,
                                 AssignmentConverter assignmentConverter,
                                 ValidateAssignment validateAssignment,
                                 TimberService timberService,
                                 LumberService lumberService,
                                 TaskService taskService) {
        AssignmentServiceImpl.assignmentManagementDAO = assignmentManagementDAO;
        AssignmentServiceImpl.assignmentConverter = assignmentConverter;
        this.validateAssignment = validateAssignment;
        this.timberService = timberService;
        this.lumberService = lumberService;
        this.taskService = taskService;
    }

    @Override
    public void addAssignment(AssignmentDTO assignmentDTO) throws ServiceLayerException {
        Assignment assignment = assignmentConverter.convertRestDTOToPlainObject(assignmentDTO);
        validateAssignment.isValid(assignment);
        try {
            assignmentManagementDAO.createAssignment(assignment);
        } catch(PersistenceLayerException e) {
            LOG.error("Database Problems: " + e.getMessage());
            throw new EntityCreationException(e.getMessage());
        }
    }

    @Override
    public List<AssignmentDTO> getAllOpenAssignments() throws ServiceLayerException {
        LOG.trace("get all open assignments called in server service layer");

        List<Assignment> allOpenAssignments = new LinkedList<>();
        List<AssignmentDTO> allOpenAssignmentsConverted = new LinkedList<>();

        try {
            allOpenAssignments = assignmentManagementDAO.getAllOpenAssignments();
        } catch (PersistenceLayerException e) {
            LOG.warn("error while getting all open assignments in server persistence layer", e.getMessage());
        }

        for(Assignment assignment : allOpenAssignments) {
            allOpenAssignmentsConverted.add(assignmentConverter.convertPlainObjectToRestDTO(assignment));
        }

        return allOpenAssignmentsConverted;
    }

    @Override
    public List<AssignmentDTO> getAllAssignments() throws ServiceLayerException {
        LOG.trace("get all open assignments called in server service layer");

        List<Assignment> allAssignments = new LinkedList<>();
        List<AssignmentDTO> allAssignmentsConverted = new LinkedList<>();

        try {
            allAssignments = assignmentManagementDAO.getAllAssignments();
        } catch (PersistenceLayerException e) {
            LOG.warn("error while getting all open assignments in server persistence layer", e.getMessage());
        }

        for(Assignment assignment : allAssignments) {
            allAssignmentsConverted.add(assignmentConverter.convertPlainObjectToRestDTO(assignment));
        }

        return allAssignmentsConverted;
    }

    @Override
    public void setDone(AssignmentDTO assignmentDTO) throws ServiceLayerException {
        LOG.debug("called setDone");

        // might throw ConversionException
        Assignment assignment = assignmentConverter.convertRestDTOToPlainObject(assignmentDTO);

        // might throw InvalidInputException
        validateAssignment.isValid(assignment);

        try {
            // 3.2.2 (rest/AssignmentController) Aufgabe als erledigt markieren.
            assignmentManagementDAO.setAssignmentDone(assignment);
        } catch (EntityNotFoundException e){
            LOG.error("Entity Not Found: " + e.getMessage());
            throw new EntityNotFoundExceptionService(e.getMessage());
        } catch(PersistenceLayerException e) {
            LOG.error("Database Problems: " + e.getMessage());
            throw new InternalServerException(e.getMessage());
        }

        // 3.2.3 (rest/TimberController) Rundholz aus dem Lager entfernen.
        timberService.removeTimberFromBox(assignment.getBox_id(), assignment.getAmount());

        // 3.2.4 (rest/LumberController) Schnittholz ins Lager hinzufügen.
        // problem: no direct link between lumber and task
        // create lumber by copying task description
        TaskDTO taskDTO = taskService.getTaskById(assignment.getTask_id());
        FilterDTO filterDTO = new FilterDTO();

        filterDTO.setDescription(taskDTO.getDescription());
        filterDTO.setFinishing(taskDTO.getFinishing());
        filterDTO.setWood_type(taskDTO.getWood_type());
        filterDTO.setQuality(taskDTO.getQuality());

        filterDTO.setSize(taskDTO.getSize()+"");
        filterDTO.setWidth(taskDTO.getWidth()+"");
        filterDTO.setLength(taskDTO.getLength()+"");

        //retrieve a list of all lumber that matches the taskDTO
        List<LumberDTO> matchingLumber = lumberService.getAllLumber(filterDTO);

        LumberDTO lumberDTO;
        // if there is lumber that matches the task produced, increase its quantity
        if(matchingLumber!=null && matchingLumber.size()>0){
            LOG.debug("there is > 0 lumber that matches the task - the first one in the list will be updated");

            lumberDTO = matchingLumber.get(0);
            int existing_quantity = lumberDTO.getQuantity();
            lumberDTO.setQuantity( existing_quantity + taskDTO.getQuantity() );

            lumberService.updateLumber(lumberDTO);

        } else { // if there is no lumber matching the task, create new lumber
            LOG.debug("there is <= 0 lumber that matches the task - new lumber will be created");

            lumberDTO = new LumberDTO();

            lumberDTO.setDescription(taskDTO.getDescription());
            lumberDTO.setFinishing(taskDTO.getFinishing());
            lumberDTO.setWood_type(taskDTO.getWood_type());
            lumberDTO.setQuality(taskDTO.getQuality());

            lumberDTO.setSize(taskDTO.getSize());
            lumberDTO.setWidth(taskDTO.getWidth());
            lumberDTO.setLength(taskDTO.getLength());
            lumberDTO.setQuantity(taskDTO.getQuantity());

            int lumberId = lumberService.addLumber(lumberDTO);
            lumberDTO.setId(lumberId);
        }

        // 3.2.5 (rest/LumberController) Hinzugefügtes Schnittholz bei Bedarf(?) reservieren.
        lumberDTO.setQuantity(taskDTO.getQuantity()); //reset quantity to produced quantity
        lumberService.reserveLumber(lumberDTO);

        // 3.2.6 (rest/TaskController) Reserviertes Schnittholz dem Auftrag hinzufügen.
        taskDTO.setProduced_quantity(taskDTO.getQuantity());
        taskService.updateTask(taskDTO);
    }
}
