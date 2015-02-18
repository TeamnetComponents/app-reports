package ro.teamnet.solutions.reportinator.export;

import net.sf.jasperreports.engine.JasperPrint;
import ro.teamnet.solutions.reportinator.export.jasper.type.ExportType;
import ro.teamnet.solutions.reportinator.generation.ReportGenerator;

import java.io.OutputStream;

/**
 * TODO Improve documentation consistency and general/exception messages improvements
 * Class that exports a report to the given format
 * Created by Bogdan.Iancu on 13-Feb-15.
 *
 * @author Bogdan.Stefan
 */
public class JasperReportExporter {

    /**
     * Converts a JasperPrint object to the desired format
     *
     * @param inputSource  the JasperPrint object to be exported
     * @param outputSource
     * @param type         desired format
     */
    public static void export(JasperPrint inputSource, OutputStream outputSource, ExportType type) throws ExporterException {
        try {
            type.getExporter().export(inputSource, outputSource);
        } catch (Exception e) {
            throw new ExporterException("Exception exporting report", e.getCause());
        }
    }

    /**
     * A method which exports to a custom format, using a {@link ro.teamnet.solutions.reportinator.generation.ReportGenerator}
     * implementation.
     *
     * @param printGenerator The generator of the report print.
     * @param outputSource   An output source
     * @param type
     * @see ro.teamnet.solutions.reportinator.generation.JasperReportGenerator JasperReportGenerator
     */
    public static void export(ReportGenerator<JasperPrint> printGenerator, OutputStream outputSource, ExportType type) throws ExporterException {
        if (printGenerator == null) {
            throw new ExporterException("Print generator must not be null.");
        }
        export(printGenerator.generate(), outputSource, type);
    }
}
