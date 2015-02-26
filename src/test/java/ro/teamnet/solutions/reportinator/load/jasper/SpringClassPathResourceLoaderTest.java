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
import org.springframework.core.io.ClassPathResource;
import ro.teamnet.solutions.reportinator.config.JasperConstantsTest;
import ro.teamnet.solutions.reportinator.load.LoaderException;

import static junit.framework.Assert.*;

/**
 * Contract and minimal tests for {@link SpringClassPathResourceLoader} class
 *
 * @author Andrei.Marica
 * @version 1.0 Date: 2/17/2015
 */
public class SpringClassPathResourceLoaderTest {

    /**
     * Path to a JRXml file that contains valid data for the Loader
     */
    private static final String PATH_TO_JRXML_FILE = JasperConstantsTest.JRXML_BLANK_PORTRAIT_TEMPLATE_PATH;

    /**
     * Object to be tested
     */
    private SpringClassPathResourceLoader springClassPathResourceLoader;


    /**
     * Creating the object to test
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        springClassPathResourceLoader = new SpringClassPathResourceLoader();
    }

    @Test
    public void testShouldLoadGivenSourceWithReadableClassPath() throws Exception {
        JRReport report = springClassPathResourceLoader.load(new ClassPathResource("jasper_test_blank_portrait_template.jrxml"));
        assertNotNull("Report should not be null", report);
        assertEquals("The loaded report should be a JasperDesign", JasperDesign.class, report.getClass());
    }


    @Test(expected = LoaderException.class)
    public void testShouldLoadDefaultWithUnreadableClassPath() throws Exception {
        JRReport report = springClassPathResourceLoader.load(new ClassPathResource(PATH_TO_JRXML_FILE));
        assertNull("Report should be null", report);
    }

    @Test(expected = LoaderException.class)
    public void testShouldFailWhenLoadingClassPathResourceAsEmptyInputStream() throws Exception {
        JRReport report = springClassPathResourceLoader.load(new ClassPathResource(JasperConstantsTest.JRXML_FAKE_TEMPLATE_PATH));
        assertNull("Report should be null", report);
    }

    @Test(expected = LoaderException.class)
    public void testShouldLoadDefaultWithClassPathResourceNull() throws Exception {
        JRReport report = springClassPathResourceLoader.load(null);
        assertNull("Report should be null", report);

    }


}
