package at.ac.tuwien.sepm.assignment.group02.client.validation;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.NoValidIntegerException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Component
public class ValidateAssignmentDTO implements ValidateInput<AssignmentDTO> {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public boolean isValid(AssignmentDTO input) throws InvalidInputException {
        LOG.debug("validating input");

        int id = input.getId();
        int amount = input.getAmount();
        int box_id = input.getBox_id();
        int task_id = input.getTask_id();
        String date = input.getCreation_date();

        try {
            isNumber(id,-1);
        } catch(NoValidIntegerException e) {
            LOG.error("Error at Id: " + e.getMessage());
            throw new InvalidInputException("Error at Id: " + e.getMessage());
        }

        try {
            isNumber(amount,800);
        } catch(NoValidIntegerException e) {
            LOG.error("Error at Amount: " + e.getMessage());
            throw new InvalidInputException("Error at Amount: " + e.getMessage());
        }

        try {
            isNumber(box_id,25);
        } catch(NoValidIntegerException e) {
            LOG.error("Error at Box Number: " + e.getMessage());
            throw new InvalidInputException("Error at Box Number: " + e.getMessage());
        }

        try {
            isNumber(task_id,-1);
        } catch(NoValidIntegerException e) {
            LOG.error("Error at Task Id: " + e.getMessage());
            throw new InvalidInputException("Error at Task Id: " + e.getMessage());
        }

        try {
            isValidDate(date);
        } catch(InvalidInputException e) {
            LOG.error("Error at Date: " + e.getMessage());
            throw new InvalidInputException("Error at Date: " + e.getMessage());
        }

        return true;
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
