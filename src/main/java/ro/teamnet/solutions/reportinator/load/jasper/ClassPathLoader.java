package ro.teamnet.solutions.reportinator.load.jasper;

import net.sf.jasperreports.engine.JRReport;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ro.teamnet.solutions.reportinator.config.JasperConstants;
import ro.teamnet.solutions.reportinator.load.Loader;
import ro.teamnet.solutions.reportinator.load.LoaderException;

import java.io.IOException;


/**
 * Created by Andrei.Marica on 2/11/2015.
 *
 * @author Andrei.Marica
 * @version 1.0 Date: 2/11/2015
 */
public class ClassPathLoader implements Loader<Resource, JRReport> {

    /**
     * Method to load a Resource to a JRReport using its InputStream;
     *
     * @param sourceFile needed to send the InputStream to InputStreamLoader
     * @return an JRReport based on the given InputStream
     * @throws LoaderException if the InputStreamLoader does not execute properly
     */
    @Override
    public JRReport load(final Resource sourceFile) throws LoaderException {

        Resource auxiliarResource = sourceFile;

        if (!sourceFile.isReadable()) {
            System.out.println("Source File is not readable , it will be set as default"); //checks if the sourceFile is readable (if it can be read by .getInputStream() )
            auxiliarResource = createDefaultResource();   //if the sourceFile is unreadable , our method sets the resource to a default path
        }

        JRReport jasperDesign;
        try {
            jasperDesign = new InputStreamLoader().load(auxiliarResource.getInputStream());
        } catch (IOException e) {
            throw new LoaderException("Can't load " + sourceFile.getClass().getCanonicalName(), e);
        }
        return jasperDesign;
    }

    /**
     * Helper method that returns a new Resource using the DEFAULT_PATH
     */
    public Resource createDefaultResource() {
        return new ClassPathResource(JasperConstants.JASPER_DEFAULT_TEMPLATE_RESOURCE_PATH);

    }


}
