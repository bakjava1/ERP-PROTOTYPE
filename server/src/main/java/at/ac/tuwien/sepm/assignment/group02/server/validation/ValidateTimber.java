package at.ac.tuwien.sepm.assignment.group02.server.validation;

import at.ac.tuwien.sepm.assignment.group02.server.entity.Timber;
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
public class ValidateTimber implements ValidateInput<Timber> {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public boolean isValid(Timber timber) throws InvalidInputException {
        LOG.debug("validating timber: ", timber.toString());

        if(timber == null){
            LOG.error("Error at Timber: Timber cannot be null");
            throw new InvalidInputException("Error at Timber: Timber cannot be null");
        }
        
        try {
            isNumber(timber.getBox_id(),25);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Timber Box: "+ e.getMessage());
            throw new InvalidInputException("Error in Timber Box: " + e.getMessage());
        }

        try {
            isNumber(timber.getAmount(),800);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Timber Amount: "+ e.getMessage());
            throw new InvalidInputException("Error in Timber Amount: " + e.getMessage());
        }

        try {
            isNumber(timber.getMAX_AMOUNT(),-1);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Timber Max Amount: "+ e.getMessage());
            throw new InvalidInputException("Error in Timber Max Amount: " + e.getMessage());
        }

        try {
            isNumber(timber.getDiameter(),520);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Timber Diameter: "+ e.getMessage());
            throw new InvalidInputException("Error in Timber Diameter: " + e.getMessage());
        }

        try {
            validateText(timber.getWood_type(),20);
            if(!timber.getWood_type().equals("Fichte") && !timber.getWood_type().equals("Tanne") && !timber.getWood_type().equals("Laerche")) {
                LOG.error("Error in Timber Wood Type: Unknown Wood Type");
                throw new InvalidInputException("Error in Timber Wood Type: Unknown Wood Type");
            }
        }catch (EmptyInputException e) {
            LOG.error("Error in Timber Wood Type: "+ e.getMessage());
            throw new InvalidInputException("Error in Timber Wood Type: " + e.getMessage());
        }

        try {
            validateText(timber.getQuality(),10);
            if(!timber.getQuality().equals("A") && !timber.getQuality().equals("B") && !timber.getQuality().equals("C") && !timber.getQuality().equals("CX")) {
                LOG.error("Error in Timber Quality: Unknown Quality");
                throw new InvalidInputException("Error in Timber Quality: Unknown Quality");
            }
        }catch (EmptyInputException e) {
            LOG.error("Error in Timber Quality: "+ e.getMessage());
            throw new InvalidInputException("Error in Timber Quality: " + e.getMessage());
        }

        try {
            isNumber(timber.getLength(),5000);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Timber Length: "+ e.getMessage());
            throw new InvalidInputException("Error in Timber Length: " + e.getMessage());
        }

        if(timber.getFestmeter() < 0 || timber.getFestmeter() > 800) {
            LOG.error("Error in Timber Bankmeter: Too much Bankmeter");
            throw new InvalidInputException("Error in Timber Bankmeter: Too much Bankmeter");
        }

        try {
            isNumber(timber.getPrice(),10000000);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Timber Price: "+ e.getMessage());
            throw new InvalidInputException("Error in Timber Price: " + e.getMessage());
        }

        try {
            isValidDate(timber.getLast_edited());
        } catch(InvalidInputException e) {
            LOG.error("Error in Timber Last Edited: "+ e.getMessage());
            throw new InvalidInputException("Error in Timber Last Edited: " + e.getMessage());
        }

        return true;
    }

    private void validateText(String task, int length) throws EmptyInputException {
        if(task == null || task.length() == 0) {
            throw new EmptyInputException("Empty Field");
        }
        if(task.length() > length && length != -1) {
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
