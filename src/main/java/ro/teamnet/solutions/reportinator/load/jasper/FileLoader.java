package ro.teamnet.solutions.reportinator.load.jasper;

import net.sf.jasperreports.engine.JRReport;
import ro.teamnet.solutions.reportinator.load.Loader;
import ro.teamnet.solutions.reportinator.load.LoaderException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Andrei.Marica on 2/10/2015.
 *
 */
public final class FileLoader implements Loader<File,JRReport> {

    private final String FILE_EXTENSION = ".jrxml"; // Se poate scoate nu ? pentru ca verificarea se poate realiza
                                                    // si in respectiva metoda fara sa initializez variabila asta
    /**
     *Method to load an File in a JRReport using its InputStream
     * @param loadSource needed to send its InputStream to the InputStreamLoader
     * @return a JRReport based on the given InputStream
     * @throws LoaderException if InputStreamLoader doesn't work properly
     */
    @Override
    public JRReport load(File loadSource) throws LoaderException {

        JRReport jasperDesign = null;

        if(checkIfJrxml(loadSource)) {
            try {

                jasperDesign = new InputStreamLoader().load(new FileInputStream(loadSource));

            } catch (FileNotFoundException e) {
                throw new LoaderException("Can't load "+ loadSource.getClass().getCanonicalName()
                                            ,e);
            }
            return jasperDesign;
        }
        return jasperDesign;

    }

    /**
     * A method to check if a file is a JRXML , if so the JRXMLLoader can .load it's source
     * @param loadSource to be verified that has a specific extension
     * @return returns a true value if our file has ".jrxml" extension
     */
    public boolean checkIfJrxml(File loadSource){

        String filePath = loadSource.getAbsolutePath();
        String extension = "";  //string that saves the extension
        boolean isXml = true ; //boolean value to return

        int i = filePath.lastIndexOf('.');
        if (i > 0) {
            extension = filePath.substring(i);            //adds the file extension to a String
        }
        if(!extension.equals(FILE_EXTENSION)){  // pot inlocui FILE_EXTENSION direct cu stringul de care am nevoie ".jrxml"
            isXml = false;
            System.out.println("File does not have a "+ FILE_EXTENSION +" extension");
        }
        return isXml;
    }


}
