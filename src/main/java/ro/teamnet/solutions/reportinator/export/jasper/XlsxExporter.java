package ro.teamnet.solutions.reportinator.export.jasper;


import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import ro.teamnet.solutions.reportinator.export.Exporter;
import ro.teamnet.solutions.reportinator.export.ExporterException;

import java.io.OutputStream;

/**
 * Class that exports a report to xlsx format
 *
 * @author Bogdan.Iancu
 * @version 1.0 Date: 2015-03-23
 */
public class XlsxExporter implements Exporter<JasperPrint> {

    /**
     * Converts a JasperPrint to xlsx format
     *
     * @param inputSource  the JasperPrint object to be exported
     * @param outputSource An output source
     */
    @Override
    public void export(JasperPrint inputSource, OutputStream outputSource) {
        if (inputSource == null || outputSource == null) {
            throw new ExporterException("input and output should not be null");
        }

        JRXlsxExporter exporterXLSX = new JRXlsxExporter();
        exporterXLSX.setExporterInput(new SimpleExporterInput(inputSource));
        exporterXLSX.setExporterOutput(new SimpleOutputStreamExporterOutput(outputSource));

        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();


        configuration.setOnePagePerSheet(false);
        configuration.setDetectCellType(true);
        configuration.isAutoFitPageHeight();
        configuration.isRemoveEmptySpaceBetweenColumns();
        configuration.isRemoveEmptySpaceBetweenRows();
        configuration.setCollapseRowSpan(true);
        configuration.setWhitePageBackground(false);
        configuration.setRemoveEmptySpaceBetweenRows(true);

        exporterXLSX.setConfiguration(configuration);

        try {
            exporterXLSX.exportReport();
        } catch (JRException e) {
            throw new ExporterException("Exception exporting report", e.getCause());
        }
    }
}
