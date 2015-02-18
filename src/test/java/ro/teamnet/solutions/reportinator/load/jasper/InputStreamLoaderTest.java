package ro.teamnet.solutions.reportinator.load.jasper;


import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.junit.Before;
import org.junit.Test;
import ro.teamnet.solutions.reportinator.config.JasperConstantsTest;
import ro.teamnet.solutions.reportinator.load.LoaderException;

import java.io.FileInputStream;

import static junit.framework.Assert.*;

/**
 * Contract and minimal tests for {@link ro.teamnet.solutions.reportinator.load.jasper.InputStreamLoader} class
 *
 * @author Andrei.Marica
 * @version 1.0 Date: 2/17/2015
 */
public class InputStreamLoaderTest {

    /**
     * Path to a JRXml file that contains valid information for the Loader
     */
    private static final String PATH_TO_JRXML_FILE = JasperConstantsTest.JRXML_BLANK_PORTRAIT_TEMPLATE_PATH;
    /**
     * Object to be tested
     */
    private InputStreamLoader inputStreamLoader;


    /**
     * Creating the object to test
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        inputStreamLoader = new InputStreamLoader();
    }

    @Test
    public void testShouldLoadCorrectlyWithValidInputStream() throws Exception {
        JRReport report = inputStreamLoader.load(new FileInputStream(PATH_TO_JRXML_FILE));
        assertNotNull(report);
        assertEquals(JasperDesign.class, report.getClass());
    }

    @Test(expected = LoaderException.class)
    public void testShouldFailWhenLoadingWithNullInputStream() throws Exception {
        JRReport report = inputStreamLoader.load(null);
        assertNull(report);
    }
}
