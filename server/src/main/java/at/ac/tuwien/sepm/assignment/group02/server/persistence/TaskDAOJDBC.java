package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.util.List;

@Component
public class TaskDAOJDBC implements TaskDAO {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static Connection dbConnection;

    public TaskDAOJDBC(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public void createTask(Task task) throws PersistenceLayerException {

    }

    @Override
    public void deleteTask(Task task) throws PersistenceLayerException {

    }

    @Override
    public void updateTask(Task task) throws PersistenceLayerException {
        LOG.info("Attempting to Update Task");
        String getStatement = "SELECT PRODUCED_QUANTITY FROM TASK WHERE ID = ?";
        String updateStatement = "UPDATE TASK SET PRODUCED_QUANTITY = ? WHERE ID = ?";

        try {
            PreparedStatement stmt = dbConnection.prepareStatement(getStatement);
            stmt.setInt(1,task.getId());
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int current_amount = rs.getInt(1);
            current_amount += task.getProduced_quantity();
            stmt = dbConnection.prepareStatement(updateStatement);
            stmt.setInt(1,current_amount);
            stmt.setInt(2,task.getId());
            stmt.executeUpdate();
        } catch(SQLException e) {
            LOG.error("SQL Exception: " + e.getMessage());
            throw new PersistenceLayerException("Database Error");
        }

        }

    @Override
    public List<Task> getAllOpenTasks() throws PersistenceLayerException {
        return null;
    }

    @Override
    public void getTaskById(int task_id) throws PersistenceLayerException {

    }
}
