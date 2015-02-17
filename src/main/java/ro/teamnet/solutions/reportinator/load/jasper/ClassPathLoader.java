package ro.teamnet.solutions.reportinator.load.jasper;

import net.sf.jasperreports.engine.JRReport;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ro.teamnet.solutions.reportinator.config.JasperConstants;
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

        Resource auxiliarResource = sourceFile;

        if (auxiliarResource == null || !sourceFile.isReadable()) {
            System.out.println("Source File is not readable or null , it will be set as a default resource"); //checks if the sourceFile is readable (if it can be read by .getInputStream() )
            auxiliarResource = createDefaultResource();  //if the sourceFile is unreadable , our method sets the resource to a default path

        }

        JRReport jasperDesign;
        try {
            jasperDesign = new InputStreamLoader().load(auxiliarResource.getInputStream());
        } catch (IOException e) {
            throw new LoaderException("Could not load  " + sourceFile.getClass().getCanonicalName() + " to a JRReport", e);
        }
        return jasperDesign;
    }

    /**
     * Helper method that returns a new Resource , using the DEFAULT_PATH, when the given Resource
     * is either null or unreadable
     */
    public Resource createDefaultResource() {
        return new ClassPathResource(JasperConstants.JASPER_TEST_TEMPLATE_RESOURCE_PATH);

    }

}
