package at.ac.tuwien.sepm.assignment.group02.client.rest;

import at.ac.tuwien.sepm.assignment.group02.client.configuration.RestTemplateConfiguration;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.util.HandleException;
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
import java.util.ArrayList;
import java.util.List;

@RestController
public class TaskControllerImpl implements TaskController {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private RestTemplate restTemplate;

    @Autowired
    public TaskControllerImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public void createTask(TaskDTO task) throws PersistenceLayerException {

    }

    @Override
    public void deleteTask(@RequestBody TaskDTO task) throws PersistenceLayerException {
        LOG.debug("sending task to be deleted to server");

        try {
            restTemplate.put("http://"+RestTemplateConfiguration.host+":"+RestTemplateConfiguration.port+"/deleteTask", task, TaskDTO.class);
        } catch(HttpStatusCodeException e){
            HandleException.handleHttpStatusCodeException(e);
        } catch(RestClientException e){
            LOG.warn("server down? ", e.getMessage());
            throw new PersistenceLayerException("Keine Antwort vom Server. Ist der Server erreichbar?");
        }
    }

    @Override
    public void updateTask(@RequestBody TaskDTO task) throws PersistenceLayerException {
        LOG.info("Attempting to update Task");
        try {
            restTemplate.put("http://"+RestTemplateConfiguration.host+":"+ RestTemplateConfiguration.port+"/updateTask", task, TaskDTO.class);
        } catch(HttpStatusCodeException e){
            HandleException.handleHttpStatusCodeException(e);
        } catch(RestClientException e){
            LOG.warn("server down? ", e.getMessage());
            throw new PersistenceLayerException("Keine Antwort vom Server. Ist der Server erreichbar?");
        }
    }

    @Override
    public List<TaskDTO> getAllOpenTasks() throws PersistenceLayerException {
        LOG.debug("called getAllOpenTasks");

        List<TaskDTO> taskList = new ArrayList<>();
        TaskDTO[] taskArray;
        try{
            taskArray = restTemplate.getForObject("http://"+RestTemplateConfiguration.host+":"+RestTemplateConfiguration.port+"/getAllOpenTasks", TaskDTO[].class);

            for (int i = 0; taskArray!= null && i < taskArray.length; i++) {
                taskList.add(taskArray[i]);
            }
        } catch(HttpStatusCodeException e){
            HandleException.handleHttpStatusCodeException(e);
        } catch(RestClientException e){
            LOG.warn("server down? ", e.getMessage());
            throw new PersistenceLayerException("Keine Antwort vom Server. Ist der Server erreichbar?");
        }

        return taskList;
    }

    @Override
    public void getTaskById(int task_id) throws PersistenceLayerException {

    }
}
