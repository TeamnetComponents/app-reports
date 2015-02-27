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

import java.io.File;
import java.net.URI;

import static org.junit.Assert.*;

/**
 * Contract and minimal tests for the {@link UriLoader} class
 *
 * @author Andrei.Marica
 * @version 1.0 Date: 2/17/2015
 */
public class UriLoaderTest {


    @Test
    public void testShouldPassWhenLoadingValidURI() throws Exception {
        File file = new File(JasperConstantsTest.JRXML_BLANK_LANDSCAPE_TEMPLATE_PATH);
        JRReport design = new UriLoader().load(URI.create(file.toURI().toString()));
        System.out.println(design.getName() + "-----" + design.getClass().getCanonicalName());
        assertNotNull("The resulted report should not be null", design);
        assertEquals("The resulted report should be a JasperDesign", JasperDesign.class, design.getClass());
    }

    @Test(expected = NullPointerException.class)
    public void testShouldPassIfExceptionIsThrownWhenLoadingNullURI() throws Exception {
        JRReport design = new UriLoader().load(null);
        assertNull("The resulted report should be null", design);
    }

    @Test(expected = LoaderException.class)
    public void testShouldPassIfExceptionIsThrownWhenLoadingFromNonexistentURIString() throws Exception {
        JRReport design = new UriLoader().load(URI.create("a/b/c"));
        assertNull("The resulted report should be null", design);

    }

    @Test(expected = LoaderException.class)
    public void testShouldPassIfExceptionIsThrownWhenLoadingFromNonexistentURI() throws Exception {
        File file = new File(JasperConstantsTest.JRXML_BLANK_LANDSCAPE_TEMPLATE_PATH);
        JRReport design = new UriLoader().load(new URI("a/b/c"));
        assertNull("The resulted report should be null", design);
    }

    @Test(expected = LoaderException.class)
    public void testShouldPassIfExceptionIsThrownWhenLoadingFromWrongProtocol() throws Exception {
        File file = new File(JasperConstantsTest.JRXML_BLANK_LANDSCAPE_TEMPLATE_PATH);
        JRReport design = new UriLoader().load(new URI("wrongprotocol:a/b/c"));
        assertNull("The resulted report should be null", design);
    }
}