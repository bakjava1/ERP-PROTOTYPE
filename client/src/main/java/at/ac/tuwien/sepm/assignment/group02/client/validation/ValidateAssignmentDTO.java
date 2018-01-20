package at.ac.tuwien.sepm.assignment.group02.client.validation;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.NoValidIntegerException;
import at.ac.tuwien.sepm.assignment.group02.rest.restDTO.AssignmentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Component
public class ValidateAssignmentDTO implements ValidateInput<AssignmentDTO> {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final PrimitiveValidator primitiveValidator;

    @Autowired
    public ValidateAssignmentDTO(PrimitiveValidator primitiveValidator) {
        this.primitiveValidator = primitiveValidator;
    }

    @Override
    public boolean isValid(AssignmentDTO input) throws InvalidInputException {
        LOG.debug("validating assignmentDTO: {}",input);

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
            primitiveValidator.isNumber(amount,50);
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
