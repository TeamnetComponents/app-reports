package ro.teamnet.solutions.reportinator.generation;

/**
 * An interface for report generators.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/13/2015
 */
public interface ReportGenerator<T> {

    /**
     * Generates a report abstraction, specific to the underlying reporting engine (denoted by {@code T}),
     * ready for exporting.
     *
     * @return A report abstraction of the specified type.
     * @throws ReportGeneratorException If anything happened during report generation.
     */
    T generate() throws ReportGeneratorException;

}
