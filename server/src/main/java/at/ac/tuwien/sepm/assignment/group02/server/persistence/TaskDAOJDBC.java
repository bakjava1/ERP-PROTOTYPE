package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.util.List;

@Component
public class TaskDAOJDBC implements TaskDAO {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static Connection dbConnection;

    @Autowired
    public TaskDAOJDBC(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public void createTask(Task task) throws PersistenceLayerException {

    }

    @Override
    public void deleteTask(Task task) throws PersistenceLayerException {
        LOG.debug("deleting task with order number: {}", task.getOrderID());

        String getLumberForDeletingTask = "SELECT DESCRIPTION, FINISHING, WOOD_TYPE, QUALITY, SIZE, WIDTH, LENGTH FROM TASK WHERE ORDERID = ?";
        String getTotalReservedLumber = "SELECT RESERVED_QUANTITY FROM LUMBER WHERE DESCRIPTION = ? AND FINISHING = ? AND WOOD_TYPE = ? AND QUALITY = ? AND SIZE = ? AND WIDTH = ? AND LENGTH = ?";
        String getReservedLumberTask = "SELECT RESERVED_QUANTITY FROM TASK WHERE WHERE DESCRIPTION = ? AND FINISHING = ? AND WOOD_TYPE = ? AND QUALITY = ? AND SIZE = ? AND WIDTH = ? AND LENGTH = ?";
        String deleteLumberReservation = "UPDATE LUMBER SET RESERVED_QUANTITY = ?, ALL_RESERVED = 0 WHERE DESCRIPTION = ? AND FINISHING = ? AND WOOD_TYPE = ? AND QUALITY = ? AND SIZE = ? AND WIDTH = ? AND LENGTH = ?";
        String deleteTask = "UPDATE TASK SET DELETED = 1 WHERE ORDERID = ?";

        try {
            //get all types of tasks and lumber by order_id
            PreparedStatement ps = dbConnection.prepareStatement(getLumberForDeletingTask, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, task.getOrderID());
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();

            Lumber lumber;
            int totalReservedLumber, reservedLumberTask, calculatedReservedLumber = 0;
            while(rs.next()) {
                lumber = new Lumber();
                lumber.setDescription(rs.getString("Description"));
                lumber.setFinishing(rs.getString("Finishing"));
                lumber.setWood_type(rs.getString("Wood_Type"));
                lumber.setQuality(rs.getString("Quality"));
                lumber.setSize(rs.getInt("Size"));
                lumber.setWidth(rs.getInt("Width"));
                lumber.setLength(rs.getInt("Length"));

                //get amount of reserved lumber in table lumber for the current task
                ps = dbConnection.prepareStatement(getTotalReservedLumber, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, lumber.getDescription());
                ps.setString(2, lumber.getFinishing());
                ps.setString(3, lumber.getWood_type());
                ps.setString(4, lumber.getQuality());
                ps.setInt(5, lumber.getSize());
                ps.setInt(6, lumber.getWidth());
                ps.setInt(7, lumber.getLength());
                ps.execute();
                totalReservedLumber = ps.getGeneratedKeys().getInt(1);

                //get amount of reserved lumber for the current task (table task)
                ps = dbConnection.prepareStatement(getReservedLumberTask, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, lumber.getDescription());
                ps.setString(2, lumber.getFinishing());
                ps.setString(3, lumber.getWood_type());
                ps.setString(4, lumber.getQuality());
                ps.setInt(5, lumber.getSize());
                ps.setInt(6, lumber.getWidth());
                ps.setInt(7, lumber.getLength());
                ps.execute();
                reservedLumberTask = ps.getGeneratedKeys().getInt(1);

                if(totalReservedLumber > reservedLumberTask) {
                    calculatedReservedLumber = totalReservedLumber - reservedLumberTask;
                }

                //set (reduced) amount of reserved lumber in table lumber for the current task
                ps = dbConnection.prepareStatement(deleteLumberReservation, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, calculatedReservedLumber);
                ps.setString(2, lumber.getDescription());
                ps.setString(3, lumber.getFinishing());
                ps.setString(4, lumber.getWood_type());
                ps.setString(5, lumber.getQuality());
                ps.setInt(6, lumber.getSize());
                ps.setInt(7, lumber.getWidth());
                ps.setInt(8, lumber.getLength());
                ps.execute();

                //set current task to deleted
                ps = dbConnection.prepareStatement(deleteTask, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, task.getOrderID());
                ps.execute();
            }
        } catch (SQLException e) {
            LOG.error("SQL Exception: " + e.getMessage());
            throw new PersistenceLayerException("Database error");
        }
    }

    @Override
    public void updateTask(Task task) throws PersistenceLayerException {

    }

    @Override
    public List<Task> getAllOpenTasks() throws PersistenceLayerException {
        return null;
    }

    @Override
    public void getTaskById(int task_id) throws PersistenceLayerException {

    }
}
