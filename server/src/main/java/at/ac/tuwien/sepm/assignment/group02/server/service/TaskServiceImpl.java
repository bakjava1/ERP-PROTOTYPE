package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.server.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.EntityCreationException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.EntityNotFoundExceptionService;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TaskDAO;
import at.ac.tuwien.sepm.assignment.group02.server.validation.ValidateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static TaskDAO taskManagementDAO;
    private static TaskConverter taskConverter;
    private ValidateTask validateTask;

    @Autowired
    public TaskServiceImpl(TaskDAO taskManagementDAO, TaskConverter taskConverter, ValidateTask validateTask) {
        TaskServiceImpl.taskManagementDAO = taskManagementDAO;
        TaskServiceImpl.taskConverter = taskConverter;
        this.validateTask = validateTask;
    }

    @Override
    public int createTask(TaskDTO taskDTO) throws ServiceLayerException {
        int task_id;
        Task task = taskConverter.convertRestDTOToPlainObject(taskDTO);
        validateTask.isValid(task);

        try {
            task_id = taskManagementDAO.createTask(task);
            return task_id;
        } catch (PersistenceLayerException e) {
            LOG.warn("Database Error", e.getMessage());
            throw new EntityCreationException(e.getMessage());
        }
    }

    @Override
    public void deleteTask(TaskDTO task) throws ServiceLayerException {
        Task taskToDelete = taskConverter.convertRestDTOToPlainObject(task);
        validateTask.isValid(taskToDelete);
        try {
            taskManagementDAO.deleteTask(taskToDelete);
        } catch (PersistenceLayerException e) {
            LOG.error("Error while deleting an task");
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public void updateTask(TaskDTO task) throws ServiceLayerException {
        LOG.info("called updateTask");

        Task toUpdate = taskConverter.convertRestDTOToPlainObject(task);
        validateTask.isValid(toUpdate);

        //check if task is done
        if( toUpdate.getProduced_quantity() == toUpdate.getQuantity() ) {
            LOG.debug("task is done");
            toUpdate.setDone(true);
            toUpdate.setIn_progress(false);
        }

        try {
            taskManagementDAO.updateTask(toUpdate);
        } catch(PersistenceLayerException e) {
            LOG.error("Database Problems: " + e.getMessage());
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public List<TaskDTO> getAllOpenTasks() throws ServiceLayerException {
        LOG.debug("called getAllOpenTasks");
        List<TaskDTO> allOpenConverted = new ArrayList<>();

        try{
            List<Task> allOpen = taskManagementDAO.getAllTasks();

            for (int i = 0; allOpen!=null && i < allOpen.size(); i++) {
                allOpenConverted.add(taskConverter.convertPlainObjectToRestDTO(allOpen.get(i)));
            }

        } catch(PersistenceLayerException e) {
            LOG.error("Error while trying to get objects from Database: " + e.getMessage());
            throw new ServiceLayerException(e.getMessage());
        }

        return allOpenConverted;
    }

    @Override
    public TaskDTO getTaskById(int task_id) throws ServiceLayerException {
        try {
            return taskConverter.convertPlainObjectToRestDTO(taskManagementDAO.getTaskById(task_id));
        } catch (PersistenceLayerException e) {
            LOG.error("Error while trying to get objects from Database: " + e.getMessage());
            throw new EntityNotFoundExceptionService(e.getMessage());
        }
    }
}
