package ro.teamnet.solutions.reportinator.export;

import net.sf.jasperreports.engine.JasperPrint;

/**
 * An interface to be implemented by classes which will export a report into a single output format, such as .PDF, .XLS,
 * .HTML, .XML etc.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/6/2015
 */
public interface Exporter {
    void export(JasperPrint jasperPrint, String fileName);
}
