package serviceLayer;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.TimberDTO;
import at.ac.tuwien.sepm.assignment.group02.server.converter.TimberConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.TimberDAO;
import at.ac.tuwien.sepm.assignment.group02.server.service.TimberService;
import at.ac.tuwien.sepm.assignment.group02.server.service.TimberServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.server.validation.ValidateTimber;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

// @RunWith attach a runner to initialize the test data
@RunWith(MockitoJUnitRunner.class)
public class TimberServerServiceImplTest {

    @Mock
    private TimberDAO timberManagementDAO;
    @Mock
    private TimberConverter timberConverter;
    @Mock
    private ValidateTimber validateTimber;

    @Test
    public void testAddTimber_works() throws Exception {
        TimberService timberService
                = new TimberServiceImpl(timberManagementDAO,timberConverter,validateTimber);

        timberService.addTimber(any(TimberDTO.class));

        verify(timberConverter,times(1)).convertRestDTOToPlainObject(any(TimberDTO.class));
        verify(validateTimber,times(1)).isValid(any(Timber.class));
        verify(timberManagementDAO,times(1)).createTimber(any(Timber.class));
    }

    @Test(expected = InvalidInputException.class)
    public void testAddTimber_ValidationException() throws Exception {
        TimberService timberService
                = new TimberServiceImpl(timberManagementDAO,timberConverter,validateTimber);

        doThrow(InvalidInputException.class).when(validateTimber).isValid(any(Timber.class));

        timberService.addTimber(any(TimberDTO.class));
        verify(timberConverter,times(1)).convertRestDTOToPlainObject(any(TimberDTO.class));
    }

    @Test(expected = ServiceLayerException.class)
    public void testAddTimber_PersistenceLayerException() throws Exception {
        TimberService timberService
                = new TimberServiceImpl(timberManagementDAO,timberConverter,validateTimber);

        doThrow(PersistenceLayerException.class).when(timberManagementDAO).createTimber(any(Timber.class));

        timberService.addTimber(any(TimberDTO.class));
    }

    @Test
    public void testRemoveTimber_works() throws Exception {
        TimberService timberService
                = new TimberServiceImpl(timberManagementDAO,timberConverter,validateTimber);

        timberService.removeTimberFromBox(2,3);

        verify(timberManagementDAO,times(1)).removeTimber(any(Timber.class));
    }

    @Ignore //Timber is currently not validated
    @Test(expected = InvalidInputException.class)
    public void testRemoveTimber_InvalidInputException() throws Exception {
        TimberService timberService
                = new TimberServiceImpl(timberManagementDAO,timberConverter,validateTimber);

        doThrow(InvalidInputException.class).when(validateTimber).isValid(any(Timber.class));

        timberService.removeTimberFromBox(2,3);
    }

    @Test(expected = ServiceLayerException.class)
    public void testRemoveTimber_PersistenceLayerException() throws Exception {
        TimberService timberService
                = new TimberServiceImpl(timberManagementDAO,timberConverter,validateTimber);

        doThrow(PersistenceLayerException.class).when(timberManagementDAO).removeTimber(any(Timber.class));

        timberService.removeTimberFromBox(2,3);

    }


    @Test
    public void testNumberOfBoxes_returnsValue() throws Exception {

        TimberService timberService
                = new TimberServiceImpl(timberManagementDAO,timberConverter,validateTimber);

        when(timberManagementDAO.getNumberOfBoxes()).thenReturn(3);
        int value = timberService.numberOfBoxes();

        verify(timberManagementDAO, times(1)).getNumberOfBoxes();
        Assert.assertEquals(value,3);

    }

    @Test(expected = ServiceLayerException.class)
    public void testNumberOfBoxes_PersistenceLayerException() throws Exception {
        TimberService timberService
                = new TimberServiceImpl(timberManagementDAO,timberConverter,validateTimber);
        doThrow(PersistenceLayerException.class).when(timberManagementDAO).getNumberOfBoxes();
        timberService.numberOfBoxes();
    }

}