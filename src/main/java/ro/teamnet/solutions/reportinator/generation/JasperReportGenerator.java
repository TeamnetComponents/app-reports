package ro.teamnet.solutions.reportinator.generation;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JasperDesign;
import ro.teamnet.solutions.reportinator.bind.jasper.FieldMetadataDesignBinder;
import ro.teamnet.solutions.reportinator.bind.jasper.StylesDesignBinder;
import ro.teamnet.solutions.reportinator.bind.jasper.TableDesignBinder;
import ro.teamnet.solutions.reportinator.config.JasperConstants;
import ro.teamnet.solutions.reportinator.config.styles.JasperStyles;
import ro.teamnet.solutions.reportinator.create.jasper.TableComponentCreator;
import ro.teamnet.solutions.reportinator.load.JasperDesignLoader;
import ro.teamnet.solutions.reportinator.load.LoaderException;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * A report generator implementation which deals with JasperReports filling and print generation.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/10/2015
 */
public final class JasperReportGenerator implements ReportGenerator<JasperPrint> {

    /**
     * The report parameters dictionary.
     */
    private final Map<String, Object> parameters;

    /**
     * The compiled template, to be used for filling.
     */
    private final JRReport report;

    /**
     * The data source, to be used in the filling process.
     */
    private final JRDataSource dataSource;
    private final Connection connection;

    /**
     * A {@code no-arg} constructor. Should be private since instances of this type are immutable. An instance of this
     * type can be obtained instead using the built-in builder.
     *
     * @see JasperReportGenerator#JasperReportGenerator(ro.teamnet.solutions.reportinator.generation.JasperReportGenerator.Builder)
     */
    private JasperReportGenerator() {
        // FUTURE Log calls to this method
        throw new IllegalStateException("Illegal reflection attack detected and logged.");
    }

    /**
     * A constructor to be used by the underlying builder to create an immutable object, which encompasses everything
     * that is needed to generate a Jasper Report, for exporting to other formats.
     *
     * @param reportBuilder An instance of a proper-configured builder.
     */
    private JasperReportGenerator(Builder reportBuilder) {
        this.parameters = reportBuilder.reportParameters;
        this.report = reportBuilder.reportDesign;
        this.dataSource = reportBuilder.reportDataSource;
        this.connection = reportBuilder.fillConnection;
    }

    /**
     * A factory method which accepts a String, as the absolute or relative path to a report template. This string will
     * be converted to a {@link java.io.File} and then loaded.
     *
     * @param absolutePathnameToJasperXml The path to the report template.
     * @return A builder instance, based on the given template.
     * @throws ReportGeneratorException If builder construction failed.
     */
    public static Builder builder(String absolutePathnameToJasperXml) throws ReportGeneratorException {
        return new Builder(absolutePathnameToJasperXml);
    }

    /**
     * A factory method which accepts an {@link java.io.InputStream} to load report template from.
     *
     * @param jrxmlAsStream The path to the report template.
     * @return A builder instance, based on the given template.
     * @throws ReportGeneratorException If builder construction failed.
     */
    public static Builder builder(InputStream jrxmlAsStream) throws ReportGeneratorException {
        return new Builder(jrxmlAsStream);
    }

    /**
     * A factory method, for a builder, which uses the API's built-in default report template.
     *
     * @return A builder instance, based on the default, built-in, template.
     * @throws ReportGeneratorException If builder construction failed.
     */
    public static Builder builder() throws ReportGeneratorException {
        String reportPath = null; // Specially set, to distinguish between constructors signatures
        return new Builder(reportPath);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JasperPrint generate() throws ReportGeneratorException {
        try {
            if (this.connection == null) {
                return JasperFillManager.fillReport((JasperReport) this.report, this.parameters, this.dataSource);
            } else {
                return JasperFillManager.fillReport((JasperReport) this.report, this.parameters, this.connection);
            }
        } catch (JRException e) {
            throw new ReportGeneratorException(
                    MessageFormat.format("Generating the print for the report failed. An exception occurred: {0}", e.getMessage()), e);
        }
    }

    /**
     * A builder for the final (to be exported) report.
     */
    public static final class Builder {

        /**
         * Holds a Jasper report's runtime parameters.
         */
        private final Map<String, Object> reportParameters = new HashMap<String, Object>();
//        private CyclicBarrier barrier;
        /**
         * An optional design JRXML, loaded when builder is instantiated. Set to a default value.
         */
        private JRReport reportDesign;

        /**
         * A report's data source.
         */
        private JRDataSource reportDataSource;

        /**
         * Holds table metadata (field names and labels).
         */
        private Map<String, String> reportTableAndColumnMetadata;

        {
            // Initialize some parameter defaults
            this.reportParameters.put(JasperConstants.JASPER_PAGE_HEADER_IDENTIFIER_KEY, "");
            this.reportParameters.put(JasperConstants.JASPER_PAGE_FOOTER_IDENTIFIER_KEY, "");
            this.reportParameters.put(JasperConstants.JASPER_TITLE_IDENTIFIER_KEY, "");
            this.reportParameters.put(JasperConstants.JASPER_SUBTITLE_IDENTIFIER_KEY, "");
        }

        private Connection fillConnection;

        /**
         * A constructor which manages a template from an absolute pathname defined by a String value.
         *
         * @param absolutePathnameToJasperXml The path (as a {@code String} to the .JRXML file.
         * @throws ro.teamnet.solutions.reportinator.generation.ReportGeneratorException If anything happens during template loading.
         */
        private Builder(String absolutePathnameToJasperXml) throws ReportGeneratorException {
            try {
                JasperDesign design = JasperDesign.class.cast(
                        JasperDesignLoader.load(
                                new File(absolutePathnameToJasperXml == null ?
                                        JasperConstants.JRXML_DEFAULT_LANDSCAPE_TEMPLATE_PATH :
                                        absolutePathnameToJasperXml))
                );
                if (design != null && absolutePathnameToJasperXml == null) {
                    // Set the name, because a built-in template was loaded
                    design.setName(JasperConstants.JASPER_REPORT_DESIGN_NAME_KEY);
                }
                this.reportDesign = design;

            } catch (LoaderException | NullPointerException e) {
                // Re-throw
                throw new ReportGeneratorException(
                        MessageFormat.format("No template file found for given path: {0}", absolutePathnameToJasperXml), e.getCause());
            }
        }

        /**
         * A constructor which manages a template from an absolute pathname defined by a String value.
         *
         * @param jrxmlReportTemplate The {@code InputStream} of the .JRXML file.
         * @throws ro.teamnet.solutions.reportinator.generation.ReportGeneratorException If anything happens during template loading.
         */
        private Builder(InputStream jrxmlReportTemplate) throws ReportGeneratorException {
            try {
                this.reportDesign = JasperDesign.class.cast(
                        JasperDesignLoader.load(jrxmlReportTemplate));
            } catch (LoaderException e) {
                // Re-throw
                throw new ReportGeneratorException(
                        MessageFormat.format("No template file found for given path: {0}", jrxmlReportTemplate), e.getCause());
            }
        }

        /**
         * Establishes the report's data source (as a runtime parameter), to be used by built-in templates..
         *
         * @param datasource A data source.
         * @return The builder instance, having the data source attached.
         */
        public Builder withDatasource(JRDataSource datasource) {
            if (!this.usingBuiltInTemplates()) {
                throw new IllegalStateException("No data source is required when using a custom JRXML template.");
            }
            this.reportDataSource = Objects.requireNonNull(datasource, "Data source must not be null.");
            // Attach a parameter for the datasource to the design
            JRDesignParameter parameter = new JRDesignParameter();
            parameter.setName(JasperConstants.JASPER_DATASOURCE_IDENTIFIER_KEY);
            parameter.setValueClass(JRDataSource.class);
            try {
                ((JasperDesign) this.reportDesign).addParameter(parameter);
            } catch (JRException e) {
                throw new ReportGeneratorException(
                        MessageFormat.format("Failed to attach datasource as a parameter to the report design. An " +
                                "exception occurred: \n{0}", e.getMessage()),
                        e.getCause());
            }
            // Add it to runtime parameters dictionary as well
            this.reportParameters.put(parameter.getName(), this.reportDataSource);

            return this;
        }

        /**
         * Establishes the report's runtime page header text.
         *
         * @param headerText The value of the report's header text.
         * @return The builder instance, having the header text attached.
         */
        public Builder withPageHeader(String headerText) {
            this.reportParameters.put(JasperConstants.JASPER_PAGE_HEADER_IDENTIFIER_KEY,
                    Objects.requireNonNull(headerText, "Header text must not be null."));

            return this;
        }

        /**
         * Establishes the report's runtime page footer text.
         *
         * @param footerText The value of the report's footer text.
         * @return The builder instance, having the footer text attached.
         */
        public Builder withPageFooter(String footerText) {
            this.reportParameters.put(JasperConstants.JASPER_PAGE_FOOTER_IDENTIFIER_KEY,
                    Objects.requireNonNull(footerText, "Footer text must not be null."));

            return this;
        }

        // FUTURE Attach encoding handling mechanism (encoding must be attached to the Style)
//        public Builder withEncoding(String encoding) {
//
//            return this;
//        }

        /**
         * Establishes the report's runtime title text, as a parameter.
         *
         * @param titleText The value of the report's title text.
         * @return The builder instance, having the title text attached.
         */
        public Builder withTitle(String titleText) {
            this.reportParameters.put(JasperConstants.JASPER_TITLE_IDENTIFIER_KEY,
                    Objects.requireNonNull(titleText, "Report title must not be null."));

            return this;
        }

        /**
         * Establishes the report's runtime subtitle text.
         *
         * @param subtitleText The value of the report's subtitle text.
         * @return The builder instance, having the subtitle text attached.
         */
        public Builder withSubtitle(String subtitleText) {
            this.reportParameters.put(JasperConstants.JASPER_SUBTITLE_IDENTIFIER_KEY,
                    Objects.requireNonNull(subtitleText, "Report subtitle must not be null."));

            return this;
        }

        /**
         * Establishes a dictionary mapping at runtime, between a data source's rows metadata to a table's column and
         * column labels information.
         * <p>The dictionary {@code keys} represent the field names to be used to extract data from the data source,
         * thus they must be the same as the data source's fields.</p>
         *
         * @param tableColumnsMetadata A dictionary containing columns metadata and labels.
         * @return The builder instance, with assigned column metadata.
         */
        public Builder withTableColumnsMetadata(Map<String, String> tableColumnsMetadata) {
            if (!this.usingBuiltInTemplates()) {
                throw new IllegalStateException("Table metadata is required when using a custom JRXML template.");
            }
            this.reportTableAndColumnMetadata = Collections.unmodifiableMap(tableColumnsMetadata);
            // Columns cannot fit into portrait?
            if (tableColumnsMetadata.keySet().size() >= JasperConstants.JASPER_MAXIMUM_NUMBER_OF_COLUMNS_FOR_PORTRAIT) {
                // Load the landscape oriented template
                this.reportDesign = JasperDesign.class.cast(
                        JasperDesignLoader.load(new File(JasperConstants.JRXML_DEFAULT_LANDSCAPE_TEMPLATE_PATH)));
                if (this.reportDesign != null) {
                    // Re-set the name, because a new built-in design was loaded
                    ((JasperDesign) this.reportDesign).setName(JasperConstants.JASPER_REPORT_DESIGN_NAME_KEY);
                }
                if (this.reportDataSource != null) {
                    // Re-inject data source fields into the design
                    return withDatasource(this.reportDataSource);
                }
            }

            return this;

        }

        /**
         * A method to add extra report parameters to the underlying dictionary, used for report compilation. Parameters added this
         * way have precedence over defaults (taking into account that they obey the same naming conventions).
         *
         * @param parameters A dictionary containing overridden or extra report reportParameters.
         * @return The current instance, with added parameters.
         * @see ro.teamnet.solutions.reportinator.config.JasperConstants
         */
        public Builder withParameters(Map<String, Object> parameters) {
            // Add everything to our parameter dictionary
            this.reportParameters.putAll(parameters);

            return this;
        }

        /**
         * Attaches a connection object to be used by custom report templates, during the data filling process. Before
         * assigning a connection, you must load the custom template into the builder.
         *
         * @param connection The connection instance to use.
         * @return The current instance, with added connection.
         * @see #builder(java.io.InputStream)
         */
        public Builder withConnection(Connection connection) {
            if (this.usingBuiltInTemplates()) {
                throw new IllegalStateException("No connection is required, when using the built-in templates. " +
                        "Try using the another builder method.");
            }
            this.fillConnection = Objects.requireNonNull(connection, "Connection must not be null.");

            return this;
        }

        /**
         * Uses the builder data, to create a report generator.
         *
         * @return A report generator, to return an instance.
         * @throws ReportGeneratorException If anything happens during the build process.
         */
        public JasperReportGenerator build() throws ReportGeneratorException {
            validateBuilder();
            CyclicBarrier barrier = new CyclicBarrier(4);
            try {
                new Thread(() -> {
                    // Bind all required styles to report design
                    this.reportDesign = new StylesDesignBinder(this.reportDesign).bind(JasperStyles.asList());
                    try {
                        barrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        throw new ReportGeneratorException(MessageFormat.format("Could not attach styles to the report. " +
                                "An exception occurred: {0}", e.getMessage()), e.getCause());
                    }
                }).start();
                new Thread(() -> {
                    // Bind fields metadata to report design
                    Collection<String> fieldNames = this.reportTableAndColumnMetadata.keySet();
                    this.reportDesign =
                            new FieldMetadataDesignBinder(this.reportDesign).bind(fieldNames);
                    try {
                        barrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        throw new ReportGeneratorException(MessageFormat.format("Could not field metadata to the report. " +
                                "An exception occurred: {0}", e.getMessage()), e.getCause());
                    }
                }).start();
                new Thread(() -> {
                    // Bind table, based on metadata, to report design
                    JRComponentElement tableComponent = // Generate the table
                            new TableComponentCreator(this.reportDesign).create(this.reportTableAndColumnMetadata);
                    this.reportDesign = // Bind it
                            new TableDesignBinder(this.reportDesign).bind(tableComponent);
                    try {
                        barrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        throw new ReportGeneratorException(MessageFormat.format("Could not bind the table to the report. " +
                                "An exception occurred: {0}", e.getMessage()), e.getCause());
                    }
                }).start();

                // This thread must also Wait until all above bindings have been completed
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new ReportGeneratorException(
                        MessageFormat.format("Building failed. An exception occurred: {0} ", e.getMessage()), e.getCause());
            }

            try {
                // Compile report
                this.reportDesign = JasperCompileManager.compileReport((JasperDesign) this.reportDesign);
            } catch (JRException e) {
                throw new ReportGeneratorException(
                        MessageFormat.format("Building failed. An exception occurred while compiling: \n{0} ", e.getMessage()), e.getCause());
            }

            return new JasperReportGenerator(this);
        }

        /**
         * A validation method to do some sanity checks before attempting start the report building process.
         */
        private void validateBuilder() {
            if (this.reportDesign == null) {
                throw new ReportGeneratorException("No report design was attached.");
            }
            if (this.reportDataSource == null ||
                    !this.reportParameters.get(JasperConstants.JASPER_DATASOURCE_IDENTIFIER_KEY).equals(this.reportDataSource)) {
                if (usingBuiltInTemplates()) {
                    throw new ReportGeneratorException("No data source was attached.");
                }
            }
            if (this.reportTableAndColumnMetadata == null) {
                // Throw an exception only if we are using the default templates
                if (usingBuiltInTemplates()) {
                    throw new ReportGeneratorException("No fields and columns metadata was attached.");
                }
            }
        }

        /**
         * A validation method which tests if the builder is using default the built-in templates.
         *
         * @return {@code True} if it is, {@code False} otherwise.
         */
        private boolean usingBuiltInTemplates() {
            return this.reportDesign.getName().equals(JasperConstants.JASPER_REPORT_DESIGN_NAME_KEY);
        }
    }
}
