import at.ac.tuwien.sepm.assignment.group02.client.converter.LumberConverter;
import at.ac.tuwien.sepm.assignment.group02.client.rest.LumberController;
import at.ac.tuwien.sepm.assignment.group02.client.service.LumberService;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;

import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by raquelsima on 01.01.18.
 */
public class updateLumberClientSideTest {
    
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

   // private static LumberService lumberService;
    //private static LumberController lumberController;
    private static LumberConverter lumberConverter;
    private static RestTemplate restTemplate;

    private static Lumber lumber;
    private static LumberDTO lumberDTO;

    private static final int UNKNOWN_ID = Integer.MAX_VALUE;

    private MockMvc mockMvc;


    @Mock
    private static LumberService lumberService;
    @InjectMocks
    private static LumberController lumberController;


    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(lumberController).build();
    }

    @Test
    public void test_update_lumber_success() throws Exception {
        Lumber lumber = new Lumber("blbla","ta",34,50);
       when(lumberService.getLumber(lumber.getId()));
        verify(lumberService, times(1)).getLumber(lumber.getId());
        verifyNoMoreInteractions(lumberService);
    }


    //This piece of code is used to write an object into JSON representation.

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
