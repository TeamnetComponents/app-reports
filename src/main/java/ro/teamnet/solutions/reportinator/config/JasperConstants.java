package ro.teamnet.solutions.reportinator.config;

import ro.teamnet.solutions.reportinator.config.styles.JasperStyles;

/**
 * TODO Doc
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/11/2015
 */
public final class JasperConstants extends ConstantsConfig {

    public static final String JASPER_PAGE_HEADER_IDENTIFIER_KEY = "ReportinatorReportHeader";
    public static final String JASPER_PAGE_FOOTER_IDENTIFIER_KEY = "ReportinatorReportFooter";
    public static final String JASPER_TITLE_IDENTIFIER_KEY = "ReportinatorReportTitle";
    public static final String JASPER_SUBTITLE_IDENTIFIER_KEY = "ReportinatorReportTitle";
    public static final String JASPER_DATASET_IDENTIFIER_KEY = "ReportinatorDataset";
    public static final String JASPER_DATASOURCE_IDENTIFIER_KEY = "ReportinatorDataSource";
    public static final String JASPER_TABLE_IDENTIFIER_KEY = "ReportinatorTable";
    // TODO Create/add landscape and potrait default .JRXML templates
    public static final String JASPER_DEFAULT_TEMPLATE_RESOURCE_PATH = "reports/template/default_template.jrxml"; // to be modified with explicit Path
    public static final String JASPER_TEST_TEMPLATE_RESOURCE_PATH = "src/test/resources/TestTemplate.jrxml"; // to be modified with explicit Path
    public static final String JASPER_TABLE_DEFAULT_STYLE_NAME_IDENTIFIER_KEY = "ReportinatorDefaultTableStyle";
    public static final String JASPER_TABLE_CONTENT_STYLE_NAME_IDENTIFIER_KEY = "ReportinatorTableContentStyle";
    public static final String JASPER_TABLE_HEADER_STYLE_NAME_IDENTIFIER_KEY = "ReportinatorTableHeaderStyle";
    public static final String JASPER_TABLE_FOOTER_STYLE_NAME_IDENTIFIER_KEY = "ReportinatorTableFooterStyle";
    public static final String JASPER_TABLE_STYLE_NAME_IDENTIFIER_KEY = "ReportinatorTableStyle";
    public static final String JASPER_STYLE_FONT_NAME_IDENTIFIER_KEY = "SansSerif";
    public static final String JASPER_PDF_FONT_NAME_IDENTIFIER_KEY = "Helvetica";
    public static final String JASPER_PDF_ENCODING_IDENTIFIER_KEY = "Cp1252";
    public static final Integer JASPER_MINIMUM_BAND_DETAIL_HEIGHT = 300;
    public static final Integer JASPER_TABLE_MINIMUM__HEADER_CELL_HEIGHT = 2 * JasperStyles.COLUMN_HEADER_STYLE.getStyle().getFontsize().intValue();
}
