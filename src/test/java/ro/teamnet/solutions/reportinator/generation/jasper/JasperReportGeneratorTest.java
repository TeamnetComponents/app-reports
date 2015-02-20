package ro.teamnet.solutions.reportinator.generation.jasper;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperPrint;
import org.junit.Before;
import org.junit.Test;
import ro.teamnet.solutions.reportinator.config.JasperConstantsTest;
import ro.teamnet.solutions.reportinator.convert.jasper.MapCollectionDataSourceConverter;
import ro.teamnet.solutions.reportinator.generation.JasperReportGenerator;
import ro.teamnet.solutions.reportinator.generation.ReportGenerator;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
                JasperReportGenerator.builder(JRXML_PATH)
                        .withDatasource(DATA_SOURCE)
                        .withTableColumnsMetadata(FIELDS_COLUMNS_METADATA)
                        .build();
        assertNull("Generator should have been null.", reportGenerator);
    }

    @Test(expected = IllegalStateException.class)
    public void testShouldPassIfExceptionWhenUsingCustomJrxmlAnTableMetadata() throws Exception {
        ReportGenerator<JasperPrint> reportGenerator =
                JasperReportGenerator.builder(JRXML_PATH)
                        .withTableColumnsMetadata(FIELDS_COLUMNS_METADATA)
                        .build();
        assertNull("Generator should have been null.", reportGenerator);
    }

    @Test
    public void testShouldGenerateAValidReportPrint() throws Exception {
        ReportGenerator<JasperPrint> reportGenerator =
                JasperReportGenerator.builder()
                        .withDatasource(DATA_SOURCE)
                        .withTableColumnsMetadata(FIELDS_COLUMNS_METADATA)
                        .build();
        JasperPrint print = reportGenerator.generate();
        assertNotNull("Generated print object was null.", print);
    }

    /**
     * A regression test, for a previously discovered bug.
     *
     * @throws Exception
     */
    @Test
    public void testReportGeneratorShouldGenerateAValidPrintWhenTableColumnMetadataBeforeDataSource() {
        ReportGenerator<JasperPrint> reportGenerator =
                JasperReportGenerator.builder() // Table metadata is given before data source
                        .withTableColumnsMetadata(FIELDS_COLUMNS_METADATA)
                        .withDatasource(DATA_SOURCE)
                        .build();
        JasperPrint print = reportGenerator.generate();
        assertNotNull("Generated print object was null.", print);
    }

    // TODO Error and other tests
}
