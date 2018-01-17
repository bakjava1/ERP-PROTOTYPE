package at.ac.tuwien.sepm.assignment.group02.server.validation;

import at.ac.tuwien.sepm.assignment.group02.server.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.EmptyInputException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.NoValidIntegerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class    ValidateLumber implements ValidateInput<Lumber> {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public boolean isValid(Lumber lumber) throws InvalidInputException {
        LOG.debug("validating lumber: {}",lumber);

        if(lumber == null){
            LOG.error("Error at Lumber: Lumber cannot be null");
            throw new InvalidInputException("Error at Lumber: Lumber cannot be null");
        }

        String description = lumber.getDescription();
        String finishing = lumber.getFinishing();
        String wood_type = lumber.getWood_type();
        String quality = lumber.getQuality();
        int strength = lumber.getSize();
        int width = lumber.getWidth();
        int length = lumber.getLength();
        int quantity = lumber.getQuantity();
        int reserver_quantity = lumber.getReserved_quantity();

        try {
            validateText(description, 50);
        }catch(EmptyInputException e) {
            LOG.error("Error at Description: " + e.getMessage());
            throw new InvalidInputException("Error at Description: " + e.getMessage());
        }

        try {
            validateText(finishing, 30);
            if (!finishing.equals("roh") && !finishing.equals("gehobelt") && !finishing.equals("besäumt")
                    && !finishing.equals("prismiert") && !finishing.equals("trocken") && !finishing.equals("lutro")
                    && !finishing.equals("frisch") && !finishing.equals("imprägniert")) {
                    LOG.error("Error at Finishing: Unknown Finishing");
                    throw new InvalidInputException("Error at Finishing: Unknown Finishing");
            }
        } catch(EmptyInputException e) {
            LOG.error("Error at Finishing: " + e.getMessage());
            throw new InvalidInputException("Error at Finishing: " + e.getMessage());
        }

        try {
            validateText(wood_type, 20);
            if (!wood_type.equals("Fi") && !wood_type.equals("Ta") && !wood_type.equals("Lä") && !wood_type.equals("Lae")) {
                LOG.error("Error at Wood Type: Unknown Wood Type");
                throw new InvalidInputException("Error at Wood Type: Unknown Wood Type");
            }
        }catch(EmptyInputException e) {
            LOG.error("Error at Wood Type: " + e.getMessage());
            throw new InvalidInputException("Error at Wood Type: " + e.getMessage());
        }

        try {
            validateText(quality, 20);
            if (!quality.equals("O") && !quality.equals("I") && !quality.equals("II") &&
                    !quality.equals("III") && !quality.equals("IV") && !quality.equals("V") &&
                    !quality.equals("O/III") && !quality.equals("III/IV") && !quality.equals("III/V")) {
                    LOG.error("Error at Quality: Unknown Quality");
                    throw new InvalidInputException("Error at Quality: Unknown Quality");
            }
        }catch(EmptyInputException e) {
            LOG.error("Error at Quality: " + e.getMessage());
            throw new InvalidInputException("Error at Quality: " + e.getMessage());
        }


        try {
            isNumber(strength, 1000);
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Size: " + e.getMessage());
            throw new InvalidInputException("Error at Size: " + e.getMessage());
        }

        try {
            isNumber(width, 1000);
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Width: " + e.getMessage());
            throw new InvalidInputException("Error at Width: " + e.getMessage());
        }

        try{
            isNumber(length, 5000);
            if (length != 3500 && length != 4000 && length != 4500 && length != 5000) {
                throw new InvalidInputException("Please enter a producable Length! (3500,4000,4500,5000");
            }
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Length: " + e.getMessage());
            throw new InvalidInputException("Error at Length: " + e.getMessage());
        }

        try{
            isNumber(quantity, -1);
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Quantity: " + e.getMessage());
            throw new InvalidInputException("Error at Quantity: " + e.getMessage());
        }

        try{
            isNumber(reserver_quantity, -1);
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Reserved Quantity: " + e.getMessage());
            throw new InvalidInputException("Error at Reserved Quantity: " + e.getMessage());
        }

        return true;
    }

    private void validateText(String toValidate, int length) throws EmptyInputException {
        if(toValidate == null || toValidate.length() == 0) {
            throw new EmptyInputException("Empty Field");
        }
        if(toValidate.length() > length && length != -1) {
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
}
