package academy.softserve.os.exception;

import academy.softserve.os.api.AddressController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(assignableTypes = AddressController.class)
public class AddressControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CreateAddressException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorApiMessage> handleCreateOrderException(CreateAddressException e) {
        return new ResponseEntity<>(new ErrorApiMessage("Error created address. " + e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}


