package ro.teamnet.solutions.reportinator.convert;

/**
 * An unchecked exception denoting conversion failures (usually pertaining to concrete implementations' internal heuristics).
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/9/2015
 */
public class ConversionException extends RuntimeException {

    public ConversionException(String message) {
        super(message);
    }

    public ConversionException(Throwable cause) {
        super(cause);
    }

    public ConversionException() {
        super();
    }

    public ConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
