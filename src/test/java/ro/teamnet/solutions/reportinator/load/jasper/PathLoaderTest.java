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

import static org.junit.Assert.*;

/**
 * Contract and minimal tests for the {@link ro.teamnet.solutions.reportinator.load.jasper.PathLoader} class
 *
 * @author Andrei.Marica
 * @version 1.0 Date: 2/17/2015
 */
public class PathLoaderTest {

    @Test
    public void testShouldPassWithValidPathToLoad() throws Exception {
        JRReport design = new PathLoader().load(new File(JasperConstantsTest.JRXML_BLANK_LANDSCAPE_TEMPLATE_PATH).toPath());
        assertNotNull("The resulted report should not be null", design);
        assertEquals("Loaded design name did not match expected", design.getName(), "SiemPdfPrototype");
        assertEquals("The resulted report should be a JasperDesign", JasperDesign.class, design.getClass());
    }

    @Test(expected = LoaderException.class)
    public void testSouldPassIfExceptionIsThrownWhenSendingAPathToNonexistentFile() throws Exception {
        JRReport design = new PathLoader().load(new File("/a/b/c").toPath());
        assertNull("The resulted report should be null", design);
    }

    @Test(expected = LoaderException.class)
    public void testShouldPassIfExceptionIsThrownWhenSendingANullPath() throws Exception {
        JRReport design = new PathLoader().load(null);
        assertNull("The resulted report should be null", design);
    }

    @Test(expected = LoaderException.class)
    public void testShouldPassIfExceptionIsThrownWhenSendingAnEmptyPath() throws Exception {
        JRReport design = new PathLoader().load(new File("").toPath());
        assertNull("The resulted report should be null", design);
    }
}