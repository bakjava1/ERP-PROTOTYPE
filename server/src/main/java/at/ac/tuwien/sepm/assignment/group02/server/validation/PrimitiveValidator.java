package at.ac.tuwien.sepm.assignment.group02.server.validation;

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
public class PrimitiveValidator {

    public static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public void isValidDate(String inDate) throws InvalidInputException {

        //case not set cause Creation
        if (inDate == null)
            return;

        //set the format to use as a constructor argument
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

        try {
            //parse the inDate parameter
            Date test = dateFormat.parse(inDate);
            if(!test.before(new Date())) {
                throw new InvalidInputException("Unmögliches Datum (liegt in der Zukunft)!");
            }
        }
        catch (ParseException pe) {
            throw new InvalidInputException("Datum ist nicht im korrekten Format!");
        }
    }

    public int validateNumber(String toValidate,int size) throws NoValidIntegerException {
        int num;
        if(toValidate == null || toValidate.length() == 0) {
            throw new NoValidIntegerException("Empty Field, No Number entered");
        }
        try {
            num = Integer.parseInt(toValidate);
            if (num <= 0) {
                throw new NoValidIntegerException("Negative Integer or Null entered");
            }
            if(num > size && size != -1) {
                throw new NoValidIntegerException("Value entered was too big! Enter Value < " + size);
            }
        } catch (NumberFormatException e) {
            LOG.error("No valid Integer entered");
            throw new NoValidIntegerException("No valid Integer entered");
        }
        return num;
    }

    public void validateText(String toValidate, int length) throws EmptyInputException {
        if(toValidate == null || toValidate.length() == 0) {
            throw new EmptyInputException("Empty Field");
        }
        if(toValidate.length() > length && length != -1) {
            throw new EmptyInputException("Input was too long! Enter Input which is max. " + length + " long");
        }
    }

    public void isNumber(int toCheck,int limit) throws NoValidIntegerException {
        if (toCheck < 0) {
            throw new NoValidIntegerException("Negative Integer entered");
        }
        if(toCheck > limit && limit != -1) {
            throw new NoValidIntegerException("Integer entered too big! Value must be < " + limit);
        }
    }


}
