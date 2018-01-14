import at.ac.tuwien.sepm.assignment.group02.client.converter.LumberConverter;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.LumberNotFountException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.AssignmentController;
import at.ac.tuwien.sepm.assignment.group02.client.rest.LumberController;
import at.ac.tuwien.sepm.assignment.group02.client.rest.LumberControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.client.rest.TaskController;
import at.ac.tuwien.sepm.assignment.group02.client.service.LumberService;
import at.ac.tuwien.sepm.assignment.group02.client.service.LumberServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.client.validation.ValidateAssignmentDTO;
import at.ac.tuwien.sepm.assignment.group02.client.validation.Validator;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import ch.qos.logback.core.db.dialect.DBUtil;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.web.WebAppConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;


import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by raquelsima on 01.01.18.
 */

//@RunWith(MockitoJUnitRunner.class)

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class updateLumberClientSideTest {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static LumberDTO lumberDTO;
    //private static LumberService lumberServiceMock, lumberServiceEmptyList;
    private static LumberController lumberController,mockLumberController;
    private static LumberConverter lumberConverter;
    private static Validator validator ;
    private static RestTemplate restTemplate;
    private static Connection dbConnection;
    private static LumberDTO[] lumberDTOS;

    @MockBean
    private static LumberService lumberServiceMock;
    private MockMvc mockMvc;


    @Before
    public  void setUp() throws Exception {
        LOG.debug("update lumber test setup initiated");

        restTemplate = mock(RestTemplate.class);
        lumberController = new LumberControllerImpl(restTemplate);
        mockLumberController = mock(LumberControllerImpl.class);
        validator = mock(Validator.class);

        lumberDTOS = new LumberDTO[0];

        LOG.debug("update lumber test setup completed");

    }

    @Test(expected = InvalidInputException.class)
    public void test_Update_Lumber_InvalidInput() throws Exception {

    }

   // @Test(expected = ServiceLayerException.class)
   /* public void test_Update_Lumber_PersistenceLayerException() throws Exception {

        // prepare data and mock's behaviour
        // here the stub is the updated lumber object with ID equal to ID of
        // lumber need to be updated
        Lumber lumber = new Lumber(1, "Latten", "Ta",  120);
        when(lumberServiceMock.getLumber(any(Integer.class))).thenReturn(lumber);

        // execute
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(DBUtil.objectToJson(lumber)))
                .andReturn();

        // verify
        int status = result.getResponse().getStatus();
        assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

        // verify that service method was called once
        verify(lumberServiceMock).any(Lumber.class));
    }*/

    @Test
    public void findById_LumberNotFound_ShouldReturnHttpStatusCode404() throws Exception {
        when(lumberServiceMock.getLumber(1)).thenThrow(new LumberNotFountException(""));

        mockMvc.perform(get("/lumbers/{id}", 1L))
                .andExpect(status().isNotFound());

        verify(lumberServiceMock, times(1)).getLumber(1);
        verifyNoMoreInteractions(lumberServiceMock);
    }



    //@Test
   /* public void testGetLumber() throws Exception {

        // prepare data and mock's behaviour
        Lumber lumber = new Lumber(1, "Latten", "Ta", 12);
        when(lumberServiceMock.getLumber(any(Integer.class))).thenReturn(lumber);

        // execute
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get( "{id}", (1))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        // verify
        int status = result.getResponse().getStatus();
        assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

        // verify that service method was called once
        verify(lumberServiceMock).getLumber(any(Integer.class));
        Lumber resultLumber = DBUtil.jsonToObject(result.getResponse()
                .getContentAsString(), Lumber.class);
        assertNotNull(lumber); //parameter muss be resultLumber, see it again
       // assertEquals(1, (resultLumber.getId()));
        assertEquals(1, resultLumber.getId();

    }*/

   // @Test
   /* public void findById_LumberFound_ShouldReturnFoundLumber() throws Exception {
        Lumber found = new Lumber();

        when(lumberServiceMock.getLumber(1)).thenReturn(found);

        mockMvc.perform(get("/lumbers/lumber/{id}", 1))
                .andExpect(status().isOk())
                .andExpect((content().contentType(DBUtil)
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.description", is("Stafell")))
                .andExpect(jsonPath("$.wood_type", is("Ta")));

        verify(lumberServiceMock, times(1)).getLumber(1);
        verifyNoMoreInteractions(lumberServiceMock);
    }*/


    @AfterClass
    public static void tearDown() throws Exception {

    }

}



