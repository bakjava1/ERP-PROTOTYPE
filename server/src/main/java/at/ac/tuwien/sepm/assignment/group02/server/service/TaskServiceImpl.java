package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.rest.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TaskDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static TaskDAO taskManagementDAO;
    private static TaskConverter taskConverter;

    @Autowired
    public TaskServiceImpl(TaskDAO taskManagementDAO, TaskConverter taskConverter) {
        TaskServiceImpl.taskManagementDAO = taskManagementDAO;
        TaskServiceImpl.taskConverter = taskConverter;
    }

    @Override
    public void createTask(TaskDTO task) {

    }

    @Override
    public void deleteTask(TaskDTO task) {
        Task taskToDelete = taskConverter.convertRestDTOToPlainObject(task);

        try {
            taskManagementDAO.deleteTask(taskToDelete);
        } catch (PersistenceLayerException e) {
            LOG.error("Error while deleting an task");
        }
    }

    @Override
    public void updateTask(TaskDTO task) {
        LOG.info("Converting TaskDTO to Task");
        Task toUpdate = taskConverter.convertRestDTOToPlainObject(task);
        try {
            taskManagementDAO.updateTask(toUpdate);
        } catch(PersistenceLayerException e) {
            LOG.error("Database Problems: " + e.getMessage());
        }
    }

    @Override
    public List<TaskDTO> getAllOpenTasks() {
        return null;
    }

    @Override
    public TaskDTO getTaskById(int task_id) {
        return null;
    }
}
