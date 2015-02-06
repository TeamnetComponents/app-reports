package ro.teamnet.solutions.reportinator.bind.jasper;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import ro.teamnet.solutions.reportinator.bind.Binder;

/**
 * Binds a {@link net.sf.jasperreports.engine.JRDataSource} to a {@link net.sf.jasperreports.engine.JRReport}.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/6/2015
 */
public final class JasperDataSourceDesignBinder implements Binder<JRDataSource, JRReport> {

    /**
     * The report design to bind to.
     */
    private final JasperDesign reportDesign;

    public JasperDataSourceDesignBinder(JasperDesign reportDesign) {
        this.reportDesign = reportDesign;
    }

    @Override
    public JRReport bind(JRDataSource dataSource) {


        return this.reportDesign;
    }
}
