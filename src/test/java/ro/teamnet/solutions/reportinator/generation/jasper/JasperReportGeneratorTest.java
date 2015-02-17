package ro.teamnet.solutions.reportinator.generation.jasper;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.JasperPrint;
import org.junit.Before;
import org.junit.Test;
import ro.teamnet.solutions.reportinator.config.JasperConstants;
import ro.teamnet.solutions.reportinator.convert.jasper.MapCollectionDataSourceConverter;
import ro.teamnet.solutions.reportinator.export.ExportType;
import ro.teamnet.solutions.reportinator.export.jasper.ExporterImpl;
import ro.teamnet.solutions.reportinator.generation.ReportGenerator;

import java.io.FileOutputStream;
import java.util.*;

import static org.junit.Assert.assertNotNull;

public class JasperReportGeneratorTest {

    private static final String JRXML_PATH = JasperConstants.JASPER_TEST_TEMPLATE_RESOURCE_PATH;
    private final static List<String> COLUMNS_META = new LinkedList<String>();
    private final static List<Map<String, ?>> ROWS = new LinkedList<Map<String, ?>>();
    private final static Map<String, String> FIELDS_COLUMNS_METADATA = new HashMap<String, String>();
    private JRDataSource dataSource;
    private JRReport reportDesign;

    @Before
    public void setUp() throws Exception {
        // Prepare map collection

        // Generate some row data
        Map<String, String> row;
        for (int i = 0; i < 39; i++) {
            row = new LinkedHashMap<String, String>();
            row.put("Col01", Integer.toString(i));
            if (i % 2 == 0) {
                row.put("Col02", "The quick and sneaky brown fox jumped over the lazy dog.");
                row.put("Col03", "The quick and sneaky brown fox jumped over the lazy dog.");
                row.put("Col04", "The quick and sneaky brown fox jumped over the lazy dog.");
                row.put("Col05", "The quick and sneaky brown fox jumped over the lazy dog.");
                row.put("Col06", "The quick and sneaky brown fox jumped over the lazy dog.");
                row.put("Col07", "The quick and sneaky brown fox jumped over the lazy dog.");
            } else {
                row.put("Col02", "The dog slept.");
                row.put("Col03", "The dog slept.");
                row.put("Col04", "The dog slept.");
                row.put("Col05", "The dog slept.");
                row.put("Col06", "The dog slept.");
                row.put("Col07", "The dog slept.");
            }
            ROWS.add(row);
        }

        // Grab column metadata from an entry
        COLUMNS_META.addAll(ROWS.get(0).keySet());

        // Establish labels
        int i = 0;
        for (String fieldName : COLUMNS_META) {
            i++;
            FIELDS_COLUMNS_METADATA.put(fieldName, "Column " + i);
        }

        dataSource = new MapCollectionDataSourceConverter(COLUMNS_META).convert(ROWS);
    }

    @Test
    public void testReportGeneratorShouldGenerateAValidPrint() throws Exception {
        ReportGenerator<JasperPrint> reportGenerator =
                JasperReportGenerator.builder(JRXML_PATH)
                        .withDatasource(this.dataSource)
                        .withTableColumnsMetadata(FIELDS_COLUMNS_METADATA)
                        .build();
        JasperPrint print = reportGenerator.generate();
        new ExporterImpl().export(print, new FileOutputStream("testExport.pdf"), ExportType.PDF);
        assertNotNull("Generated print object was null.", print);
    }
}
