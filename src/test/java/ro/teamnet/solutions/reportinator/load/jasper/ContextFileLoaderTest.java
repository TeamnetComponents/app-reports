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
import ro.teamnet.solutions.reportinator.load.LoaderException;

import static org.junit.Assert.*;

/**
 * Contract and minimal tests for the {@link ContextFileLoader} class
 *
 * @author Andrei.Marica
 * @version 1.0 Date: 2/17/2015
 */
public class ContextFileLoaderTest {

    @Test
    public void testShouldPassWithTheGivenFileName() throws Exception {
        JRReport report = new ContextFileLoader().load("default_template.jrxml");
        assertNotNull("The resulted report should not be null", report);
        assertEquals("The resulted report should be a JasperDesign", JasperDesign.class, report.getClass());
    }

    @Test(expected = LoaderException.class)
    public void testShouldPassIfExceptionIsThrownForEmptyFileName() throws Exception {
        JRReport report = new ContextFileLoader().load("");
        assertNull("The resulted report should be null", report);
    }

    @Test(expected = LoaderException.class)
    public void testShouldPassIfExceptionIsThrownForNullFileName() throws Exception {
        JRReport report = new ContextFileLoader().load(null);
        assertNull("The resulted report should be null", report);
    }

    @Test(expected = LoaderException.class)
    public void testLoad4() throws Exception {
        JRReport report = new ContextFileLoader().load("a");
        assertNull("The resulted report should be null", report);
    }
}