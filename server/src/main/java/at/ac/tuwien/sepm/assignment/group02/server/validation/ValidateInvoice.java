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

@Component
public class ValidateInvoice implements ValidateInput<Order> {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final PrimitiveValidator primitiveValidator;

    @Autowired
    public ValidateInvoice(PrimitiveValidator primitiveValidator) {
        this.primitiveValidator = primitiveValidator;
    }

    @Override
    public boolean isValid(Order input) throws InvalidInputException {
        try {
            primitiveValidator.isNumber(input.getID(),-1);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Order Id: "+ e.getMessage());
            throw new InvalidInputException("Error in Order Id: " + e.getMessage());
        }

        try {
            primitiveValidator.validateText(input.getCustomerName(),100);
        }catch(EmptyInputException e) {
            LOG.error("Error at Customer Name: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Customer Name: " + e.getMessage());
        }

        try {
            primitiveValidator.validateText(input.getCustomerAddress(),100);
        }catch(EmptyInputException e) {
            LOG.error("Error at Customer Address: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Customer Address: " + e.getMessage());
        }

        try {
            primitiveValidator.validateText(input.getCustomerUID(),50);
        }catch(EmptyInputException e) {
            LOG.error("Error at Customer UID: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Customer UID: " + e.getMessage());
        }

        try {
            primitiveValidator.isValidDate(input.getOrderDate());
        } catch(InvalidInputException e) {
            LOG.error("Error at Order Date: " + e.getMessage());
            throw new InvalidInputException("Error at Order Date: " + e.getMessage());
        }

        if(input.getTaskList() == null) {
            LOG.error("Error at Task List: List cannot be null");
            throw new InvalidInputException("Fehler bei Order Date: List cannot be null");
        }

        try {
            primitiveValidator.isNumber(input.getGrossAmount(),102000000);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Invoice: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Rechnung: " + e.getMessage());
        }

        try {
            primitiveValidator.isNumber(input.getTaxAmount(),2000000);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Invoice: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Rechnung: " + e.getMessage());
        }

        try {
            primitiveValidator.isNumber(input.getNetAmount(),100000000);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Invoice: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Rechnung: " + e.getMessage());
        }
        
        return true;
    }
}
