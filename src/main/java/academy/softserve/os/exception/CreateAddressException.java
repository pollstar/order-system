package academy.softserve.os.exception;

public class CreateAddressException extends RuntimeException {
    public CreateAddressException(){
        super();
    }
    public CreateAddressException(String e) {
        super(e == null ? "" : e);
    }
}
