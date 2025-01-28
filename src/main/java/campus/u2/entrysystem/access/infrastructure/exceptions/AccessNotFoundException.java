package campus.u2.entrysystem.access.infrastructure.exceptions;

public class AccessNotFoundException extends RuntimeException{
    
    public AccessNotFoundException(String message) {
        super(message); 
    }
}
