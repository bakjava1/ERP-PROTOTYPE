package at.ac.tuwien.sepm.assignment.group02.server.rest;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ResourceNotFoundException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value="Task Controller")
public class TaskControllerImpl {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static TaskService taskService;

    @Autowired
    public TaskControllerImpl(TaskService taskService) {
        TaskControllerImpl.taskService = taskService;
    }

    @RequestMapping(value="/createTask", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "create task")
    public void createTask(@RequestBody TaskDTO taskDTO) throws ResourceNotFoundException {
        LOG.debug("createTask: {}" + taskDTO.toString());
        try {
            taskService.createTask(taskDTO);
        } catch(ServiceLayerException e) {
            LOG.error(e.getMessage());
            throw new ResourceNotFoundException("Failed Creation");
        }
    }

    @RequestMapping(value="/updateTask",method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "update task")
    public void updateTask(@RequestBody TaskDTO task) throws ResourceNotFoundException {
        LOG.debug("Atemmpting to Update Task, {}", task.toString());
        try {
            taskService.updateTask(task);
        } catch (ServiceLayerException e) {
            LOG.error("Error in Service Layer of Server: " + e.getMessage());
            throw new ResourceNotFoundException("Failed Update");
        }
    }

    @RequestMapping(value="/deleteTask", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "delete task")
    public void deleteTask(TaskDTO task) throws ResourceNotFoundException {
        LOG.debug("Deleting task " + task.getId());
        try {
            taskService.deleteTask(task);
        } catch (ServiceLayerException e) {
            LOG.error("Error in service layer of server: " + e.getMessage());
            throw new ResourceNotFoundException("Failed Delete");
        }
    }

    @RequestMapping(value="/getAllOpenTasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "get all open tasks")
    public List<TaskDTO> getAllOpenTasks() throws ResourceNotFoundException {
        LOG.debug("called getAllOpenTasks");
        try {
            return taskService.getAllOpenTasks();
        } catch (ServiceLayerException e) {
            LOG.error(e.getMessage());
            throw new ResourceNotFoundException("failed to get all open tasks.");
        }
    }

    @RequestMapping(value="/getTaskById/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get Task By Id")
    public TaskDTO getTaskById(int task_id) throws ResourceNotFoundException {
        LOG.debug("called getTaskById");
        try {
            return taskService.getTaskById(task_id);
        } catch (ServiceLayerException e) {
            LOG.error(e.getMessage());
            throw new ResourceNotFoundException("failed to get task.");
        }
    }
}
