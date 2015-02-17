package ro.teamnet.solutions.reportinator.load.jasper;

import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import ro.teamnet.solutions.reportinator.load.LoaderException;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static junit.framework.Assert.*;

/**
 * Contract and minimal tests for the {@link ro.teamnet.solutions.reportinator.load.jasper.DesignLoader} class
 *
 * @author Andrei.Marica
 * @version 1.0 Date: 2/17/2015
 */
public class DesignLoaderTest {

    /**
     * Path to a JRXml file that contains valid data for the Loader
     */
    private static final String PATH_TO_JRXML_FILE = "G:\\reportinator\\src\\test\\resources\\Silhouette_Landscape_No_detail_band.jrxml";

    /**
     * Object to be tested
     */
    private DesignLoader designLoader;


    /**
     * Creating the object to be tested
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        designLoader = new DesignLoader();
    }

    @Test(expected = LoaderException.class)
    public void testShouldFailWhenLoadingWihNullFile() throws Exception {
        File file = null;
        JRReport report = designLoader.load(file);
        assertNull(report);
    }

    @Test(expected = LoaderException.class)
    public void testShouldFailWhenLoadingWihNullInputStream() throws Exception {
        InputStream inputStream = null;
        JRReport report = designLoader.load(inputStream);
        assertNull(report);
    }

    @Test
    public void testShouldPassWhenLoadingWihValidClassPath() throws Exception {
        JRReport report = designLoader.load(new ClassPathResource("Silhouette_Landscape_No_detail_band.jrxml"));
        assertNotNull(report);
        assertEquals(JasperDesign.class, report.getClass());
    }

    @Test(expected = LoaderException.class)
    public void testShouldLoadDefaultWithInvalidClassPathAsInput() throws Exception {
        JRReport report = designLoader.load(new ClassPathResource(PATH_TO_JRXML_FILE));
        assertNull(report);
    }

    @Test(expected = LoaderException.class)
    public void testShouldLoadDefaultWithNullClassPathAsInput() throws Exception {
        ClassPathResource classPathResource = null;
        JRReport report = designLoader.load(classPathResource);
        assertNull(report);
    }


    @Test
    public void testShouldPassWhenLoadingWithAValidFile() throws Exception {
        JRReport report = designLoader.load(new File(PATH_TO_JRXML_FILE));
        assertNotNull(report);
        assertEquals(JasperDesign.class, designLoader.load(new File(PATH_TO_JRXML_FILE)).getClass());
    }

    @Test(expected = LoaderException.class)
    public void testShouldFailWhenLoadingWithAInvalidFile() throws Exception {
        JRReport report = designLoader.load(new File("G:\\reportinator\\src\\test\\resources\\Silhouette_Landscape_No_detail_band.someExtension"));
        assertNull(report);
    }

    @Test
    public void testShouldPassWhenLoadingWithAValidInputStream() throws Exception {
        JRReport report = designLoader.load(new FileInputStream(PATH_TO_JRXML_FILE));
        assertNotNull(report);
        assertEquals(JasperDesign.class, report.getClass());
    }

}