package at.ac.tuwien.sepm.assignment.group02.client.validation;

import at.ac.tuwien.sepm.assignment.group02.client.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;
import org.springframework.stereotype.Service;

@Service
public class ValidateTimber implements ValidateInput<Timber> {
    @Override
    public boolean isValid(Timber toValidate) throws InvalidInputException {

        if(toValidate.getBox_id()<0)
            throw new InvalidInputException("Error in Timber Box: Box ID is negative");


        if(toValidate.getAmount()< 0)
            throw new InvalidInputException("Error in Timber Amount: Amount is negative");

        if(toValidate.getBox_id() > 25)
            throw new InvalidInputException("Error in Timber Box : Unknow Box Id, Enter Box Id < 25");

        if(toValidate.getAmount() > 500)
            throw new InvalidInputException("Error in Timber Amount: Amount entered too big! Enter Amount < 500");

        return true;
    }
}
