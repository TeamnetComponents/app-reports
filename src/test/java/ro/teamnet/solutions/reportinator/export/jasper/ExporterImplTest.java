package ro.teamnet.solutions.reportinator.export.jasper;

import net.sf.jasperreports.engine.JasperPrint;
import org.junit.Before;
import org.junit.Test;
import ro.teamnet.solutions.reportinator.export.ExportType;

import java.io.OutputStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ExporterImplTest {

    private JasperPrint jasperPrint;
    private OutputStream outputStream;
    private ExporterImpl exporter;

    @Before
    public void setUp() throws Exception {
        jasperPrint = mock(JasperPrint.class);
        outputStream = mock(OutputStream.class);
        exporter = new ExporterImpl();
    }

    @Test
    public void verifyThatPdfExporterIsUsed(){
        JasperPdfExporter pdfExporter = mock(JasperPdfExporter.class);

        exporter.export(jasperPrint,outputStream, ExportType.PDF);
        verify(pdfExporter, times(1)).export(jasperPrint,outputStream);
    }

}