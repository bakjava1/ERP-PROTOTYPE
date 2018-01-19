package at.ac.tuwien.sepm.assignment.group02.client.util;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.LumberNotFountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.invoke.MethodHandles;

/**
 * Created by raquelsima on 12.01.18.
 */
@ControllerAdvice
public class RestErrorHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @ExceptionHandler(LumberNotFountException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleLumberNotFoundException(LumberNotFountException ex) {
        LOG.debug("handling 404 error on a lumber class");
    }
}
