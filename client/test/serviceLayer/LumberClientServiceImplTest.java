package serviceLayer;

import at.ac.tuwien.sepm.assignment.group02.client.converter.LumberConverter;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.LumberController;
import at.ac.tuwien.sepm.assignment.group02.client.rest.TaskController;
import at.ac.tuwien.sepm.assignment.group02.client.service.LumberService;
import at.ac.tuwien.sepm.assignment.group02.client.service.LumberServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.client.validation.PrimitiveValidator;
import at.ac.tuwien.sepm.assignment.group02.client.validation.Validator;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;

/**
 * Created by raquelsima on 15.01.18.
 */
@Ignore
public class LumberClientServiceImplTest {


    private static MockMvc mockMvc;

    @Mock
    private static LumberController lumberController;
    @Mock
    private static LumberConverter lumberConverter;
    @Mock
    private static TaskController taskController;
    @Mock
    private static Validator validator;
    @Mock
    private static LumberDTO lumberDTO;
    @Mock
    private static LumberService lumberService;
    @Mock
    private static PrimitiveValidator primitiveValidator;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(lumberController)
                .build();
    }

    /*
    @Test
    public void testUpdate_Lumber_ShouldBe_Valid() throws Exception {
        lumberService=new LumberServiceImpl(lumberController, lumberConverter, taskController, validator, primitiveValidator) ;

        Lumber lumber = new Lumber();
        when(lumberConverter.convertRestDTOToPlainObject(any(LumberDTO.class))).thenReturn(lumber);
        lumberService.updateLumber(lumber);

        //verify(lumberConverter,times(1)).convertPlainObjectToRestDTO(any(LumberDTO.class));
       // verify(lumberController,times(1)).updateLumber(lumberDTO);
    }
    */
    @Ignore
    @Test
    public void testDeleteLumber_InvalidInputException() throws Exception {
         lumberService=new LumberServiceImpl(lumberController, lumberConverter, taskController, validator,primitiveValidator) ;
        lumberService.deleteLumber(any(Lumber.class));
    }


    @Test
    public void testDelete_Lumber_PersistenceLayerException() throws Exception {
        lumberService=new LumberServiceImpl(lumberController, lumberConverter, taskController, validator,primitiveValidator) ;
        Lumber lumber = new Lumber();
        when(lumberConverter.convertRestDTOToPlainObject(any(LumberDTO.class))).thenReturn(lumber);

        //doThrow(PersistenceLayerException.class).when(validator).isValid(any(Lumber.class));

        lumberService.deleteLumber(any(Lumber.class));
    }

    /*
    @Test
    public void test_update_lumber_Should_throw_InvalidInputException() throws Exception {

        lumberService=new LumberServiceImpl(lumberController, lumberConverter, taskController, validator,primitiveValidator) ;

        Lumber lumber= new Lumber();

        lumberDTO = new LumberDTO();
        lumberDTO.setId(0);

        lumberService.updateLumber(lumber);
        verify(lumberController, never()).updateLumber(lumberDTO);

        //doThrow(InvalidInputException.class).when(lumberController).updateLumber(any(LumberDTO.class));
        //lumberService.updateLumber(lumber);
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

}
