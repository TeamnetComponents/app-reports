package ro.teamnet.solutions.reportinator.load.jasper;

import net.sf.jasperreports.engine.JRReport;
import org.springframework.core.io.Resource;
import ro.teamnet.solutions.reportinator.load.Loader;
import ro.teamnet.solutions.reportinator.load.LoaderException;

import java.io.IOException;


/**
 * A class that implements {@link ro.teamnet.solutions.reportinator.load.Loader} interface by overriding the inherited
 * method to load a {@link org.springframework.core.io.Resource} into a {@link net.sf.jasperreports.engine.JRReport},
 * using the {@code sourceFile}'s {@link java.io.InputStream} sent to the
 * {@link ro.teamnet.solutions.reportinator.load.jasper.InputStreamLoader} load method.
 *
 * @author Andrei.Marica
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/11/2015
 */
public final class ClassPathLoader implements Loader<Resource, JRReport> {

    /**
     * Method to load a Resource as a {@link net.sf.jasperreports.engine.JRReport}.
     *
     * @param resource Needed to send the InputStream to InputStreamLoader.
     * @return A JRReport based on Resource's  InputStream given to the InputStreamLoader.
     * @throws LoaderException if the {@code resource} is {@code null} or unreadable.
     */
    @Override
    public JRReport load(final Resource resource) throws LoaderException { // TODO Review this code and devise another method for loading from classpath; this code is coupled to Spring Framework's Resource class
        if (resource == null || !resource.isReadable()) {
            throw new LoaderException("Could not load given Resource type into a JasperDesign: " +
                    "Parameter is either null or Resource is unreadable.");
        }
        JRReport jasperDesign;
        try {
            jasperDesign = new InputStreamLoader().load(resource.getInputStream());
        } catch (IOException e) {
            throw new LoaderException("Could not load  " + resource.getClass().getCanonicalName() + " to a JRReport.", e.getCause());
        }
        return jasperDesign;
    }

}
