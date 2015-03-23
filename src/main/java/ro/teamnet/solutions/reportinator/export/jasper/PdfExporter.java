/*
 * Copyright (c) 2015 Teamnet S.A. All Rights Reserved.
 *
 * This source file may not be copied, modified or redistributed,
 * in whole or in part, in any form or for any reason, without the express
 * written consent of Teamnet S.A.
 */

package ro.teamnet.solutions.reportinator.export.jasper;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import ro.teamnet.solutions.reportinator.export.Exporter;
import ro.teamnet.solutions.reportinator.export.ExporterException;

import java.io.OutputStream;

/**
 * Class that exports a report to pdf format
 *
 * @author Bogdan.Iancu
 * @author Bogdan.Stefan
 * @version 1.0 Date: 20-Feb-15
 */
public class PdfExporter implements Exporter<JasperPrint> {

    /**
     * Converts a JasperPrint to pdf format
     *
     * @param inputSource  the JasperPrint object to be exported
     * @param outputSource An output source
     */
    @Override
    public void export(JasperPrint inputSource, OutputStream outputSource) throws ExporterException {
        if (inputSource == null || outputSource == null) {
            throw new ExporterException("input and output should not be null");
        }
        try {
            JasperExportManager.exportReportToPdfStream(inputSource, outputSource);
        } catch (JRException e) {
            throw new ExporterException("Exception exporting report: " + e.getMessage(), e.getCause());
        }
    }
}
