package at.ac.tuwien.sepm.assignment.group02.server.rest;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.EntityNotFoundException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import java.awt.*;
import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
public class TaskControllerImpl {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static TaskService taskService;

    @Autowired
    public TaskControllerImpl(TaskService taskService) {
        TaskControllerImpl.taskService = taskService;
    }

    public void createTask(TaskDTO task) {

    }

    @RequestMapping(value="/deleteTask", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteTask(TaskDTO task) throws EntityNotFoundException {
        LOG.debug("Deleting task " + task.getId());
        try {
            taskService.deleteTask(task);
        } catch (ServiceLayerException e) {
            throw new EntityNotFoundException("Failed to delete task.");
        }
    }

    public void updateTask(TaskDTO task) {

    }

    public List<TaskDTO> getAllOpenTasks() {
        return null;
    }

    public void getTaskById(int task_id) {

    }
}
