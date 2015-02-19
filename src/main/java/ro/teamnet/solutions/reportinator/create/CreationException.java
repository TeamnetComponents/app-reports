package ro.teamnet.solutions.reportinator.create;

/**
 * An unchecked exception denoting failures during component creation (usually pertaining to concrete implementations'
 * internal heuristics).
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/19/2015
 */
public class CreationException extends RuntimeException {

    public CreationException() {
        super();
    }

    public CreationException(Throwable cause) {
        super(cause);
    }

    public CreationException(String message) {
        super(message);
    }

    public CreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
