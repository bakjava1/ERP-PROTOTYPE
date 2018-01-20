package at.ac.tuwien.sepm.assignment.group02.server.validation;

import at.ac.tuwien.sepm.assignment.group02.server.entity.Assignment;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.NoValidIntegerException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ValidateAssignment implements ValidateInput<Assignment> {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final PrimitiveValidator primitiveValidator;

    @Autowired
    public ValidateAssignment(PrimitiveValidator primitiveValidator) {
        this.primitiveValidator = primitiveValidator;
    }

    @Override
    public boolean isValid(Assignment input) throws InvalidInputException {
        LOG.debug("validating assignment: {}",input);

        int id = input.getId();
        int amount = input.getAmount();
        int box_id = input.getBox_id();
        int task_id = input.getTask_id();
        String date = input.getCreation_date();

        try {
            primitiveValidator.isNumber(id,-1);
        } catch(NoValidIntegerException e) {
            LOG.error("Error at Id: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Aufgaben ID: " + e.getMessage());
        }

        try {
            primitiveValidator.isNumber(amount,800);
        } catch(NoValidIntegerException e) {
            LOG.error("Error at Amount: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Anzahl: " + e.getMessage());
        }

        try {
            primitiveValidator.isNumber(box_id,25);
        } catch(NoValidIntegerException e) {
            LOG.error("Error at Box Number: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Box Nummer: " + e.getMessage());
        }

        try {
            primitiveValidator.isNumber(task_id,-1);
        } catch(NoValidIntegerException e) {
            LOG.error("Error at Task Id: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Auftrags ID: " + e.getMessage());
        }

        try {
            primitiveValidator.isValidDate(date);
        } catch(InvalidInputException e) {
            LOG.error("Error at Date: " + e.getMessage());
            throw new InvalidInputException("Fehler bei Erstellungsdatum: " + e.getMessage());
        }

        return true;
    }

}
