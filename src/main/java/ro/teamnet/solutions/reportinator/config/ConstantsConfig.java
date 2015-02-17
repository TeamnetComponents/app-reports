package ro.teamnet.solutions.reportinator.config;

/**
 * A class containing various constants, common to the reporting engine.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/10/2015
 */
public class ConstantsConfig {

//    private ConstantsConfig() throws IllegalAccessException {
//        throw new IllegalAccessException("Constants class should not be instantiated.");
//    }

    /**
     * Maximum allowed table width (in pixels) for an A4 Landscape page format.
     */
    public static final Integer TABLE_MAXIMUM_WIDTH_LANDSCAPE = 792;

    /**
     * Maximum allowed table width (in pixels) for an A4 Portrait page format.
     */
    public static final Integer TABLE_MAXIMUM_WIDTH_PORTRAIT = 555;

    /**
     * Standard Table Border width.
     */
    public static final Float TABLE_BORDER_WIDTH = 0.7f; //usage for styles
}
