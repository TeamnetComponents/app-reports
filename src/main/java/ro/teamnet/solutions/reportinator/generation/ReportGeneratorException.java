package ro.teamnet.solutions.reportinator.generation;

/**
 * An exception, denoting report build failures.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/10/2015
 */
public class ReportGeneratorException extends RuntimeException {

    public ReportGeneratorException(Throwable cause) {
        super(cause);
    }

    public ReportGeneratorException() {
    }

    public ReportGeneratorException(String message) {
        super(message);
    }

    public ReportGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }
}
