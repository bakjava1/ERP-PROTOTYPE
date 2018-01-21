package restLayer;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.FilterDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;
import at.ac.tuwien.sepm.assignment.group02.server.MainApplication;
import at.ac.tuwien.sepm.assignment.group02.server.converter.LumberConverter;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.LumberDAOJDBC;
import at.ac.tuwien.sepm.assignment.group02.server.rest.LumberControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.server.service.LumberService;
import at.ac.tuwien.sepm.assignment.group02.server.service.LumberServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.server.util.DBUtil;
import at.ac.tuwien.sepm.assignment.group02.server.validation.PrimitiveValidator;
import at.ac.tuwien.sepm.assignment.group02.server.validation.ValidateLumber;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=MainApplication.class)
@WebMvcTest(LumberControllerImpl.class)
public class LumberTests {

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
    public void updateLumber_works() throws Exception {
        LumberDTO lumberDTO = new LumberDTO();
        lumberDTO.setId(1);
        lumberDTO.setDescription("Latten");
        lumberDTO.setFinishing("prismiert");
        lumberDTO.setWood_type("Fi");
        lumberDTO.setQuality("IV");
        lumberDTO.setSize(21);
        lumberDTO.setWidth(49);
        lumberDTO.setLength(5000);
        lumberDTO.setQuantity(40);

        this.mvc.perform(put("/updateLumber")
                .content(mapper.writeValueAsString(lumberDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateLumber_wrongContent() throws Exception {
        TimberDTO timber = new TimberDTO();
        this.mvc.perform(put("/updateLumber")
                .content(mapper.writeValueAsString(timber))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void removeLumber_works() throws Exception {
        LumberDTO lumberDTO;

        LumberService lumberService = new LumberServiceImpl(
                new LumberDAOJDBC(DBUtil.getConnection()),
                new LumberConverter(),
                new ValidateLumber(new PrimitiveValidator()));

        lumberDTO = lumberService.getLumberById(1);
        int quantityBefore = lumberDTO.getQuantity();
        int diff = 1;
        lumberDTO.setQuantity(diff);

        System.out.println(lumberDTO.toString());

        this.mvc.perform(put("/deleteLumber")
                .content(mapper.writeValueAsString(lumberDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        int quantityAfter = lumberService.getLumberById(1).getQuantity();

        Assert.assertTrue(quantityBefore==(quantityAfter+diff));

    }

    @Test
    public void removeLumber_entityNotFound() throws Exception {
        LumberDTO lumberDTO = new LumberDTO();
        lumberDTO.setId(338829900);
        lumberDTO.setDescription("Latten");
        lumberDTO.setFinishing("prismiert");
        lumberDTO.setWood_type("Ta");
        lumberDTO.setQuality("O/III");
        lumberDTO.setSize(22);
        lumberDTO.setWidth(48);
        lumberDTO.setLength(3500);
        lumberDTO.setQuantity(40);

        this.mvc.perform(put("/deleteLumber")
                .content(mapper.writeValueAsString(lumberDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void getLumberById_works() throws Exception {

        this.mvc.perform(get("/getLumberById/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getLumberById_NotFound() throws Exception {

        this.mvc.perform(get("/getLumberById/{id}",338829900)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllLumber_works1() throws Exception {
        //empty filter
        FilterDTO filterDTO = new FilterDTO();

        PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM LUMBER");
        ps.execute();
        ResultSet rs = ps.getResultSet();
        int allLumber=-1;
        if(rs.next())
            allLumber = rs.getInt("COUNT(*)");

        this.mvc.perform(MockMvcRequestBuilders.post("/getAllLumber")
                .content(mapper.writeValueAsString(filterDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(allLumber)))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllLumber_works2() throws Exception {
        //filter finishing
        FilterDTO filterDTO = new FilterDTO();
        filterDTO.setFinishing("prismiert");

        PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM LUMBER WHERE FINISHING='prismiert'");
        ps.execute();
        ResultSet rs = ps.getResultSet();
        int allLumber=-1;
        if(rs.next())
            allLumber = rs.getInt("COUNT(*)");

        this.mvc.perform(MockMvcRequestBuilders.post("/getAllLumber")
                .content(mapper.writeValueAsString(filterDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(allLumber)))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllLumber_badFilter() throws Exception {
        // filter nonsense
        FilterDTO filterDTO = new FilterDTO();
        filterDTO.setFinishing("nonsense");

        this.mvc.perform(MockMvcRequestBuilders.post("/getAllLumber")
                .content(mapper.writeValueAsString(filterDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string(""))
                .andExpect(status().isOk());
    }

    @Test
    public void reserveLumber_works() throws Exception {
        LumberDTO lumberDTO;

        LumberService lumberService = new LumberServiceImpl(
                new LumberDAOJDBC(DBUtil.getConnection()),
                new LumberConverter(),
                new ValidateLumber(new PrimitiveValidator()));

        //reserve all existing and not yet reserved lumber
        lumberDTO = lumberService.getLumberById(1);
        int quantityBefore = lumberDTO.getQuantity();
        int reservedQuantityBefore = lumberDTO.getReserved_quantity();
        int diff = quantityBefore-reservedQuantityBefore;

        lumberDTO.setQuantity(diff);

        this.mvc.perform(put("/reserveLumber")
                .content(mapper.writeValueAsString(lumberDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        int quantityAfter = lumberService.getLumberById(1).getQuantity();
        int reservedQuantityAfter = lumberService.getLumberById(1).getReserved_quantity();

        Assert.assertTrue(reservedQuantityAfter==(reservedQuantityBefore+diff));
        Assert.assertTrue(quantityAfter==(quantityBefore-diff));
    }

    @Test
    public void reserveLumber_notEnoughLumber() throws Exception {
        LumberDTO lumberDTO;

        LumberService lumberService = new LumberServiceImpl(
                new LumberDAOJDBC(DBUtil.getConnection()),
                new LumberConverter(),
                new ValidateLumber(new PrimitiveValidator()));

        //reserve all existing and not yet reserved lumber
        lumberDTO = lumberService.getLumberById(1);
        int quantityBefore = lumberDTO.getQuantity();
        int reservedQuantityBefore = lumberDTO.getReserved_quantity();
        int diff = quantityBefore + 1;

        lumberDTO.setQuantity(diff);

        this.mvc.perform(put("/reserveLumber")
                .content(mapper.writeValueAsString(lumberDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        int quantityAfter = lumberService.getLumberById(1).getQuantity();
        int reservedQuantityAfter = lumberService.getLumberById(1).getReserved_quantity();

        Assert.assertTrue(reservedQuantityAfter==reservedQuantityBefore);
        Assert.assertTrue(quantityAfter==quantityBefore);
    }

    @Test
    public void reserveLumber_unknownLumber() throws Exception {
        LumberDTO lumberDTO = new LumberDTO();
        lumberDTO.setId(338829900);
        lumberDTO.setDescription("Latten");
        lumberDTO.setFinishing("prismiert");
        lumberDTO.setWood_type("Ta");
        lumberDTO.setQuality("O/III");
        lumberDTO.setSize(22);
        lumberDTO.setWidth(48);
        lumberDTO.setLength(3500);
        lumberDTO.setQuantity(40);

        this.mvc.perform(put("/reserveLumber")
                .content(mapper.writeValueAsString(lumberDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @AfterClass
    public static void tearDown() {
        DBUtil.closeConnection();
    }

}