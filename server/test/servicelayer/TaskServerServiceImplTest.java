package servicelayer;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.server.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TaskDAO;
import at.ac.tuwien.sepm.assignment.group02.server.service.TaskService;
import at.ac.tuwien.sepm.assignment.group02.server.service.TaskServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.server.validation.ValidateTask;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

// @RunWith attach a runner to initialize the test data
@RunWith(MockitoJUnitRunner.class)
public class TaskServerServiceImplTest {

    @Mock
    private TaskDAO taskManagementDAO;
    @Mock
    private TaskConverter taskConverter;
    @Mock
    private ValidateTask validateTask;

    @Test
    public void testGetTaskById_works() throws Exception {
        TaskService taskService
                = new TaskServiceImpl(taskManagementDAO,taskConverter,validateTask);

        Task task = Mockito.mock(Task.class);
        TaskDTO taskDTO = Mockito.mock(TaskDTO.class);

        when(taskManagementDAO.getTaskById(anyInt())).thenReturn(task);
        when(taskConverter.convertPlainObjectToRestDTO(task)).thenReturn(taskDTO);

        TaskDTO taskDTO1 = taskService.getTaskById(anyInt());

        verify(taskManagementDAO, times(1)).getTaskById(anyInt());
        verify(taskConverter,times(1)).convertPlainObjectToRestDTO(any(Task.class));

        Assert.assertSame(taskDTO, taskDTO1);
    }


    @Test(expected = ServiceLayerException.class)
    public void testGetTaskById_PersistenceLayerException() throws Exception {
        TaskService taskService
                = new TaskServiceImpl(taskManagementDAO,taskConverter,validateTask);

        doThrow(PersistenceLayerException.class).when(taskManagementDAO).getTaskById(anyInt());

        taskService.getTaskById(anyInt());
    }

    @Test
    public void testCreateTask_works() throws Exception {
        TaskService taskService
                = new TaskServiceImpl(taskManagementDAO,taskConverter,validateTask);

        int task_id1=3;
        when(taskManagementDAO.createTask(any(Task.class))).thenReturn(task_id1);

        int task_id2 = taskService.createTask(any(TaskDTO.class));

        verify(taskConverter,times(1)).convertRestDTOToPlainObject(any(TaskDTO.class));
        verify(validateTask,times(1)).isValid(any(Task.class));
        verify(taskManagementDAO,times(1)).createTask(any(Task.class));

        Assert.assertEquals(task_id1,task_id2);
    }

    @Test(expected = InvalidInputException.class)
    public void testCreateTask_ValidationException() throws Exception {
        TaskService taskService
                = new TaskServiceImpl(taskManagementDAO,taskConverter,validateTask);

        doThrow(InvalidInputException.class).when(validateTask).isValid(any(Task.class));

        taskService.createTask(any(TaskDTO.class));
        verify(taskConverter,times(1)).convertRestDTOToPlainObject(any(TaskDTO.class));

    }

    @Test(expected = ServiceLayerException.class)
    public void testCreateTask_PersistenceLayerException() throws Exception {
        TaskService taskService
                = new TaskServiceImpl(taskManagementDAO,taskConverter,validateTask);

        doThrow(PersistenceLayerException.class).when(taskManagementDAO).createTask(any(Task.class));

        taskService.createTask(any(TaskDTO.class));
    }

    @Test
    public void testGetAllTask_returns2Task() throws Exception {
        TaskService taskService
                = new TaskServiceImpl(taskManagementDAO,taskConverter,validateTask);

        List<Task> allTasks = new ArrayList<>();

        Task l1 = Mockito.mock(Task.class);
        Task l2 = Mockito.mock(Task.class);
        allTasks.add(l1);
        allTasks.add(l2);
        when(taskManagementDAO.getAllTasks()).thenReturn(allTasks);

        List<TaskDTO> allTasksConverted = taskService.getAllOpenTasks();

        verify(taskManagementDAO, times(1)).getAllTasks();
        verify(taskConverter, times(2)).convertPlainObjectToRestDTO(any(Task.class));

        Assert.assertEquals(allTasksConverted.size(),2);
    }

    @Test
    public void testGetAllTask_returnsNoTask() throws Exception {
        TaskService taskService
                = new TaskServiceImpl(taskManagementDAO,taskConverter,validateTask);

        List<Task> allTask = new ArrayList<>();
        when(taskManagementDAO.getAllTasks()).thenReturn(allTask);

        List<TaskDTO> allTaskConverted = taskService.getAllOpenTasks();

        verify(taskManagementDAO, times(1)).getAllTasks();
        verify(taskConverter, times(0)).convertPlainObjectToRestDTO(any(Task.class));

        Assert.assertEquals(allTaskConverted.size(),0);
    }

    @Test(expected = ServiceLayerException.class)
    public void testGetAllTask_PersistenceLayerException() throws Exception {
        TaskService taskService
                = new TaskServiceImpl(taskManagementDAO,taskConverter,validateTask);

        doThrow(PersistenceLayerException.class).when(taskManagementDAO).getAllTasks();

        taskService.getAllOpenTasks();
    }

    @Test
    public void testUpdateTask_works1() throws Exception {
        TaskService taskService
                = new TaskServiceImpl(taskManagementDAO,taskConverter,validateTask);

        //check if task is done
        //if( toUpdate.getProduced_quantity() == toUpdate.getQuantity() )
        Task task = new Task();
        task.setProduced_quantity(0);
        task.setQuantity(1);
        when(taskConverter.convertRestDTOToPlainObject(any(TaskDTO.class))).thenReturn(task);

        taskService.updateTask(any(TaskDTO.class));

        verify(taskConverter,times(1)).convertRestDTOToPlainObject(any(TaskDTO.class));
        verify(taskManagementDAO,times(1)).updateTask(task);
        Assert.assertFalse(task.isDone());
    }

    @Test
    public void testUpdateTask_works2() throws Exception {
        TaskService taskService
                = new TaskServiceImpl(taskManagementDAO,taskConverter,validateTask);

        //check if task is done
        //if( toUpdate.getProduced_quantity() == toUpdate.getQuantity() )
        Task task = new Task();
        task.setProduced_quantity(1);
        task.setQuantity(1);
        when(taskConverter.convertRestDTOToPlainObject(any(TaskDTO.class))).thenReturn(task);

        taskService.updateTask(any(TaskDTO.class));

        verify(taskConverter,times(1)).convertRestDTOToPlainObject(any(TaskDTO.class));
        verify(taskManagementDAO,times(1)).updateTask(task);
        Assert.assertTrue(task.isDone());
    }

    @Test(expected = InvalidInputException.class)
    public void testUpdateTask_InvalidInputException() throws Exception {
        TaskService taskService
                = new TaskServiceImpl(taskManagementDAO,taskConverter,validateTask);

        doThrow(InvalidInputException.class).when(validateTask).isValid(any(Task.class));

        taskService.updateTask(any(TaskDTO.class));
    }

    @Test(expected = ServiceLayerException.class)
    public void testUpdateTask_PersistenceLayerException() throws Exception {
        TaskService taskService
                = new TaskServiceImpl(taskManagementDAO,taskConverter,validateTask);

        //check if task is done before persistence updateTask
        Task task = new Task();
        when(taskConverter.convertRestDTOToPlainObject(any(TaskDTO.class))).thenReturn(task);

        doThrow(PersistenceLayerException.class).when(taskManagementDAO).updateTask(any(Task.class));

        taskService.updateTask(any(TaskDTO.class));
    }

    @Test
    public void testRemoveTask_works() throws Exception {
        TaskService taskService
                = new TaskServiceImpl(taskManagementDAO,taskConverter,validateTask);

        Task task = new Task();
        when(taskConverter.convertRestDTOToPlainObject(any(TaskDTO.class))).thenReturn(task);

        taskService.deleteTask(any(TaskDTO.class));

        verify(taskConverter,times(1)).convertRestDTOToPlainObject(any(TaskDTO.class));
        verify(taskManagementDAO,times(1)).deleteTask(task);
    }

    @Test(expected = InvalidInputException.class)
    public void testRemoveTask_InvalidInputException() throws Exception {
        TaskService taskService
                = new TaskServiceImpl(taskManagementDAO,taskConverter,validateTask);

        doThrow(InvalidInputException.class).when(validateTask).isValid(any(Task.class));

        taskService.deleteTask(any(TaskDTO.class));
    }

    @Test(expected = ServiceLayerException.class)
    public void testRemoveTask_PersistenceLayerException() throws Exception {
        TaskService taskService
                = new TaskServiceImpl(taskManagementDAO,taskConverter,validateTask);

        doThrow(PersistenceLayerException.class).when(taskManagementDAO).deleteTask(any(Task.class));

        taskService.deleteTask(any(TaskDTO.class));
    }

}