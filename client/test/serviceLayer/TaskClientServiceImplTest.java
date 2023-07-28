package serviceLayer;

import at.ac.tuwien.sepm.assignment.group02.client.configuration.RestTemplateConfiguration;
import at.ac.tuwien.sepm.assignment.group02.client.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.client.entity.UnvalidatedTask;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.TaskController;
import at.ac.tuwien.sepm.assignment.group02.client.rest.TaskControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.client.service.TaskService;
import at.ac.tuwien.sepm.assignment.group02.client.service.TaskServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.client.validation.PrimitiveValidator;
import at.ac.tuwien.sepm.assignment.group02.client.validation.Validator;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import org.junit.*;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class TaskClientServiceImplTest {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static Task task;
    private static RestTemplate restTemplate;
    private static TaskService taskService;
    private static TaskConverter taskConverter;
    private static Validator validator;
    private static TaskController taskController;

    @BeforeClass
    public static void setup(){

        LOG.debug("setup task service test on client");
        taskConverter = new TaskConverter();
        validator = new Validator(new PrimitiveValidator());
    }

    @Before
    public void before(){
        //create a valid task
        task = new Task();
        task.setId(1);
        task.setPrice(2);
        task.setFinishing("roh");
        task.setLength(3500);
        task.setQuality("I");
        task.setWood_type("Ta");
        task.setDescription("Latten");
        task.setSize(30);
        task.setWidth(30);
        task.setProduced_quantity(10);
        task.setQuantity(30);

        restTemplate = mock(RestTemplate.class);
        taskController = new TaskControllerImpl(restTemplate);
        taskService = new TaskServiceImpl(validator, taskController, taskConverter);
    }


    @Test
    public void testValidateTaskPositive() throws InvalidInputException {
        LOG.debug("test validate task return valid task");

        UnvalidatedTask unvalidatedTask = new UnvalidatedTask("Latten", "roh", "Ta", "I", "30",
                "30", "3500","30", "2");

        task.setProduced_quantity(0);
        assertEquals(taskService.validateTaskInput(unvalidatedTask).toString(), task.toString());
    }

    @Test
    public void testValidateTaskNotExpectedTask() throws InvalidInputException {
        LOG.debug("test validate task return different task");

        UnvalidatedTask unvalidatedTask = new UnvalidatedTask("Balken", "roh", "Ta", "I", "30",
                "30", "3500","30", "2");

        assertNotEquals(taskService.validateTaskInput(unvalidatedTask), task);
    }

    @Test(expected = InvalidInputException.class)
    public void testValidateTaskCouldNotValidate() throws InvalidInputException {
        LOG.debug("test validate task could not validate");

        UnvalidatedTask unvalidatedTask = new UnvalidatedTask("Balken", "roh", "Ta", "I", "30",
                "30", "3500","A", "2");

        taskService.validateTaskInput(unvalidatedTask);
    }

    @Test
    public void testGetAllOpenTasksPositive() throws ServiceLayerException {
        LOG.debug("test get all open tasks positive");

        TaskDTO[] taskArray = new TaskDTO[2];
        TaskDTO taskDTO1 = new TaskDTO();
        TaskDTO taskDTO2 = new TaskDTO();
        taskArray[0] = taskDTO1;
        taskArray[1] = taskDTO2;
        when(restTemplate.getForObject("http://"+ RestTemplateConfiguration.host+":"+RestTemplateConfiguration.port+"/getAllOpenTasks", TaskDTO[].class)).thenReturn(taskArray);
        assertEquals(2, taskService.getAllOpenTasks().size());
    }

    @Test(expected = ServiceLayerException.class)
    public void testGetAllOpenTasksPersistenceError() throws ServiceLayerException {
        LOG.debug("test get all open tasks persistence error");

        when(restTemplate.getForObject("http://"+ RestTemplateConfiguration.host+":"+RestTemplateConfiguration.port+"/getAllOpenTasks", TaskDTO[].class)).thenThrow(PersistenceLayerException.class);
        assertEquals(2, taskService.getAllOpenTasks().size());
    }

    @Test
    public void testGetTaskByIdPositive() throws ServiceLayerException, PersistenceLayerException {
        LOG.debug("test get task by id positive");
        TaskDTO taskDTO = taskConverter.convertPlainObjectToRestDTO(task);
        when(restTemplate.getForObject("http://"+RestTemplateConfiguration.host+":"+ RestTemplateConfiguration.port+"/getTaskById/{id}", TaskDTO.class, task.getId())).thenReturn(taskDTO);
        assertEquals(task.getId(), taskService.getTaskById(task.getId()).getId());
    }

    @Test(expected = ServiceLayerException.class)
    public void testGetTaskByIdPersistenceError() throws ServiceLayerException, PersistenceLayerException {
        LOG.debug("test get task by id persistence error");

        when(restTemplate.getForObject("http://"+RestTemplateConfiguration.host+":"+ RestTemplateConfiguration.port+"/getTaskById/{id}", TaskDTO.class, task.getId())).thenThrow(PersistenceLayerException.class);
        taskService.getTaskById(task.getId());
    }


    @After
    public void after(){
        task = null;
    }

    @AfterClass
    public static void teardown(){
        LOG.debug("teardown task service test on client");

    }
}
