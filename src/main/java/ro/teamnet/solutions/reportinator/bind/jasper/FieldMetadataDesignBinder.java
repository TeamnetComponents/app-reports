package ro.teamnet.solutions.reportinator.bind.jasper;

import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.JRStaticText;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.type.WhenResourceMissingTypeEnum;
import ro.teamnet.solutions.reportinator.bind.Binder;
import ro.teamnet.solutions.reportinator.bind.BindingException;
import ro.teamnet.solutions.reportinator.config.JasperConstants;

import java.util.Collection;
import java.util.Objects;

/**
 * Binds data source field metadata to a {@link net.sf.jasperreports.engine.JRReport}. This metadata is to be
 * used when filling the report, for internal field matching.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/6/2015
 */
public final class FieldMetadataDesignBinder implements Binder<Collection<String>, JRReport> {

    /**
     * The report design to bind field metadata to.
     */
    private final JasperDesign reportDesign;

    /**
     * Receives a {@link net.sf.jasperreports.engine.JRReport}
     * (usually a {@link net.sf.jasperreports.engine.design.JasperDesign}) to bind field metadata to it.
     *
     * @param reportDesign A mutable report design to bind field metadata to.
     */
    public FieldMetadataDesignBinder(JRReport reportDesign) {
        this.reportDesign = JasperDesign.class.cast(
                Objects.requireNonNull(reportDesign, "Jasper report reference must not be null."));
        // Extra report design overrides
        init();
    }

    /**
     * Binds a collection of fields metadata to the attached report template, by creating Jasper
     * {@link net.sf.jasperreports.engine.JRField}s.
     *
     * @param fieldNames A collection of fields metadata.
     * @return The report template, having the fields metadata bound to it.
     */
    @Override
    public JRReport bind(Collection<String> fieldNames) throws BindingException {
        // As a new dataset
        JRDesignDataset dataset = new JRDesignDataset(false); // As a sub-dataset
        dataset.setName(JasperConstants.JASPER_DATASET_IDENTIFIER_KEY);
        dataset.setWhenResourceMissingType(WhenResourceMissingTypeEnum.KEY);
        try {
            // Attach fields to dataset
            for (String fieldName : fieldNames) {
                JRDesignField field = new JRDesignField();
                field.setName(fieldName);
                field.setValueClass(java.lang.String.class); // Fields always hold String type
                dataset.addField(field);
            }

            // Attach dataset to report, as a subdataset
            this.reportDesign.addDataset(dataset);
        } catch (JRException e) {
            // Re-throw
            throw new BindingException("Could not bind field metadata to report design.", e);
        }

        return this.reportDesign;
    }

    private void init() {
        // Overrides report settings to display a custom "No Data" section
        this.reportDesign.setWhenNoDataType(WhenNoDataTypeEnum.NO_DATA_SECTION);
        // Replaces "No Data"
        this.reportDesign.setNoData(noData());
    }

    /**
     * A helper method which creates a custom band, used to override or assign a report's "No Data" band for when the
     * fields are not available at runtime.
     *
     * @return A band to be used as a "No Data" replacement band, for a design.
     */
    private static JRBand noData() {
        // TODO Configure a proper "No data band"
        JRDesignBand noDataBand = new JRDesignBand();
        JRStaticText staticText;

        staticText = new JRDesignStaticText();
//        staticText.setX(0);
        staticText.setPrintWhenDetailOverflows(true);
        staticText.setText("No items to display.");
        noDataBand.addElement(staticText);

        noDataBand.setHeight(20); // pixels

        return noDataBand;
    }
}
