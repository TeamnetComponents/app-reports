package ro.teamnet.solutions.reportinator.convert.jasper;

import net.sf.jasperreports.engine.JRDataSource;
import org.junit.Before;
import org.junit.Test;
import ro.teamnet.solutions.reportinator.config.JasperConstantsTest;

import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MapCollectionDataSourceConverterTest {

    private static final Collection<Map<String, ?>> ROWS_MAP_COLLECTION = JasperConstantsTest.ROWS_MAP_COLLECTION;
    private static final Collection<String> FIELDS_METADATA = JasperConstantsTest.COLUMNS_METADATA;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testShouldPassIfConversionToJasperDataSourceIsSuccessful() throws Exception {
        JRDataSource dataSource = new MapCollectionDataSourceConverter(FIELDS_METADATA).convert(ROWS_MAP_COLLECTION);
        assertNotNull("Map collection could not be converted to a data source.", dataSource);
        assertTrue("Data source reported no items, when it should have items from collection.", dataSource.next());
    }
}
