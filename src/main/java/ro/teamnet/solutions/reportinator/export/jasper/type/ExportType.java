/*
 * Copyright (c) 2015 Teamnet S.A. All Rights Reserved.
 *
 * This source file may not be copied, modified or redistributed,
 * in whole or in part, in any form or for any reason, without the express
 * written consent of Teamnet S.A.
 */

package ro.teamnet.solutions.reportinator.export.jasper.type;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperPrint;
import ro.teamnet.solutions.reportinator.export.Exporter;
import ro.teamnet.solutions.reportinator.export.jasper.HtmlExporter;
import ro.teamnet.solutions.reportinator.export.jasper.PdfExporter;
import ro.teamnet.solutions.reportinator.export.jasper.XlsExporter;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum that returns an instance of the exporter class of the desired type.
 *
 * @author Bogdan.Iancu
 * @version 1.0.1 Date: 2015-03-20
 * @since 1.0 Date: 2015-02-06
 */
public enum ExportType {

    PDF {
        public Exporter<JasperPrint> getExporter() {
            return new PdfExporter();
        }
    },
    XLS {
        {
            getParameters().put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
        }
        public Exporter<JasperPrint> getExporter() {
            return new XlsExporter();
        }
    },
    HTML {
        {
            getParameters().put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
        }
        public Exporter<JasperPrint> getExporter() {
            return new HtmlExporter();
        }
    };
    /**
     * Parameters required by the print generator to give the jasper print extra properties
     * for each type, if needed
     */
    private Map<String, Object> parameters = new HashMap<String, Object>();
    public abstract Exporter<JasperPrint> getExporter();
    public Map<String, Object> getParameters(){
        return parameters;
    }
}
