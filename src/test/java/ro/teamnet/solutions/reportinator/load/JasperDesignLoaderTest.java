/*
 * Copyright (c) 2015 Teamnet S.A. All Rights Reserved.
 *
 * This source file may not be copied, modified or redistributed,
 * in whole or in part, in any form or for any reason, without the express
 * written consent of Teamnet S.A.
 */

package ro.teamnet.solutions.reportinator.load;

import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.junit.Test;
import ro.teamnet.solutions.reportinator.config.JasperConstantsTest;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Contract and minimal tests for the {@link ro.teamnet.solutions.reportinator.load.JasperDesignLoader} class
 *
 * @author Andrei.Marica
 * @version 1.0 Date: 2/17/2015
 */
public class JasperDesignLoaderTest {

    /**
     * Path to a JRXml file that contains valid data for the Loader
     */
    private static final String PATH_TO_JRXML_FILE = JasperConstantsTest.JRXML_BLANK_PORTRAIT_TEMPLATE_PATH;


    @Test(expected = LoaderException.class)
    public void testShouldFailWhenLoadingWihNullFile() throws Exception {
        File file = null;
        JRReport report = JasperDesignLoader.load(file);
        assertNull("Report should be null", report);
    }

    @Test(expected = LoaderException.class)
    public void testShouldFailWhenLoadingWihNullInputStream() throws Exception {
        InputStream inputStream = null;
        JRReport report = JasperDesignLoader.load(inputStream);
        assertNull("Report should be null", report);
    }

    @Test
    public void testShouldPassWhenLoadingWithAValidFile() throws Exception {
        JRReport report = JasperDesignLoader.load(new File(PATH_TO_JRXML_FILE));
        assertNotNull("Report should not be null", report);
        assertEquals("the loaded report should be a JasperDesign", JasperDesign.class, JasperDesignLoader.load(new File(PATH_TO_JRXML_FILE)).getClass());
    }

    @Test(expected = LoaderException.class)
    public void testShouldFailWhenLoadingWithAInvalidFile() throws Exception {
        JRReport report = JasperDesignLoader.load(new File("G:\\reportinator\\src\\test\\resources\\Silhouette_Landscape_No_detail_band.someExtension"));
        assertNull("Report should be null", report);
    }

    @Test
    public void testShouldPassWhenLoadingWithAValidPath() throws Exception {
        JRReport report = JasperDesignLoader.load(new File(JasperConstantsTest.JRXML_BLANK_LANDSCAPE_TEMPLATE_PATH).toPath());
        assertNotNull("Report should not be null", report);
        assertEquals("The generated report should be a JasperDesign", JasperDesign.class, report.getClass());
    }

    @Test(expected = LoaderException.class)
    public void testShouldPassWhenLoadingWithInvalidPath() throws Exception {
        JRReport report = JasperDesignLoader.load(new File("/a/b/c").toPath());
        assertNotNull("Report should not be null", report);
        assertEquals("The generated report should be a JasperDesign", JasperDesign.class, report.getClass());
    }

    @Test
    public void testShouldPassWhenLoadingWithAValidFileName() throws Exception {
        JasperDesignLoader jasperDesignLoader = new JasperDesignLoader();
        JRReport report = jasperDesignLoader.load("default_template.jrxml");
        assertNotNull("Report should not be null", report);
        assertEquals("The generated report should be a JasperDesign", JasperDesign.class, report.getClass());
    }

    @Test(expected = LoaderException.class)
    public void testShouldPassWhenLoadingWithInvalidFileName() throws Exception {
        JRReport report = JasperDesignLoader.load("invalid_file_name");
        assertNull("Report should not be null", report);
    }

    @Test
    public void testShouldPassWhenLoadingWithValidURI() throws Exception {
        JRReport report = JasperDesignLoader.load(new File(JasperConstantsTest.JRXML_BLANK_LANDSCAPE_TEMPLATE_PATH).toURI());
        assertNotNull("Report should not be null", report);
        assertEquals("The generated report should be a JasperDesign", JasperDesign.class, report.getClass());
    }

    @Test(expected = LoaderException.class)
    public void testShouldPassWhenLoadingWithInvalidURI() throws Exception {
        JRReport report = JasperDesignLoader.load(new URI("a/b/c"));
        assertNull("Report should be null", report);
    }

    @Test
    public void testShouldPassWhenLoadingWithValidURL() throws Exception {
        JRReport report = JasperDesignLoader.load(new URL("file:" + JasperConstantsTest.JRXML_BLANK_LANDSCAPE_TEMPLATE_PATH));
        assertNotNull("Report should not be null", report);
        assertEquals("The generated report should be a JasperDesign", JasperDesign.class, report.getClass());
    }

    @Test(expected = LoaderException.class)
    public void testShouldPassWhenLoadingWithInvalidURL() throws Exception {
        JRReport report = JasperDesignLoader.load(new URL("file:a/b/c"));
        assertNull("Report should  be null", report);
    }

}
