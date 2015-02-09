package ro.teamnet.solutions.reportinator.convert;

/**
 * An checked exception which denotes that a conversion error occurred.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/9/2015
 */
public class ConversionException extends Exception {

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
