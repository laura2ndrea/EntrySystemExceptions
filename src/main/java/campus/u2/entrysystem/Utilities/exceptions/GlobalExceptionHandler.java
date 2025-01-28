package campus.u2.entrysystem.Utilities.exceptions;

import campus.u2.entrysystem.access.infrastructure.exceptions.AccessInvalidInputException;
import campus.u2.entrysystem.access.infrastructure.exceptions.AccessNotAllowedException;
import campus.u2.entrysystem.access.infrastructure.exceptions.AccessNotFoundException;
import campus.u2.entrysystem.accessnotes.infrastructure.exceptions.AccessNoteNotFoundException;
import campus.u2.entrysystem.people.infrastructure.exceptions.PeopleNotFoundException;
import campus.u2.entrysystem.porters.infrastructure.exceptions.PorterNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    // Exceptions for access
    @ExceptionHandler(AccessNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleAccessNotFoundException(AccessNotFoundException ex) {
        ErrorResponses errorResponse = new ErrorResponses(
                "Access not found",
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(AccessInvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleInvalidInputException(AccessInvalidInputException ex) {
        ErrorResponses errorResponse = new ErrorResponses(
                "Invalid input",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(AccessNotAllowedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Object> handleAccessNotAllowedException(AccessNotAllowedException ex) {
        ErrorResponses errorResponse = new ErrorResponses(
                "Access not allowed",
                ex.getMessage(),
                HttpStatus.FORBIDDEN,
                HttpStatus.FORBIDDEN.value()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }
    
    // Exceptions for access notes 
    @ExceptionHandler(AccessNoteNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleAccessNoteNotFoundException(AccessNoteNotFoundException ex) {
        ErrorResponses errorResponse = new ErrorResponses(
                "Access note not found",
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    
    // Exceptions for people
    @ExceptionHandler(PeopleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handlePeopleNotFoundException(PeopleNotFoundException ex) {
        ErrorResponses errorResponse = new ErrorResponses(
                "Person not found",
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    
    // Exceptions for porters 
    @ExceptionHandler(PorterNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handlePorterNotFoundException(PorterNotFoundException ex) {
        ErrorResponses errorResponse = new ErrorResponses(
                "Porter not found",
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    
    // Generic exceptions 
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleGlobalException(Exception ex) {
        ErrorResponses errorResponse = new ErrorResponses(
                "Application error",
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
