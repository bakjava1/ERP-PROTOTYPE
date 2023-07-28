package at.ac.tuwien.sepm.assignment.group02.client.validation;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.EmptyInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.NoValidIntegerException;
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
            throw new NoValidIntegerException("Leeres Feld! Keine Zahl eingegeben! Bitte geben sie eine Zahl ein!");
        }
        try {
            num = Integer.parseInt(toValidate);
            if (num <= 0) {
                throw new NoValidIntegerException("Negative Zahl eingegeben! Bitte nur positive Zahlen eingeben!");
            }
            if(num > size && size != -1) {
                throw new NoValidIntegerException("Zahl war zu groß! Bitte geben sie einen Wert der maximal  " + size + " groß ist ein!");
            }
        } catch (NumberFormatException e) {
            LOG.error("No valid Integer entered");
            throw new NoValidIntegerException("Keine valide Zahl eingegeben! Bitte geben sie nur valide Zahlen an!");
        }
        return num;
    }

    public void validateText(String toValidate, int length) throws EmptyInputException {
        if(toValidate == null || toValidate.length() == 0) {
            throw new EmptyInputException("Leeres Feld! Keine Eingabe getätigt! Bitte geben sie etwas ein!");
        }
        if(toValidate.length() > length && length != -1) {
            throw new EmptyInputException("Eingabe war zu lang! Bitte geben sie maximal " + length + " ein!");
        }
    }

    public void isNumber(int toCheck,int limit) throws NoValidIntegerException {
        if (toCheck < 0) {
            throw new NoValidIntegerException("Negative Zahl eingegeben! Bitte nur positive Zahlen eingeben!");
        }
        if(toCheck > limit && limit != -1) {
            throw new NoValidIntegerException("Zahl war zu groß! Bitte geben sie einen Wert der maximal  " + limit + " groß ist ein!");
        }
    }


}
