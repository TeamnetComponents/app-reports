package ro.teamnet.solutions.reportinator;

/**
 * An exception, denoting build failures.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/10/2015
 */
public class ReportBuilderException extends Exception {

    public ReportBuilderException(Throwable cause) {
        super(cause);
    }

    public ReportBuilderException() {
    }

    public ReportBuilderException(String message) {
        super(message);
    }

    public ReportBuilderException(String message, Throwable cause) {
        super(message, cause);
    }
}
