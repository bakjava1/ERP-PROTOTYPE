package at.ac.tuwien.sepm.assignment.group02.server.exceptions;

public class ResourceNotFoundException extends ServiceLayerException {
    public ResourceNotFoundException(String message) { super(message); }
}