package at.ac.tuwien.sepm.assignment.group02.server.validation;

import at.ac.tuwien.sepm.assignment.group02.server.exceptions.InvalidInputException;

public interface ValidateInput<A> {

    /**
     * validates a given input
     * @param input the object to validate
     * @return true if the input is valid
     * @throws InvalidInputException thrown if the input is invalid
     */
    boolean isValid(A input) throws InvalidInputException;
}
