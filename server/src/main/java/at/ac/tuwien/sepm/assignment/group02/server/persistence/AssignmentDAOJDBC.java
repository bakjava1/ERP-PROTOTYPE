package at.ac.tuwien.sepm.assignment.group02.server.persistence;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Assignment;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    }

    @Override
    public List<Assignment> getAllAssignments() throws PersistenceLayerException {
        LOG.debug("get all assignments called in server persistence layer");

        List<Assignment> assignmentList = new LinkedList<>();
        Assignment assignment;
        String getAllOpenAssignments = "SELECT ID, CREATION_DATE, AMOUNT, BOX_ID FROM ASSIGNMENT WHERE ISDONE = 0";

        try {
            PreparedStatement ps = dbConnection.prepareStatement(getAllOpenAssignments);
            ps.execute();

            ResultSet rs = ps.getResultSet();

            while(rs.next()) {
                assignment = new Assignment();
                assignment.setId(rs.getInt("ID"));
                assignment.setCreation_date(rs.getDate("CREATION_DATE"));
                assignment.setAmount(rs.getInt("AMOUNT"));
                assignment.setBoxID(rs.getInt("BOX_ID"));
                assignmentList.add(assignment);
            }
        } catch (SQLException e) {
            LOG.warn("SQLException: {}", e.getMessage());
            throw new PersistenceLayerException("Database error");
        }

        return assignmentList;
    }

    @Override
    public void updateAssignment(Assignment assignment) throws PersistenceLayerException {

    }

    @Override
    public void deleteAssignment(int id) throws PersistenceLayerException {

    }
}
