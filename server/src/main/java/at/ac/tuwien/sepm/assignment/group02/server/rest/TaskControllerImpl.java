package at.ac.tuwien.sepm.assignment.group02.server.rest;

import at.ac.tuwien.sepm.assignment.group02.rest.restController.TaskController;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import java.awt.*;
import java.lang.invoke.MethodHandles;
import java.util.List;

import static at.ac.tuwien.sepm.assignment.group02.server.MainApplication.taskService;

@RestController
public class TaskControllerImpl implements TaskController {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void createTask(TaskDTO task) {

    }

    @Override
    @RequestMapping(value="/deleteTask", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteTask(TaskDTO task) {
        LOG.debug("Deleting task " + task.getId());
        taskService.deleteTask(task);
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
