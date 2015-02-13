package ro.teamnet.solutions.reportinator.export.jasper;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import ro.teamnet.solutions.reportinator.export.Exporter;
import ro.teamnet.solutions.reportinator.export.ExporterException;

import java.io.OutputStream;

/**
 * Class that exports a report to pdf format
 * Created by Bogdan.Iancu on 13-Feb-15.
 */
public class JasperPdfExporter implements Exporter<JasperPrint> {

    @Override
    public void export(JasperPrint inputSource, OutputStream outputSource){
        try {
            JasperExportManager.exportReportToPdfStream(inputSource, outputSource);
        } catch (JRException e) {
            throw new ExporterException("Exception exporting raport", e);
        }

    }

}
