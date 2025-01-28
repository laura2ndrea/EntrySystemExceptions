package campus.u2.entrysystem.access.infrastructure.exceptions;

public class AccessNotAllowedException extends RuntimeException {

    public AccessNotAllowedException(String message) {
        super(message); 
    }
    
}
