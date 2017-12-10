package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLevelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.util.List;

public class TaskDAOJDBC implements TaskDAO {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static Connection dbConnection;

    public TaskDAOJDBC(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public void createTask(Task task) throws PersistenceLevelException {

    }

    @Override
    public void deleteTask(Task task) throws PersistenceLevelException {
        LOG.debug("deleting task with order number: {}", task.getOrderID());

        String deleteTask = "UPDATE TASK SET DELETED = 1 WHERE ORDERID = ?";
    }

    @Override
    public void updateTask(Task task) throws PersistenceLevelException {

    }

    @Override
    public List<Task> getAllOpenTasks() throws PersistenceLevelException {
        return null;
    }

    @Override
    public void getTaskById(int task_id) throws PersistenceLevelException {

    }
}
