package ro.teamnet.solutions.reportinator.load.jasper;

import net.sf.jasperreports.engine.JRReport;
import org.springframework.core.io.Resource;
import ro.teamnet.solutions.reportinator.load.Loader;
import ro.teamnet.solutions.reportinator.load.LoaderException;

import java.io.IOException;


/**
 * A class that implements {@link ro.teamnet.solutions.reportinator.load.Loader} interface by overriding the inherited method
 * to load a {@link org.springframework.core.io.Resource} into a {@link net.sf.jasperreports.engine.JRReport}, using the {@code sourceFile}'s
 * {@link java.io.InputStream} sent to the {@link ro.teamnet.solutions.reportinator.load.jasper.InputStreamLoader} load method.
 *
 * @author Andrei.Marica
 * @version 1.0 Date: 2/11/2015
 */
public final class ClassPathLoader implements Loader<Resource, JRReport> {

    /**
     * Method to load a Resource to a JRReport by sending it's InputStream to InputStreamLoaders load method ;
     *
     * @param sourceFile needed to send the InputStream to InputStreamLoader
     * @return an JRReport based on Resource's  InputStream given to the InputStreamLoader
     * @throws LoaderException if the {@code sourceFile} is {@code null} or {@code} unreadable
     */
    @Override
    public JRReport load(final Resource sourceFile) throws LoaderException {


        if (sourceFile == null || !sourceFile.isReadable()) {
            throw new LoaderException("Could not load given resource of Resource type into a JasperDesign :" +
                    " Resource is either null or unreadable.");
        }

        JRReport jasperDesign;
        try {
            jasperDesign = new InputStreamLoader().load(sourceFile.getInputStream());
        } catch (IOException e) {
            throw new LoaderException("Could not load  " + sourceFile.getClass().getCanonicalName() + " to a JRReport.", e.getCause());
        }
        return jasperDesign;
    }

}
