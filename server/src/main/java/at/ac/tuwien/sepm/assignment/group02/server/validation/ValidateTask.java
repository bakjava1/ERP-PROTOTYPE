package at.ac.tuwien.sepm.assignment.group02.server.validation;

import at.ac.tuwien.sepm.assignment.group02.server.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.EmptyInputException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.NoValidIntegerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class ValidateTask implements ValidateInput<Task> {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final PrimitiveValidator primitiveValidator;

    @Autowired
    public ValidateTask(PrimitiveValidator primitiveValidator) {
        this.primitiveValidator = primitiveValidator;
    }

    @Override
    public boolean isValid(Task task) throws InvalidInputException {
        LOG.debug("validating task: {}",task);

        if(task == null){
            LOG.error("Error at Task: Task cannot be null");
            throw new InvalidInputException("Fehler bei Auftrag: Auftrag kann nicht null sein");
        }

        try {
            primitiveValidator.isNumber(task.getId(),-1);
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Id: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Auftrags ID: " + e.getMessage());
        }

        try {
            primitiveValidator.isNumber(task.getOrder_id(),-1);
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Id: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Bestellungs ID: " + e.getMessage());
        }

        try {
            primitiveValidator.validateText(task.getDescription(),50);
        }catch(EmptyInputException e) {
            LOG.error("Error at Description: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Beschreibung: " + e.getMessage());
        }

        try {
            primitiveValidator.validateText(task.getFinishing(),15);
            if(!task.getFinishing().equals("roh") && !task.getFinishing().equals("gehobelt") && !task.getFinishing().equals("besäumt")
                    && !task.getFinishing().equals("prismiert") && !task.getFinishing().equals("trocken") && !task.getFinishing().equals("lutro")
                    && !task.getFinishing().equals("frisch") && !task.getFinishing().equals("imprägniert")) {
                LOG.error("Error at Finishing: Unknown Finishing");
                throw new InvalidInputException("Fehler bei Ausführung: Unbekannte Ausführung!");
            }
        }catch(EmptyInputException e) {
            LOG.error("Error at Finishing: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Ausführung: " + e.getMessage());
        }

        try {
            primitiveValidator.validateText(task.getWood_type(),10);
            if(!task.getWood_type().equals("Fi") && !task.getWood_type().equals("Ta") && !task.getWood_type().equals("Lae")) {
                LOG.error("Error at Wood Type: Unknown Wood Type");
                throw new InvalidInputException("Fehler bei Holzart: Unbekannte Holzart!");
            }
        }catch(EmptyInputException e) {
            LOG.error("Error at Wood Type: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Holzart: " + e.getMessage());
        }

        try {
            primitiveValidator.validateText(task.getQuality(),10);
            if(!task.getQuality().equals("O") && !task.getQuality().equals("I") && !task.getQuality().equals("II") &&
                    !task.getQuality().equals("III") && !task.getQuality().equals("IV") && !task.getQuality().equals("V") &&
                    !task.getQuality().equals("O/III") && !task.getQuality().equals("III/IV") && !task.getQuality().equals("III/V") ) {
                LOG.error("Error at Quality: Unknown Quality");
                throw new InvalidInputException("Fehler bei Qualität: Unbekannte Qualität!");
            }
        }catch(EmptyInputException e) {
            LOG.error("Error at Quality: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Qualität: " + e.getMessage());
        }

        try {
            primitiveValidator.isNumber(task.getSize(),515);
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Size: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Dicke: " + e.getMessage());
        }

        try {
            primitiveValidator.isNumber(task.getWidth(),515);
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Width: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Breite: " + e.getMessage());
        }

        try {
            primitiveValidator.isNumber(task.getLength(),5000);
            if(task.getLength() != 3500 && task.getLength() != 4000 && task.getLength() != 4500 && task.getLength() != 5000) {
                throw new InvalidInputException("Keine produzierbare Länge! Bitte geben sie eine produzierbare Länge an! (3500,4000,4500,5000");
            }
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Length: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Länge: " + e.getMessage());
        }

        try {
            primitiveValidator.isNumber(task.getQuantity(),100000);
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Quantity: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Anzahl: " + e.getMessage());
        }

        try {
            primitiveValidator.isNumber(task.getPrice(),10000000);
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Price: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Gesamtpreis: " + e.getMessage());
        }

        try {
            primitiveValidator.isNumber(task.getProduced_quantity(),-1);
        }catch(NoValidIntegerException e) {
            LOG.error("Error at Produced Quantity: " + e.getMessage());
            throw new InvalidInputException("Fehler bei produzierter Anzahl: " + e.getMessage());
        }
        
        return true;
    }
}
