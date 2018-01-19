import at.ac.tuwien.sepm.assignment.group02.client.converter.OrderConverter;
import at.ac.tuwien.sepm.assignment.group02.client.converter.TaskConverter;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.ServiceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.rest.OrderController;
import at.ac.tuwien.sepm.assignment.group02.client.rest.OrderControllerImpl;
import at.ac.tuwien.sepm.assignment.group02.client.service.OrderService;
import at.ac.tuwien.sepm.assignment.group02.client.service.OrderServiceImpl;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class deleteOrderTest {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static OrderService orderService;
    private static OrderController orderController;
    private static OrderConverter orderConverter;
    private static TaskConverter taskConverter;
    private static RestTemplate restTemplate;

    private static Order order;
    private static OrderDTO orderDTO;

    @BeforeClass
    public static void setup() {
        LOG.debug("delete order test setup initiated");

        restTemplate = mock(RestTemplate.class);
        orderController = new OrderControllerImpl(restTemplate);
        orderConverter = mock(OrderConverter.class);
        taskConverter = new TaskConverter();
        orderService = new OrderServiceImpl(orderController,orderConverter, taskConverter);

        order = new Order();


        order.setID(1);

        orderDTO = new OrderDTO();
        orderDTO.setID(1);

        LOG.debug("delete order test setup completed");
    }

    @Test
    public void testdeleteOrder_in_client_serviceLayer() throws ServiceLayerException {
        LOG.debug("test delete order in client service layer");
        orderService.deleteOrder(order);
        verify(restTemplate, times(1))
                .exchange(Mockito.matches(".*deleteOrder"), eq(HttpMethod.PUT), any(HttpEntity.class), eq(OrderDTO.class));
    }

    @Test (expected = PersistenceLayerException.class)
    public void deleteOrder_throws_RestClientException() throws PersistenceLayerException {
        LOG.debug("delete order throws RestClientException in client rest interface");
        doThrow(RestClientException.class).when(restTemplate)
                .exchange(Mockito.matches(".*deleteOrder"), eq(HttpMethod.PUT), any(HttpEntity.class), eq(OrderDTO.class));
        orderController.deleteOrder(orderDTO);
    }

    @Test (expected = PersistenceLayerException.class)
    public void deleteOrder_throws_HttpStatusCodeException() throws PersistenceLayerException {
        LOG.debug("delete order throws HttpStatusCodeException in client rest interface");

        doThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND)).when(restTemplate)
                .exchange(Mockito.matches(".*deleteOrder"), eq(HttpMethod.PUT), any(HttpEntity.class), eq(OrderDTO.class));

        orderController.deleteOrder(orderDTO);
    }
}
