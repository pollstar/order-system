package academy.softserve.os.service.exception;

public class WrongRoleNameException extends RuntimeException{

    public static final String MESSAGE = "You should specify correct role. Example: [ROLE_WORKER, ROLE_ADMIN]";

    public WrongRoleNameException() {
        super(MESSAGE);
    }

    public WrongRoleNameException(String message) {
        super(message);
    }
}
