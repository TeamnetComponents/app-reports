package ro.teamnet.solutions.reportinator.export;

import java.io.OutputStream;

/**
 * An interface to be implemented by classes which will export a report into a single output format (such as .PDF, .XLS,
 * .HTML, .XML etc.).
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/6/2015
 */
public interface Exporter<IN> {
    void export(IN inputSource, OutputStream outputSource) throws ExporterException;
}
