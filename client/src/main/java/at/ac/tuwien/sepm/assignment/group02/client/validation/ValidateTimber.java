package at.ac.tuwien.sepm.assignment.group02.client.validation;

import at.ac.tuwien.sepm.assignment.group02.client.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.EmptyInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.NoValidIntegerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ValidateTimber implements ValidateInput<Timber> {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public boolean isValid(Timber toValidate) throws InvalidInputException {

        try {
            isNumber(toValidate.getBox_id(),25);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Timber Box: "+ e.getMessage());
            throw new InvalidInputException("Error in Timber Box: " + e.getMessage());
        }

        try {
            isNumber(toValidate.getAmount(),800);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Timber Amount: "+ e.getMessage());
            throw new InvalidInputException("Error in Timber Amount: " + e.getMessage());
        }

        try {
            isNumber(toValidate.getMAX_AMOUNT(),-1);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Timber Max Amount: "+ e.getMessage());
            throw new InvalidInputException("Error in Timber Max Amount: " + e.getMessage());
        }

        try {
            isNumber(toValidate.getDiameter(),520);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Timber Diameter: "+ e.getMessage());
            throw new InvalidInputException("Error in Timber Diameter: " + e.getMessage());
        }

        try {
            validateText(toValidate.getWood_type(),20);
            if(!toValidate.getWood_type().equals("Fichte") && !toValidate.getWood_type().equals("Tanne") && !toValidate.getWood_type().equals("Laerche")) {
                LOG.error("Error in Timber Wood Type: Unknown Wood Type");
                throw new InvalidInputException("Error in Timber Wood Type: Unknown Wood Type");
            }
        }catch (EmptyInputException e) {
            LOG.error("Error in Timber Wood Type: "+ e.getMessage());
            throw new InvalidInputException("Error in Timber Wood Type: " + e.getMessage());
        }

        try {
            validateText(toValidate.getQuality(),10);
            if(!toValidate.getQuality().equals("A") && !toValidate.getQuality().equals("B") && !toValidate.getQuality().equals("C") && !toValidate.getQuality().equals("CX")) {
                LOG.error("Error in Timber Quality: Unknown Quality");
                throw new InvalidInputException("Error in Timber Quality: Unknown Quality");
            }
        }catch (EmptyInputException e) {
            LOG.error("Error in Timber Quality: "+ e.getMessage());
            throw new InvalidInputException("Error in Timber Quality: " + e.getMessage());
        }

        try {
            isNumber(toValidate.getLength(),5000);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Timber Length: "+ e.getMessage());
            throw new InvalidInputException("Error in Timber Length: " + e.getMessage());
        }

        if(toValidate.getFestmeter() < 0 || toValidate.getFestmeter() > 800) {
            LOG.error("Error in Timber Bankmeter: Too much Bankmeter");
            throw new InvalidInputException("Error in Timber Bankmeter: Too much Bankmeter");
        }

        try {
            isNumber(toValidate.getPrice(),10000000);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Timber Price: "+ e.getMessage());
            throw new InvalidInputException("Error in Timber Price: " + e.getMessage());
        }

        try {
            isValidDate(toValidate.getLast_edited());
        } catch(InvalidInputException e) {
            LOG.error("Error in Timber Last Edited: "+ e.getMessage());
            throw new InvalidInputException("Error in Timber Last Edited: " + e.getMessage());
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

    private void validateText(String toValidate, int length) throws EmptyInputException {
        if(toValidate == null || toValidate.length() == 0) {
            throw new EmptyInputException("Empty Field");
        }
        if(toValidate.length() > length && length != -1) {
            throw new EmptyInputException("Input was too long! Enter Input which is max. " + length + " long");
        }
    }
}
