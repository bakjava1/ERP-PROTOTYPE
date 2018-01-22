package restLayer;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.LumberController;
import at.ac.tuwien.sepm.assignment.group02.client.rest.LumberControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;


/**
 * Created by raquelsima on 15.01.18.
 */

//@RunWith(SpringJUnit4ClassRunner.class)

@RunWith(MockitoJUnitRunner.class)
public class LumberClientControllerImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Ignore
    @Test
    public void test_Update_A_Lumber_persists() throws PersistenceLayerException, ServiceLayerException {
        LumberController lumberController = new LumberControllerImpl(restTemplate);
        LumberDTO lumberDTO = new LumberDTO();
        lumberController.updateLumber(lumberDTO);
        verify(restTemplate, times(1)).postForObject(
                Mockito.matches(".*updateLumber")
                , eq(lumberDTO), eq(LumberDTO.class));
    }

    @Ignore
    @Test(expected = PersistenceLayerException.class)
    public void testUpdate_A_Lumber_reacts_To_Error404_ShouldThrowException() throws PersistenceLayerException, ServiceLayerException {
        LumberController lumberController = new LumberControllerImpl(restTemplate);
        LumberDTO lumberDTO = new LumberDTO();
        doThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND))
                .when(restTemplate).postForObject(Mockito.matches(".*updateLumber"), eq(lumberDTO), eq(LumberDTO.class));
        lumberController.updateLumber(lumberDTO);
    }

    @Test
    public void testRemove_A_Lumber_ShouldPersists() throws PersistenceLayerException {
        LumberController lumberController = new LumberControllerImpl(restTemplate);
        LumberDTO lumberDTO = new LumberDTO();
        doThrow(RestClientException.class)
                .when(restTemplate).delete(Mockito.matches(".*removeLumber"), eq(lumberDTO), eq(LumberDTO.class));
        lumberController.removeLumber(lumberDTO);
    }


}






















