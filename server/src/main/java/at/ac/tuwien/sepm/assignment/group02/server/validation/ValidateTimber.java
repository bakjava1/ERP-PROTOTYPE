package at.ac.tuwien.sepm.assignment.group02.server.validation;

import at.ac.tuwien.sepm.assignment.group02.server.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.EmptyInputException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.NoValidIntegerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class ValidateTimber implements ValidateInput<Timber> {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final PrimitiveValidator primitiveValidator;

    @Autowired
    public ValidateTimber(PrimitiveValidator primitiveValidator) {
        this.primitiveValidator = primitiveValidator;
    }

    @Override
    public boolean isValid(Timber timber) throws InvalidInputException {
        LOG.debug("validating timber: ", timber.toString());

        if(timber == null){
            LOG.error("Error at Timber: Timber cannot be null");
            throw new InvalidInputException("Fehler bei Rundholz: Rundholz kann nicht null sein!");
        }
        
        try {
            primitiveValidator.isNumber(timber.getBox_id(),25);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Timber Box: "+ e.getMessage());
            throw new InvalidInputException("Fehler bei Box Nummer: " + e.getMessage());
        }

        try {
            primitiveValidator.isNumber(timber.getAmount(),800);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Timber Amount: "+ e.getMessage());
            throw new InvalidInputException("Fehler bei Anzahl Rundholz: " + e.getMessage());
        }

        try {
            primitiveValidator.isNumber(timber.getMAX_AMOUNT(),-1);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Timber Max Amount: "+ e.getMessage());
            throw new InvalidInputException("Fehler bei Maximalanzahl der Box: " + e.getMessage());
        }

        try {
            primitiveValidator.isNumber(timber.getDiameter(),520);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Timber Diameter: "+ e.getMessage());
            throw new InvalidInputException("Fehler bei Durchmesser Rundholz: " + e.getMessage());
        }

        try {
            primitiveValidator.validateText(timber.getWood_type(),20);
            if(!timber.getWood_type().equals("Fichte") && !timber.getWood_type().equals("Tanne") && !timber.getWood_type().equals("Laerche")) {
                LOG.error("Error in Timber Wood Type: Unknown Wood Type");
                throw new InvalidInputException("Fehler bei Rundholz Holzart: Unbekannte Rundholz Holzart!");
            }
        }catch (EmptyInputException e) {
            LOG.error("Error in Timber Wood Type: "+ e.getMessage());
            throw new InvalidInputException("Fehler bei Rundholz Holzart: " + e.getMessage());
        }

        try {
            primitiveValidator.validateText(timber.getQuality(),10);
            if(!timber.getQuality().equals("A") && !timber.getQuality().equals("B") && !timber.getQuality().equals("C") && !timber.getQuality().equals("CX")) {
                LOG.error("Error in Timber Quality: Unknown Quality");
                throw new InvalidInputException("Fehler bei Rundholz Qualität: Unbekannte Qualität");
            }
        }catch (EmptyInputException e) {
            LOG.error("Error in Timber Quality: "+ e.getMessage());
            throw new InvalidInputException("Fehler bei Rundholz Qualität: " + e.getMessage());
        }

        try {
            primitiveValidator.isNumber(timber.getLength(),5000);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Timber Length: "+ e.getMessage());
            throw new InvalidInputException("Fehler bei Rundholz Länge: " + e.getMessage());
        }

        if(timber.getFestmeter() < 0 || timber.getFestmeter() > 800) {
            LOG.error("Error in Timber Bankmeter: Too much Bankmeter");
            throw new InvalidInputException("Fehler bei Festmeter: Zu große Anzahl an Festmeter > 800");
        }

        try {
            primitiveValidator.isNumber(timber.getPrice(),10000000);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Timber Price: "+ e.getMessage());
            throw new InvalidInputException("Fehler bei Rundholzpreis: " + e.getMessage());
        }

        try {
            primitiveValidator.isValidDate(timber.getLast_edited());
        } catch(InvalidInputException e) {
            LOG.error("Error in Timber Last Edited: "+ e.getMessage());
            throw new InvalidInputException("Fehler beim zuletzt editiert Datum: " + e.getMessage());
        }

        return true;
    }
}
