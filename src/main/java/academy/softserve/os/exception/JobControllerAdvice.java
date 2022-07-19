package academy.softserve.os.exception;

import academy.softserve.os.api.JobController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(assignableTypes = JobController.class)
public class JobControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(JobFindException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> jobFindException(JobFindException e){
        return ResponseEntity.noContent().build();
    }

}
