package academy.softserve.os.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ErrorResponse {
    private String message;
    private List<String> details;
}
