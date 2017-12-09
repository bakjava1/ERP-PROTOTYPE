package at.ac.tuwien.sepm.assignment.group02.client.validation;

import at.ac.tuwien.sepm.assignment.group02.client.entity.UnvalidatedTask;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.EmptyInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.NoValidIntegerException;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class Validator {

    public static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public Task inputValidationTask(UnvalidatedTask toValidate) throws InvalidInputException {
        try {
            validateText(toValidate.getDescription());
        }catch(EmptyInputException e) {
            LOG.error("Error at Description: " + e.getMessage());
            throw new InvalidInputException("Error at Description: " + e.getMessage());
        }
        try {
            validateText(toValidate.getFinishing());
        }catch(EmptyInputException e) {
            LOG.error("Error at Finishing: " + e.getMessage());
            throw new InvalidInputException("Error at Finishing: " + e.getMessage());
        }
        try {
            validateText(toValidate.getWood_type());
        }catch(EmptyInputException e) {
            LOG.error("Error at Wood Type: " + e.getMessage());
            throw new InvalidInputException("Error at Wood Type: " + e.getMessage());
        }
        try {
            validateText(toValidate.getQuality());
        }catch(EmptyInputException e) {
            LOG.error("Error at Quality: " + e.getMessage());
            throw new InvalidInputException("Error at Quality: " + e.getMessage());
        }
        int validatedSize;
        try {
            validatedSize = validateNumber(toValidate.getSize());
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Size: " + e.getMessage());
            throw new InvalidInputException("Error at Size: " + e.getMessage());
        }
        int validatedWidth;
        try {
            validatedWidth = validateNumber(toValidate.getWidth());
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Width: " + e.getMessage());
            throw new InvalidInputException("Error at Width: " + e.getMessage());
        }
        int validatedLength;
        try {
            validatedLength = validateNumber(toValidate.getLength());
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Length: " + e.getMessage());
            throw new InvalidInputException("Error at Length: " + e.getMessage());
        }
        int validatedQuantity;
        try {
            validatedQuantity = validateNumber(toValidate.getQuantity());
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Quantity: " + e.getMessage());
            throw new InvalidInputException("Error at Quantity: " + e.getMessage());
        }
        Task validated = new Task(-1,-1,toValidate.getDescription(),toValidate.getFinishing(),toValidate.getWood_type(),toValidate.getQuality(),validatedSize,validatedWidth,validatedLength,validatedQuantity,0,false);
        return validated;
    }

    private int validateNumber(String toValidate) throws NoValidIntegerException {
        int num = -1;
        if(toValidate.length() == 0 || toValidate == null) {
            throw new NoValidIntegerException("Empty Field, No Number entered");
        }
        try {
            num = Integer.parseInt(toValidate);
            if (num < 0) {
                throw new NoValidIntegerException("Negative Integer entered");
            }
        } catch (NumberFormatException e) {
            LOG.error("No valid Integer entered");
            throw new NoValidIntegerException("No valid Integer entered");
        }
        return num;
    }

    private void validateText(String toValidate) throws EmptyInputException {
        if(toValidate == null || toValidate.length() == 0) {
            throw new EmptyInputException("Empty Field");
        }
    }
}
