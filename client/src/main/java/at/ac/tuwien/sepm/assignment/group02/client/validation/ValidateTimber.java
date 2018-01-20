package at.ac.tuwien.sepm.assignment.group02.client.validation;

import at.ac.tuwien.sepm.assignment.group02.client.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.EmptyInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.NoValidIntegerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ValidateTimber implements ValidateInput<Timber> {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final PrimitiveValidator primitiveValidator;

    @Autowired
    public ValidateTimber(PrimitiveValidator primitiveValidator) {
        this.primitiveValidator = primitiveValidator;
    }

    @Override
    public boolean isValid(Timber toValidate) throws InvalidInputException {

        try {
            primitiveValidator.isNumber(toValidate.getBox_id(),25);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Timber Box: "+ e.getMessage());
            throw new InvalidInputException("Fehler bei Box Nummer: " + e.getMessage());
        }

        try {
            primitiveValidator.isNumber(toValidate.getAmount(),800);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Timber Amount: "+ e.getMessage());
            throw new InvalidInputException("Fehler bei Anzahl Rundholz: " + e.getMessage());
        }

        try {
            primitiveValidator.isNumber(toValidate.getMAX_AMOUNT(),-1);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Timber Max Amount: "+ e.getMessage());
            throw new InvalidInputException("Fehler bei Maximalanzahl der Box: " + e.getMessage());
        }

        try {
            primitiveValidator.isNumber(toValidate.getDiameter(),520);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Timber Diameter: "+ e.getMessage());
            throw new InvalidInputException("Fehler bei Durchmesser Rundholz: " + e.getMessage());
        }

        try {
            primitiveValidator.validateText(toValidate.getWood_type(),20);
            if(!toValidate.getWood_type().equals("Fichte") && !toValidate.getWood_type().equals("Tanne") && !toValidate.getWood_type().equals("Laerche")) {
                LOG.error("Error in Timber Wood Type: Unknown Wood Type");
                throw new InvalidInputException("Fehler bei Rundholz Holzart: Unbekannte Holzart!");
            }
        }catch (EmptyInputException e) {
            LOG.error("Error in Timber Wood Type: "+ e.getMessage());
            throw new InvalidInputException("Fehler bei Rundholz Holzart: " + e.getMessage());
        }

        try {
            primitiveValidator.validateText(toValidate.getQuality(),10);
            if(!toValidate.getQuality().equals("A") && !toValidate.getQuality().equals("B") && !toValidate.getQuality().equals("C") && !toValidate.getQuality().equals("CX")) {
                LOG.error("Error in Timber Quality: Unknown Quality");
                throw new InvalidInputException("Fehler bei Rundholz Qualität: Unbekannte Qualität!");
            }
        }catch (EmptyInputException e) {
            LOG.error("Error in Timber Quality: "+ e.getMessage());
            throw new InvalidInputException("Fehler bei Rundholz Qualität: " + e.getMessage());
        }

        try {
            primitiveValidator.isNumber(toValidate.getLength(),5000);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Timber Length: "+ e.getMessage());
            throw new InvalidInputException("Fehler bei Rundholz Länge: " + e.getMessage());
        }

        if(toValidate.getFestmeter() < 0 || toValidate.getFestmeter() > 800) {
            LOG.error("Error in Timber Bankmeter: Too much Bankmeter");
            throw new InvalidInputException("Fehler bei Festmeter: Zu große Anzahl an Festmeter > 800");
        }

        try {
            primitiveValidator.isNumber(toValidate.getPrice(),1000000);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Timber Price: "+ e.getMessage());
            throw new InvalidInputException("Fehler bei Rundholzpreis: " + e.getMessage());
        }

        try {
            primitiveValidator.isValidDate(toValidate.getLast_edited());
        } catch(InvalidInputException e) {
            LOG.error("Error in Timber Last Edited: "+ e.getMessage());
            throw new InvalidInputException("Fehler beim zuletzt editiert Datum: " + e.getMessage());
        }

        return true;
    }
}
