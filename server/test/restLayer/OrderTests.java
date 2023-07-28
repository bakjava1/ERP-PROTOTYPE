package restLayer;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TaskDTO;
import at.ac.tuwien.sepm.assignment.group02.server.MainApplication;
import at.ac.tuwien.sepm.assignment.group02.server.rest.OrderControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.server.util.DBUtil;
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
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=MainApplication.class)
@WebMvcTest(OrderControllerImpl.class)
public class OrderTests {

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
    public void getAllOpenOrders_works() throws Exception {

        PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM ORDERS WHERE ISPAIDFLAG=FALSE");
        ps.execute();
        ResultSet rs = ps.getResultSet();
        int allOrder=-1;
        if(rs.next())
            allOrder = rs.getInt("COUNT(*)");

        this.mvc.perform(MockMvcRequestBuilders.get("/getAllOpen")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(allOrder)))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllOpenOrders_wrongMethod() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/getAllOpen")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getAllClosedOrders_works() throws Exception {

        PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM ORDERS WHERE ISPAIDFLAG=TRUE");
        ps.execute();
        ResultSet rs = ps.getResultSet();
        int allOrder=-1;
        if(rs.next())
            allOrder = rs.getInt("COUNT(*)");

        this.mvc.perform(MockMvcRequestBuilders.get("/getAllClosedOrders")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(allOrder)))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllClosedOrders_wrongMethod() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/getAllClosedOrders")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void createOrder_works() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCustomerName("test");
        orderDTO.setCustomerAddress("test");
        orderDTO.setCustomerUID("test");
        orderDTO.setOrderDate("2018-01-01 12:38:40.123");

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(3);
        taskDTO.setOrder_id(2);
        taskDTO.setDescription("Latten");
        taskDTO.setFinishing("prismiert");
        taskDTO.setWood_type("Ta");
        taskDTO.setQuality("I/III");
        taskDTO.setSize(22);
        taskDTO.setWidth(48);
        taskDTO.setLength(3000);
        taskDTO.setQuantity(40);

        List<TaskDTO> taskDTOList = new ArrayList<>();
        taskDTOList.add(taskDTO);
        orderDTO.setTaskList(taskDTOList);

        this.mvc.perform(MockMvcRequestBuilders.post("/createOrder")
                .content(mapper.writeValueAsString(orderDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void createOrder_invalidTaskList() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCustomerName("test");
        orderDTO.setCustomerAddress("test");
        orderDTO.setCustomerUID("test");
        orderDTO.setOrderDate("2018-01-01 12:38:40.123");
        List<TaskDTO> taskDTOList = new ArrayList<>();
        taskDTOList.add(new TaskDTO());
        orderDTO.setTaskList(taskDTOList);

        this.mvc.perform(MockMvcRequestBuilders.post("/createOrder")
                .content(mapper.writeValueAsString(orderDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotModified());
    }

    @Test
    public void deleteOrder_works() throws Exception {
        OrderDTO orderDTO = new OrderDTO();

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(3);
        taskDTO.setOrder_id(2);
        taskDTO.setDescription("Latten");
        taskDTO.setFinishing("prismiert");
        taskDTO.setWood_type("Ta");
        taskDTO.setQuality("I/III");
        taskDTO.setSize(22);
        taskDTO.setWidth(48);
        taskDTO.setLength(3000);
        taskDTO.setQuantity(40);

        List<TaskDTO> taskDTOList = new ArrayList<>();
        taskDTOList.add(taskDTO);
        orderDTO.setTaskList(taskDTOList);

        PreparedStatement ps = con.prepareStatement("SELECT * FROM ORDERS WHERE ISDONEFLAG=FALSE LIMIT 1");
        ps.execute();
        ResultSet rs = ps.getResultSet();
        if(rs.next()){
            orderDTO.setID(rs.getInt("ID"));
        }
        rs.close();
        ps.close();

        this.mvc.perform(MockMvcRequestBuilders.put("/deleteOrder")
                .content(mapper.writeValueAsString(orderDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        PreparedStatement ps2 = con.prepareStatement("SELECT * FROM ORDERS WHERE ID=?");
        ps2.setInt(1,orderDTO.getID());
        ps2.execute();
        ResultSet rs2 = ps2.getResultSet();
        rs2.next();
        Assert.assertTrue(rs2.getBoolean("ISDONEFLAG")==true);
        ps2.close();
        rs2.close();

    }


    @AfterClass
    public static void tearDown() {
        DBUtil.closeConnection();
    }

}