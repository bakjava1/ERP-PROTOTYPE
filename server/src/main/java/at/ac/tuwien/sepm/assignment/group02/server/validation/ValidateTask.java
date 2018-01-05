package at.ac.tuwien.sepm.assignment.group02.server.validation;

import at.ac.tuwien.sepm.assignment.group02.server.entity.Task;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.NoValidIntegerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class ValidateTask implements ValidateInput<Task> {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public boolean isValid(Task task) throws InvalidInputException {
        LOG.debug("validating task: {}",task);

        if(task==null){
            LOG.debug("Task is null.");
            try {
                throw new InvalidInputException("Task can't be empty.");
            } catch (InvalidInputException e) {
                LOG.warn(e.getMessage());
                throw new InvalidInputException(e.getMessage());
            }
        }

        if(task.getId()<0){
            LOG.warn("ID: {}", task.getId());
            try {
                throw new NoValidIntegerException("Invalid ID.");
            } catch (NoValidIntegerException e) {
                LOG.warn(e.getMessage());
                throw new InvalidInputException(e.getMessage());
            }
        }

        if(task.getSize()<=0){
            LOG.warn("SIZE: {}", task.getSize());
            try {
                throw new NoValidIntegerException("Negative Integer or Null entered");
            } catch (NoValidIntegerException e) {
                LOG.warn(e.getMessage());
                throw new InvalidInputException(e.getMessage());
            }
        }

        if(task.getWidth()<=0){
            LOG.warn("WIDTH: {}", task.getWidth());
            try {
                throw new NoValidIntegerException("Negative Integer or Null entered.");
            } catch (NoValidIntegerException e) {
                LOG.warn(e.getMessage());
                throw new InvalidInputException(e.getMessage());
            }
        }

        if(task.getLength()<=0){
            LOG.warn("LENGTH: {}", task.getLength());
            try {
                throw new NoValidIntegerException("Negative Integer or Null entered.");
            } catch (NoValidIntegerException e) {
                LOG.warn(e.getMessage());
                throw new InvalidInputException(e.getMessage());
            }
        }
        if(task.getQuantity()<0){
            LOG.warn("QUALITY: {}", task.getQuantity());
            try {
                throw new NoValidIntegerException("Negative Integer or Null entered.");
            } catch (NoValidIntegerException e) {
                LOG.warn(e.getMessage());
                throw new InvalidInputException(e.getMessage());
            }
        }
        if(task.getProduced_quantity()<0){
            LOG.warn("Produced QUANTITY: {}", task.getProduced_quantity());
            try {
                throw new NoValidIntegerException("Negative Integer or Null entered.");
            } catch (NoValidIntegerException e) {
                LOG.warn(e.getMessage());
                throw new InvalidInputException(e.getMessage());
            }
        }

        if(task.getDescription()==null || task.getDescription().isEmpty()){
            LOG.warn("Description: '{}'.", task.getDescription());
            throw new InvalidInputException("Description can't be empty.");
        }
        if(task.getFinishing()==null || task.getFinishing().isEmpty()){
            LOG.warn("Finishing: '{}'.", task.getFinishing());
            throw new InvalidInputException("Finishing can't be empty.");
        }

        if(task.getWood_type()==null || task.getWood_type().isEmpty()){
            LOG.warn("Wood_type: '{}'.", task.getWood_type());
            throw new InvalidInputException("Wood_type can't be empty.");
        }

        if(task.getQuality()==null || task.getQuality().isEmpty()){
            LOG.warn("Quality: '{}'.", task.getQuality());
            throw new InvalidInputException(" Quality can't be empty.");
        }

        return true;
    }
}
