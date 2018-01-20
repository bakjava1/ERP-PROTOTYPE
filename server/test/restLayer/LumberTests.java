package restLayer;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;
import at.ac.tuwien.sepm.assignment.group02.server.MainApplication;
import at.ac.tuwien.sepm.assignment.group02.server.rest.LumberControllerImpl;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=MainApplication.class)
@WebMvcTest(LumberControllerImpl.class)
public class LumberTests {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @Before
    public void setup() {

    }

    @Test
    public void updateLumber_works() throws Exception {
        LumberDTO lumberDTO = new LumberDTO();
        lumberDTO.setId(3);
        lumberDTO.setDescription("Latten");
        lumberDTO.setFinishing("prismiert");
        lumberDTO.setWood_type("Ta");
        lumberDTO.setQuality("O/III");
        lumberDTO.setSize(22);
        lumberDTO.setWidth(48);
        lumberDTO.setLength(3500);
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
    public void removeLumber_entityNotFound() throws Exception {
        LumberDTO lumberDTO = new LumberDTO();
        lumberDTO.setId(303037299);
        lumberDTO.setDescription("Latten");
        lumberDTO.setFinishing("prismiert");
        lumberDTO.setWood_type("Ta");
        lumberDTO.setQuality("O/III");
        lumberDTO.setSize(22);
        lumberDTO.setWidth(48);
        lumberDTO.setLength(3500);
        lumberDTO.setQuantity(40);

        this.mvc.perform(put("/removeLumber")
                .content(mapper.writeValueAsString(lumberDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getLumberById_works() throws Exception {

        this.mvc.perform(get("/getLumberById/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}