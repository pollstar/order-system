package academy.softserve.os.exception;

public class CreateOrderException extends RuntimeException{
    public CreateOrderException() {
        super();
    }

    public CreateOrderException(String message) {
        super(message);
    }
}
