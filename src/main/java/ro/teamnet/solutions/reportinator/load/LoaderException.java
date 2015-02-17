package ro.teamnet.solutions.reportinator.load;

/**
 * An exception denoting loader failures (usually pertaining to concrete implementations' internal heuristics).
 *
 * @author Andrei.Marica
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/10/2015
 */
public class LoaderException extends RuntimeException {

    public LoaderException() {
    }

    public LoaderException(String message) {
        super(message);
    }

    public LoaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoaderException(Throwable cause) {
        super(cause);
    }

    public LoaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
