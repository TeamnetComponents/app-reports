package ro.teamnet.solutions.reportinator.export.jasper.type;

import net.sf.jasperreports.engine.JasperPrint;
import ro.teamnet.solutions.reportinator.export.Exporter;
import ro.teamnet.solutions.reportinator.export.jasper.HtmlExporter;
import ro.teamnet.solutions.reportinator.export.jasper.PdfExporter;
import ro.teamnet.solutions.reportinator.export.jasper.XlsExporter;

/**
 * Enum that returns an instance of the exporter class of the desired type
 * Created by Bogdan.Iancu on 13-Feb-15.
 */
public enum ExportType {
    PDF{
        public Exporter<JasperPrint> getExporter(){
            return new PdfExporter();
        }
    },
    XLS{
        public Exporter<JasperPrint> getExporter(){
            return new XlsExporter();
        }
    },
    HTML{
        public Exporter<JasperPrint> getExporter(){
            return new HtmlExporter();
        }
    };

    public abstract Exporter<JasperPrint> getExporter();
}
