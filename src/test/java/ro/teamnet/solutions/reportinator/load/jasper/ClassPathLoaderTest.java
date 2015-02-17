package ro.teamnet.solutions.reportinator.load.jasper;

import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import ro.teamnet.solutions.reportinator.load.LoaderException;

import static junit.framework.Assert.*;

/**
 * Contract and minimal tests for {@link ro.teamnet.solutions.reportinator.load.jasper.ClassPathLoader} class
 *
 * @author Andrei.Marica
 * @version 1.0 Date: 2/17/2015
 */
public class ClassPathLoaderTest {

    /**
     * Path to a JRXml file that contains valid data for the Loader
     */
    private static final String PATH_TO_JRXML_FILE = "G:\\reportinator\\src\\test\\resources\\Silhouette_Landscape_No_detail_band.jrxml";

    /**
     * Object to be tested
     */
    private ClassPathLoader classPathLoader;


    /**
     * Creating the object to test
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        classPathLoader = new ClassPathLoader();
    }

    @Test
    public void testShouldLoadGivenSourceWithReadableClassPath() throws Exception {
        JRReport report = classPathLoader.load(new ClassPathResource("Silhouette_Landscape_No_detail_band.jrxml"));
        assertNotNull(report);
        assertEquals(JasperDesign.class, report.getClass());
    }


    @Test
    public void testShouldLoadDefaultWithUnreadableClassPath() throws Exception {
        JRReport report = classPathLoader.load(new ClassPathResource(PATH_TO_JRXML_FILE));
        assertNotNull(report);
        assertEquals(JasperDesign.class, report.getClass());
    }

    @Test(expected = LoaderException.class)
    public void testShouldFailWhenLoadingClassPathResourceAsEmptyInputStream() throws Exception {
        JRReport report = classPathLoader.load(new ClassPathResource("random_empty_template.jrxml"));
        assertNull(report);
    }

    @Test
    public void testShouldLoadDefaultWithClassPathResourceNull() throws Exception {
        JRReport report = classPathLoader.load(null);
        assertNotNull(report);
        assertEquals(JasperDesign.class, report.getClass());
    }

    @Test
    public void testShouldCreateDefaultResource() throws Exception {
        assertNotNull(classPathLoader.createDefaultResource());
        assertEquals(ClassPathResource.class, classPathLoader.createDefaultResource().getClass());
    }
}