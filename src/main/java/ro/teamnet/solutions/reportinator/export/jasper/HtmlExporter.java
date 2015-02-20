package ro.teamnet.solutions.reportinator.export.jasper;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import ro.teamnet.solutions.reportinator.export.Exporter;
import ro.teamnet.solutions.reportinator.export.ExporterException;

import java.io.OutputStream;

/**
 * Class that exports a report to html format
 * @author Bogdan.Iancu
 * @version 1.0 Date: 20-Feb-15
 */
public class HtmlExporter implements Exporter<JasperPrint> {

    @Override
    public void export(JasperPrint inputSource, OutputStream outputSource){
        if(inputSource == null || outputSource == null){
            throw new ExporterException("input and output should not be null");
        }
        net.sf.jasperreports.engine.export.HtmlExporter exporter = new net.sf.jasperreports.engine.export.HtmlExporter();

        exporter.setExporterInput(new SimpleExporterInput(inputSource));
        exporter.setExporterOutput(new SimpleHtmlExporterOutput(outputSource));

        try {
            exporter.exportReport();
        } catch (JRException e) {
            throw new ExporterException("Exception exporting report", e);
        }

    }
}
