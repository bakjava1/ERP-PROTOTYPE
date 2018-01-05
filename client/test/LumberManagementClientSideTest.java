import at.ac.tuwien.sepm.assignment.group02.client.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.client.rest.LumberController;
import at.ac.tuwien.sepm.assignment.group02.client.service.LumberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Verify.verify;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by raquelsima on 03.01.18.
 */
public class LumberManagementClientSideTest {


    private static final int UNKNOWN_ID = Integer.MAX_VALUE;

    private MockMvc mockMvc;

    @Mock
    private LumberService lumberService;

    @InjectMocks
    private LumberController lumberController;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(lumberController)
                .addFilters()
                .build();
}

    @Test
    public void test_get_all_Lumber_success() throws Exception {
        List<Lumber> lumbers = Arrays.asList(
                new Lumber("Schnittholz","Ta",22,30),
                new Lumber("Taffel","Fa",30,35));

        when(lumberService.getAllLumber()).thenReturn(lumbers);

        ResultActions resultActions = mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)));
        verifyNoMoreInteractions(lumberService);
    }

    @Test
    public void test_create_lumber_fail_404_not_found() throws Exception {
        Lumber lumber = new Lumber();
        when(lumberService.equals(lumber)).thenReturn(true);
        verifyNoMoreInteractions(lumberService);
    }

    @Test
    public void test_get_lumber_by_id_fail_404_not_found() throws Exception {

        when(lumberService.getLumber(1)).thenReturn(null);

        mockMvc.perform(get("/lumbers/{id}", 1))
                .andExpect(status().isNotFound());

        verifyNoMoreInteractions(lumberService);
    }

    @Test
    public void test_create_lumber_success() throws Exception {
        Lumber lumber = new Lumber("test", "ta", 2, 5);

    }

    @Test
    public void test_create_lumber_fail_409_conflict() throws Exception {
        Lumber lumber = new Lumber();

        when(lumberService.lumberExists(lumber)).thenReturn(true);

       /* mockMvc.perform(
               post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(lumber)))
                .andExpect(status().isConflict());*/

       // verify(lumberService, times(1)).exists(lumber);
        verifyNoMoreInteractions(lumberService);
    }

    @Test
    public void test_update_lumber_success() throws Exception {
        Lumber lumber = new Lumber("test","test",20,15);

        when(lumberService.getLumber(lumber.getId())).thenReturn(lumber);
        doNothing().when(lumberService).updateLumber(lumber);

        /*ResultActions resultActions = mockMvc.perform(
               put("/lumbers/{id}", lumber.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(lumber)))
                .andExpect(status().isOk());*/

        //verify(lumberService, times(1))(lumber.getId());
        //verify(lumberService, times(1)).update(lumber);
        verifyNoMoreInteractions(lumberService);
    }

    @Test
    public void test_update_lumber_fail_404_not_found() throws Exception {
        Lumber lumber = new Lumber("lumber not found","blabla",2,3);

        when(lumberService.getLumber(lumber.getId())).thenReturn(null);

        /*ResultActions resultActions = mockMvc.perform(
                put("/lumbers/{id}", lumber.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(lumber)))
                .andExpect(status().isNotFound());*/

        // verify(lumberService, times(1)).findById(lumber.getId());
        verifyNoMoreInteractions(lumberService);
    }

    @Test
    public void test_delete_lumber_success() throws Exception {
        Lumber lumber = new Lumber("test","test",40,50);

        when(lumberService.getLumber(lumber.getId())).thenReturn(lumber);
        doNothing().when(lumberService).deleteLumber(lumber);

        mockMvc.perform(
                delete("/lumbers/{id}", lumber.getId()))
                .andExpect(status().isOk());

        //verify(lumberService, times(1)).findById(lumber.getId());
       // verify(lumberService, times(1)).delete(lumber.getId());
        verifyNoMoreInteractions(lumberService);
    }

    @Test
    public void test_delete_lumber_fail_404_not_found() throws Exception {
        Lumber lumber = new Lumber("lumber not found","test",2,3);

        when(lumberService.getLumber(lumber.getId())).thenReturn(null);

        mockMvc.perform(
                delete("/lumbers/{id}", lumber.getId()))
                .andExpect(status().isNotFound());

        //verify(lumberService, times(1)).findById(lumber.getId());
        verifyNoMoreInteractions(lumberService);
    }

    /*
     * converts a Java object into JSON representation
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
