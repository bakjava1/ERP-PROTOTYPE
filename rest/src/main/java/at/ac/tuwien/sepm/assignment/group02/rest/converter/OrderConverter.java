package at.ac.tuwien.sepm.assignment.group02.rest.converter;

import at.ac.tuwien.sepm.assignment.group02.rest.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.OrderDTO;
import org.springframework.beans.BeanUtils;

public class OrderConverter implements SimpleConverter<Order, OrderDTO> {

    @Override
    public OrderDTO convertPlainObjectToRestDTO(Order pojo) {
        OrderDTO convertedOrder = new OrderDTO();
        BeanUtils.copyProperties(pojo, convertedOrder);
        return convertedOrder;
    }

    @Override
    public Order convertRestDTOToPlainObject(OrderDTO restDTO) {
        Order convertedOrder = new Order();
        BeanUtils.copyProperties(restDTO, convertedOrder);
        return convertedOrder;
    }
}
