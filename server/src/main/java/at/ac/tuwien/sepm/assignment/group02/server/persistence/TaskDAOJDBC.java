package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.server.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.EntityNotFoundException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class TaskDAOJDBC implements TaskDAO {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static Connection dbConnection;

    public TaskDAOJDBC(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public int createTask(Task task) throws PersistenceLayerException {
        return 1;
    }

    @Override
    public void deleteTask(Task task) throws PersistenceLayerException {
        LOG.debug("deleting task with order number: {}", task.getOrder_id());

        String getLumberForDeletingTask = "SELECT * FROM TASK WHERE ORDERID = ?";
        String getIsTaskDone = "SELECT DONE FROM TASK WHERE ID = ?";
        String getTotalReservedLumber = "SELECT RESERVED_QUANTITY FROM LUMBER WHERE DESCRIPTION = ? AND FINISHING = ? AND WOOD_TYPE = ? AND QUALITY = ? AND SIZE = ? AND WIDTH = ? AND LENGTH = ?";
        String getTaskLumberAmount = "SELECT QUANTITY FROM TASK WHERE DESCRIPTION = ? AND FINISHING = ? AND WOOD_TYPE = ? AND QUALITY = ? AND SIZE = ? AND WIDTH = ? AND LENGTH = ?";
        String deleteLumberReservation = "UPDATE LUMBER SET RESERVED_QUANTITY = ? WHERE DESCRIPTION = ? AND FINISHING = ? AND WOOD_TYPE = ? AND QUALITY = ? AND SIZE = ? AND WIDTH = ? AND LENGTH = ?";
        String deleteTask = "UPDATE TASK SET DELETED = 1 WHERE ORDERID = ?";
        String deleteAssignment = "UPDATE ASSIGNMENT SET ISDONE = 1 WHERE TASK_ID = ?";

        try {
            //get all types of tasks and lumber by order_id
            PreparedStatement ps = dbConnection.prepareStatement(getLumberForDeletingTask, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, task.getOrder_id());
            ps.execute();

            ResultSet rs = ps.getResultSet();

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

                PreparedStatement ps1 = dbConnection.prepareStatement(getIsTaskDone, Statement.RETURN_GENERATED_KEYS);
                ps1.setInt(1, rs.getInt("ID"));
                ps1.execute();
                ResultSet rs1 = ps1.getResultSet();
                boolean taskIsDone = false;
                if(rs1.next()){
                    taskIsDone = rs1.getBoolean("DONE");
                    LOG.debug("taskIsDone "+ taskIsDone);
                }

                if(taskIsDone) {
                    //get amount of reserved lumber in table lumber for the current task
                    PreparedStatement ps2 = dbConnection.prepareStatement(getTotalReservedLumber);
                    ps2.setString(1, lumber.getDescription());
                    ps2.setString(2, lumber.getFinishing());
                    ps2.setString(3, lumber.getWood_type());
                    ps2.setString(4, lumber.getQuality());
                    ps2.setInt(5, lumber.getSize());
                    ps2.setInt(6, lumber.getWidth());
                    ps2.setInt(7, lumber.getLength());
                    ps2.execute();
                    ps2.getResultSet().next();
                    totalReservedLumber = ps2.getResultSet().getInt("RESERVED_QUANTITY");
                    LOG.debug("totalReservedLumber: "+totalReservedLumber);
                    ps2.close();

                    //get amount of reserved lumber for the current task (table task)
                    PreparedStatement ps3 = dbConnection.prepareStatement(getTaskLumberAmount, Statement.RETURN_GENERATED_KEYS);
                    ps3.setString(1, lumber.getDescription());
                    ps3.setString(2, lumber.getFinishing());
                    ps3.setString(3, lumber.getWood_type());
                    ps3.setString(4, lumber.getQuality());
                    ps3.setInt(5, lumber.getSize());
                    ps3.setInt(6, lumber.getWidth());
                    ps3.setInt(7, lumber.getLength());
                    ps3.execute();
                    ps3.getResultSet().next();
                    reservedLumberTask = ps3.getResultSet().getInt("QUANTITY");
                    LOG.debug("reservedLumberTask: "+reservedLumberTask);
                    ps3.close();

                    if (totalReservedLumber > reservedLumberTask) {
                        calculatedReservedLumber = totalReservedLumber - reservedLumberTask;
                    } /*else {
                        //TODO add else
                        calculatedReservedLumber = totalReservedLumber;
                    }*/
                    LOG.debug("calculatedReservedLumber: "+calculatedReservedLumber);

                    //set (reduced) amount of reserved lumber in table lumber for the current task
                    PreparedStatement ps4 = dbConnection.prepareStatement(deleteLumberReservation, Statement.RETURN_GENERATED_KEYS);
                    ps4.setInt(1, calculatedReservedLumber);
                    ps4.setString(2, lumber.getDescription());
                    ps4.setString(3, lumber.getFinishing());
                    ps4.setString(4, lumber.getWood_type());
                    ps4.setString(5, lumber.getQuality());
                    ps4.setInt(6, lumber.getSize());
                    ps4.setInt(7, lumber.getWidth());
                    ps4.setInt(8, lumber.getLength());
                    ps4.execute();
                    LOG.debug("ps4.execute(): "+ps4.toString());
                    ps4.close();
                }

                //set current task to deleted
                PreparedStatement ps5 = dbConnection.prepareStatement(deleteTask, Statement.RETURN_GENERATED_KEYS);
                ps5.setInt(1, task.getOrder_id());
                ps5.execute();
                LOG.debug("ps.execute(): "+ps5.toString());
                ps5.close();

                //set connected assignment to done
                PreparedStatement ps6 = dbConnection.prepareStatement(deleteAssignment, Statement.RETURN_GENERATED_KEYS);
                ps6.setInt(1, task.getId());
                ps6.execute();
                LOG.debug("ps.execute(): "+ps6.toString());
                ps6.close();
            }
        } catch (SQLException e) {
            LOG.error("SQL Exception: " + e.getMessage());
            throw new PersistenceLayerException("Database error");
        }
    }

    @Override
    public void updateTask(Task task) throws PersistenceLayerException {
        LOG.info("called updateTask");
        String getStatement = "SELECT PRODUCED_QUANTITY FROM TASK WHERE ID = ?";
        String updateStatement = "UPDATE TASK SET PRODUCED_QUANTITY = ?, DONE=?, IN_PROGRESS=? WHERE ID = ?";

        try {
            PreparedStatement stmt = dbConnection.prepareStatement(getStatement);
            stmt.setInt(1,task.getId());
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int current_amount = rs.getInt(1);
            //current_amount += task.getProduced_quantity();
            int new_amount = task.getProduced_quantity();

            if(new_amount>=current_amount) {
                stmt = dbConnection.prepareStatement(updateStatement);
                stmt.setInt(1, new_amount);
                stmt.setBoolean(2, task.isDone());
                stmt.setBoolean(3, task.isIn_progress());
                stmt.setInt(4, task.getId());
                stmt.executeUpdate();
            }

            stmt.close();
            rs.close();
        } catch(SQLException e) {
            LOG.error("SQL Exception: " + e.getMessage());
            throw new PersistenceLayerException("Database Error");
        }

        }

    @Override
    public List<Task> getAllOpenTasks() throws PersistenceLayerException {

        LOG.trace("called getAllOpenTasks");

        List<Task> taskList = new ArrayList<>();

        try {

            PreparedStatement ps = dbConnection.prepareStatement("SELECT * FROM TASK WHERE " +
                    "DONE = 0 AND DELETED = 0 ORDER BY ORDERID");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Task currentTask = new Task();
                currentTask.setId(rs.getInt("id"));
                currentTask.setOrder_id(rs.getInt("orderid"));

                currentTask.setDescription(rs.getString("description"));
                currentTask.setFinishing(rs.getString("finishing"));
                currentTask.setWood_type(rs.getString("wood_type"));
                currentTask.setQuality(rs.getString("quality"));

                currentTask.setSize(rs.getInt("size"));
                currentTask.setWidth(rs.getInt("width"));
                currentTask.setLength(rs.getInt("length"));

                currentTask.setQuantity(rs.getInt("quantity"));
                currentTask.setProduced_quantity(rs.getInt("produced_quantity"));

                currentTask.setPrice(rs.getInt("price"));
                currentTask.setDone(rs.getBoolean("done"));
                currentTask.setIn_progress(rs.getBoolean("in_progress"));
                taskList.add(currentTask);
            }

            rs.close();
            ps.close();

            if (taskList.size() == 0) {
                //no open tasks was found
                LOG.debug("No open task found");
                //throw new PersistenceLayerException("No open task found");
            }

        } catch (SQLException e) {
            LOG.error("SQL Exception: " +  e.getMessage());
            throw new PersistenceLayerException("Database error");
        }

        return taskList;
    }

    @Override
    public List<Task> getAllTasks() throws PersistenceLayerException {
        LOG.trace("called getAllTasks");

        List<Task> taskList = new ArrayList<>();

        try {

            PreparedStatement ps = dbConnection.prepareStatement("SELECT * FROM TASK WHERE " +
                    "DELETED = 0 ORDER BY ORDERID");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Task currentTask = new Task();
                currentTask.setId(rs.getInt("id"));
                currentTask.setOrder_id(rs.getInt("orderid"));

                currentTask.setDescription(rs.getString("description"));
                currentTask.setFinishing(rs.getString("finishing"));
                currentTask.setWood_type(rs.getString("wood_type"));
                currentTask.setQuality(rs.getString("quality"));

                currentTask.setSize(rs.getInt("size"));
                currentTask.setWidth(rs.getInt("width"));
                currentTask.setLength(rs.getInt("length"));

                currentTask.setQuantity(rs.getInt("quantity"));
                currentTask.setProduced_quantity(rs.getInt("produced_quantity"));

                currentTask.setPrice(rs.getInt("price"));
                currentTask.setDone(rs.getBoolean("done"));
                currentTask.setIn_progress(rs.getBoolean("in_progress"));

                taskList.add(currentTask);
            }

            rs.close();
            ps.close();

            if (taskList.size() == 0) {
                //no open tasks was found
                LOG.debug("No open task found");
                //throw new PersistenceLayerException("No open task found");
            }

        } catch (SQLException e) {
            LOG.error("SQL Exception: " +  e.getMessage());
            throw new PersistenceLayerException("Database error");
        }

        return taskList;
    }

    @Override
    public Task getTaskById(int task_id) throws PersistenceLayerException {

        try {

            PreparedStatement ps = dbConnection.prepareStatement("SELECT * FROM TASK WHERE ID = ? LIMIT 1");
            ps.setInt(1,task_id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Task currentTask = new Task();
                currentTask.setId(rs.getInt("id"));
                currentTask.setOrder_id(rs.getInt("orderid"));
                currentTask.setDescription(rs.getString("description"));
                currentTask.setFinishing(rs.getString("finishing"));
                currentTask.setWood_type(rs.getString("wood_type"));
                currentTask.setQuality(rs.getString("quality"));
                currentTask.setSize(rs.getInt("size"));
                currentTask.setWidth(rs.getInt("width"));
                currentTask.setLength(rs.getInt("length"));
                currentTask.setQuantity(rs.getInt("quantity"));
                currentTask.setProduced_quantity(rs.getInt("produced_quantity"));
                currentTask.setPrice(rs.getInt("price"));
                currentTask.setDone(rs.getBoolean("done"));
                currentTask.setIn_progress(rs.getBoolean("in_progress"));

                return currentTask;
            } else throw new EntityNotFoundException("");

        } catch (SQLException e){
            throw new PersistenceLayerException("Database error:" + e.getMessage());
        }

    }


    @Override
    public List<Task> getTasksByOrderId(int order_id) throws PersistenceLayerException {
        LOG.trace("Get tasks by orderId from database");

        List<Task> taskList = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;

        try {

            //connect to db
            ps = dbConnection.prepareStatement("SELECT * FROM TASK WHERE ORDERID = ?");
            ps.setInt(1, order_id);
            rs = ps.executeQuery();

            while (rs.next()) {

                Task currentTask = new Task();
                currentTask.setQuantity(rs.getInt("quantity"));
                currentTask.setPrice(rs.getInt("price"));
                currentTask.setOrder_id(rs.getInt("orderid"));
                currentTask.setDescription(rs.getString("description"));
                currentTask.setFinishing(rs.getString("finishing"));
                currentTask.setWood_type(rs.getString("wood_type"));
                currentTask.setQuality(rs.getString("quality"));
                currentTask.setSize(rs.getInt("size"));
                currentTask.setWidth(rs.getInt("width"));
                currentTask.setLength(rs.getInt("length"));
                currentTask.setProduced_quantity(rs.getInt("produced_quantity"));
                currentTask.setDone(rs.getBoolean("done"));
                currentTask.setIn_progress(rs.getBoolean("in_progress"));
                currentTask.setId(rs.getInt("id"));

                taskList.add(currentTask);
                LOG.trace("adding task: "+ currentTask.toString());
            }

            if (taskList.size() == 0) {
                throw new PersistenceLayerException("No tasks found");
            }

        } catch (SQLException e) {
            throw new PersistenceLayerException("Database error:" + e.getMessage());
        }
        return taskList;
    }


}
