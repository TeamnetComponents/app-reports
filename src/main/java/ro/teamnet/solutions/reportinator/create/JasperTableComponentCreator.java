package ro.teamnet.solutions.reportinator.create;

import net.sf.jasperreports.components.table.DesignCell;
import net.sf.jasperreports.components.table.StandardColumn;
import net.sf.jasperreports.components.table.StandardTable;
import net.sf.jasperreports.components.table.WhenNoDataTypeTableEnum;
import net.sf.jasperreports.engine.JRComponentElement;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.component.ComponentKey;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;
import net.sf.jasperreports.engine.design.JRDesignDatasetRun;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.type.StretchTypeEnum;
import ro.teamnet.solutions.reportinator.config.ConstantsConfig;
import ro.teamnet.solutions.reportinator.config.JasperConstants;

import java.text.MessageFormat;
import java.util.Map;

/**
 * TODO Doc
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/10/2015
 */
public final class JasperTableComponentCreator {

    public JasperTableComponentCreator(JRReport reportDesign) {
        // TODO report design is required to obtain dataset info?? Not really. What could I use here, instead?
    }

    private final JRDesignDatasetRun DATASET_RUN;

    { // Initialization data

        // Build dataset parameter runtime identifier
        String datasetParameter = "$P{" + JasperConstants.JASPER_DATASET_IDENTIFIER_KEY + '}';
        DATASET_RUN = new JRDesignDatasetRun();
        DATASET_RUN.setDatasetName(JasperConstants.JASPER_DATASET_IDENTIFIER_KEY);
        JRExpression expression = new JRDesignExpression(datasetParameter);
        DATASET_RUN.setDataSourceExpression(expression);

        // Ensure everything was setup correctly
        assert DATASET_RUN.getDatasetName().equals(JasperConstants.JASPER_DATASET_IDENTIFIER_KEY) :
                MessageFormat.format("Discovered dataset name {0} does not match required name {1}.",
                        DATASET_RUN.getDatasetName(),
                        JasperConstants.JASPER_DATASET_IDENTIFIER_KEY);
        assert DATASET_RUN.getDataSourceExpression().getText().equals(datasetParameter) :
                MessageFormat.format("Discovered dataset parameter {0} does not match required parameter {1}.",
                        DATASET_RUN.getDataSourceExpression().getText(),
                        datasetParameter);
    }

    /**
     * Dynamically creates a {@link net.sf.jasperreports.components.table.TableComponent table} as a
     * {@link net.sf.jasperreports.engine.JRComponentElement}, from column metadata, to be attached to a report
     * {@link net.sf.jasperreports.engine.JRBand band}.
     *
     * @param columnMetadata A dictionary whose keys represent column identifiers in a datasource and values represent
     *                       the labels associated with the columns, to be displayed on the resulting report.
     * @return A dynamically generated table, directly as a {@link JRComponentElement}.
     */
    public JRComponentElement create(final Map<String, String> columnMetadata) {
        final int numberOfColumns = columnMetadata.size();
        final StandardTable table = new StandardTable();
        table.setWhenNoDataType(WhenNoDataTypeTableEnum.ALL_SECTIONS_NO_DETAIL); // Display only headers & footers
        table.setDatasetRun(DATASET_RUN);
        for (Map.Entry<String, String> columnMetadatum : columnMetadata.entrySet()) {
            StandardColumn column = new StandardColumn();
            // TODO Width calculation ---.
//            column.setWidth(???);

            // Define Column headers
            JRDesignExpression expression = new JRDesignExpression("\"" + columnMetadatum.getValue() + "\"");
            JRDesignTextField dynamicTextField = new JRDesignTextField(); // Future Investigate replacing this using StaticText
            dynamicTextField.setExpression(expression);
            dynamicTextField.setStretchWithOverflow(true);
            dynamicTextField.setStretchType(StretchTypeEnum.RELATIVE_TO_TALLEST_OBJECT);
            // TODO Style ---.
//            dynamicTextField.setStyle(columnHeaderStyle???);
            // TODO Width calculation ---.
//            dynamicTextField.setWidth(???);
            // A holder 'box'
            DesignCell cell = new DesignCell();
            // TODO Style ---.
//            cell.setStyle(columnHeaderStyle???);
            // TODO Height calculation ---.
//            cell.setHeight(???);
            cell.addElement(dynamicTextField);
            column.setColumnHeader(cell);

            // Define Detail cells
            expression = new JRDesignExpression("$F{" + columnMetadatum.getKey() + '}');
            dynamicTextField = new JRDesignTextField();
            dynamicTextField.setExpression(expression);
            dynamicTextField.setStretchWithOverflow(true);
            dynamicTextField.setStretchType(StretchTypeEnum.RELATIVE_TO_TALLEST_OBJECT);
            // TODO Style ---.
//            dynamicTextField.setStyle(normalOrDetailStyle???);
            // TODO Width calculation ---.
//            dynamicTextField.setWidth(???);
            cell = new DesignCell();
            // TODO Style ---.
//            cell.setStyle(columnHeaderStyle???);
            // TODO Height calculation ---. // Do-able by taking Style font-size and doubling it
//            cell.setHeight(how???);
            cell.addElement(dynamicTextField);
            column.setDetailCell(cell);

            // Add our freshly generated column to the table
            table.addColumn(column);
        }

        // Everything went okay?
        assert table.getColumns().size() == columnMetadata.entrySet().size() :
                MessageFormat.format("Generated table columns number {0} does not match given dictionary " +
                        "columns number {1}.", table.getColumns().size(), columnMetadata.entrySet().size());

        JRDesignComponentElement componentElement = new JRDesignComponentElement();
        componentElement.setComponentKey( // Sets type of runtime XML DOM component
                new ComponentKey("http://jasperreports.sourceforge.net/jasperreports/components", "jr", "table")
        );
        componentElement.setComponent(table);
        componentElement.setKey(JasperConstants.JASPER_TABLE_IDENTIFIER_KEY);
        componentElement.setWidth(ConstantsConfig.TABLE_MAXIMUM_WIDTH_LANDSCAPE); // As minimum, in pixels
        componentElement.setHeight(100); // As minimum, in pixels

        return componentElement;
    }
}
