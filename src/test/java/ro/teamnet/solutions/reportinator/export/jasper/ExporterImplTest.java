package ro.teamnet.solutions.reportinator.export.jasper;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ro.teamnet.solutions.reportinator.config.JasperConstants;
import ro.teamnet.solutions.reportinator.export.ExportType;
import ro.teamnet.solutions.reportinator.export.Exporter;
import ro.teamnet.solutions.reportinator.export.ExporterException;
import ro.teamnet.solutions.reportinator.load.jasper.DesignLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ExporterImplTest {

    private JasperPrint reportPrint;
    private Map<String, Object> reportParameters;
    private OutputStream out;


    @Before
    public void setUp() throws Exception {
        reportParameters = new HashMap();
        this.reportPrint = JasperFillManager.fillReport(JasperCompileManager.compileReport((JasperDesign) DesignLoader.load(new File(JasperConstants.JASPER_TEST_TEMPLATE_RESOURCE_PATH))), reportParameters);
        out = new FileOutputStream( "test.pdf");
    }

    @After
    public void tearDown() throws Exception {
        out.close();
        Path path = Paths.get(".\\test.pdf");
        Files.delete(path);
    }

    @Test
    public void testExport() throws Exception {
        ExporterImpl mockExporter = mock(ExporterImpl.class, CALLS_REAL_METHODS);
        mockExporter.export(this.reportPrint, out, ExportType.PDF);
        verify(mockExporter, times(1)).export(this.reportPrint, out,ExportType.PDF);
    }

    @Test(expected = ExporterException.class)
    public void testShouldPassIfParametersAreNull() throws Exception{
        new ExporterImpl().export(null, null, null);
    }

}