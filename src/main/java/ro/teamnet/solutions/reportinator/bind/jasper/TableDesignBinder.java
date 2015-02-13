package ro.teamnet.solutions.reportinator.bind.jasper;

import net.sf.jasperreports.engine.JRComponentElement;
import net.sf.jasperreports.engine.JRReport;
import ro.teamnet.solutions.reportinator.bind.Binder;
import ro.teamnet.solutions.reportinator.bind.BindingException;

/**
 * TODO Doc
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/12/2015
 */
public final class TableDesignBinder implements Binder<JRComponentElement, JRReport> {

    private final JRReport reportDesign;

    public TableDesignBinder(JRReport reportDesign) {
        this.reportDesign = reportDesign;
    }

    @Override
    public JRReport bind(JRComponentElement item) throws BindingException {
        // TODO We are assuming that the design does not contain a "Detail 1" band
        return null;
    }
}
