/*
 * Copyright (c) 2015 Teamnet S.A. All Rights Reserved.
 *
 * This source file may not be copied, modified or redistributed,
 * in whole or in part, in any form or for any reason, without the express
 * written consent of Teamnet S.A.
 */

package ro.teamnet.solutions.reportinator.export;

import net.sf.jasperreports.engine.JasperPrint;
import ro.teamnet.solutions.reportinator.export.jasper.type.ExportType;
import ro.teamnet.solutions.reportinator.generation.ReportGenerator;

import java.io.OutputStream;

/**
 * Class that exports a report to the given format
 *
 * @author Bogdan.Stefan
 * @author Bogdan.Iancu
 * @version 1.0 Date: 20-Feb-15
 */


public class JasperReportExporter {

    /**
     * Converts a JasperPrint object to the desired format
     *
     * @param inputSource  the JasperPrint object to be exported
     * @param outputSource An output source
     * @param type         desired format
     */
    public static void export(JasperPrint inputSource, OutputStream outputSource, ExportType type) throws ExporterException {
        if (type == null) {
            throw new ExporterException("Export type must not be null");
        }
        try {
            type.getExporter().export(inputSource, outputSource);
        } catch (ExporterException e) {
            throw new ExporterException("Exception exporting report", e.getCause());
        }
    }

    /**
     * A method which exports to a custom format, using a {@link ro.teamnet.solutions.reportinator.generation.ReportGenerator}
     * implementation.
     *
     * @param printGenerator The generator of the report print.
     * @param outputSource   An output source
     * @param type           desired format
     * @see ro.teamnet.solutions.reportinator.generation.JasperReportGenerator JasperReportGenerator
     */
    public static void export(ReportGenerator<JasperPrint> printGenerator, OutputStream outputSource, ExportType type) throws ExporterException {
        if (printGenerator == null) {
            throw new ExporterException("Print generator must not be null.");
        }
        export(printGenerator.generate(), outputSource, type);
    }
}
