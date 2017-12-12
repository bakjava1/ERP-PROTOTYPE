package at.ac.tuwien.sepm.assignment.group02.client.validation;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.InvalidInputException;

public interface ValidateInput<A> {
    boolean isValid(A input) throws InvalidInputException;
}
