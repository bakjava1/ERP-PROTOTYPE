package at.ac.tuwien.sepm.assignment.group02.server.validation;

import at.ac.tuwien.sepm.assignment.group02.server.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.EmptyInputException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.NoValidIntegerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class ValidateInvoice implements ValidateInput<Order> {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final PrimitiveValidator primitiveValidator;

    @Autowired
    public ValidateInvoice(PrimitiveValidator primitiveValidator) {
        this.primitiveValidator = primitiveValidator;
    }

    @Override
    public boolean isValid(Order input) throws InvalidInputException {

        if(input == null) {
            throw new InvalidInputException("Fehler in Rechnung: Rechnung kann nicht null sein!");
        }

        try {
            primitiveValidator.isNumber(input.getID(),-1);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Order Id: "+ e.getMessage());
            throw new InvalidInputException("Fehler bei Bestellungs ID: " + e.getMessage());
        }

        try {
            primitiveValidator.validateText(input.getCustomerName(),100);
        }catch(EmptyInputException e) {
            LOG.error("Error at Customer Name: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Kundenname: " + e.getMessage());
        }

        try {
            primitiveValidator.validateText(input.getCustomerAddress(),100);
        }catch(EmptyInputException e) {
            LOG.error("Error at Customer Address: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Kundenaddresse: " + e.getMessage());
        }

        try {
            primitiveValidator.validateText(input.getCustomerUID(),50);
        }catch(EmptyInputException e) {
            LOG.error("Error at Customer UID: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Kunden UID: " + e.getMessage());
        }

        try {
            primitiveValidator.isValidDate(input.getOrderDate());
        } catch(InvalidInputException e) {
            LOG.error("Error at Order Date: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Erstellungsdatum: " + e.getMessage());
        }

        if(input.getTaskList() == null) {
            LOG.error("Error at Task List: List cannot be null");
            throw new InvalidInputException("Fehler bei Auftragsliste: Liste kann nicht null sein!");
        }

        try {
            primitiveValidator.isNumber(input.getGrossAmount(),102000000);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Invoice: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Rechnung Bruttopreis: " + e.getMessage());
        }

        try {
            primitiveValidator.isNumber(input.getTaxAmount(),2000000);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Invoice: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Rechnung Steuersatz: " + e.getMessage());
        }

        try {
            primitiveValidator.isNumber(input.getNetAmount(),100000000);
        } catch(NoValidIntegerException e) {
            LOG.error("Error in Invoice: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Rechnung Nettopreis: " + e.getMessage());
        }

        try{
            primitiveValidator.isValidDate(input.getDeliveryDate());
        } catch(InvalidInputException e) {
            LOG.error("Error in Invoice: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Rechnung Lieferdatum: " + e.getMessage());
        }

        try{
            primitiveValidator.isValidDate(input.getInvoiceDate());
        } catch(InvalidInputException e) {
            LOG.error("Error in Invoice: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Rechnung Erstellungsdatum: " + e.getMessage());
        }

        return true;
    }
}
