package serviceLayer;

import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.FilterDTO;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.LumberDTO;
import at.ac.tuwien.sepm.assignment.group02.server.converter.LumberConverter;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.server.persistence.LumberDAO;
import at.ac.tuwien.sepm.assignment.group02.server.service.LumberService;
import at.ac.tuwien.sepm.assignment.group02.server.service.LumberServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.server.validation.ValidateLumber;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

// @RunWith attach a runner to initialize the test data
@RunWith(MockitoJUnitRunner.class)
public class LumberServerServiceImplTest {

    @Mock
    private LumberDAO lumberManagementDAO;
    @Mock
    private LumberConverter lumberConverter;
    @Mock
    private ValidateLumber validateLumber;


    @Test
    public void testGetLumberById_works() throws Exception {
        LumberService lumberService
                = new LumberServiceImpl(lumberManagementDAO,lumberConverter,validateLumber);

        Lumber lumber = Mockito.mock(Lumber.class);
        LumberDTO lumberDTO = Mockito.mock(LumberDTO.class);

        when(lumberManagementDAO.readLumberById(anyInt())).thenReturn(lumber);
        when(lumberConverter.convertPlainObjectToRestDTO(lumber)).thenReturn(lumberDTO);

        LumberDTO lumberDTO1 = lumberService.getLumberById(anyInt());

        verify(lumberManagementDAO, times(1)).readLumberById(anyInt());
        verify(lumberConverter,times(1)).convertPlainObjectToRestDTO(any(Lumber.class));

        Assert.assertSame(lumberDTO, lumberDTO1);
    }


    @Test(expected = ServiceLayerException.class)
    public void testGetLumberById_PersistenceLayerException() throws Exception {
        LumberService lumberService
                = new LumberServiceImpl(lumberManagementDAO,lumberConverter,validateLumber);

        doThrow(PersistenceLayerException.class).when(lumberManagementDAO).readLumberById(anyInt());

        lumberService.getLumberById(anyInt());
    }

    @Test
    public void testCreateLumber_works() throws Exception {
        LumberService lumberService
                = new LumberServiceImpl(lumberManagementDAO,lumberConverter,validateLumber);

        int lumber_id1=3;
        when(lumberManagementDAO.createLumber(any(Lumber.class))).thenReturn(lumber_id1);

        int lumber_id2 = lumberService.addLumber(any(LumberDTO.class));

        verify(lumberConverter,times(1)).convertRestDTOToPlainObject(any(LumberDTO.class));
        verify(validateLumber,times(1)).isValid(any(Lumber.class));
        verify(lumberManagementDAO,times(1)).createLumber(any(Lumber.class));

        Assert.assertEquals(lumber_id1,lumber_id2);
    }

    @Test(expected = InvalidInputException.class)
    public void testCreateLumber_ValidationException() throws Exception {
        LumberService lumberService
                = new LumberServiceImpl(lumberManagementDAO,lumberConverter,validateLumber);

        doThrow(InvalidInputException.class).when(validateLumber).isValid(any(Lumber.class));

        lumberService.addLumber(any(LumberDTO.class));
        verify(lumberConverter,times(1)).convertRestDTOToPlainObject(any(LumberDTO.class));

    }

    @Test(expected = ServiceLayerException.class)
    public void testCreateLumber_PersistenceLayerException() throws Exception {
        LumberService lumberService
                = new LumberServiceImpl(lumberManagementDAO,lumberConverter,validateLumber);

        doThrow(PersistenceLayerException.class).when(lumberManagementDAO).createLumber(any(Lumber.class));

        lumberService.addLumber(any(LumberDTO.class));
    }

    @Test
    public void testGetAllLumber_returns2Lumber() throws Exception {
        LumberService lumberService
                = new LumberServiceImpl(lumberManagementDAO,lumberConverter,validateLumber);

        List<Lumber> allLumber = new LinkedList<>();
        Lumber l1 = Mockito.mock(Lumber.class);
        Lumber l2 = Mockito.mock(Lumber.class);
        allLumber.add(l1);
        allLumber.add(l2);
        when(lumberManagementDAO.getAllLumber(any(FilterDTO.class))).thenReturn(allLumber);

        List<LumberDTO> allLumberConverted = lumberService.getAllLumber(any(FilterDTO.class));

        verify(lumberManagementDAO, times(1)).getAllLumber(any(FilterDTO.class));
        verify(lumberConverter, times(2)).convertPlainObjectToRestDTO(any(Lumber.class));

        Assert.assertEquals(allLumberConverted.size(),2);
    }

    @Test
    public void testGetAllLumber_returnsNoLumber() throws Exception {
        LumberService lumberService
                = new LumberServiceImpl(lumberManagementDAO,lumberConverter,validateLumber);

        List<Lumber> allLumber = new LinkedList<>();
        when(lumberManagementDAO.getAllLumber(any(FilterDTO.class))).thenReturn(allLumber);

        List<LumberDTO> allLumberConverted = lumberService.getAllLumber(any(FilterDTO.class));

        verify(lumberManagementDAO, times(1)).getAllLumber(any(FilterDTO.class));
        verify(lumberConverter, times(0)).convertPlainObjectToRestDTO(any(Lumber.class));

        Assert.assertEquals(allLumberConverted.size(),0);
    }

    @Test(expected = ServiceLayerException.class)
    public void testGetAllLumber_PersistenceLayerException() throws Exception {
        LumberService lumberService
                = new LumberServiceImpl(lumberManagementDAO,lumberConverter,validateLumber);

        doThrow(PersistenceLayerException.class).when(lumberManagementDAO).getAllLumber(any(FilterDTO.class));

        lumberService.getAllLumber(any(FilterDTO.class));
    }

    @Test
    public void testReserveLumber_works1() throws Exception {
        LumberService lumberService
                = new LumberServiceImpl(lumberManagementDAO,lumberConverter,validateLumber);

        Lumber lumber = new Lumber();
        lumber.setQuantity(1);
        when(lumberConverter.convertRestDTOToPlainObject(any(LumberDTO.class))).thenReturn(lumber);

        Lumber existing_lumber = new Lumber();
        existing_lumber.setQuantity(2);
        existing_lumber.setReserved_quantity(2);
        when(lumberManagementDAO.readLumberById(anyInt())).thenReturn(existing_lumber);

        lumberService.reserveLumber(any(LumberDTO.class));

        //if(lumber.getQuantity() <= existingQuantity)
        verify(lumberManagementDAO,times(1)).updateLumber(existing_lumber);

        //existing_lumber.setReserved_quantity( existingReservedQuantity + toReserve );
        Assert.assertEquals(existing_lumber.getReserved_quantity(),3);

        //existing_lumber.setQuantity( existingQuantity - toReserve );
        Assert.assertEquals(existing_lumber.getQuantity(),1);
    }

    @Test
    public void testReserveLumber_works2() throws Exception {
        LumberService lumberService
                = new LumberServiceImpl(lumberManagementDAO,lumberConverter,validateLumber);

        Lumber lumber = new Lumber();
        lumber.setQuantity(1);
        when(lumberConverter.convertRestDTOToPlainObject(any(LumberDTO.class))).thenReturn(lumber);

        Lumber existing_lumber = new Lumber();
        existing_lumber.setQuantity(1);
        existing_lumber.setReserved_quantity(5);
        when(lumberManagementDAO.readLumberById(anyInt())).thenReturn(existing_lumber);

        lumberService.reserveLumber(any(LumberDTO.class));

        //if(lumber.getQuantity() <= existingQuantity)
        verify(lumberManagementDAO,times(1)).updateLumber(existing_lumber);

        //existing_lumber.setReserved_quantity( existingReservedQuantity + toReserve );
        Assert.assertEquals(existing_lumber.getReserved_quantity(),6);

        //existing_lumber.setQuantity( existingQuantity - toReserve );
        Assert.assertEquals(existing_lumber.getQuantity(),0);
    }

    @Test
    public void testReserveLumber_NotEnoughFreeLumber() throws Exception {
        LumberService lumberService
                = new LumberServiceImpl(lumberManagementDAO,lumberConverter,validateLumber);

        Lumber lumber = new Lumber();
        lumber.setQuantity(1);
        when(lumberConverter.convertRestDTOToPlainObject(any(LumberDTO.class))).thenReturn(lumber);

        Lumber existing_lumber = new Lumber();
        existing_lumber.setQuantity(0);
        existing_lumber.setReserved_quantity(2);
        when(lumberManagementDAO.readLumberById(anyInt())).thenReturn(existing_lumber);

        try {
            lumberService.reserveLumber(any(LumberDTO.class));
        } catch (ServiceLayerException e){

        }

        //if(lumber.getQuantity() <= existingQuantity)
        verify(lumberManagementDAO,times(0)).updateLumber(existing_lumber);


    }

    @Test(expected = InvalidInputException.class)
    public void testReserveLumber_InvalidInputException() throws Exception {
        LumberService lumberService
                = new LumberServiceImpl(lumberManagementDAO,lumberConverter,validateLumber);

        doThrow(InvalidInputException.class).when(validateLumber).isValid(any(Lumber.class));

        lumberService.reserveLumber(any(LumberDTO.class));
    }

    @Test(expected = ServiceLayerException.class)
    public void testReserveLumber_PersistenceLayerException1() throws Exception {
        LumberService lumberService
                = new LumberServiceImpl(lumberManagementDAO,lumberConverter,validateLumber);

        Lumber lumber = Mockito.mock(Lumber.class);
        when(lumberConverter.convertRestDTOToPlainObject(any(LumberDTO.class))).thenReturn(lumber);

        doThrow(PersistenceLayerException.class).when(lumberManagementDAO).readLumberById(anyInt());

        lumberService.reserveLumber(any(LumberDTO.class));
    }

    @Test(expected = ServiceLayerException.class)
    public void testReserveLumber_PersistenceLayerException2() throws Exception {
        LumberService lumberService
                = new LumberServiceImpl(lumberManagementDAO,lumberConverter,validateLumber);

        Lumber lumber = Mockito.mock(Lumber.class);
        when(lumberConverter.convertRestDTOToPlainObject(any(LumberDTO.class))).thenReturn(lumber);
        when(lumberManagementDAO.readLumberById(anyInt())).thenReturn(lumber);

        doThrow(PersistenceLayerException.class).when(lumberManagementDAO).updateLumber(any(Lumber.class));

        lumberService.reserveLumber(any(LumberDTO.class));
    }

    @Test
    public void testUpdateLumber_works() throws Exception {
        LumberService lumberService
                = new LumberServiceImpl(lumberManagementDAO,lumberConverter,validateLumber);

        Lumber lumber = new Lumber();
        when(lumberConverter.convertRestDTOToPlainObject(any(LumberDTO.class))).thenReturn(lumber);

        lumberService.updateLumber(any(LumberDTO.class));

        verify(lumberConverter,times(1)).convertRestDTOToPlainObject(any(LumberDTO.class));
        verify(lumberManagementDAO,times(1)).updateLumber(lumber);
    }

    @Test(expected = InvalidInputException.class)
    public void testUpdateLumber_InvalidInputException() throws Exception {
        LumberService lumberService
                = new LumberServiceImpl(lumberManagementDAO,lumberConverter,validateLumber);

        doThrow(InvalidInputException.class).when(validateLumber).isValid(any(Lumber.class));

        lumberService.updateLumber(any(LumberDTO.class));
    }

    @Test(expected = ServiceLayerException.class)
    public void testUpdateLumber_PersistenceLayerException() throws Exception {
        LumberService lumberService
                = new LumberServiceImpl(lumberManagementDAO,lumberConverter,validateLumber);

        doThrow(PersistenceLayerException.class).when(lumberManagementDAO).updateLumber(any(Lumber.class));

        lumberService.updateLumber(any(LumberDTO.class));
    }

    @Test
    public void testRemoveLumber_works() throws Exception {
        LumberService lumberService
                = new LumberServiceImpl(lumberManagementDAO,lumberConverter,validateLumber);

        Lumber lumber = new Lumber();
        when(lumberConverter.convertRestDTOToPlainObject(any(LumberDTO.class))).thenReturn(lumber);

        lumberService.removeLumber(any(LumberDTO.class));

        verify(lumberConverter,times(1)).convertRestDTOToPlainObject(any(LumberDTO.class));
        verify(lumberManagementDAO,times(1)).deleteLumber(lumber);
    }

    @Test(expected = InvalidInputException.class)
    public void testRemoveLumber_InvalidInputException() throws Exception {
        LumberService lumberService
                = new LumberServiceImpl(lumberManagementDAO,lumberConverter,validateLumber);

        doThrow(InvalidInputException.class).when(validateLumber).isValid(any(Lumber.class));

        lumberService.removeLumber(any(LumberDTO.class));
    }

    @Test(expected = ServiceLayerException.class)
    public void testRemoveLumber_PersistenceLayerException() throws Exception {
        LumberService lumberService
                = new LumberServiceImpl(lumberManagementDAO,lumberConverter,validateLumber);

        doThrow(PersistenceLayerException.class).when(lumberManagementDAO).deleteLumber(any(Lumber.class));

        lumberService.removeLumber(any(LumberDTO.class));
    }

}