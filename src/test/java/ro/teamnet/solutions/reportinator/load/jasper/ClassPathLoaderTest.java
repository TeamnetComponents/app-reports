package ro.teamnet.solutions.reportinator.load.jasper;

import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import ro.teamnet.solutions.reportinator.load.LoaderException;

import static junit.framework.Assert.*;

/**
 * TODO DOC
 */
public class ClassPathLoaderTest {

    /**
     * TODO DOC
     */
    private static final String PATH_TO_XML_FILE = "G:\\reportinator\\src\\test\\resources\\Silhouette_Landscape_No_detail_band.jrxml";
    private ClassPathLoader classPathLoader;

    /**
     * TODO DOC
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        classPathLoader = new ClassPathLoader();
    }

    /**
     * TODO DOC
     *
     * @throws Exception
     */
    @Test
    public void testLoadWithReadableClassPath() throws Exception {
        JRReport report = classPathLoader.load(new ClassPathResource("Silhouette_Landscape_No_detail_band.jrxml"));
        assertNotNull(report);
        assertEquals(JasperDesign.class, report.getClass());
    }

    /**
     * TODO DOC
     *
     * @throws Exception
     */
    @Test
    public void testLoadWithUnreadableClassPath() throws Exception {
        JRReport report = classPathLoader.load(new ClassPathResource(PATH_TO_XML_FILE));
        assertNotNull(report);
        assertEquals(JasperDesign.class, report.getClass());
    }

    @Test(expected = LoaderException.class)
    public void testShouldFailWhenLoadingClassPathResourceAsEmptyInputStream() throws Exception {
        JRReport report = classPathLoader.load(new ClassPathResource("random_empty_template.jrxml"));
        assertNull(report);
    }

    @Test
    public void testLoadWithClassPathResourceNull() throws Exception {
        JRReport report = classPathLoader.load(null);
        assertNotNull(report);
        assertEquals(JasperDesign.class, report.getClass());
    }

    @Test
    public void testCreateDefaultResource() throws Exception {
        assertNotNull(classPathLoader.createDefaultResource());
        assertEquals(ClassPathResource.class, classPathLoader.createDefaultResource().getClass());
    }
}