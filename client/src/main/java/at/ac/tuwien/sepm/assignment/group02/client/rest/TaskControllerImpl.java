package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
public class TaskControllerImpl implements TaskController {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private RestTemplate restTemplate;

    @Autowired
    public TaskControllerImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void createTask(TaskDTO task) throws PersistenceLayerException {

    }

    @Override
    public void deleteTask(@RequestBody TaskDTO task) throws PersistenceLayerException {
        LOG.debug("sending task to be deleted to server");

        try {
            restTemplate.postForObject("http://localhost:8080/deleteTask", task, TaskDTO.class);
        } catch(HttpStatusCodeException e){
            LOG.warn("HttpStatusCodeException {}", e.getResponseBodyAsString());
        } catch(RestClientException e){
            //no response payload, probably server not running
            LOG.warn("server is down? - {}", e.getMessage());
        }
    }

    @Override
    public void updateTask(TaskDTO task) throws PersistenceLayerException {

    }

    @Override
    public List<TaskDTO> getAllOpenTasks() throws PersistenceLayerException {
        return null;
    }

    @Override
    public void getTaskById(int task_id) throws PersistenceLayerException {

    }
}
