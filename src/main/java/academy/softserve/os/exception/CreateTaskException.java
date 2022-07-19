package academy.softserve.os.exception;

public class CreateTaskException extends RuntimeException{
    public CreateTaskException() {
        super();
    }

    public CreateTaskException(String message) {
        super(message);
    }
}
