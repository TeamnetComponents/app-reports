package ro.teamnet.solutions.reportinator.config.styles;


import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import org.junit.Test;
import ro.teamnet.solutions.reportinator.config.JasperConstants;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Contract and minimal tests for {@link ro.teamnet.solutions.reportinator.config.styles.JasperStyles} enum
 *
 * @author Andrei.Marica
 * @version 1.0 Date: 2/17/2015
 */
public class JasperStylesTest {


    @Test
    public void testShouldPassWhenCreatingAListWithIteratorThroughEnum() throws Exception {
        ArrayList<JRStyle> styleList = new ArrayList<>();
        for (JRStyle style : JasperStyles.asList()) {
            styleList.add(style);
            System.out.println(style.getName());
        }
        assertNotNull("The returned list of JRStyle must not be null", styleList);
    }

    @Test
    public void testShouldPassWhenCreatingAListByAddingManuallyFromEnum() throws Exception {
        ArrayList<JRStyle> styleList = new ArrayList<>();
        styleList.add(JasperStyles.COLUMN_CONTENT_STYLE.getStyle());
        styleList.add(JasperStyles.COLUMN_FOOTER_STYLE.getStyle());
        styleList.add(JasperStyles.COLUMN_HEADER_STYLE.getStyle());
        styleList.add(JasperStyles.DEFAULT_STYLE.getStyle());
        styleList.add(JasperStyles.TABLE_STYLE.getStyle());
        styleList.add(JasperStyles.HEADER_TABLE_STYLE.getStyle());
        assertNotNull("The returned JRStyle list must not be null", styleList);
    }

    @Test
    public void testShouldPassWhenCreatingAListWithAsListMethodFromEnum() throws Exception {
        ArrayList<JRStyle> styleList;
        styleList = (ArrayList<JRStyle>) JasperStyles.asList();
        assertNotNull("The returned JRStyle list must not be null", styleList);
    }

    @Test
    public void testShouldPassWhenCallingGetDefaultStyle() throws Exception {
        assertEquals("The returned default JRStyle must be a JRDesignStyle", JRDesignStyle.class, JasperStyles.DEFAULT_STYLE.getStyle().getClass());
        assertEquals("The returned default JRStyle must have the default style name", JasperConstants.JASPER_TABLE_DEFAULT_STYLE_NAME_IDENTIFIER_KEY, JasperStyles.DEFAULT_STYLE.getStyle().getName());
    }

    @Test
    public void testShouldPassWhenCallingGetTableStyle() throws Exception {
        assertEquals("The returned table JRStyle must be a JRDesignStyle", JRDesignStyle.class, JasperStyles.TABLE_STYLE.getStyle().getClass());
        assertEquals("The returned table JRStyle must have the table style name", JasperConstants.JASPER_TABLE_STYLE_NAME_IDENTIFIER_KEY, JasperStyles.TABLE_STYLE.getStyle().getName());
    }

    @Test
    public void testShouldPassWhenCallingGetColumnHeaderStyle() throws Exception {
        assertEquals("The returned column header JRStyle must be a JRDesignStyle", JRDesignStyle.class, JasperStyles.COLUMN_HEADER_STYLE.getStyle().getClass());
        assertEquals("The returned column header JRStyle must have the table header style name", JasperConstants.JASPER_TABLE_HEADER_STYLE_NAME_IDENTIFIER_KEY, JasperStyles.COLUMN_HEADER_STYLE.getStyle().getName());
    }

    @Test
    public void testShouldPassWhenCallingGetColumnContentStyle() throws Exception {
        assertEquals("The returned column content JRStyle must be a JRDesignStyle", JRDesignStyle.class, JasperStyles.COLUMN_CONTENT_STYLE.getStyle().getClass());
        assertEquals("The returned column content JRStyle must have the column content style name", JasperConstants.JASPER_TABLE_CONTENT_STYLE_NAME_IDENTIFIER_KEY, JasperStyles.COLUMN_CONTENT_STYLE.getStyle().getName());
    }

    @Test
    public void testShouldPassWhenCallingGetColumnFooterStyle() throws Exception {
        assertEquals("The returned column footer JRStyle must be a JRDesignStyle", JRDesignStyle.class, JasperStyles.COLUMN_FOOTER_STYLE.getStyle().getClass());
        assertEquals("The returned column footer JRStyle must have the column footer style name", JasperConstants.JASPER_TABLE_FOOTER_STYLE_NAME_IDENTIFIER_KEY, JasperStyles.COLUMN_FOOTER_STYLE.getStyle().getName());
    }

    @Test
    public void testShouldPassWhenCallingGetTableHeaderStyle() throws Exception {
        assertEquals("The returned table header JRStyle must be a JRDesignStyle", JRDesignStyle.class, JasperStyles.HEADER_TABLE_STYLE.getStyle().getClass());
        assertEquals("The returned table header JRStyle must have the table header name", JasperConstants.JASPER_TABLE_BOX_HEADER_STYLE_NAME_IDENTIFIER_KEY, JasperStyles.HEADER_TABLE_STYLE.getStyle().getName());
    }
}