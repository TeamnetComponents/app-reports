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

import java.util.Map;

/**
 * TODO Doc
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/10/2015
 */
public class JasperTableComponentCreator {

    public JasperTableComponentCreator(JRReport reportDesign) {
        // TODO report design is required to obtain dataset info?? Not really. What could I use here, instead?
    }

    private static final JRDesignDatasetRun DATASET_RUN;

    static {
        DATASET_RUN = new JRDesignDatasetRun();
        DATASET_RUN.setDatasetName(ConstantsConfig.JASPER_DATASET_IDENTIFIER_KEY);
        JRExpression expression = new JRDesignExpression("$P{" + ConstantsConfig.JASPER_DATASET_IDENTIFIER_KEY + '}');
        DATASET_RUN.setDataSourceExpression(expression);
    }

    /**
     * Dynamically creates a {@link net.sf.jasperreports.components.table.TableComponent table} as a
     * {@link net.sf.jasperreports.engine.JRComponentElement}, from column metadata, to be attached to a report
     * {@link net.sf.jasperreports.engine.JRBand band}.
     *
     * @param columnMetadata A dictionary whose keys represent column identifiers in a datasource and values represent
     *                       the labels associated to the columns.
     * @return A dynamically generated table as a {@link JRComponentElement}.
     */
    public JRComponentElement create(final Map<String, String> columnMetadata) {
        StandardTable table = new StandardTable();
        table.setWhenNoDataType(WhenNoDataTypeTableEnum.ALL_SECTIONS_NO_DETAIL);
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
            // TODO Height calculation ---.
//            cell.setHeight(???);
            cell.addElement(dynamicTextField);
            column.setDetailCell(cell);

            // Add our freshly generated column to the table
            table.addColumn(column);
        }

        JRDesignComponentElement componentElement = new JRDesignComponentElement();
        componentElement.setComponentKey( // Sets type of runtime XML DOM component
                new ComponentKey("http://jasperreports.sourceforge.net/jasperreports/components", "jr", "table")
        );
        componentElement.setComponent(table);
        componentElement.setKey(ConstantsConfig.JASPER_TABLE_IDENTIFIER_KEY);
        componentElement.setWidth(ConstantsConfig.TABLE_MAXIMUM_WIDTH); // As minimum, in pixels
        componentElement.setHeight(100); // As minimum, in pixels

        return componentElement;
    }
}
