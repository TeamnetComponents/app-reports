package ro.teamnet.solutions.reportinator.load.jasper;


import net.sf.jasperreports.engine.design.JasperDesign;
import org.junit.Before;
import org.junit.Test;
import ro.teamnet.solutions.reportinator.load.LoaderException;

import java.io.FileInputStream;
import java.io.InputStream;

import static org.easymock.EasyMock.*;

/**
 * TODO DOC
 */
public class InputStreamLoaderTest {

    /**
     * TODO DOC FOR VARIABLES
     */
    private static final String PATH_TO_XML_FILE = "G:\\reportinator\\src\\test\\resources\\Silhouette_Landscape_No_detail_band.jrxml";
    private InputStreamLoader inputStreamLoader;
    private InputStreamLoader inputStreamLoaderMock;
    private InputStream is;


    /**
     * TODO DOC
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        inputStreamLoader = new InputStreamLoader();
    }

    /**
     * TODO DOC
     *
     * @throws Exception
     */
    @Test
    public void testLoadWithValidInputStream() throws Exception {
        inputStreamLoaderMock = createMock(InputStreamLoader.class);
        is = new FileInputStream(PATH_TO_XML_FILE);
        expect(inputStreamLoaderMock.load(new FileInputStream(PATH_TO_XML_FILE)))
                .andReturn(anyObject(JasperDesign.class));
        replay();

        try {
            inputStreamLoader.load(is);
        } finally {
            verify();
        }

    }

    /**
     * TODO DOC
     *
     * @throws Exception
     */
    @Test(expected = LoaderException.class)
    public void testLoadWithNullInputStream() throws Exception {
        inputStreamLoaderMock = createMock(InputStreamLoader.class);
        expect(inputStreamLoaderMock.load(null)).andThrow(new LoaderException());
        replay();
        try {
            inputStreamLoader.load(is);
        } finally {
            verify();
        }

    }
}