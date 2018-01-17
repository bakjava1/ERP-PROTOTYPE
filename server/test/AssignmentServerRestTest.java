import at.ac.tuwien.sepm.assignment.group02.server.MainApplication;
import at.ac.tuwien.sepm.assignment.group02.server.rest.AssignmentControllerImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=MainApplication.class)
@WebMvcTest(AssignmentControllerImpl.class)
public class AssignmentServerRestTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

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
    public void getAllAssignments_works() throws Exception {
        this.mvc.perform(get("/getAllAssignments").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]done")
                        .isNotEmpty());
    }
}