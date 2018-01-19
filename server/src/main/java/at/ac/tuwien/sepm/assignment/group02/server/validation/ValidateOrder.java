package at.ac.tuwien.sepm.assignment.group02.server.validation;

import at.ac.tuwien.sepm.assignment.group02.server.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.EmptyInputException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.NoValidIntegerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ValidateOrder implements ValidateInput<Order>{

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public boolean isValid(Order order) throws InvalidInputException {
        LOG.debug("validating order: {}",order);

        if(order == null){
            LOG.error("Error at Order: Order cannot be null");
            throw new InvalidInputException("Error at Order: Order cannot be null");
        }

        try {
            isNumber(order.getID(),-1);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Order Id: "+ e.getMessage());
            throw new InvalidInputException("Error in Order Id: " + e.getMessage());
        }

        try {
            validateText(order.getCustomerName(),50);
        }catch(EmptyInputException e) {
            LOG.error("Error at Customer Name: " + e.getMessage());
            throw new InvalidInputException("Error at Customer Name: " + e.getMessage());
        }

        try {
            validateText(order.getCustomerAddress(),50);
        }catch(EmptyInputException e) {
            LOG.error("Error at Customer Address: " + e.getMessage());
            throw new InvalidInputException("Error at Customer Address: " + e.getMessage());
        }

        try {
            validateText(order.getCustomerUID(),10);
        }catch(EmptyInputException e) {
            LOG.error("Error at Customer UID: " + e.getMessage());
            throw new InvalidInputException("Error at Customer UID: " + e.getMessage());
        }

        try {
            isValidDate(order.getOrderDate().toString());
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

    private void validateText(String order, int length) throws EmptyInputException {
        if(order == null || order.length() == 0) {
            throw new EmptyInputException("Empty Field");
        }
        if(order.length() > length && length != -1) {
            throw new EmptyInputException("Input was too long! Enter Input which is max. " + length + " long");
        }
    }

    private void isNumber(int toCheck,int limit) throws NoValidIntegerException {
        if (toCheck < 0) {
            throw new NoValidIntegerException("Negative Integer entered");
        }
        if(toCheck > limit && limit != -1) {
            throw new NoValidIntegerException("Integer entered too big! Value must be < " + limit);
        }
    }

    public void isValidDate(String inDate) throws InvalidInputException {

        //case not set cause assignment getting created
        if (inDate == null)
            return;

        //set the format to use as a constructor argument
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (inDate.trim().length() != dateFormat.toPattern().length())
            throw new InvalidInputException("Date is not in correct format!");

        dateFormat.setLenient(false);

        try {
            //parse the inDate parameter
            Date test = dateFormat.parse(inDate.trim());
            if(!test.before(new Date())) {
                throw new InvalidInputException("Impossible Date");
            }
        }
        catch (ParseException pe) {
            throw new InvalidInputException("Date is not in correct format!");
        }
    }
}
