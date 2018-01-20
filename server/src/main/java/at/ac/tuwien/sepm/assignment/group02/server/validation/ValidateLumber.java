package at.ac.tuwien.sepm.assignment.group02.server.validation;

import at.ac.tuwien.sepm.assignment.group02.server.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.EmptyInputException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.NoValidIntegerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class ValidateLumber implements ValidateInput<Lumber> {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final PrimitiveValidator primitiveValidator;

    @Autowired
    public ValidateLumber(PrimitiveValidator primitiveValidator) {
        this.primitiveValidator = primitiveValidator;
    }

    @Override
    public boolean isValid(Lumber lumber) throws InvalidInputException {
        LOG.debug("validating lumber: {}",lumber);

        if(lumber == null){
            LOG.error("Error at Lumber: Lumber cannot be null");
            throw new InvalidInputException("Fehler bei Schnittholz: Schnittholz kann nicht null sein!");
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
            primitiveValidator.validateText(description, 50);
        }catch(EmptyInputException e) {
            LOG.error("Error at Description: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Beschreibung: " + e.getMessage());
        }

        try {
            primitiveValidator.validateText(finishing, 30);
            if (!finishing.equals("roh") && !finishing.equals("gehobelt") && !finishing.equals("besäumt")
                    && !finishing.equals("prismiert") && !finishing.equals("trocken") && !finishing.equals("lutro")
                    && !finishing.equals("frisch") && !finishing.equals("imprägniert")) {
                    LOG.error("Error at Finishing: Unknown Finishing");
                    throw new InvalidInputException("Fehler bei Ausführung: Unbekannte Ausführung");
            }
        } catch(EmptyInputException e) {
            LOG.error("Error at Finishing: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Ausführung: " + e.getMessage());
        }

        try {
            primitiveValidator.validateText(wood_type, 20);
            if (!wood_type.equals("Fi") && !wood_type.equals("Ta") && !wood_type.equals("Lä") && !wood_type.equals("Lae")) {
                LOG.error("Error at Wood Type: Unknown Wood Type");
                throw new InvalidInputException("Fehler bei Holzart: Unbekannte Holzart");
            }
        }catch(EmptyInputException e) {
            LOG.error("Error at Wood Type: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Holzart: " + e.getMessage());
        }

        try {
            primitiveValidator.validateText(quality, 20);
            if (!quality.equals("O") && !quality.equals("I") && !quality.equals("II") &&
                    !quality.equals("III") && !quality.equals("IV") && !quality.equals("V") &&
                    !quality.equals("O/III") && !quality.equals("III/IV") && !quality.equals("III/V")) {
                    LOG.error("Error at Quality: Unknown Quality");
                    throw new InvalidInputException("Fehler bei Qualität: Unbekannte Qualität");
            }
        }catch(EmptyInputException e) {
            LOG.error("Error at Quality: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Qualität: " + e.getMessage());
        }


        try {
            primitiveValidator.isNumber(strength, 1000);
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Size: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Dicke: " + e.getMessage());
        }

        try {
            primitiveValidator.isNumber(width, 1000);
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Width: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Breite: " + e.getMessage());
        }

        try{
            primitiveValidator.isNumber(length, 5000);
            if (length != 3500 && length != 4000 && length != 4500 && length != 5000) {
                throw new InvalidInputException("Keine produzierbare Länge! Bitte geben sie eine produzierbare Länge an! (3500,4000,4500,5000");
            }
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Length: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Länge: " + e.getMessage());
        }

        try{
            primitiveValidator.isNumber(quantity, -1);
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Quantity: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Anzahl: " + e.getMessage());
        }

        try{
            primitiveValidator.isNumber(reserver_quantity, -1);
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Reserved Quantity: " + e.getMessage());
            throw new InvalidInputException("Fehler bei reservierter Anzahl: " + e.getMessage());
        }

        return true;
    }
}
