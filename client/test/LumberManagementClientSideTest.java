import at.ac.tuwien.sepm.assignment.group02.client.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.client.rest.LumberController;
import at.ac.tuwien.sepm.assignment.group02.client.service.LumberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
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


    private MockMvc mockMvc;

    private LumberService lumberService;

    private LumberController lumberController;

    @BeforeClass
    public static  void init(){

}

    @Test
    public void test_get_all_Lumber_success() throws Exception {
        List<Lumber> lumbers = Arrays.asList(
                new Lumber("Schnittholz","Ta",22,30),
                new Lumber("Taffel","Fa",30,35));

    }

    @Test
    public void test_create_lumber_fail_404_not_found() throws Exception {
        Lumber lumber = new Lumber();

    }

    @Test
    public void test_get_lumber_by_id_fail_404_not_found() throws Exception {

    }

    @Test
    public void test_create_lumber_success() throws Exception {
        Lumber lumber = new Lumber("test", "ta", 2, 5);

    }

    @Test
    public void test_create_lumber_fail_409_conflict() throws Exception {
        Lumber lumber = new Lumber();
    }

    @Test
    public void test_update_lumber_success() throws Exception {
        Lumber lumber = new Lumber("test","test",20,15);

    }

    @Test
    public void test_update_lumber_fail_404_not_found() throws Exception {
        Lumber lumber = new Lumber("lumber not found","blabla",2,3);

    }

    @Test
    public void test_delete_lumber_success() throws Exception {
        Lumber lumber = new Lumber("test","test",40,50);

    }

    @Test
    public void test_delete_lumber_fail_404_not_found() throws Exception {
        Lumber lumber = new Lumber("lumber not found","test",2,3);

    }

    @After
    public  void tearDown() throws Exception {

    }
}
