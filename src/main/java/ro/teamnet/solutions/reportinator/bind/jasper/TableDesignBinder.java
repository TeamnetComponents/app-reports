package ro.teamnet.solutions.reportinator.bind.jasper;

import net.sf.jasperreports.engine.JRComponentElement;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignSection;
import ro.teamnet.solutions.reportinator.bind.Binder;
import ro.teamnet.solutions.reportinator.bind.BindingException;
import ro.teamnet.solutions.reportinator.config.JasperConstants;

/**
 * Concrete implementation of a {@link ro.teamnet.solutions.reportinator.bind.Binder} which attaches a table component
 * to a given JasperReports design.
 *
 * @author Bogdan.Stefan
 * @author Bogdan.Iancu
 * @version 1.0 Date: 2/12/2015
 */
public final class TableDesignBinder implements Binder<JRComponentElement, JRReport> {

    private final JRReport reportDesign;

    /**
     * A constructor which receives a report design, to later bind a table to. The design MUST NOT have its detail band setup.
     *
     * @param reportDesign The template to bind the table to.
     * @throws BindingException If the report design already has a detail band.
     */
    public TableDesignBinder(JRReport reportDesign) throws BindingException {
        if (reportDesign.getDetailSection().getBands().length > 0)
            throw new BindingException("Report design/template already has a detail band.");
        this.reportDesign = reportDesign;
    }

    /**
     * Binds a given Jasper table component to a report design.
     *
     * @param tableComponent A component, denoting a table.
     * @return The contained report design, having the table component bound to it.
     * @throws BindingException If the given component is not a table.
     */
    @Override
    public JRReport bind(JRComponentElement tableComponent) throws BindingException {
        if (tableComponent == null) {
            throw new IllegalArgumentException("The table component must not be null.");
        }
        if (tableComponent.getComponentKey()== null || !tableComponent.getComponentKey().getName().equals("table") && !tableComponent.getComponentKey().getNamespace().equals("jr")) {
            throw new BindingException("The component is not a JasperDesign Table");
        }

        JRDesignBand band;
        // Setup Detail (content data)
        band = new JRDesignBand();
        band.setHeight(JasperConstants.JASPER_MINIMUM_BAND_DETAIL_HEIGHT);
        // Add tableComponent as a component to a band

        band.addElement(tableComponent);
        // Add to details band
        ((JRDesignSection) reportDesign.getDetailSection()).addBand(band);

        return reportDesign;
    }
}
