package at.ac.tuwien.sepm.assignment.group02.client.validation;

import at.ac.tuwien.sepm.assignment.group02.client.entity.*;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.EmptyInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.NoValidIntegerException;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.FilterDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class Validator implements ValidateInput{

    public static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final PrimitiveValidator primitiveValidator;

    @Autowired
    public Validator(PrimitiveValidator primitiveValidator) {
        this.primitiveValidator = primitiveValidator;
    }

    public void inputValidationOrder(Order toValidate) throws InvalidInputException {

        try {
            primitiveValidator.isNumber(toValidate.getID(),-1);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Order Id: "+ e.getMessage());
            throw new InvalidInputException("Fehler bei Bestellungs ID: " + e.getMessage());
        }

        try {
            primitiveValidator.validateText(toValidate.getCustomerName(),100);
        }catch(EmptyInputException e) {
            LOG.error("Error at Customer Name: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Kundenname: " + e.getMessage());
        }

        try {
            primitiveValidator.validateText(toValidate.getCustomerAddress(),100);
        }catch(EmptyInputException e) {
            LOG.error("Error at Customer Address: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Kundenaddresse: " + e.getMessage());
        }

        try {
            primitiveValidator.validateText(toValidate.getCustomerUID(),50);
        }catch(EmptyInputException e) {
            LOG.error("Error at Customer UID: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Kunden UID: " + e.getMessage());
        }

        try {
            primitiveValidator.isValidDate(toValidate.getOrderDate());
        } catch(InvalidInputException e) {
            LOG.error("Error at Order Date: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Erstellungsdatum: " + e.getMessage());
        }

        if(toValidate.getTaskList() == null) {
            LOG.error("Error at Task List: List cannot be null");
            throw new InvalidInputException("Fehler bei Auftragsliste: Liste kann nicht null sein!");
        }
    }

    public void inputValidationInvoice(Order invoice) throws InvalidInputException{
        inputValidationOrder(invoice);

        try {
            primitiveValidator.isNumber(invoice.getGrossAmount(),102000000);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Invoice: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Rechnung Bruttopreis: " + e.getMessage());
        }

        try {
            primitiveValidator.isNumber(invoice.getTaxAmount(),2000000);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Invoice: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Rechnung Steuersatz: " + e.getMessage());
        }

        try {
            primitiveValidator.isNumber(invoice.getNetAmount(),100000000);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Invoice: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Rechnung Nettopreis: " + e.getMessage());
        }

        try{
            primitiveValidator.isValidDate(invoice.getDeliveryDate());
        } catch(InvalidInputException e) {
            LOG.error("Error in Invoice: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Rechnung Lieferdatum: " + e.getMessage());
        }

        try{
            primitiveValidator.isValidDate(invoice.getInvoiceDate());
        } catch(InvalidInputException e) {
            LOG.error("Error in Invoice: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Rechnung Erstellungsdatum: " + e.getMessage());
        }
    }

    public Task inputValidationTask(UnvalidatedTask toValidate) throws InvalidInputException {
        try {
            primitiveValidator.validateText(toValidate.getDescription(),50);
        }catch(EmptyInputException e) {
            LOG.error("Error at Description: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Beschreibung: " + e.getMessage());
        }

        try {
            primitiveValidator.validateText(toValidate.getFinishing(),15);
            if(!toValidate.getFinishing().equals("roh") && !toValidate.getFinishing().equals("gehobelt") && !toValidate.getFinishing().equals("besäumt")
                    && !toValidate.getFinishing().equals("prismiert") && !toValidate.getFinishing().equals("trocken") && !toValidate.getFinishing().equals("lutro")
                    && !toValidate.getFinishing().equals("frisch") && !toValidate.getFinishing().equals("imprägniert")) {
                LOG.error("Error at Finishing: Unknown Finishing");
                throw new InvalidInputException("Fehler bei Ausführung: Unbekannte Ausführung");
            }
        }catch(EmptyInputException e) {
            LOG.error("Error at Finishing: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Ausführung: " + e.getMessage());
        }

        try {
            primitiveValidator.validateText(toValidate.getWood_type(),10);
            if(!toValidate.getWood_type().equals("Fi") && !toValidate.getWood_type().equals("Ta") && !toValidate.getWood_type().equals("Lae")) {
                LOG.error("Error at Wood Type: Unknown Wood Type");
                throw new InvalidInputException("Fehler bei Holzart: Unbekannte Holzart");
            }
        }catch(EmptyInputException e) {
            LOG.error("Error at Wood Type: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Holzart: " + e.getMessage());
        }

        try {
            primitiveValidator.validateText(toValidate.getQuality(),10);
            if(!toValidate.getQuality().equals("O") && !toValidate.getQuality().equals("I") && !toValidate.getQuality().equals("II") &&
                    !toValidate.getQuality().equals("III") && !toValidate.getQuality().equals("IV") && !toValidate.getQuality().equals("V") &&
                    !toValidate.getQuality().equals("O/III") && !toValidate.getQuality().equals("III/IV") && !toValidate.getQuality().equals("III/V") ) {
                LOG.error("Error at Quality: Unknown Quality");
                throw new InvalidInputException("Fehler bei Qualität: unbekannte Qualität");
            }
        }catch(EmptyInputException e) {
            LOG.error("Error at Quality: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Qualität: " + e.getMessage());
        }
        int validatedSize;
        try {
            validatedSize = primitiveValidator.validateNumber(toValidate.getSize(),515);
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Size: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Dicke: " + e.getMessage());
        }
        int validatedWidth;
        try {
            validatedWidth = primitiveValidator.validateNumber(toValidate.getWidth(),515);
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Width: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Breite: " + e.getMessage());
        }
        int validatedLength;
        try {
            validatedLength = primitiveValidator.validateNumber(toValidate.getLength(),5000);
            if(validatedLength != 3500 && validatedLength != 4000 && validatedLength != 4500 && validatedLength != 5000) {
                LOG.error("No producable Length!");
                throw new InvalidInputException("Keine produzierbare Länge! Bitte geben sie eine produzierbare Länge an! (3500,4000,4500,5000");
            }
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Length: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Länge: " + e.getMessage());
        }
        int validatedQuantity;
        try {
            validatedQuantity = primitiveValidator.validateNumber(toValidate.getQuantity(),100000);
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Quantity: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Menge: " + e.getMessage());
        }
        int validatedPrice;
        try {
            validatedPrice = primitiveValidator.validateNumber(toValidate.getPrice(),100000000);
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Price: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Gesamtpreis: " + e.getMessage());
        }

        return new Task(-1,-1,toValidate.getDescription(),
                toValidate.getFinishing(),toValidate.getWood_type(),
                toValidate.getQuality(),validatedSize,validatedWidth,
                validatedLength,validatedQuantity,0,
                false,validatedPrice);

    }

    //not entity validators
    public int[] temporaryAddTaskToLumberValidation(String id, String amount) throws InvalidInputException {
        int[] result = new int[2];
        int validatedId;
        try {
            validatedId = primitiveValidator.validateNumber(id,Integer.MAX_VALUE);
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Id: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Id: " + e.getMessage());
        }
        result[0] = validatedId;
        int validatedAmount;
        try {
            validatedAmount = primitiveValidator.validateNumber(amount,Integer.MAX_VALUE);
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Amount: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Amount: " + e.getMessage());
        }
        result[1] = validatedAmount;
        return result;
    }

    public void inputValidationTaskOnOrder(Task toValidate) throws InvalidInputException {
        try {
            primitiveValidator.validateText(toValidate.getDescription(), -1);
        }catch(EmptyInputException e) {
            LOG.error("Error at Task Description: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Task Description: " + e.getMessage());
        }
        try {
            primitiveValidator.validateText(toValidate.getFinishing(), -1);
        }catch(EmptyInputException e) {
            LOG.error("Error at Task Finishing: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Task Finishing: " + e.getMessage());
        }
        try {
            primitiveValidator.validateText(toValidate.getWood_type(), -1);
        }catch(EmptyInputException e) {
            LOG.error("Error at Task Wood Type: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Task Wood Type: " + e.getMessage());
        }
        try {
            primitiveValidator.validateText(toValidate.getQuality(), -1);
        }catch(EmptyInputException e) {
            LOG.error("Error at Task Quality: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Task Quality: " + e.getMessage());
        }
        if(toValidate.getSize()<=0){LOG.error("Error at Task Size: Size <= 0");
            throw new InvalidInputException("Fehler bei Task Size: Size <= 0");
        }
        if(toValidate.getWidth()<=0){LOG.error("Error at Task Width: Width <= 0");
            throw new InvalidInputException("Fehler bei Task Width: Width <= 0");
        }
        if(toValidate.getLength()<=0){LOG.error("Error at Task Length: Length <= 0");
            throw new InvalidInputException("Fehler bei Task Length: Length <= 0");
        }
        if(toValidate.getQuantity()<=0){LOG.error("Error at Task Quantity: Quantity <= 0");
            throw new InvalidInputException("Fehler bei Task Quantity: Quantity <= 0");
        }
        if(toValidate.getPrice()<=0){LOG.error("Error at Task Price: Price <= 0");
            throw new InvalidInputException("Fehler bei Task Price: Price <= 0");
        }
    }

    public FilterDTO validateFilter(FilterDTO filter) throws InvalidInputException{

        String description = filter.getDescription().trim();
        String finishing = filter.getFinishing().trim().equals("alle Veredelungen")? "" :
                filter.getFinishing().trim();
        String wood_type = filter.getWood_type().trim().equals("alle Holzarten")? "" :
                filter.getWood_type().trim();
        String quality = filter.getQuality().trim().equals("alle Qualitäten")? "" :
                filter.getQuality().trim();
        String strength = filter.getSize().trim();
        String width = filter.getWidth().trim();
        String length = filter.getLength().trim();

        FilterDTO validatedFilter = new FilterDTO();

        try {
            if(!description.equals("")) {
                primitiveValidator.validateText(description, 50);
                validatedFilter.setDescription(description);
            }
        }catch(EmptyInputException e) {
            LOG.error("Error at Description: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Description: " + e.getMessage());
        }

        try {
            if(!finishing.equals("")) {
                primitiveValidator.validateText(finishing, 30);
                if (!finishing.equals("roh") && !finishing.equals("gehobelt") && !finishing.equals("besäumt")
                        && !finishing.equals("prismiert") && !finishing.equals("trocken") && !finishing.equals("lutro")
                        && !finishing.equals("frisch") && !finishing.equals("imprägniert")) {
                    LOG.error("Error at Finishing: Unknown Finishing");
                    throw new InvalidInputException("Fehler bei Finishing: Unknown Finishing");
                }
                validatedFilter.setFinishing(finishing);
            }
        }catch(EmptyInputException e) {
            LOG.error("Error at Finishing: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Finishing: " + e.getMessage());
        }

        try {
            if(!wood_type.equals("")) {
                primitiveValidator.validateText(wood_type, 20);
                if (!wood_type.equals("Fi") && !wood_type.equals("Ta") && !wood_type.equals("Lae")) {
                    LOG.error("Error at Wood Type: Unknown Wood Type");
                    throw new InvalidInputException("Fehler bei Wood Type: Unknown Wood Type");
                }
                validatedFilter.setWood_type(wood_type);
            }
        }catch(EmptyInputException e) {
            LOG.error("Error at Wood Type: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Wood Type: " + e.getMessage());
        }

        try {
            if(!quality.equals("")) {
                primitiveValidator.validateText(quality, 20);
                if (!quality.equals("O") && !quality.equals("I") && !quality.equals("II") &&
                        !quality.equals("III") && !quality.equals("IV") && !quality.equals("V") &&
                        !quality.equals("O/III") && !quality.equals("III/IV") && !quality.equals("III/V")) {
                    LOG.error("Error at Quality: Unknown Quality");
                    throw new InvalidInputException("Fehler bei Quality: Unknown Quality");
                }
                validatedFilter.setQuality(quality);
            }
        }catch(EmptyInputException e) {
            LOG.error("Error at Quality: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Quality: " + e.getMessage());
        }


        try {
            if(strength.equals("")) { validatedFilter.setSize(null); }
            else {

                int validatedStrength = primitiveValidator.validateNumber(strength, 1000);
                validatedFilter.setSize(validatedStrength+"");
            }
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Size: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Size: " + e.getMessage());
        }

        try {
            if(width.equals("")) { validatedFilter.setWidth(null); }
            else {
                int validatedWidth = primitiveValidator.validateNumber(width, 1000);
                validatedFilter.setWidth(validatedWidth+"");
            }
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Width: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Width: " + e.getMessage());
        }

        try {
            if(length.equals("")) { validatedFilter.setLength(null); }
            else {
                int validatedLength = primitiveValidator.validateNumber(length, 5000);
                if (validatedLength != 3500 && validatedLength != 4000 && validatedLength != 4500 && validatedLength != 5000) {
                    throw new InvalidInputException("Please enter a producable Length! (3500,4000,4500,5000");
                }
                validatedFilter.setLength(validatedLength+"");
            }
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Length: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Length: " + e.getMessage());
        }

        return validatedFilter;

    }


    @Override
    public boolean isValid(Object input) throws InvalidInputException {
        return false;
    }
}
