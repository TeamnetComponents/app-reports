package ro.teamnet.solutions.reportinator.config;

/**
 * TODO Doc
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/10/2015
 */
public class ConstantsConfig {

    private ConstantsConfig() throws IllegalAccessException {
        throw new IllegalAccessException("Constants class should not be instantiated.");
    }

    public static final String JASPER_TITLE_IDENTIFIER_KEY = "ReportinatorReportTitle";

    public static final String JASPER_SUBTITLE_IDENTIFIER_KEY = "ReportinatorReportTitle";

    public static final String JASPER_DATASET_IDENTIFIER_KEY = "ReportinatorDataset";

    public static final String JASPER_DATASOURCE_IDENTIFIER_KEY = "ReportinatorDataSource";

    public static final String JASPER_TABLE_IDENTIFIER_KEY = "ReportinatorTable";

    public static final Integer TABLE_MAXIMUM_WIDTH = 700;
}
