package at.ac.tuwien.sepm.assignment.group02.server.validation;

import at.ac.tuwien.sepm.assignment.group02.server.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.NoValidIntegerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class ValidateLumber implements ValidateInput<Lumber> {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public boolean isValid(Lumber lumber) throws InvalidInputException {
        LOG.debug("validating lumber: {}",lumber);

        if(lumber==null){
            LOG.debug("Lumber is null.");
            try {
                throw new InvalidInputException("Lumber can't be empty.");
            } catch (InvalidInputException e) {
                LOG.warn(e.getMessage());
                throw new InvalidInputException(e.getMessage());
            }
        }

        if(lumber.getId()<0){
            LOG.warn("ID: {}", lumber.getId());
            try {
                throw new NoValidIntegerException("Invalid ID.");
            } catch (NoValidIntegerException e) {
                LOG.warn(e.getMessage());
                throw new InvalidInputException(e.getMessage());
            }
        }

        if(lumber.getSize()<=0){
            LOG.warn("SIZE: {}", lumber.getSize());
            try {
                throw new NoValidIntegerException("Negative Integer or Null entered");
            } catch (NoValidIntegerException e) {
                LOG.warn(e.getMessage());
                throw new InvalidInputException(e.getMessage());
            }
        }

        if(lumber.getWidth()<=0){
            LOG.warn("WIDTH: {}", lumber.getWidth());
            try {
                throw new NoValidIntegerException("Negative Integer or Null entered.");
            } catch (NoValidIntegerException e) {
                LOG.warn(e.getMessage());
                throw new InvalidInputException(e.getMessage());
            }
        }

        if(lumber.getLength()<=0){
            LOG.warn("LENGTH: {}", lumber.getLength());
            try {
                throw new NoValidIntegerException("Negative Integer or Null entered.");
            } catch (NoValidIntegerException e) {
                LOG.warn(e.getMessage());
                throw new InvalidInputException(e.getMessage());
            }
        }
        if(lumber.getQuantity()<0){
            LOG.warn("QUALITY: {}", lumber.getQuantity());
            try {
                throw new NoValidIntegerException("Negative Integer or Null entered.");
            } catch (NoValidIntegerException e) {
                LOG.warn(e.getMessage());
                throw new InvalidInputException(e.getMessage());
            }
        }
        if(lumber.getReserved_quantity()<0){
            LOG.warn("RESERVED QUANTITY: {}", lumber.getReserved_quantity());
            try {
                throw new NoValidIntegerException("Negative Integer or Null entered.");
            } catch (NoValidIntegerException e) {
                LOG.warn(e.getMessage());
                throw new InvalidInputException(e.getMessage());
            }
        }

        if(lumber.getDescription()==null || lumber.getDescription().isEmpty()){
            LOG.warn("Description: '{}'.", lumber.getDescription());
            throw new InvalidInputException("Description can't be empty.");
        }
        if(lumber.getFinishing()==null || lumber.getFinishing().isEmpty()){
            LOG.warn("Finishing: '{}'.", lumber.getFinishing());
            throw new InvalidInputException("Finishing can't be empty.");
        }

        if(lumber.getWood_type()==null || lumber.getWood_type().isEmpty()){
            LOG.warn("Wood_type: '{}'.", lumber.getWood_type());
            throw new InvalidInputException("Wood_type can't be empty.");
        }

        if(lumber.getQuality()==null || lumber.getQuality().isEmpty()){
            LOG.warn("Quality: '{}'.", lumber.getQuality());
            throw new InvalidInputException(" Quality can't be empty.");
        }

        return true;
    }
}
