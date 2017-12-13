package at.ac.tuwien.sepm.assignment.group02.client.service;

import at.ac.tuwien.sepm.assignment.group02.client.entity.UnvalidatedTask;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import at.ac.tuwien.sepm.assignment.group02.client.validation.Validator;
import at.ac.tuwien.sepm.assignment.group02.rest.entity.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    public static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private Validator validator;

    @Autowired
    public TaskServiceImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public void createTask(Task task) throws InvalidInputException {

    }

    @Override
    public List<Task> getAllOpenTasks() {
        return null;
    }

    @Override
    public Task validateTaskInput(UnvalidatedTask toValidate) throws InvalidInputException {
        Task validated;
        try {
            validated = validator.inputValidationTask(toValidate);
        } catch(InvalidInputException e) {
            //TODO maybe add another exception like Failed TaskCreationException
            LOG.error("Input Validation failed: " + e.getMessage());
            throw new InvalidInputException(e.getMessage());
        }
        return validated;
    }
}
