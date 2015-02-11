package ro.teamnet.solutions.reportinator.load;

import net.sf.jasperreports.engine.JRReport;
import org.springframework.core.io.Resource;
import ro.teamnet.solutions.reportinator.load.jasper.ClassPathLoader;
import ro.teamnet.solutions.reportinator.load.jasper.FileLoader;
import ro.teamnet.solutions.reportinator.load.jasper.InputStreamLoader;
import ro.teamnet.solutions.reportinator.load.utils.LoaderException;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Andrei.Marica on 2/11/2015.
 *
 * Acts as a facade for the Loader implemented classes , it's similar to JrxmlLoader class.
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
