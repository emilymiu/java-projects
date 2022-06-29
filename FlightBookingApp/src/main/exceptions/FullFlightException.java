package exceptions;

// represents exception that is thrown when flight is full
public class FullFlightException extends Exception {

    public FullFlightException(String msg) {
        super(msg);
    }
}
