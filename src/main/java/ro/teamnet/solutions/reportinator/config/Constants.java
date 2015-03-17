/*
 * Copyright (c) 2015 Teamnet S.A. All Rights Reserved.
 *
 * This source file may not be copied, modified or redistributed,
 * in whole or in part, in any form or for any reason, without the express
 * written consent of Teamnet S.A.
 */

package ro.teamnet.solutions.reportinator.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * A class containing various constants, all common to the reporting engines.
 *
 * @author Bogdan.Stefan
 * @author Bogdan.Iancu
 * @version 1.0.1 Date: 2015-03-17
 * @since 1.0 Date: 2015-02-10
 */
public class Constants {

    /**
     * Maximum allowed table width (in pixels) for an A4, Landscape, page format.
     *
     * @see #DEFAULT_TABLE_MAXIMUM_WIDTH_LANDSCAPE
     */
    public static final Integer TABLE_MAXIMUM_WIDTH_LANDSCAPE;

    /**
     * Maximum allowed table width (in pixels) for an A4 Portrait page format.
     *
     * @see #DEFAULT_TABLE_MAXIMUM_WIDTH_PORTRAIT
     */
    public static final Integer TABLE_MAXIMUM_WIDTH_PORTRAIT;

    /**
     * Standard table border width.
     *
     * @see #DEFAULT_TABLE_BORDER_WIDTH
     */
    public static final Float TABLE_BORDER_WIDTH; // Usage for styles

    // Defaults (for above) general properties
    public static final String DEFAULT_TABLE_MAXIMUM_WIDTH_LANDSCAPE = "792";
    public static final String DEFAULT_TABLE_MAXIMUM_WIDTH_PORTRAIT = "555";
    public static final String DEFAULT_TABLE_BORDER_WIDTH = "0.7f";

    // Initialize some properties from disk configuration file
    /**
     * Path to a disk file where configuration properties can be loaded from.
     */
    private static final String CONFIGURATION_PROPERTIES_FILE_PATH = "src/test/resources/reportinator-config.properties";

    /**
     * A dictionary containing configuration properties loaded from a disk {@code .properties} file.
     *
     * @see ro.teamnet.solutions.reportinator.config.Constants#CONFIGURATION_PROPERTIES_FILE_PATH
     */
    protected static final Properties CONFIGURATION_PROPERTIES = loadProperties(CONFIGURATION_PROPERTIES_FILE_PATH);

    static {
        // A holder reference
        String property;
        // Do we have external configuration properties?
        if (CONFIGURATION_PROPERTIES != null) {
            property = CONFIGURATION_PROPERTIES.getProperty("TABLE_MAXIMUM_WIDTH_LANDSCAPE", DEFAULT_TABLE_MAXIMUM_WIDTH_LANDSCAPE);
            TABLE_MAXIMUM_WIDTH_LANDSCAPE = Integer.valueOf(property);
            property = CONFIGURATION_PROPERTIES.getProperty("TABLE_MAXIMUM_WIDTH_PORTRAIT", DEFAULT_TABLE_MAXIMUM_WIDTH_PORTRAIT);
            TABLE_MAXIMUM_WIDTH_PORTRAIT = Integer.valueOf(property);
            property = CONFIGURATION_PROPERTIES.getProperty("TABLE_BORDER_WIDTH", DEFAULT_TABLE_BORDER_WIDTH);
            TABLE_BORDER_WIDTH = Float.valueOf(property);
        } else {
            TABLE_MAXIMUM_WIDTH_LANDSCAPE = Integer.valueOf(DEFAULT_TABLE_MAXIMUM_WIDTH_LANDSCAPE);
            TABLE_MAXIMUM_WIDTH_PORTRAIT = Integer.valueOf(DEFAULT_TABLE_MAXIMUM_WIDTH_PORTRAIT);
            TABLE_BORDER_WIDTH = Float.valueOf(DEFAULT_TABLE_BORDER_WIDTH);
        }
    }

    /**
     * A helper method which uses a {@link java.io.FileInputStream} to load settings from a properties configuration
     * file, given a path to it.
     *
     * @param pathToPropertiesFile The path to the disk configuration properties file.
     * @return A dictionary containing information about different settings.
     */
    protected static Properties loadProperties(String pathToPropertiesFile) {
        Properties properties = new Properties();
        File file = new File(pathToPropertiesFile);
        if (file.exists()) {
            try (InputStream fileInputStream = new FileInputStream(file)) {
                properties.load(fileInputStream);
            } catch (IOException e) {
                // Re-throw
                throw new RuntimeException(e.getMessage(), e.getCause());
            }
        } else {
            properties = null;
        }
        return properties;
    }

}
