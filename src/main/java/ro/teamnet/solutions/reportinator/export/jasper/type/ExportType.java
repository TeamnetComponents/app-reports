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
import ro.teamnet.solutions.reportinator.export.jasper.XlsxExporter;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum that returns an instance of the exporter class of the desired type. This class also supports extra export
 * customization, via the underlying {@link #parameters parameters} dictionary (i.e. one might not need pagination when
 * exporting to <em>.XLS/X</em> or <em>.HTML</em> formats. This can be achieved by adding
 * {@link net.sf.jasperreports.engine.JRParameter#IS_IGNORE_PAGINATION} to it via a instance-initialization block - see
 * code).
 *
 * @author Bogdan.Iancu
 * @version 1.0.1 Date: 2015-03-20
 * @since 1.0 Date: 2015-02-20
 */
public enum ExportType {

    PDF {
        public Exporter<JasperPrint> getExporter() {
            return new PdfExporter();
        }
    },
    XLS {
        // Exporting to this format requires some extra parameters
        {
            getParameters().put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
        }
        public Exporter<JasperPrint> getExporter() {
            return new XlsExporter();
        }
    },
    XLSX {
        {
            getParameters().put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
        }
        public Exporter<JasperPrint> getExporter() {
            return new XlsxExporter();
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
     * A dictionary to hold extra parameters.
     *
     * @see #getParameters()
     */
    private final Map<String, Object> parameters = new HashMap<String, Object>();

    /**
     * A factory method which returns an {@link ro.teamnet.solutions.reportinator.export.Exporter} for the chosen type.
     *
     * @return A {@code Exporter} instance
     */
    public abstract Exporter<JasperPrint> getExporter();

    /**
     * Retrieves a dictionary containing extra parameters required by a
     * {@link ro.teamnet.solutions.reportinator.generation.ReportGenerator ReportGenerator},
     * to fine tune the export if needed.
     *
     * @return Dictionary containing extra export parameters, which might be required when exporting to several types.
     *
     * @see ro.teamnet.solutions.reportinator.generation.ReportGenerator#generate(java.util.Map) ReportGenerator.generate(Map)
     */
    public Map<String, Object> getParameters(){
        return this.parameters;
    }
}
