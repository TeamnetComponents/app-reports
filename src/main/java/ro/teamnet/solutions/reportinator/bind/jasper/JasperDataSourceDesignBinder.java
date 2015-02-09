package ro.teamnet.solutions.reportinator.bind.jasper;

import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.JRStaticText;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import ro.teamnet.solutions.reportinator.bind.Binder;

import java.util.Collection;
import java.util.Objects;

/**
 * Binds data source field metadata to a {@link net.sf.jasperreports.engine.JRReport}. This metadata is to be
 * used when filling the report, for internal field matching.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/6/2015
 */
public final class JasperDataSourceDesignBinder implements Binder<Collection<String>, JRReport> {

    /**
     * A mutable report design to bind metadata to.
     */
    private final JasperDesign reportDesign;

    public JasperDataSourceDesignBinder(JRReport reportDesign) {
        this.reportDesign = JasperDesign.class.cast(
                Objects.requireNonNull(reportDesign, "Jasper report reference must not be null.")); // Future extract this message as a i18n key
        // Extra report design overrides
        init();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JRReport bind(Collection<String> fieldNames) {
        JRExpression expression;
        expression = new JRDesignExpression();

        return this.reportDesign;
    }

    private void init() {
        // Overrides report settings to display a custom "No Data" section
        this.reportDesign.setWhenNoDataType(WhenNoDataTypeEnum.NO_DATA_SECTION);
        // Replaces "No Data"
        this.reportDesign.setNoData(noData());
    }

    /**
     * A helper method which creates a custom band, used to override or assign a report's "No Data" band.
     *
     * @return A band to be used as a "No Data" replacement.
     */
    private static JRBand noData() {
        // TODO Configure a proper no data
        JRDesignBand noDataBand = new JRDesignBand();
        JRStaticText staticText;

        staticText = new JRDesignStaticText();
        staticText.setX(0);
        staticText.setPrintWhenDetailOverflows(true);
        staticText.setText("No items."); // Future extract this message as a i18n key
        noDataBand.addElement(staticText);

        noDataBand.setHeight(20); // pixels

        return noDataBand;
    }
}
