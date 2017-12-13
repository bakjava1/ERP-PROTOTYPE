package at.ac.tuwien.sepm.assignment.group02.server.rest;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value="/updateTask",method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateTask(@RequestBody TaskDTO task) {
        LOG.debug("Atemmpting to Update Task");
        try {
            taskService.updateTask(task);
        } catch (ServiceLayerException e) {
            LOG.error("Error in Service Layer of Server: " + e.getMessage());
        }
    }

    @RequestMapping(value="/deleteTask", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteTask(TaskDTO task) {
        LOG.debug("Deleting task " + task.getId());
        try {
            taskService.deleteTask(task);
        } catch (ServiceLayerException e) {
            LOG.error("Error in service layer of server: " + e.getMessage());
        }
    }

    public List<TaskDTO> getAllOpenTasks() {
        return null;
    }

    public void getTaskById(int task_id) {

    }
}
