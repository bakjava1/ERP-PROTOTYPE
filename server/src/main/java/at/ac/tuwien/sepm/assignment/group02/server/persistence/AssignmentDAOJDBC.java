package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.server.entity.Assignment;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.EntityNotFoundException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Component
public class AssignmentDAOJDBC implements AssignmentDAO {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private Connection dbConnection;

    @Autowired
    public AssignmentDAOJDBC(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }


    @Override
    public void createAssignment(Assignment assignment) throws PersistenceLayerException {
        LOG.debug("called createAssignment");

        String insert =
                "INSERT INTO ASSIGNMENT(ID, creation_date, amount, box_ID, isDone, task_id) VALUES"+
                "(default, now(), ?, ?, false, ?)";

        try {

            PreparedStatement ps = dbConnection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1,assignment.getAmount());
            ps.setInt(2,assignment.getBox_id());
            ps.setInt(3,assignment.getTask_id());
            ps.executeUpdate();

            ResultSet generatedKey = ps.getGeneratedKeys();
            generatedKey.next();

            int newID = generatedKey.getInt(1);
            assignment.setId(newID);

            generatedKey.close();
            ps.close();

        } catch (SQLException e) {
            LOG.warn("SQLException: {}", e.getMessage());
            throw new PersistenceLayerException("Database error");
        }
    }

    @Override
    public List<Assignment> getAllOpenAssignments() throws PersistenceLayerException {
        LOG.debug("get all assignments called in server persistence layer");

        List<Assignment> assignmentList = new LinkedList<>();
        Assignment assignment;
        String getAllOpenAssignments = "SELECT ID, CREATION_DATE, AMOUNT, BOX_ID, ISDONE, TASK_ID FROM ASSIGNMENT WHERE ISDONE = 0";

        try {
            PreparedStatement ps = dbConnection.prepareStatement(getAllOpenAssignments);
            ps.execute();

            ResultSet rs = ps.getResultSet();

            while(rs.next()) {
                assignment = new Assignment();
                assignment.setId(rs.getInt("ID"));
                assignment.setCreation_date(rs.getDate("CREATION_DATE").toString());
                assignment.setAmount(rs.getInt("AMOUNT"));
                assignment.setBox_id(rs.getInt("BOX_ID"));
                assignment.setDone(rs.getBoolean("ISDONE"));
                assignment.setTask_id(rs.getInt("TASK_ID"));
                assignmentList.add(assignment);
            }
        } catch (SQLException e) {
            LOG.warn("SQLException: {}", e.getMessage());
            throw new PersistenceLayerException("Database error");
        }

        return assignmentList;
    }

    @Override
    public List<Assignment> getAllAssignments() throws PersistenceLayerException {
        LOG.debug("get all assignments called in server persistence layer");

        List<Assignment> assignmentList = new LinkedList<>();
        Assignment assignment;
        String getAllAssignments = "SELECT ID, CREATION_DATE, AMOUNT, BOX_ID, ISDONE, TASK_ID FROM ASSIGNMENT";

        try {
            PreparedStatement ps = dbConnection.prepareStatement(getAllAssignments);
            ps.execute();

            ResultSet rs = ps.getResultSet();

            while(rs.next()) {
                assignment = new Assignment();
                assignment.setId(rs.getInt("ID"));
                assignment.setCreation_date(rs.getDate("CREATION_DATE").toString());
                assignment.setAmount(rs.getInt("AMOUNT"));
                assignment.setBox_id(rs.getInt("BOX_ID"));
                assignment.setDone(rs.getBoolean("ISDONE"));
                assignment.setTask_id(rs.getInt("TASK_ID"));
                assignmentList.add(assignment);
            }
        } catch (SQLException e) {
            LOG.warn("SQLException: {}", e.getMessage());
            throw new PersistenceLayerException("Database error");
        }

        return assignmentList;
    }

    @Override
    public void setAssignmentDone(Assignment assignment) throws PersistenceLayerException {
        LOG.debug("called updateAssignment: {}", assignment.toString());
        String updateStatement = "UPDATE ASSIGNMENT SET isDone = TRUE WHERE ID = ?";

        try {
            PreparedStatement stmt = dbConnection.prepareStatement(updateStatement);
            stmt.setInt(1,assignment.getId());
            int result = stmt.executeUpdate();
            LOG.debug("number of updated rows: {}", result);
            if(result<1) throw new EntityNotFoundException("number of updated rows less than 1");
        } catch(SQLException e) {
            LOG.error("SQL Exception: " + e.getMessage());
            throw new PersistenceLayerException("Database Error");
        }

    }

    @Override
    public void deleteAssignment(int id) throws PersistenceLayerException {

    }
}
