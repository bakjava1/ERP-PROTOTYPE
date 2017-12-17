package at.ac.tuwien.sepm.assignment.group02.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends PersistenceLayerException {

    public EntityNotFoundException(String message) {
        super(message);
    }

}