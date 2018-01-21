package restLayer;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;
import at.ac.tuwien.sepm.assignment.group02.server.MainApplication;
import at.ac.tuwien.sepm.assignment.group02.server.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TaskDAOJDBC;
import at.ac.tuwien.sepm.assignment.group02.server.rest.TaskControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.server.service.TaskService;
import at.ac.tuwien.sepm.assignment.group02.server.service.TaskServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.server.util.DBUtil;
import at.ac.tuwien.sepm.assignment.group02.server.validation.PrimitiveValidator;
import at.ac.tuwien.sepm.assignment.group02.server.validation.ValidateTask;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=MainApplication.class)
@WebMvcTest(TaskControllerImpl.class)
public class TaskTests {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    private static Connection con;

    @BeforeClass
    public static void setUp() {
        con = DBUtil.getConnection();
    }

    @Before
    public void beforeMethod() {

        DBUtil.initDB(false);

    }

    @Test
    public void updateTask_finishesTask() throws Exception {

        TaskDTO taskDTO = new TaskDTO();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM TASK WHERE DONE=FALSE LIMIT 1;");
        ps.execute();
        ResultSet rs = ps.getResultSet();
        if(rs.next()) {
            taskDTO.setId(rs.getInt("id"));
            taskDTO.setOrder_id(rs.getInt("orderid"));
            taskDTO.setDescription(rs.getString("description"));
            taskDTO.setFinishing(rs.getString("finishing"));
            taskDTO.setWood_type(rs.getString("wood_type"));
            taskDTO.setQuality(rs.getString("quality"));
            taskDTO.setSize(rs.getInt("size"));
            taskDTO.setWidth(rs.getInt("width"));
            taskDTO.setLength(rs.getInt("length"));
            taskDTO.setQuantity(rs.getInt("quantity"));
            taskDTO.setProduced_quantity(rs.getInt("produced_quantity"));
            taskDTO.setPrice(rs.getInt("price"));
            taskDTO.setDone(rs.getBoolean("done"));
            taskDTO.setIn_progress(rs.getBoolean("in_progress"));
        }
        int neededQuantity = taskDTO.getQuantity();
        int producedQuantityBefore = taskDTO.getProduced_quantity();
        int diff = neededQuantity-producedQuantityBefore;
        taskDTO.setProduced_quantity(producedQuantityBefore+diff);

        this.mvc.perform(put("/updateTask")
                .content(mapper.writeValueAsString(taskDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        TaskService taskService
                = new TaskServiceImpl(new TaskDAOJDBC(DBUtil.getConnection()),new TaskConverter(),new ValidateTask(new PrimitiveValidator()));

        TaskDTO afterTask = taskService.getTaskById(taskDTO.getId());

        Assert.assertTrue(afterTask.isDone()==true);
        Assert.assertTrue(afterTask.getQuantity()==taskDTO.getQuantity());
        Assert.assertTrue(afterTask.getProduced_quantity()==taskDTO.getProduced_quantity());

    }

    @Test
    public void updateTask_works() throws Exception {

        TaskService taskService
                = new TaskServiceImpl(new TaskDAOJDBC(DBUtil.getConnection()),new TaskConverter(),new ValidateTask(new PrimitiveValidator()));

        TaskDTO taskDTO = taskService.getTaskById(1);
        taskDTO.setProduced_quantity(taskDTO.getProduced_quantity()+1);
        Assert.assertTrue(taskDTO.getProduced_quantity()<taskDTO.getQuantity());

        this.mvc.perform(put("/updateTask")
                .content(mapper.writeValueAsString(taskDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        TaskDTO afterTask = taskService.getTaskById(taskDTO.getId());

        Assert.assertTrue(afterTask.isDone()==false);
        Assert.assertTrue(afterTask.getQuantity()==taskDTO.getQuantity());
        Assert.assertTrue(afterTask.getProduced_quantity()==taskDTO.getProduced_quantity());

    }

    @Test
    public void updateTask_negativeAmountOfProducedLumber() throws Exception {
        TaskService taskService
                = new TaskServiceImpl(new TaskDAOJDBC(DBUtil.getConnection()),new TaskConverter(),new ValidateTask(new PrimitiveValidator()));

        TaskDTO taskDTO = taskService.getTaskById(1);
        taskDTO.setProduced_quantity(taskDTO.getProduced_quantity()-10);

        this.mvc.perform(put("/updateTask")
                .content(mapper.writeValueAsString(taskDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

    }

    @Test
    public void updateTask_validationException() throws Exception {
        this.mvc.perform(put("/updateTask")
                .content(mapper.writeValueAsString(new TaskDTO()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void updateTask_wrongContent() throws Exception {
        TimberDTO timber = new TimberDTO();
        this.mvc.perform(put("/updateTask")
                .content(mapper.writeValueAsString(timber))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getTaskById_works() throws Exception {

        this.mvc.perform(get("/getTaskById/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id")
                        .value("1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getTaskById_NotFound() throws Exception {

        this.mvc.perform(get("/getTaskById/{id}",338829900)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllOpenTasks_works() throws Exception {

        PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM TASK WHERE DELETED=FALSE");
        ps.execute();
        ResultSet rs = ps.getResultSet();
        int allTask=-1;
        if(rs.next())
            allTask = rs.getInt("COUNT(*)");

        this.mvc.perform(MockMvcRequestBuilders.get("/getAllOpenTasks")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(allTask)))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllOpenTasks_wrongMethod() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/getAllOpenTasks")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }


    @AfterClass
    public static void tearDown() {
        DBUtil.closeConnection();
    }

}