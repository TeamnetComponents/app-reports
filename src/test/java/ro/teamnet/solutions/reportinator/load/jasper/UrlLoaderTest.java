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
import org.junit.Test;
import ro.teamnet.solutions.reportinator.config.JasperConstantsTest;
import ro.teamnet.solutions.reportinator.load.LoaderException;

import java.net.URL;

import static org.junit.Assert.*;

/**
 * Contract and minimal tests for the {@link UrlLoader} class
 *
 * @author Andrei.Marica
 * @version 1.0 Date: 2/17/2015
 */
public class UrlLoaderTest {

    @Test
    public void testShouldPassWhenLoadingAValidURL() throws Exception {
        URL url = new URL("file:" + JasperConstantsTest.JRXML_BLANK_LANDSCAPE_TEMPLATE_PATH);
        UrlLoader urlLoader = new UrlLoader();
        JRReport design = urlLoader.load(url);
        assertNotNull("The resulted report should not be null", design);
        assertEquals("The resulted report should be a JasperDesign", JasperDesign.class, design.getClass());
    }

    @Test(expected = NullPointerException.class)
    public void testShouldPassIfExceptionIsThrownWhenLoadingANullURL() throws Exception {
        JRReport design = new UrlLoader().load(null);
        assertNull("The resulted report should be null", design);
    }

    @Test(expected = LoaderException.class)
    public void testShouldPassWhenThrowingExceptionIfLoadingAnURLToNonexistentFile() throws Exception {
        JRReport design = new UrlLoader().load(new URL("file:" + JasperConstantsTest.JRXML_BLANK_LANDSCAPE_TEMPLATE_PATH + "a"));
        assertNull("The resulted report should be null", design);
    }


}