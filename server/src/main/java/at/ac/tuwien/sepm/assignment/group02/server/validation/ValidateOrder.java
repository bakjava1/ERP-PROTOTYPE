package at.ac.tuwien.sepm.assignment.group02.server.validation;

import at.ac.tuwien.sepm.assignment.group02.server.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.EmptyInputException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.NoValidIntegerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ValidateOrder implements ValidateInput<Order>{

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final PrimitiveValidator primitiveValidator;

    @Autowired
    public ValidateOrder(PrimitiveValidator primitiveValidator) {
        this.primitiveValidator = primitiveValidator;
    }

    @Override
    public boolean isValid(Order order) throws InvalidInputException {
        LOG.debug("validating order: {}",order);

        if(order == null){
            LOG.error("Error at Order: Order cannot be null");
            throw new InvalidInputException("Error at Order: Order cannot be null");
        }

        try {
            primitiveValidator.isNumber(order.getID(),-1);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Order Id: "+ e.getMessage());
            throw new InvalidInputException("Error in Order Id: " + e.getMessage());
        }

        try {
            primitiveValidator.validateText(order.getCustomerName(),100);
        }catch(EmptyInputException e) {
            LOG.error("Error at Customer Name: " + e.getMessage());
            throw new InvalidInputException("Error at Customer Name: " + e.getMessage());
        }

        try {
            primitiveValidator.validateText(order.getCustomerAddress(),100);
        }catch(EmptyInputException e) {
            LOG.error("Error at Customer Address: " + e.getMessage());
            throw new InvalidInputException("Error at Customer Address: " + e.getMessage());
        }

        try {
            primitiveValidator.validateText(order.getCustomerUID(),50);
        }catch(EmptyInputException e) {
            LOG.error("Error at Customer UID: " + e.getMessage());
            throw new InvalidInputException("Error at Customer UID: " + e.getMessage());
        }

        try {
            primitiveValidator.isValidDate(order.getOrderDate().toString());
        } catch(InvalidInputException e) {
            LOG.error("Error at Order Date: " + e.getMessage());
            throw new InvalidInputException("Error at Order Date: " + e.getMessage());
        }

        if(order.getTaskList() == null) {
            LOG.error("Error at Task List: List cannot be null");
            throw new InvalidInputException("Error at Order Date: List cannot be null");
        }
        return true;
    }
}
