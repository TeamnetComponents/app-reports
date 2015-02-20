package ro.teamnet.solutions.reportinator.export.jasper;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import ro.teamnet.solutions.reportinator.export.Exporter;
import ro.teamnet.solutions.reportinator.export.ExporterException;

import java.io.OutputStream;

/**
 * Class that exports a report to xls format
 * @author Bogdan.Iancu
 * @version 1.0 Date: 20-Feb-15
 */
public class XlsExporter implements Exporter<JasperPrint> {

    @Override
    public void export(JasperPrint inputSource, OutputStream outputSource){
        if(inputSource == null || outputSource == null){
            throw new ExporterException("input and output should not be null");
        }
        JRXlsExporter exporterXLS = new JRXlsExporter();
        exporterXLS.setExporterInput(new SimpleExporterInput(inputSource));
        exporterXLS.setExporterOutput(new SimpleOutputStreamExporterOutput(outputSource));

        SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();


        configuration.setOnePagePerSheet(true);
        configuration.setDetectCellType(true);
        configuration.isRemoveEmptySpaceBetweenColumns();
        configuration.isRemoveEmptySpaceBetweenRows();
        configuration.setCollapseRowSpan(false);
        configuration.setWhitePageBackground(false);
        configuration.setRemoveEmptySpaceBetweenRows(true);

        exporterXLS.setConfiguration(configuration);

        try {
            exporterXLS.exportReport();
        } catch (JRException e) {
            throw new ExporterException("Exception exporting report", e);
        }
    }
}
