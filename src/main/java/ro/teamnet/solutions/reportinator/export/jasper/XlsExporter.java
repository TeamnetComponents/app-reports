/*
 * Copyright (c) 2015 Teamnet S.A. All Rights Reserved.
 *
 * This source file may not be copied, modified or redistributed,
 * in whole or in part, in any form or for any reason, without the express
 * written consent of Teamnet S.A.
 */

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
 *
 * @author Bogdan.Iancu
 * @version 1.0.1 Date: 2015-03-20
 * @since 1.0 Date: 2015-02-06
 */
public class XlsExporter implements Exporter<JasperPrint> {

    /**
     * Converts a JasperPrint to xls format
     *
     * @param inputSource  the JasperPrint object to be exported
     * @param outputSource An output source
     */
    @Override
    public void export(JasperPrint inputSource, OutputStream outputSource) {
        if (inputSource == null || outputSource == null) {
            throw new ExporterException("input and output should not be null");
        }

        JRXlsExporter exporterXLS = new JRXlsExporter();
        exporterXLS.setExporterInput(new SimpleExporterInput(inputSource));
        exporterXLS.setExporterOutput(new SimpleOutputStreamExporterOutput(outputSource));

        SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();


        configuration.setOnePagePerSheet(false);
        configuration.setDetectCellType(true);
        configuration.isAutoFitPageHeight();
        configuration.isRemoveEmptySpaceBetweenColumns();
        configuration.isRemoveEmptySpaceBetweenRows();
        configuration.setCollapseRowSpan(true);
        configuration.setWhitePageBackground(false);
        configuration.setRemoveEmptySpaceBetweenRows(true);

        exporterXLS.setConfiguration(configuration);

        try {
            exporterXLS.exportReport();
        } catch (JRException e) {
            throw new ExporterException("Exception exporting report", e.getCause());
        }
    }
}
