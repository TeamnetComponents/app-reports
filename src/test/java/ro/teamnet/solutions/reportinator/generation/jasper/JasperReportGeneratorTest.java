/*
 * Copyright (c) 2015 Teamnet S.A. All Rights Reserved.
 *
 * This source file may not be copied, modified or redistributed,
 * in whole or in part, in any form or for any reason, without the express
 * written consent of Teamnet S.A.
 */

package ro.teamnet.solutions.reportinator.generation.jasper;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperPrint;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ro.teamnet.solutions.reportinator.config.JasperConstants;
import ro.teamnet.solutions.reportinator.config.JasperConstantsTest;
import ro.teamnet.solutions.reportinator.convert.jasper.MapCollectionDataSourceConverter;
import ro.teamnet.solutions.reportinator.generation.JasperReportGenerator;
import ro.teamnet.solutions.reportinator.generation.ReportGenerator;
import ro.teamnet.solutions.reportinator.generation.ReportGeneratorException;

import java.io.InputStream;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author Bogdan.Stefan
 */
public class JasperReportGeneratorTest {

    private static final String JRXML_PATH = JasperConstantsTest.JRXML_BLANK_PORTRAIT_TEMPLATE_PATH;
    private final static List<String> COLUMNS_METADATA = JasperConstantsTest.COLUMNS_METADATA;
    private final static List<Map<String, ?>> ROWS_MAP_COLLECTION = JasperConstantsTest.ROWS_MAP_COLLECTION;
    private final static Map<String, String> FIELDS_COLUMNS_METADATA = JasperConstantsTest.FIELDS_COLUMNS_METADATA;
    private static JRDataSource DATA_SOURCE;

    @Before
    public void setUp() throws Exception {

        DATA_SOURCE = new MapCollectionDataSourceConverter(COLUMNS_METADATA).convert(ROWS_MAP_COLLECTION);
    }

    @Test(expected = IllegalStateException.class)
    public void testShouldPassIfExceptionWhenUsingCustomJrxmlAndDataSource() throws Exception {
        ReportGenerator<JasperPrint> reportGenerator =
                JasperReportGenerator.builder(JRXML_PATH)   // Load a custom JXRML
                        .withDatasource(DATA_SOURCE)
                        .withTableColumnsMetadata(FIELDS_COLUMNS_METADATA)
                        .build();
        assertNull("Generator reference should have been null.", reportGenerator);
    }

    @Test(expected = IllegalStateException.class)
    public void testShouldPassIfExceptionWhenUsingCustomJrxmlAndTableMetadataInsteadOfConnection() throws Exception {
        ReportGenerator<JasperPrint> reportGenerator =
                JasperReportGenerator.builder(JRXML_PATH)   // Load a custom JXRML
                        .withTableColumnsMetadata(FIELDS_COLUMNS_METADATA)
                        .build();
        assertNull("Generator reference should have been null.", reportGenerator);
    }

    @Test
    public void testShouldGenerateValidReportPrint() throws Exception {
        ReportGenerator<JasperPrint> reportGenerator =
                JasperReportGenerator.builder()
                        .withDatasource(DATA_SOURCE)
                        .withTableColumnsMetadata(FIELDS_COLUMNS_METADATA)
                        .build();
        JasperPrint print = reportGenerator.generate();
        assertNotNull("Generated print object was null.", print);
        assertEquals("Generated print report name does not match built-in name.", JasperConstants.JASPER_REPORT_DESIGN_NAME_KEY, print.getName());
    }

    /**
     * A regression test, for a previously discovered bug in which when setting the builder's column metadata before
     * the data source threw an exception.
     */
    @Test
    public void testReportGeneratorShouldGenerateAValidPrintWhenTableColumnMetadataBeforeDataSource() {
        ReportGenerator<JasperPrint> reportGenerator =
                JasperReportGenerator.builder() // Table metadata is given before data source
                        .withTableColumnsMetadata(FIELDS_COLUMNS_METADATA)
                        .withDatasource(DATA_SOURCE)
                        .build();
        assertNotNull("Generator reference should not have been null.", reportGenerator);
        JasperPrint print = reportGenerator.generate();
        assertEquals("Generated print report name does not match built-in name.", JasperConstants.JASPER_REPORT_DESIGN_NAME_KEY, print.getName());
    }

    @Test(expected = ReportGeneratorException.class)
    public void testReportGeneratorBuilderMethodShouldThrowExceptionIfTemplateArgumentIsNull() throws Exception {
        InputStream fakeTemplateStream = null; // Specially crafted, to distinguish between builder methods
        JasperReportGenerator.Builder reportGeneratorBuilder =
                JasperReportGenerator.builder(fakeTemplateStream);
        assertNull("Builder reference should have been null.", reportGeneratorBuilder);
    }

    @Test(expected = IllegalStateException.class)
    public void testReportGeneratorBuilderBuildMethodShouldThrowExceptionIfCustomJrxmlBuilderHasNoBuildInformation() throws Exception {
        ReportGenerator<JasperPrint> reportGenerator =
                JasperReportGenerator.builder(JRXML_PATH) // No build information defined; builder is inconsistent
                        .build();
        assertNull("Generator reference should have been null.", reportGenerator);
    }


    @Test(expected = IllegalStateException.class)
    public void testReportGeneratorBuilderBuildMethodShouldThrowExceptionIfBuilderHasNoBuildInformation() throws Exception {
        ReportGenerator<JasperPrint> reportGenerator =
                JasperReportGenerator.builder() // No build information defined; builder is inconsistent
                        .build();
        assertNull("Generator reference should have been null.", reportGenerator);
    }

    @Test(expected = IllegalStateException.class)
    public void testReportGeneratorBuilderBuildMethodShouldThrowExceptionIfBuilderHasPartialBuildInformation() throws Exception {
        ReportGenerator<JasperPrint> reportGenerator =
                JasperReportGenerator.builder() // Builder uses built-in template, no field metadata
                        .withDatasource(DATA_SOURCE) // and only a data source
                        .build();
        assertNull("Generator reference should have been null.", reportGenerator);
    }

    @Test(expected = IllegalStateException.class)
    public void testReportGeneratorBuilderBuildMethodShouldThrowExceptionIfBuilderHasPartialBuildInformation2() throws Exception {
        ReportGenerator<JasperPrint> reportGenerator =
                JasperReportGenerator.builder() // Builder uses built-in template, no data source
                        .withTableColumnsMetadata(FIELDS_COLUMNS_METADATA) // and only field metadata
                        .build();
        assertNull("Generator reference should have been null.", reportGenerator);
    }

    /**
     * TODO Test that this actually works; for now it should fail since no valid connection information.
     */
    @Test
    @Ignore
    public void testShouldPassWhenUsingValidCustomJrxmlAndConnection() {
        Connection connection = null; // FUTURE Test that this actually works
        ReportGenerator<JasperPrint> reportGenerator =
                JasperReportGenerator.builder(JRXML_PATH)   // Load a custom JXRML
                        .withConnection(connection)
                        .build();
        assertNull("Generator reference should not have been null.", reportGenerator); // Revert this
    }

    @Test(expected = NullPointerException.class)
    public void testShouldPassIfExceptionWhenUsingCustomJrxmlAndNullConnection() {
        Connection connection = null; // Specially set to 'null'
        ReportGenerator<JasperPrint> reportGenerator =
                JasperReportGenerator.builder(JRXML_PATH)   // Load a custom JXRML
                        .withConnection(connection)
                        .build();
        assertNull("Generator reference should have been null.", reportGenerator);
    }
}
