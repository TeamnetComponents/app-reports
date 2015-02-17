package ro.teamnet.solutions.reportinator.bind.jasper;

import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.junit.Before;
import org.junit.Test;
import ro.teamnet.solutions.reportinator.bind.BindingException;
import ro.teamnet.solutions.reportinator.config.styles.JasperStyles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Contract and minimal tests for the {@link ro.teamnet.solutions.reportinator.bind.jasper.StylesDesignBinder} class
 *
 * @author Andrei.Marica
 * @version 1.0 Date: 2/17/2015
 */
public class StylesDesignBinderTest {

    /**
     * Object to be tested
     */
    private StylesDesignBinder stylesDesignBinder;
    /**
     * Object needed by the constructor and the report template to be returned after binding the styles to it
     */
    private JasperDesign jasperDesign;

    /**
     * Creating the object to be tested and it's {@link net.sf.jasperreports.engine.design.JasperDesign}
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        jasperDesign = new JasperDesign();
        stylesDesignBinder = new StylesDesignBinder(jasperDesign);
    }


    @Test(expected = NullPointerException.class)
    public void testShouldThrowExceptionWhenUsingConstructorWithNullJasperDesign() throws Exception {
        stylesDesignBinder = new StylesDesignBinder(null);
        assertNull(stylesDesignBinder);
    }

    @Test(expected = BindingException.class)
    public void testShouldThrowExceptionWhenBindingWithNullList() throws Exception {
        List<JRStyle> styles = null;
        stylesDesignBinder.bind(styles);

    }

    @Test(expected = BindingException.class)
    public void testShouldThrowExceptionWhenBindingWithAnEmptyList() throws Exception {
        List<JRStyle> styleList = new ArrayList<>();
        stylesDesignBinder.bind(styleList);
    }

    @Test
    public void testShouldPassWhenBindingWithValidSJRStylesList() throws Exception {
        List<JRStyle> styleList = JasperStyles.asList();
        stylesDesignBinder.bind(styleList);
        assertEquals(JasperStyles.asList().getClass(), jasperDesign.getStylesList().getClass());
    }
}