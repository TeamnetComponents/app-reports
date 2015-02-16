package ro.teamnet.solutions.reportinator.load;

/**
 * TODO Doc
 *
 * Created by Andrei.Marica on 2/10/2015.
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
