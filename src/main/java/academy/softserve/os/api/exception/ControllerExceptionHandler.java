package academy.softserve.os.api.exception;

import academy.softserve.os.service.exception.CreateAddressException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CreateAddressException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorApiMessage> handleCreateOrderException(CreateAddressException e) {
        return new ResponseEntity<>(new ErrorApiMessage("Error created address"), HttpStatus.BAD_REQUEST);
    }
}

@Getter
@Setter
@AllArgsConstructor
class ErrorApiMessage {
    String message;
}