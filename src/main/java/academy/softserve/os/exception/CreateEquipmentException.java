package academy.softserve.os.exception;

public class CreateEquipmentException extends RuntimeException {
    public CreateEquipmentException() {
        super();
    }
    public CreateEquipmentException(String message) {
        super("Create equipment error. " + message);
    }
}
