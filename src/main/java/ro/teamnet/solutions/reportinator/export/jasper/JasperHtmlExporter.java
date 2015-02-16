package ro.teamnet.solutions.reportinator.export.jasper;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import ro.teamnet.solutions.reportinator.export.Exporter;
import ro.teamnet.solutions.reportinator.export.ExporterException;

import java.io.OutputStream;

/**
 * Class that exports a report to html format
 * Created by Bogdan.Iancu on 13-Feb-15.
 */
public class JasperHtmlExporter implements Exporter<JasperPrint> {

    @Override
    public void export(JasperPrint inputSource, OutputStream outputSource){
        HtmlExporter exporter = new HtmlExporter();

        exporter.setExporterInput(new SimpleExporterInput(inputSource));
        exporter.setExporterOutput(new SimpleHtmlExporterOutput(outputSource));

        try {
            exporter.exportReport();
        } catch (JRException e) {
            throw new ExporterException("Exception exporting raport", e);
        }

    }
}
