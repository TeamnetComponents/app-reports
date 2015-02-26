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
import org.springframework.core.io.ClassPathResource;
import ro.teamnet.solutions.reportinator.config.JasperConstantsTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

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
    public void testShouldPassWhenLoadingWihValidClassPath() throws Exception {
        JRReport report = JasperDesignLoader.load(new ClassPathResource("jasper_test_blank_portrait_template.jrxml"));
        assertNotNull("Report should not be null", report);
        assertEquals("The generated report should be a JasperDesign", JasperDesign.class, report.getClass());
    }

    @Test(expected = LoaderException.class)
    public void testShouldLoadDefaultWithInvalidClassPathAsInput() throws Exception {
        JRReport report = JasperDesignLoader.load(new ClassPathResource(PATH_TO_JRXML_FILE));
        assertNull("Report should be null", report);
    }

    @Test(expected = LoaderException.class)
    public void testShouldLoadDefaultWithNullClassPathAsInput() throws Exception {
        ClassPathResource classPathResource = null;
        JRReport report = JasperDesignLoader.load(classPathResource);
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
    public void testShouldPassWhenLoadingWithAValidInputStream() throws Exception {
        JRReport report = JasperDesignLoader.load(new FileInputStream(PATH_TO_JRXML_FILE));
        assertNotNull("Report should not be null", report);
        assertEquals("The generated report should be a JasperDesign", JasperDesign.class, report.getClass());
    }

}
