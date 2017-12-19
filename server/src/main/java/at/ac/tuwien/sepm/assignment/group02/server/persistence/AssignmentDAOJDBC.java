package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.server.entity.Assignment;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.EntityNotFoundException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    }

    @Override
    public List<Assignment> getAllAssignments() throws PersistenceLayerException {
        return null;
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
