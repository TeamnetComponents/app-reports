package ro.teamnet.solutions.reportinator.config;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Reusable constants, for various tests. Contains immutable objects.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/18/2015
 */
public class JasperConstantsTest {

    /**
     * Sample column metadata to be used in tests.
     */
    public static final List<String> COLUMNS_METADATA;

    /**
     * Sample column field and column label metadata to be used in tests.
     */
    public static final Map<String, String> FIELDS_COLUMNS_METADATA;

    /**
     * A Map Collection containing sample data, to be used in tests (or to easily generate data sources from, for tests).
     */
    public static final List<Map<String, ?>> ROWS_MAP_COLLECTION;

    private static final String RESOURCES_ROOT = "src\\test\\resources\\";

    /**
     * Relative path to a blank (unadorned) .JRXML portrait-oriented template to be used in testing.
     */
    public static final String JRXML_BLANK_PORTRAIT_TEMPLATE_PATH = RESOURCES_ROOT.concat("jasper_test_blank_portrait_template.jrxml");

    /**
     * Relative path to a blank (unadorned) .JRXML landscape-oriented template to be used in testing.
     */
    public static final String JRXML_BLANK_LANDSCAPE_TEMPLATE_PATH = RESOURCES_ROOT.concat("jasper_test_blank_landscape_template.jrxml");

    /**
     * Relative path to an empty file, containing just a .JRXML extension to be used in testing.
     */
    public static final String JRXML_FAKE_TEMPLATE_PATH = RESOURCES_ROOT.concat("jasper_invalid_empty_template.jrxml");

    // Initialization data
    static {  // --- DO NOT CHANGE WHAT IS BELOW UNLESS YOU KNOW WHAT YOU ARE DOING !!!
        COLUMNS_METADATA = new LinkedList<String>();
        FIELDS_COLUMNS_METADATA = new LinkedHashMap<String, String>();
        ROWS_MAP_COLLECTION = new LinkedList<Map<String, ?>>();

        // Generate some row data
        Map<String, String> row;
        for (int i = 0; i < 39; i++) {
            row = new LinkedHashMap<String, String>();
            row.put("Col01", Integer.toString(i));
            if (i % 2 == 0) {
                row.put("Col02", "The quick and sneaky brown fox jumped over the lazy dog.");
                row.put("Col05", "The quick and sneaky brown fox jumped over the lazy dog.");
                row.put("Col03", "The quick and sneaky brown fox jumped over the lazy dog.");
                row.put("Col04", "The quick and sneaky brown fox jumped over the lazy dog.");
                row.put("Col06", "The quick and sneaky brown fox jumped over the lazy dog.");
                row.put("Col07", "The quick and sneaky brown fox jumped over the lazy dog.");
            } else {
                row.put("Col02", "The dog slept.");
                row.put("Col05", "The dog slept.");
                row.put("Col03", "The dog slept.");
                row.put("Col04", "The dog slept.");
                row.put("Col06", "The dog slept.");
                row.put("Col07", "The dog slept.");
            }
            ROWS_MAP_COLLECTION.add(row);
        }

        // Grab column metadata from an entry
        COLUMNS_METADATA.addAll(ROWS_MAP_COLLECTION.get(0).keySet());

        // Establish column labels
        int i = 0;
        for (String fieldName : COLUMNS_METADATA) {
            i++;
            FIELDS_COLUMNS_METADATA.put(fieldName, "Column label " + i);
        }
    }


}
