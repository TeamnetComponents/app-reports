package ro.teamnet.solutions.reportinator.load.jasper;


import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.junit.Before;
import org.junit.Test;
import ro.teamnet.solutions.reportinator.load.LoaderException;

import java.io.File;

import static junit.framework.Assert.*;

/**
 * TODO DOC
 */
public class FileLoaderTest {


    /**
     * TODO DOC
     */
    private static final String PATH_TO_XML_FILE = "G:\\reportinator\\src\\test\\resources\\Silhouette_Landscape_No_detail_band.jrxml";
    private static final String PATH_TO_NON_XML_FILE = "G:\\reportinator\\src\\test\\resources\\Silhouette_Landscape_No_detail_band.someExtension";
    private FileLoader fileLoader;

    /**
     * TODO DOC
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        fileLoader = new FileLoader();
    }

    /**
     * TODO DOC
     *
     * @throws Exception
     */
    @Test
    public void testLoadIfFileHasAJrxmlExtension() throws Exception {
        JRReport report = fileLoader.load(new File(PATH_TO_XML_FILE));
        assertNotNull(report);
        assertEquals(JasperDesign.class, report.getClass());

    }

    /**
     * TODO DOC
     *
     * @throws Exception
     */
    @Test
    public void testLoadIfFileDoesNotExist() throws Exception {
        JRReport report = fileLoader.load(new File(PATH_TO_XML_FILE));
        assertNotNull(report);
        assertEquals(JasperDesign.class, report.getClass());

    }

    /**
     * TODO DOC
     *
     * @throws Exception
     */
    @Test(expected = LoaderException.class)
    public void testLoadIfFileIsEmpty() throws Exception {
        JRReport report = fileLoader.load(new File("G:\\reportinator\\src\\test\\resources\\random_empty_template.jrxml"));
        assertNull(report);
        //TODO evaluate statement for an empty file


    }

    /**
     * TODO DOC
     *
     * @throws Exception
     */
    @Test(expected = LoaderException.class)
    public void testLoadIfFileIsNull() throws Exception {
        JRReport report = fileLoader.load(null);
        assertNull(report);


    }

    /**
     * TODO DOC
     *
     * @throws Exception
     */
    @Test(expected = LoaderException.class)
    public void testLoadIfFileHasNotAJrxmlExtension() throws Exception {
        JRReport report = fileLoader.load(new File(PATH_TO_NON_XML_FILE));
        assertNull(report);
    }

    /**
     * TODO DOC
     *
     * @throws Exception
     */
    @Test
    public void testCheckIfJrxmlWithNonJrxmlFile() throws Exception {
        String pathToNonJrxmlFile = PATH_TO_NON_XML_FILE;
        assertEquals(false, fileLoader.checkIfJrxml(new File(pathToNonJrxmlFile)));
    }

    /**
     * TODO DOC
     *
     * @throws Exception
     */
    @Test
    public void testCheckIfJrxmlWithJrxmlFile() throws Exception {
        String pathToJrxmlFile = PATH_TO_XML_FILE;
        assertEquals(true, fileLoader.checkIfJrxml(new File(pathToJrxmlFile)));
    }
}