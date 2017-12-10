package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.rest.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLevelException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TaskDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class TaskServiceImpl implements TaskService {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static TaskDAO taskManagementDAO;
    private static TaskConverter taskConverter = new TaskConverter();

    public TaskServiceImpl(TaskDAO taskManagementDAO) {
        TaskServiceImpl.taskManagementDAO = taskManagementDAO;
    }

    @Override
    public void createTask(TaskDTO task) {

    }

    @Override
    public void deleteTask(TaskDTO task) {
        Task taskToDelete = taskConverter.convertRestDTOToPlainObject(task);

        try {
            taskManagementDAO.deleteTask(taskToDelete);
        } catch (PersistenceLevelException e) {
            LOG.error("Error while deleting an task");
        }
    }

    @Override
    public void updateTask(TaskDTO task) {

    }

    @Override
    public List<TaskDTO> getAllOpenTasks() {
        return null;
    }

    @Override
    public void getTaskById(int task_id) {

    }
}
