package ro.teamnet.solutions.reportinator.config;

import ro.teamnet.solutions.reportinator.config.styles.JasperStyles;

/**
 * A class containing various constants related to JasperReports API configuration, to be used internally.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/11/2015
 */
public final class JasperConstants extends Constants {



    /**
     * A key used to create a parameter, as a placeholder, where to assign the header text at runtime.
     */
    public static final String JASPER_PAGE_HEADER_IDENTIFIER_KEY = "ReportinatorReportHeader";

    /**
     * A key used to create a parameter, as a placeholder, where to assign the footer text at runtime.
     */
    public static final String JASPER_PAGE_FOOTER_IDENTIFIER_KEY = "ReportinatorReportFooter";

    /**
     * A key used to create a parameter, as a placeholder, where to assign the report title text at runtime.
     */
    public static final String JASPER_TITLE_IDENTIFIER_KEY = "ReportinatorReportTitle";

    /**
     * A key used to create a parameter, as a placeholder, where to assign the report subtitle text at runtime.
     */
    public static final String JASPER_SUBTITLE_IDENTIFIER_KEY = "ReportinatorReportTitle";

    /**
     * A key used to create a parameter, as a placeholder, where to assign the report data set at runtime.
     */
    public static final String JASPER_DATASET_IDENTIFIER_KEY = "ReportinatorDataset";

    /**
     * A key used to create a parameter, as a placeholder, where to assign the report data source at runtime.
     */
    public static final String JASPER_DATASOURCE_IDENTIFIER_KEY = "ReportinatorDataSource";

    /**
     * A key used to identify a component key for the tables generated by this library.
     */
    public static final String JASPER_TABLE_IDENTIFIER_KEY = "ReportinatorTable";
    // TODO Documentation for all below
    public static final String JASPER_REPORT_DESIGN_NAME_KEY = "ReportinatorLoadedDesign";
    // TODO Physically create/add landscape and potrait default .JRXML templates below
    public static final String JASPER_JRXML_DEFAULT_PORTRAIT_TEMPLATE_PATH = "src/test/resources/jasper_test_blank_portrait_template.jrxml"; // FUTURE Modify with correct path
    public static final String JASPER_JRXML_DEFAULT_LANDSCAPE_TEMPLATE_PATH = "src/test/resources/jasper_test_blank_landscape_template.jrxml"; // FUTURE Modify with correct path
    public static final String JASPER_TABLE_DEFAULT_STYLE_NAME_IDENTIFIER_KEY = "ReportinatorDefaultTableStyle";
    public static final String JASPER_TABLE_BOX_HEADER_STYLE_NAME_IDENTIFIER_KEY = "ReportinatorHeaderTableBoxStyle";
    public static final String JASPER_TABLE_CONTENT_STYLE_NAME_IDENTIFIER_KEY = "ReportinatorTableContentStyle";
    public static final String JASPER_TABLE_HEADER_STYLE_NAME_IDENTIFIER_KEY = "ReportinatorTableHeaderStyle";
    public static final String JASPER_TABLE_FOOTER_STYLE_NAME_IDENTIFIER_KEY = "ReportinatorTableFooterStyle";
    public static final String JASPER_TABLE_STYLE_NAME_IDENTIFIER_KEY = "ReportinatorTableStyle";
    public static final String JASPER_STYLE_FONT_NAME_IDENTIFIER_KEY = "SansSerif";
    public static final String JASPER_PDF_FONT_NAME_IDENTIFIER_KEY = "Helvetica";
    public static final String JASPER_PDF_ENCODING_IDENTIFIER_KEY = "Cp1252";
    public static final Integer JASPER_MINIMUM_BAND_DETAIL_HEIGHT = 300;
    public static final Integer JASPER_TABLE_MINIMUM__HEADER_CELL_HEIGHT = 2 * JasperStyles.COLUMN_HEADER_STYLE.getStyle().getFontsize().intValue();
    public static final Integer JASPER_MAXIMUM_NUMBER_OF_COLUMNS_FOR_PORTRAIT;

    // Defaults (for above) JasperReports properties
    public static final String DEFAULT_JASPER_MAXIMUM_NUMBER_OF_COLUMNS_FOR_PORTRAIT = "8";

    static {
        String property;
        property = CONFIGURATION_PROPERTIES.getProperty("JASPER_MAXIMUM_NUMBER_OF_COLUMNS_FOR_PORTRAIT", DEFAULT_JASPER_MAXIMUM_NUMBER_OF_COLUMNS_FOR_PORTRAIT);
        JASPER_MAXIMUM_NUMBER_OF_COLUMNS_FOR_PORTRAIT = Integer.valueOf(property);
    }

}
