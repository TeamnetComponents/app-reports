package ro.teamnet.solutions.reportinator.load.jasper;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import ro.teamnet.solutions.reportinator.load.utils.Loader;
import ro.teamnet.solutions.reportinator.load.utils.LoaderException;

import java.io.InputStream;

/**
 * Created by Andrei.Marica on 2/11/2015.
 *
 */
public class InputStreamLoader implements Loader<InputStream,JRReport> {
    /**
     * A method that loads an InputStream in a JasperDesign
     * @param loadSource given InputStream to be loaded in the JRXMLLoader
     * @return a JRReport based on the given InputStream
     * @throws LoaderException
     */
    @Override
    public JRReport load(InputStream loadSource) throws LoaderException {

        JasperDesign jasperDesign = null;
        if(loadSource!=null) {     // Is this condition enough to pass?
            try {
                jasperDesign = JRXmlLoader.load(loadSource);
            } catch (JRException e) {
               throw new LoaderException("Can't load "+loadSource.getClass().getCanonicalName()
                                        ,e);
            }
        }
        return jasperDesign;
    }



}
