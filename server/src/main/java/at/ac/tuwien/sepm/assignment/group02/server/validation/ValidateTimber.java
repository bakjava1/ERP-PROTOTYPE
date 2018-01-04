package at.ac.tuwien.sepm.assignment.group02.server.validation;

import at.ac.tuwien.sepm.assignment.group02.server.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.server.exceptions.InvalidInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class ValidateTimber implements ValidateInput<Timber> {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public boolean isValid(Timber timber) throws InvalidInputException {
        LOG.debug("validating timber: ", timber.toString());

        //TODO

        if(timber.getAmount()<0){
            throw new InvalidInputException("Error: added amount is negative");
        }

        return true;
    }
}
