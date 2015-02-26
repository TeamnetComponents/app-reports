/*
 * Copyright (c) 2015 Teamnet S.A. All Rights Reserved.
 *
 * This source file may not be copied, modified or redistributed,
 * in whole or in part, in any form or for any reason, without the express
 * written consent of Teamnet S.A.
 */

package ro.teamnet.solutions.reportinator.load.jasper;


import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.junit.Before;
import org.junit.Test;
import ro.teamnet.solutions.reportinator.config.JasperConstantsTest;
import ro.teamnet.solutions.reportinator.load.LoaderException;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Contract and minimal tests for the {@link ro.teamnet.solutions.reportinator.load.jasper.FileLoader} class
 *
 * @author Andrei.Marica
 * @version 1.0 Date: 2/17/2015
 */
public class FileLoaderTest {


    /**
     * Path to a JRXml file that contains valid data for the Loader
     */
    private static final String PATH_TO_JRXML_FILE = JasperConstantsTest.JRXML_BLANK_PORTRAIT_TEMPLATE_PATH;
    /**
     * Path to a Non-JRXml file that contains invalid data and has improper extension for the Loader
     */
    private static final String PATH_TO_NON_JRXML_FILE = "G:\\reportinator\\src\\test\\resources\\Silhouette_Landscape_No_detail_band.someExtension";
    /**
     * Object to be tested
     */
    private FileLoader fileLoader;


    /**
     * Creating the object to test
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        fileLoader = new FileLoader();
    }

    @Test
    public void testShouldLoadIfFileHasAJrxmlExtension() throws Exception {
        JRReport report = fileLoader.load(new File(PATH_TO_JRXML_FILE));
        assertNotNull("Report should not be null", report);
        assertEquals("The loaded report should be a JasperDesign", JasperDesign.class, report.getClass());

    }

    @Test(expected = LoaderException.class)
    public void testShouldFailWhenLoadingAFileThatDoesNotExist() throws Exception {
        JRReport report = fileLoader.load(new File("path_to_non_existent_file"));
        assertNull("Report should be null", report);

    }

    @Test(expected = LoaderException.class)
    public void testShouldFailWhenLoadingAFileThatIsEmpty() throws Exception {
        JRReport report = fileLoader.load(new File("G:\\reportinator\\src\\test\\resources\\jasper_invalid_empty_template.jrxml"));
        assertNull("Report should be null", report);
    }

    @Test(expected = LoaderException.class)
    public void testShouldFailWhenLoadingAFileThatIsNull() throws Exception {
        JRReport report = fileLoader.load(null);
        assertNull("Report should be null", report);
    }

    @Test(expected = LoaderException.class)
    public void testShouldFailWhenLoadingAFileThatHasNotAJrxmlExtension() throws Exception {
        JRReport report = fileLoader.load(new File(PATH_TO_NON_JRXML_FILE));
        assertNull("Report should be null", report);
    }

    @Test
    public void testShouldFailWhenCheckingIfJrxmlWithNonJrxmlFile() throws Exception {
        String pathToNonJrxmlFile = PATH_TO_NON_JRXML_FILE;
        assertEquals("File extension should differ from .jrxml", false, fileLoader.checkIfJrxml(new File(pathToNonJrxmlFile)));
    }

    @Test
    public void testShouldPassWhenCheckingIfJrxmlWithJrxmlFile() throws Exception {
        String pathToJrxmlFile = PATH_TO_JRXML_FILE;
        assertEquals("File extension should be .jrxml", true, fileLoader.checkIfJrxml(new File(pathToJrxmlFile)));
    }
}
