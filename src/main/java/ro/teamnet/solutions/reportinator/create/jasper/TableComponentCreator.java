package ro.teamnet.solutions.reportinator.create.jasper;

import net.sf.jasperreports.components.table.DesignCell;
import net.sf.jasperreports.components.table.StandardColumn;
import net.sf.jasperreports.components.table.StandardTable;
import net.sf.jasperreports.components.table.WhenNoDataTypeTableEnum;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.component.ComponentKey;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.type.StretchTypeEnum;
import ro.teamnet.solutions.reportinator.config.Constants;
import ro.teamnet.solutions.reportinator.config.JasperConstants;
import ro.teamnet.solutions.reportinator.config.styles.JasperStyles;
import ro.teamnet.solutions.reportinator.create.ComponentCreator;
import ro.teamnet.solutions.reportinator.create.CreationException;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;

/**
 * A creator implementation, which creates a table, as a {@link net.sf.jasperreports.engine.JRComponentElement} to be
 * used in JasperReports template designs.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/10/2015
 */
public final class TableComponentCreator implements ComponentCreator<JRComponentElement, Map<String, String>> {

    private static final JRDesignDatasetRun DATASET_RUN;

    static { // Initialization data
        DATASET_RUN = new JRDesignDatasetRun();
        // Assign a dataset
        DATASET_RUN.setDatasetName(JasperConstants.JASPER_DATASET_IDENTIFIER_KEY);
        // Build datasource parameter runtime identifier
        String dataSourceAsParameter = "$P{" + JasperConstants.JASPER_DATASOURCE_IDENTIFIER_KEY + '}';
        // Attach data source parameter
        JRExpression dataSourceExpression = new JRDesignExpression(dataSourceAsParameter);
        DATASET_RUN.setDataSourceExpression(dataSourceExpression);


        // Ensure everything was setup correctly
        assert DATASET_RUN.getDatasetName().equals(JasperConstants.JASPER_DATASET_IDENTIFIER_KEY) :
                MessageFormat.format("Discovered dataset name {0} does not match required name {1}.",
                        DATASET_RUN.getDatasetName(),
                        JasperConstants.JASPER_DATASET_IDENTIFIER_KEY);
        assert DATASET_RUN.getDataSourceExpression().getText().equals(dataSourceAsParameter) :
                MessageFormat.format("Discovered dataset parameter {0} does not match required parameter {1}.",
                        DATASET_RUN.getDataSourceExpression().getText(),
                        dataSourceAsParameter);
    }

    private final JasperDesign reportDesign;


    public TableComponentCreator(JRReport reportDesign) {
        this.reportDesign = JasperDesign.class.cast(
                Objects.requireNonNull(reportDesign, "Jasper report reference must not be null."));
    }

    /**
     * A helper method which computes the column width, equally divided between the column number and orientation
     * permitted width.
     *
     * @param numberOfColumns The total number of columns, for the table.
     * @return The width of a column.
     */
    private static int calculateColumnWidth(JasperDesign reportDesign, int numberOfColumns) {
        int calculatedWidth = JasperConstants.TABLE_MAXIMUM_WIDTH_PORTRAIT / numberOfColumns;
        switch (reportDesign.getOrientationValue()) {
            case LANDSCAPE:
                calculatedWidth = JasperConstants.TABLE_MAXIMUM_WIDTH_LANDSCAPE / numberOfColumns;
                break;
            default:
                break;
        }

        return calculatedWidth;
    }

    /**
     * Determines a text field's width, based on the surounding column container, accounting for left and right padding.
     *
     * @param element     The text field.
     * @param columnWidth The maximum allowed width for the column.
     * @return The calculated width of the text field.
     */
    private static int determineWidth(JRTextField element, int columnWidth) {
        return columnWidth - element.getStyle().getLineBox().getLeftPadding() - element.getLineBox().getRightPadding();
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
    @Override
    public JRComponentElement create(final Map<String, String> columnMetadata) {
        if (columnMetadata == null) {
            throw new IllegalArgumentException("Column metadata must not be null.");
        }
        final int numberOfColumns = columnMetadata.size();
        final int columnWidth = calculateColumnWidth(this.reportDesign, numberOfColumns);
        final StandardTable table = new StandardTable();
        table.setWhenNoDataType(WhenNoDataTypeTableEnum.ALL_SECTIONS_NO_DETAIL); // Display only headers & footers
        table.setDatasetRun(DATASET_RUN);
        for (Map.Entry<String, String> columnMetadatum : columnMetadata.entrySet()) {
            StandardColumn column = new StandardColumn();
            column.setWidth(columnWidth);
            // Define Column headers
            DesignCell cell = new DesignCell();
            cell.setStyle(JasperStyles.HEADER_TABLE_STYLE.getStyle());
            cell.setHeight(JasperConstants.JASPER_TABLE_MINIMUM__HEADER_CELL_HEIGHT);
            // Attach runtime text holder
            String columnLabelExpression = "\"" + columnMetadatum.getValue() + "\"";
            cell.addElement(
                    generateHolderTextBox(columnLabelExpression, JasperStyles.COLUMN_HEADER_STYLE.getStyle(), columnWidth));
            column.setColumnHeader(cell);

            // Define Detail cells
            cell = new DesignCell();
            cell.setStyle(JasperStyles.TABLE_STYLE.getStyle());
            cell.setHeight(JasperConstants.JASPER_TABLE_MINIMUM__HEADER_CELL_HEIGHT);
            // Attach runtime text holder
            String columnContentExpression = "$F{" + columnMetadatum.getKey() + '}';
            cell.addElement(
                    generateHolderTextBox(columnContentExpression, JasperStyles.COLUMN_CONTENT_STYLE.getStyle(), columnWidth));
            column.setDetailCell(cell);

            // Add our freshly generated column to the table
            table.addColumn(column);
        }

        if (table.getColumns().size() != columnMetadata.entrySet().size()) {
            throw new CreationException(MessageFormat.format("Generated table columns number {0} does not match given dictionary " +
                    "columns number {1}.", table.getColumns().size(), columnMetadata.entrySet().size()));
        }

        JRDesignComponentElement componentElement = new JRDesignComponentElement();
        componentElement.setComponentKey( // Sets type of runtime XML DOM component
                new ComponentKey("http://jasperreports.sourceforge.net/jasperreports/components", "jr", "table"));
        componentElement.setComponent(table);
        componentElement.setKey(JasperConstants.JASPER_TABLE_IDENTIFIER_KEY);
        componentElement.setWidth(Constants.TABLE_MAXIMUM_WIDTH_LANDSCAPE); // As minimum, in pixels
        componentElement.setHeight(100); // As minimum, in pixels

        return componentElement;
    }

    /**
     * A helper method which creates a {@link net.sf.jasperreports.engine.design.JRDesignTextField} as a holder box for
     * the column content, using the given constraints. An expression is required to fill it with explicit content, during
     * the <em>filling process</em>. This holder box must also obey a maximum permitted {@code width}, given its
     * {@code style} (and left/right padding).
     *
     * @param columnExpressionText An JasperReports expression, to be used during report filling for data binding.
     * @param columnStyle A JasperReports style to be applied to the column element, during generation.
     * @param maximumPermittedWidth The maximum permitted width, to fit the holder box in.
     * @return A holder box, with the given <em>constraints</em>.
     */
    private static JRDesignTextField generateHolderTextBox(String columnExpressionText, JRStyle columnStyle, int maximumPermittedWidth) {
        JRDesignExpression expression = new JRDesignExpression(columnExpressionText);
        JRDesignTextField dynamicTextField = new JRDesignTextField(); // FUTURE Investigate replacing this with StaticText fields (for column labels)
        dynamicTextField.setExpression(expression);
        dynamicTextField.setStretchWithOverflow(true);
        dynamicTextField.setStretchType(StretchTypeEnum.RELATIVE_TO_TALLEST_OBJECT);
        dynamicTextField.setStyle(columnStyle);
        int determinedWidth = determineWidth(dynamicTextField, maximumPermittedWidth);
        dynamicTextField.setWidth(determinedWidth);

        return dynamicTextField;
    }
}
