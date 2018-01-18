package at.ac.tuwien.sepm.assignment.group02.server.rest;

import at.ac.tuwien.sepm.assignment.group02.server.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.invoke.MethodHandles;

@ControllerAdvice
public class ExceptionHandlingImpl {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @ExceptionHandler({ServiceLayerException.class})
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public String defaultError(ServiceLayerException e){
        LOG.debug("defaultError: "+e.getMessage());
        return "Interner Fehler";
    }

    @ExceptionHandler({InternalServerException.class})
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public String defaultError(InternalServerException e){
        LOG.debug("defaultError: "+e.getMessage());
        return "Interner Fehler";
    }

    @ExceptionHandler({InvalidInputException.class})
    @ResponseStatus(value=HttpStatus.UNAUTHORIZED)
    public String defaultError(InvalidInputException e) {
        LOG.debug("defaultError: "+e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler({EntityNotFoundExceptionService.class})
    @ResponseStatus(value=HttpStatus.NOT_FOUND)
    public String defaultError(EntityNotFoundExceptionService e) {
        LOG.debug("defaultError: "+e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler({EntityCreationException.class})
    @ResponseStatus(value=HttpStatus.NOT_MODIFIED)
    public String defaultError(EntityCreationException e) {
        LOG.debug("defaultError: "+e.getMessage());
        return e.getMessage();
    }

}
