package ro.teamnet.solutions.reportinator.export;

/**
 * An unchecked exception which denotes that an error occurred while exporting the report
 * @author Bogdan.Iancu
 * @version 1.0 Date: 20-Feb-15
 */
public class ExporterException extends RuntimeException {
    public ExporterException(String message) {
        super(message);
    }

    public ExporterException(Throwable cause) {
        super(cause);
    }

    public ExporterException() {
        super();
    }

    public ExporterException(String message, Throwable cause) {
        super(message, cause);
    }
}
