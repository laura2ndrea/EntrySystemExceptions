package campus.u2.entrysystem.access.infrastructure.exceptions;

import org.springframework.http.HttpStatus;

public class AccessTypeMismatchException extends RuntimeException {
    private final String fieldName;
    private final String expectedType;
    private final String receivedValue;

    public AccessTypeMismatchException(String expectedType, String receivedValue) {
        super(String.format("Field 'IdAccess' expected a value of type '%s' but received '%s'", expectedType, receivedValue));
        this.fieldName = "IdAccess"; // Determinamos el valor directamente
        this.expectedType = expectedType;
        this.receivedValue = receivedValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getExpectedType() {
        return expectedType;
    }

    public String getReceivedValue() {
        return receivedValue;
    }
    
}
