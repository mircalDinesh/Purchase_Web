package cloud9.webapplication.exception;

public class InvalidToken extends RuntimeException {
    public InvalidToken(String message) {
        super(message);
    }
}
