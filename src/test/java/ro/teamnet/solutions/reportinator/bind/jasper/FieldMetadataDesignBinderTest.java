package ro.teamnet.solutions.reportinator.bind.jasper;

import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRReport;
import org.junit.Before;
import org.junit.Test;
import ro.teamnet.solutions.reportinator.bind.Binder;
import ro.teamnet.solutions.reportinator.config.JasperConstantsTest;
import ro.teamnet.solutions.reportinator.load.JasperDesignLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class FieldMetadataDesignBinderTest {

    private static final String JRXML_PATH = JasperConstantsTest.JRXML_BLANK_PORTRAIT_TEMPLATE_PATH;
    private static final Collection<String> COLUMNS_METADATA = JasperConstantsTest.COLUMNS_METADATA;
    private JRReport reportDesign;

    @Before
    public void setUp() throws Exception {
        // Load-up a no-field report
        this.reportDesign = JasperDesignLoader.load(new File(JRXML_PATH));
    }

    @Test
    public void testShouldPassIfFieldMetadataIsSuccessfullyBoundToDesign() throws Exception {
        JRReport fieldAttachedReport = new FieldMetadataDesignBinder(this.reportDesign).bind(COLUMNS_METADATA);
        // Retrieve all fields (including extra ones)
        Collection<String> attachedFieldsNames = new ArrayList<String>();
        for (JRField field : fieldAttachedReport.getFields()) {
            attachedFieldsNames.add(field.getName());
        }
        // Test that all fields have been attached
        assertTrue("Not all column metadata fields have been attached to the design.", attachedFieldsNames.containsAll(COLUMNS_METADATA));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShouldPassIfExceptionIsThrownForNullColumnMetadata() throws Exception {
        Collection<String> nullCollectionReference = null;
        JRReport fieldAttachedReport = new FieldMetadataDesignBinder(this.reportDesign).bind(nullCollectionReference);
        // No valid reference should be returned since collection was null
        assertNull("The returned report reference must be null.", fieldAttachedReport);
    }

    @Test(expected = NullPointerException.class)
    public void testShouldPassIfExceptionIsThrownForNullReportDesign() throws Exception {
        JRReport nullReportReference = null;
        Binder<Collection<String>, JRReport> metadataDesignBinder = new FieldMetadataDesignBinder(nullReportReference);
        // No valid reference should be attached since design was null
        assertNull("The returned binder reference must be null.", metadataDesignBinder);
    }
}
