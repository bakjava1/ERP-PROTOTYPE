package restLayer;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import at.ac.tuwien.sepm.assignment.group02.server.MainApplication;
import at.ac.tuwien.sepm.assignment.group02.server.rest.AssignmentControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.server.util.DBUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=MainApplication.class)
@WebMvcTest(AssignmentControllerImpl.class)
public class AssignmentTests {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @Before
    public void beforeMethod() {
        Connection con = DBUtil.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO ASSIGNMENT(ID,CREATION_DATE, AMOUNT, BOX_ID, ISDONE, TASK_ID) " +
                    "VALUES " +
                    "(99,CURRENT_DATE,2,2,TRUE,1)," +
                    "(DEFAULT,CURRENT_DATE,1,1,TRUE,2), " +
                    "(DEFAULT,CURRENT_DATE,1,1,FALSE,3), " +
                    "(DEFAULT,CURRENT_DATE,1,1,FALSE,4), ");
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void smthNonExistent() throws Exception {
        this.mvc.perform(get("/smthNonExistent").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getAllOpenAssignments_works() throws Exception {

        this.mvc.perform(get("/getAllOpenAssignments").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]done")
                    .value("false"));
    }

    @Test
    public void getAllClosedAssignments_works() throws Exception {
        this.mvc.perform(get("/getAllClosedAssignments").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]done")
                        .value("true"));
    }

    @Test
    public void cleanUpAssignments_works() throws Exception {
        this.mvc.perform(put("/cleanUpAssignments"))
                .andExpect(status().isOk());
    }

    @Test
    public void cleanUpAssignments_wrongHttpMethod() throws Exception {
        this.mvc.perform(get("/cleanUpAssignments"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void createAssignments_works() throws Exception {
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setTask_id(1);
        assignmentDTO.setAmount(1);
        assignmentDTO.setBox_id(1);

        this.mvc.perform(MockMvcRequestBuilders.post("/createAssignment")
                .content(mapper.writeValueAsString(assignmentDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
    }

    @Test
    public void createAssignments_wrongContent() throws Exception {

        this.mvc.perform(MockMvcRequestBuilders.post("/createAssignment")
                .content(mapper.writeValueAsString("bussi"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void createAssignments_validationException() throws Exception {
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setTask_id(-1);
        assignmentDTO.setAmount(1);
        assignmentDTO.setBox_id(1);

        this.mvc.perform(MockMvcRequestBuilders.post("/createAssignment")
                .content(mapper.writeValueAsString(assignmentDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()); //ValidationException
    }

    @Test
    public void createAssignments_taskDoesNotExist() throws Exception {
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setTask_id(13920293);
        assignmentDTO.setAmount(1);
        assignmentDTO.setBox_id(1);

        this.mvc.perform(MockMvcRequestBuilders.post("/createAssignment")
                .content(mapper.writeValueAsString(assignmentDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection()); //EntityCreationException
    }

    @Test
    public void setAssignmentDone_works() throws Exception {
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setId(99);
        assignmentDTO.setTask_id(1);
        assignmentDTO.setAmount(1);
        assignmentDTO.setBox_id(1);

        this.mvc.perform(MockMvcRequestBuilders.put("/setAssignmentDone")
                .content(mapper.writeValueAsString(assignmentDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void setAssignmentDone_AssignmentHasInvalidID() throws Exception {
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setId(-1);
        assignmentDTO.setTask_id(1);
        assignmentDTO.setAmount(1);
        assignmentDTO.setBox_id(1);

        this.mvc.perform(MockMvcRequestBuilders.put("/setAssignmentDone")
                .content(mapper.writeValueAsString(assignmentDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()); //EntityNotFoundExceptionService
    }

    @Test
    public void setAssignmentDone_wrongContent() throws Exception {
        LumberDTO lumberDTO = new LumberDTO();

        this.mvc.perform(MockMvcRequestBuilders.put("/setAssignmentDone")
                .content(mapper.writeValueAsString(lumberDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }
}