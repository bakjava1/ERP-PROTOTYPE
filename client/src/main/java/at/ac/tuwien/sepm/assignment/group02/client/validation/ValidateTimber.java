package at.ac.tuwien.sepm.assignment.group02.client.validation;

import at.ac.tuwien.sepm.assignment.group02.client.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import org.springframework.stereotype.Service;

@Service
public class ValidateTimber implements ValidateInput<Timber> {
    @Override
    public boolean isValid(Timber toValidate) throws InvalidInputException {

        if(toValidate.getBox_id()<0){
            throw new InvalidInputException("Error in Timber Box: Box ID is negative");
        }

        if(toValidate.getAmount()< 0)
            throw new InvalidInputException("Error in Timber Amount: Amount is negative");
        return true;
    }
}
