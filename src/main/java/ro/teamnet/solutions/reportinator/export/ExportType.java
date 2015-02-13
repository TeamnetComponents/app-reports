package ro.teamnet.solutions.reportinator.export;

import ro.teamnet.solutions.reportinator.export.jasper.JasperHtmlExporter;
import ro.teamnet.solutions.reportinator.export.jasper.JasperPdfExporter;
import ro.teamnet.solutions.reportinator.export.jasper.JasperXlsExporter;

/**
 * Enum that returns an instance of the exporter class of the desired type
 * Created by Bogdan.Iancu on 13-Feb-15.
 */
public enum ExportType {
    PDF{
        public Exporter getExporter(){
            return new JasperPdfExporter();
        }
    },
    XLS{
        public Exporter getExporter(){
            return new JasperXlsExporter();
        }
    },
    HTML{
        public Exporter getExporter(){
            return new JasperHtmlExporter();
        }
    };

    public abstract Exporter getExporter();
}
