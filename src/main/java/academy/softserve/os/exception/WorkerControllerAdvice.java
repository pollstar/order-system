package academy.softserve.os.exception;

import academy.softserve.os.api.WorkerController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@RestControllerAdvice(assignableTypes = WorkerController.class)
public class WorkerControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(LoginIsNotUniqueException.class)
    public ResponseEntity<String> handleLoginIsNotUnique(LoginIsNotUniqueException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ex.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(collectingAndThen(toList(),
                        details -> ResponseEntity.badRequest()
                                .body(new ErrorResponse("Validation failed!", details))));
    }
}
