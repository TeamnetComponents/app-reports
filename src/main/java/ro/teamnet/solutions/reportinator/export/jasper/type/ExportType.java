/*
 * Copyright (c) 2015 Teamnet S.A. All Rights Reserved.
 *
 * This source file may not be copied, modified or redistributed,
 * in whole or in part, in any form or for any reason, without the express
 * written consent of Teamnet S.A.
 */

package ro.teamnet.solutions.reportinator.export.jasper.type;

import net.sf.jasperreports.engine.JasperPrint;
import ro.teamnet.solutions.reportinator.export.Exporter;
import ro.teamnet.solutions.reportinator.export.jasper.HtmlExporter;
import ro.teamnet.solutions.reportinator.export.jasper.PdfExporter;
import ro.teamnet.solutions.reportinator.export.jasper.XlsExporter;

/**
 * Enum that returns an instance of the exporter class of the desired type.
 *
 * @author Bogdan.Iancu
 * @version 1.0 Date: 2/20/2015
 */
public enum ExportType {
    PDF {
        public Exporter<JasperPrint> getExporter() {
            return new PdfExporter();
        }
    },
    XLS {
        public Exporter<JasperPrint> getExporter() {
            return new XlsExporter();
        }
    },
    HTML {
        public Exporter<JasperPrint> getExporter() {
            return new HtmlExporter();
        }
    };

    public abstract Exporter<JasperPrint> getExporter();
}
