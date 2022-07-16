package academy.softserve.os.exception;

import academy.softserve.os.api.EquipmentController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(assignableTypes = EquipmentController.class)
public class EquipmentControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CreateEquipmentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorApiMessage> handleCreateEquipmentException(CreateEquipmentException e) {
        return new ResponseEntity<>(new ErrorApiMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}


