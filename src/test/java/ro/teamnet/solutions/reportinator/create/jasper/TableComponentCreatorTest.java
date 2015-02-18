package ro.teamnet.solutions.reportinator.create.jasper;

import net.sf.jasperreports.engine.JRComponentElement;
import org.junit.Before;
import org.junit.Test;
import ro.teamnet.solutions.reportinator.config.JasperConstantsTest;
import ro.teamnet.solutions.reportinator.load.JasperDesignLoader;

import java.io.File;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TableComponentCreatorTest {

    private static final String TEST_TEMPLATE_PATH = JasperConstantsTest.JRXML_BLANK_PORTRAIT_TEMPLATE_PATH;

    private TableComponentCreator componentCreator;

    @Before
    public void setUp() throws Exception {
        this.componentCreator = new TableComponentCreator(JasperDesignLoader.load(new File(TEST_TEMPLATE_PATH)));
    }

    @Test
    public void testShouldPassIfAComponentWasCreated() throws Exception {
        JRComponentElement tableComponent = this.componentCreator.create(JasperConstantsTest.FIELDS_COLUMNS_METADATA);
        assertNotNull("Generated component element should not be null.", tableComponent);
        assertNotNull("Element's contained component should not be null.", tableComponent.getComponent());
        assertNotNull("Generated component's component key should not be null.", tableComponent.getComponentKey());
    }

    @Test
    public void testShouldPassIfAValidTableComponentWasCreated() throws Exception {
        JRComponentElement componentElement = this.componentCreator.create(JasperConstantsTest.FIELDS_COLUMNS_METADATA);
        assertTrue("Generated component element is not a JasperReports table component.",
                componentElement.getComponentKey().getNamespacePrefix().equals("jr") &&
                componentElement.getComponentKey().getName().equals("table"));
    }

    // TODO Error checking tests for this component
}
