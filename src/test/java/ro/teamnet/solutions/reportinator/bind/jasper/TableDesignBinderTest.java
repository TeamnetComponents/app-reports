package ro.teamnet.solutions.reportinator.bind.jasper;

import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRComponentElement;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.component.ComponentKey;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.Bean;
import ro.teamnet.solutions.reportinator.bind.BindingException;
import ro.teamnet.solutions.reportinator.config.JasperConstants;

import static org.junit.Assert.*;

public class TableDesignBinderTest {

    private TableDesignBinder tableDesignBinder;


    @Before
    public void setUp(){
        tableDesignBinder = new TableDesignBinder(new JasperDesign());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShouldPassIfComponentElementIsNull(){
        tableDesignBinder.bind(null);
    }

    @Test(expected = BindingException.class)
    public void testShouldPassIfComponentHasNoComponentKey(){
        JRComponentElement componentElement = new JRDesignComponentElement();
        tableDesignBinder.bind(componentElement);
    }

    @Test(expected = BindingException.class)
    public void testShouldPassIfComponentKeyIsNotATable(){
        JRComponentElement componentElement = new JRDesignComponentElement();
        ((JRDesignComponentElement)componentElement).setComponentKey(new ComponentKey("http://jasperreports.sourceforge.net/jasperreports/components", "jr", "box"));
        tableDesignBinder.bind(componentElement);
    }

    @Test
    public void bindShouldReturnAValidJRReport() {
        JRComponentElement componentElement = new JRDesignComponentElement();
        ((JRDesignComponentElement) componentElement).setComponentKey(new ComponentKey("http://jasperreports.sourceforge.net/jasperreports/components", "jr", "table"));
        ((JRDesignComponentElement) componentElement).setKey(JasperConstants.JASPER_TABLE_IDENTIFIER_KEY);
        JRReport report = tableDesignBinder.bind(componentElement);
        JRBand[] bands = report.getDetailSection().getBands();
        boolean tableFound = false;
        for (JRBand band : bands) {
            if (band.getElementByKey(JasperConstants.JASPER_TABLE_IDENTIFIER_KEY) != null)
                tableFound = true;
        }
        assertTrue(tableFound);
    }

}