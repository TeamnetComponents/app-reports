package ro.teamnet.solutions.reportinator.config;

import java.io.*;
import java.util.Properties;

/**
 * A class containing various constants, common to the reporting engine.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/10/2015
 */
public class Constants {

//    private Constants() throws IllegalAccessException {
//        throw new IllegalAccessException("Constants class should not be instantiated.");
//    }

    private static final String PROPERTIES_FILE_PATH = "src/test/resources/config.properties";
    /**
     * Maximum allowed table width (in pixels) for an A4 Landscape page format.
     */
    public static final Integer TABLE_MAXIMUM_WIDTH_LANDSCAPE;

    /**
     * Maximum allowed table width (in pixels) for an A4 Portrait page format.
     */
    public static final Integer TABLE_MAXIMUM_WIDTH_PORTRAIT;

    /**
     * Standard Table Border width.
     */
    public static final Float TABLE_BORDER_WIDTH; //usage for styles


    public static final Integer JASPER_MAXIMUM_NUMBER_OF_COLUMNS_FOR_PORTRAIT;

    static {
        Properties properties = new Properties();
        try {
            InputStream input = new FileInputStream(PROPERTIES_FILE_PATH);
            properties.load(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String property;

        property = properties.getProperty("TABLE_MAXIMUM_WIDTH_LANDSCAPE", "792");
        TABLE_MAXIMUM_WIDTH_LANDSCAPE = Integer.valueOf(property);


        property = properties.getProperty("TABLE_MAXIMUM_WIDTH_PORTRAIT", "555");
        TABLE_MAXIMUM_WIDTH_PORTRAIT = Integer.valueOf(property);


        property = properties.getProperty("TABLE_BORDER_WIDTH", "0.7f");
        TABLE_BORDER_WIDTH = Float.valueOf(property);


        property = properties.getProperty("JASPER_MAXIMUM_NUMBER_OF_COLUMNS_FOR_PORTRAIT", "8");
        JASPER_MAXIMUM_NUMBER_OF_COLUMNS_FOR_PORTRAIT = Integer.valueOf(property);



    }



}
