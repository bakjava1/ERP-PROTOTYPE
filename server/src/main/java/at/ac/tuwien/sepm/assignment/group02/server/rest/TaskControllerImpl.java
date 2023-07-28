package at.ac.tuwien.sepm.assignment.group02.server.rest;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value="/updateTask",method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "update task")
    public void updateTask(@RequestBody TaskDTO task) throws ServiceLayerException {
        LOG.debug("Atemmpting to Update Task, {}", task.toString());
        taskService.updateTask(task);
    }

    @RequestMapping(value="/updateTaskAlg",method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "update task")
    public void updateTaskAlg(@RequestBody TaskDTO task) {
        LOG.debug("Atemmpting to Update Task, {}", task.toString());
        taskService.updateTaskAlg(task);
    }

    @CrossOrigin
    @RequestMapping(value="/getAllOpenTasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "get all open tasks")
    public List<TaskDTO> getAllOpenTasks() throws ServiceLayerException {
        LOG.trace("called getAllOpenTasks");
        return taskService.getAllOpenTasks();
    }

    @RequestMapping(value="/getTaskById/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get Task By Id")
    public TaskDTO getTaskById(@PathVariable(value = "id")int id) throws ServiceLayerException {
        LOG.debug("called getTaskById");
        return taskService.getTaskById(id);
    }

    /*
    @RequestMapping(value="/createTask", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "create task")
    public void createTask(@RequestBody TaskDTO taskDTO) throws ServiceLayerException {
        LOG.debug("createTask: {}" + taskDTO.toString());
        taskService.createTask(taskDTO);
    }

    @RequestMapping(value="/deleteTask", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "delete task")
    public void deleteTask(TaskDTO task) throws ServiceLayerException {
        LOG.debug("Deleting task " + task.getId());
        taskService.deleteTask(task);
    }
    */
}
