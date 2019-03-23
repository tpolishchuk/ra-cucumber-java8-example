package demo_app.exception;

import demo_app.domain.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@RestController
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity buildResponseEntity(Exception e, WebRequest request, HttpStatus httpStatus) {
        ErrorDetails errorDetails = new ErrorDetails(e.getMessage(),
                                                     request.getDescription(false));
        return new ResponseEntity<>(errorDetails, httpStatus);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public final ResponseEntity handleUserNotFoundExceptions(Exception e, WebRequest request) {
        return buildResponseEntity(e, request, NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity handleAllExceptions(Exception e, WebRequest request) {
        return buildResponseEntity(e, request, BAD_REQUEST);
    }
}
