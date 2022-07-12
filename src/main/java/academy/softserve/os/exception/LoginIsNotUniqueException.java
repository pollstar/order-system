package academy.softserve.os.exception;

public class LoginIsNotUniqueException extends RuntimeException{
    public static final String MESSAGE = "You must specify unique login in your request";

    public LoginIsNotUniqueException(){
        super(MESSAGE);
    }

    public LoginIsNotUniqueException(Throwable e) {
        super(MESSAGE,e);
    }

}
