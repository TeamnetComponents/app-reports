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

    public JRReport load(File file) throws LoaderException {
        return new FileLoader().load(file);
    }

    public JRReport load(InputStream inputStream) throws LoaderException {
        return new InputStreamLoader().load(inputStream);
    }

    public JRReport load(Resource resource) throws LoaderException {
        return new ClassPathLoader().load(resource);
    }
}
