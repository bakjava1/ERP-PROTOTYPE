package serviceLayer;

import at.ac.tuwien.sepm.assignment.group02.client.converter.LumberConverter;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.client.rest.LumberController;
import at.ac.tuwien.sepm.assignment.group02.client.rest.TaskController;
import at.ac.tuwien.sepm.assignment.group02.client.service.LumberService;
import at.ac.tuwien.sepm.assignment.group02.client.service.LumberServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.client.util.CORSFilter;
import at.ac.tuwien.sepm.assignment.group02.client.validation.Validator;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.invoke.MethodHandles;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by raquelsima on 15.01.18.
 */
public class LumberClientServiceImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    private static MockMvc mockMvc;

    @Mock
    private LumberService lumberService;

    @Mock
    private static LumberController lumberController;
    @Mock
    private static LumberConverter lumberConverter;

    @Mock
    private static TaskController taskController;

    @Mock
    private static Validator validator;


    @Test
    public void test_update_lumber_success() throws Exception {

        lumberService=new LumberServiceImpl(lumberController, lumberConverter, taskController, validator) ;

        Lumber lumber= new Lumber();
        when(lumberService.getLumber(lumber.getId()));
        doNothing().when(lumberService).updateLumber(lumber);
        mockMvc.perform(
                put("/lumbers/{id}", lumber)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(lumber)))
                .andExpect(status().isOk());
        verify(lumberService, times(1)).getLumber(lumber.getId());
        verify(lumberService, times(1)).updateLumber(lumber);
        verifyNoMoreInteractions(lumberService);
    }

    @Test
    public void test_update_lumber_fail_404_not_found() throws Exception {
        Lumber lumber = new Lumber();

        when(lumberService.getLumber(lumber.getId())).thenReturn(null);

        mockMvc.perform(
                put("/lumbers/{id}", lumber.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(lumber)))
                .andExpect(status().isNotFound());

        verify(lumberService, times(1)).getLumber(lumber.getId());
        verifyNoMoreInteractions(lumberService);
    }

    @Test
    public void test_delete_Lumber_success() throws Exception {
        Lumber lumber = new Lumber();
        when(lumberService.getLumber(lumber.getId()));
        doNothing().when(lumberService).deleteLumber(lumber);
        mockMvc.perform(
                delete("/lumbers/{id}", lumber.getId()))
                .andExpect(status().isOk());
        verify(lumberService, times(1)).getLumber(lumber.getId());
        verify(lumberService, times(1)).deleteLumber(lumber);
        verifyNoMoreInteractions(lumberService);
    }

    @Test
    public void test_delete_lumber_fail_404_not_found() throws Exception {
        Lumber lumber = new Lumber();

        when(lumberService.getLumber(lumber.getId())).thenReturn(null);

        mockMvc.perform(
                delete("/lumbers/{id}", lumber.getId()))
                .andExpect(status().isNotFound());

        verify(lumberService, times(1)).getLumber(lumber.getId());
        verifyNoMoreInteractions(lumberService);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




}
