package ro.teamnet.solutions.reportinator.bind.jasper;

import net.sf.jasperreports.engine.JRComponentElement;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JasperDesign;
import ro.teamnet.solutions.reportinator.bind.Binder;
import ro.teamnet.solutions.reportinator.bind.BindingException;
import ro.teamnet.solutions.reportinator.config.JasperConstants;

/**
 * TODO Doc
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/12/2015
 */
public final class TableDesignBinder implements Binder<JRComponentElement, JRReport> {

    private final JRReport reportDesign;

    public TableDesignBinder(JRReport reportDesign) {
        if(reportDesign.getDetailSection() != null)
            throw new BindingException("Template already has a detail band");
        this.reportDesign = reportDesign;
    }

    @Override
    public JRReport bind(JRComponentElement item) throws BindingException {

        if(item == null ){
            throw new BindingException("The table is null");
        }

        if(!item.getComponentKey().getName().equals("table") && !item.getComponentKey().getNamespace().equals("jr") ){
            throw new BindingException("The component is not a JasperDesign Table");
        }

        JRDesignBand band;
        // Setup Detail (content data)
        band = new JRDesignBand();
        band.setHeight(JasperConstants.JASPER_MINIMUM_BAND_DETAIL_HEIGHT);
        // Add table as a component to a band

        band.addElement(item);
        // Add to details band
        ((JRDesignSection) reportDesign.getDetailSection()).addBand(band);

        return reportDesign;
    }
}
