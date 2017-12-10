package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.TaskController;
import at.ac.tuwien.sepm.assignment.group02.rest.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static TaskController taskController;
    private static TaskConverter taskConverter;

    @Autowired
    public TaskServiceImpl (TaskController taskController, TaskConverter taskConverter) {
        TaskServiceImpl.taskController = taskController;
        TaskServiceImpl.taskConverter = taskConverter;
    }

    @Override
    public void createTask(Task task) throws InvalidInputException, ServiceLayerException {

    }

    @Override
    public void deleteTask(Task task) throws ServiceLayerException {
        LOG.debug("deleteTask called: {}", task);

        TaskDTO taskToDelete = taskConverter.convertPlainObjectToRestDTO(task);

        try {
            taskController.deleteTask(taskToDelete);
        } catch (PersistenceLayerException e) {
            LOG.warn(e.getMessage());
        }
    }

    @Override
    public List<Task> getAllOpenTasks() throws ServiceLayerException {
        return null;
    }
}
