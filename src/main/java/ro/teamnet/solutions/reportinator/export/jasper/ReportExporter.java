package ro.teamnet.solutions.reportinator.export.jasper;

import net.sf.jasperreports.engine.JasperPrint;
import ro.teamnet.solutions.reportinator.export.ExportType;
import ro.teamnet.solutions.reportinator.export.ExporterException;

import java.io.OutputStream;

/**
 * Class that exports a report to the given format
 * Created by Bogdan.Iancu on 13-Feb-15.
 */
public class ReportExporter {

    /**
     * Converts a JasperPrint object to the desired format
     *
     * @param inputSource  the JasperPrint object to be exported
     * @param outputSource
     * @param type         desired format
     */
    public static void export(JasperPrint inputSource, OutputStream outputSource, ExportType type) {
        try {
            type.getExporter().export(inputSource, outputSource);
        } catch (Exception e) {
            throw new ExporterException("Exception exporting report", e);
        }
    }
}
