package ro.teamnet.solutions.reportinator.load.jasper;

import net.sf.jasperreports.engine.JRReport;
import org.springframework.core.io.Resource;
import ro.teamnet.solutions.reportinator.load.LoaderException;

import java.io.File;
import java.io.InputStream;

/**
 * Acts as a facade for the Loader implemented classes , it's similar to JrxmlLoader class.
 *
 * @author Andrei.Marica
 * @version 1.0 Date: 2/11/2015
 */
public class DesignLoader {

    /**
     * TODO Doc
     *
     * @param file
     * @return
     * @throws LoaderException If loading the template from a {@code File} fails.
     */
    public static JRReport load(File file) throws LoaderException {
        return new FileLoader().load(file);
    }

    /**
     * TODO Doc
     *
     * @param inputStream
     * @return
     * @throws LoaderException If loading the template from an {@code InputStream} fails.
     */
    public static JRReport load(InputStream inputStream) throws LoaderException {
        return new InputStreamLoader().load(inputStream);
    }

    /**
     * TODO Doc
     *
     * @param resource
     * @return
     * @throws LoaderException If loading the template from a {@code Resource} fails.
     */
    public static JRReport load(Resource resource) throws LoaderException {
        return new ClassPathLoader().load(resource);
    }
}
