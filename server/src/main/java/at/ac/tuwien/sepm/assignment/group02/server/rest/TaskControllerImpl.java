package at.ac.tuwien.sepm.assignment.group02.server.rest;

import at.ac.tuwien.sepm.assignment.group02.rest.restController.TaskController;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public void deleteTask(TaskDTO task) {

    }

    @Override
    @RequestMapping(value="/updateTask",method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateTask(@RequestBody TaskDTO task) {
        LOG.debug("Atemmpting to Update Task");
        taskService.updateTask(task);
    }

    @Override
    public List<TaskDTO> getAllOpenTasks() {
        return null;
    }

    @Override
    public void getTaskById(int task_id) {

    }
}
