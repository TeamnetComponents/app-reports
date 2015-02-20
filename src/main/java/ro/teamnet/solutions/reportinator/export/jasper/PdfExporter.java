package ro.teamnet.solutions.reportinator.export.jasper;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import ro.teamnet.solutions.reportinator.export.Exporter;
import ro.teamnet.solutions.reportinator.export.ExporterException;

import java.io.OutputStream;

/**
 * Class that exports a report to pdf format
 * @author Bogdan.Iancu
 * @version 1.0 Date: 20-Feb-15
 */
public class PdfExporter implements Exporter<JasperPrint> {

    @Override
    public void export(JasperPrint inputSource, OutputStream outputSource){
        if(inputSource == null || outputSource == null){
            throw new ExporterException("input and output should not be null");
        }
        try {
            JasperExportManager.exportReportToPdfStream(inputSource, outputSource);
        } catch (JRException e) {
            throw new ExporterException("Exception exporting report", e);
        }
    }
}
