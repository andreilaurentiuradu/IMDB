package exceptions;

public class InvalidInputType extends RuntimeException {
    public InvalidInputType(String message) {
        super(message);
    }
}
