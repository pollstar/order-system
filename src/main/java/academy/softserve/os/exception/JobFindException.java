package academy.softserve.os.exception;

public class JobFindException extends RuntimeException{
    public JobFindException() {
        super();
    }

    public JobFindException(String message) {
        super(message);
    }
}
