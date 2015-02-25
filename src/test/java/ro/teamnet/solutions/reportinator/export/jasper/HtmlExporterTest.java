package ro.teamnet.solutions.reportinator.export.jasper;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ro.teamnet.solutions.reportinator.config.JasperConstantsTest;
import ro.teamnet.solutions.reportinator.export.Exporter;
import ro.teamnet.solutions.reportinator.export.ExporterException;
import ro.teamnet.solutions.reportinator.load.JasperDesignLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

/**
 * @author Bogdan.Iancu
 * @version 1.0 Date: 2/6/2015
 */
public class HtmlExporterTest {
    private JasperPrint reportPrint;
    private Map<String, Object> reportParameters;
    private OutputStream out;


    @Before
    public void setUp() throws Exception {
        reportParameters = new HashMap<String, Object>();
        this.reportPrint = JasperFillManager.fillReport(JasperCompileManager.compileReport((JasperDesign) JasperDesignLoader.load(new File(JasperConstantsTest.JRXML_BLANK_PORTRAIT_TEMPLATE_PATH))), reportParameters);
        out = new FileOutputStream( "test.html");
    }

    @After
    public void tearDown() throws Exception {
        out.close();
        Path path = Paths.get(".\\test.html");
        Files.delete(path);
    }

    @Test
    public void testExport() throws Exception {
        Exporter<JasperPrint> mockExporter = mock(HtmlExporter.class, CALLS_REAL_METHODS);
        mockExporter.export(this.reportPrint, out);
        verify(mockExporter, times(1)).export(this.reportPrint, out);
    }

    @Test(expected = ExporterException.class)
    public void testShouldPassIfParametersAreNull() throws Exception{
        new HtmlExporter().export(null, null);
    }
}
