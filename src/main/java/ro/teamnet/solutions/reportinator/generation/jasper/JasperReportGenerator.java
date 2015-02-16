package ro.teamnet.solutions.reportinator.generation.jasper;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import ro.teamnet.solutions.reportinator.bind.jasper.FieldMetadataDesignBinder;
import ro.teamnet.solutions.reportinator.bind.jasper.StylesDesignBinder;
import ro.teamnet.solutions.reportinator.bind.jasper.TableDesignBinder;
import ro.teamnet.solutions.reportinator.config.JasperConstants;
import ro.teamnet.solutions.reportinator.config.styles.JasperStyles;
import ro.teamnet.solutions.reportinator.create.JasperTableComponentCreator;
import ro.teamnet.solutions.reportinator.generation.ReportGenerator;
import ro.teamnet.solutions.reportinator.generation.ReportGeneratorException;
import ro.teamnet.solutions.reportinator.load.LoaderException;
import ro.teamnet.solutions.reportinator.load.jasper.DesignLoader;

import java.io.File;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A report generator implementation which deals with Jasper Reports generation.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/10/2015
 */
public final class JasperReportGenerator implements ReportGenerator<JasperPrint> {

    private final Map<String, Object> parameters;

    private final JRReport report;

    private final JRDataSource dataSource;

    /**
     * No-arg constructor. Should be private since instances of this type are immutable.
     *
     * @see JasperReportGenerator#JasperReportGenerator()
     */
    private JasperReportGenerator() {
        // TODO Log access to this class
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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JasperPrint generate() throws ReportGeneratorException {
        try {
            return JasperFillManager.fillReport((JasperReport) this.report, this.parameters, this.dataSource);
        } catch (JRException e) {
            throw new ReportGeneratorException(
                    MessageFormat.format("Generating the print for the report failed. An exception occurred: {0}", e.getMessage()), e);
        }
    }

    /**
     * A builder class for the final (to be exported) report.
     */
    public static class Builder {

        /**
         * Holds a Jasper report's runtime parameters.
         */
        private final Map<String, Object> reportParameters = new HashMap<String, Object>();

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

        /**
         * A constructor which manages a template from an absolute pathname defined by a String value.
         *
         * @param absolutePathnameToJasperXml The path (as a {@code String} to the .JRXML file.
         * @throws ro.teamnet.solutions.reportinator.generation.ReportGeneratorException If anything happens during template loading.
         */
        private Builder(String absolutePathnameToJasperXml) throws ReportGeneratorException {
            try {
                this.reportDesign = JasperDesign.class.cast(
                        DesignLoader.load(new File(absolutePathnameToJasperXml == null ?
                                JasperConstants.JASPER_DEFAULT_TEMPLATE_RESOURCE_PATH :
                                absolutePathnameToJasperXml))
                );
            } catch (LoaderException e) {
                // Re-throw
                throw new ReportGeneratorException(
                        MessageFormat.format("No template file found for given path: {0}", absolutePathnameToJasperXml), e);
            }
        }

        /**
         * A factory method which accepts a String, as the path to a report template.
         *
         * @param absolutePathnameToJasperXml The path to the report template.
         * @return A builder instance, based on the given template.
         * @throws ReportGeneratorException If builder construction failed.
         */
        public static Builder builder(String absolutePathnameToJasperXml) throws ReportGeneratorException {
            return new Builder(absolutePathnameToJasperXml);
        }

        /**
         * A factory method, for a builder
         *
         * @return A builder instance, based on the default, built-in, template.
         * @throws ReportGeneratorException If builder construction failed.
         */
        public static Builder builder() throws ReportGeneratorException {
            return new Builder(null);
        }

        /**
         * Establishes the report's data source.
         *
         * @param datasource A data source.
         * @return The builder instance, having the data source attached.
         */
        public Builder withDatasource(JRDataSource datasource) {
            Objects.requireNonNull(datasource, "Data source must not be null.");
            this.reportDataSource = datasource;

            return this;
        }

        /**
         * Establishes the report's runtime page header text.
         *
         * @param headerText The value of the report's header text.
         * @return The builder instance, having the header text attached.
         */
        public Builder withPageHeader(String headerText) {
            this.reportParameters.put(JasperConstants.JASPER_PAGE_HEADER_IDENTIFIER_KEY, headerText == null ? "" : headerText);

            return this;
        }

        /**
         * Establishes the report's runtime page footer text.
         *
         * @param footerText The value of the report's footer text.
         * @return The builder instance, having the footer text attached.
         */
        public Builder withPageFooter(String footerText) {
            this.reportParameters.put(JasperConstants.JASPER_PAGE_FOOTER_IDENTIFIER_KEY, footerText == null ? "" : footerText);

            return this;
        }

        // TODO Attach encoding to a style
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
            this.reportParameters.put(JasperConstants.JASPER_TITLE_IDENTIFIER_KEY, titleText == null ? "" : titleText);

            return this;
        }

        /**
         * Establishes the report's runtime subtitle text.
         *
         * @param subtitleText The value of the report's subtitle text.
         * @return The builder instance, having the subtitle text attached.
         */
        public Builder withSubtitle(String subtitleText) {
            this.reportParameters.put(JasperConstants.JASPER_SUBTITLE_IDENTIFIER_KEY, subtitleText == null ? "" : subtitleText);

            return this;
        }

        /**
         * Establishes a dictionary mapping between table runtime rows-to-column metadata, and runtime column labels
         * information.
         * <p>The dictionary {@code keys} represent the field names to be used to extract data from the data source
         * and they must be the same.</p>
         *
         * @param tableColumnsMetadata A dictionary containing columns metadata and labels.
         * @return The builder instance, with assigned column metadata.
         */
        public Builder withTableColumnsMetadata(Map<String, String> tableColumnsMetadata) {
            this.reportTableAndColumnMetadata = Collections.unmodifiableMap(tableColumnsMetadata);

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
         * Uses the builder data, to create a report generator.
         *
         * @return A report generator, to return an instance.
         * @throws ReportGeneratorException If
         */
        public JasperReportGenerator build() throws ReportGeneratorException {
            validateBuilder();
            try {
                // Bind all required styles to report design
                this.reportDesign = new StylesDesignBinder(this.reportDesign).bind(JasperStyles.asList());
                // Bind fields metadata to report design
                this.reportDesign =
                        new FieldMetadataDesignBinder(this.reportDesign).bind(this.reportTableAndColumnMetadata.keySet());
                // Bind table, based on metadata, to report design
                JRComponentElement tableComponent = new JasperTableComponentCreator(this.reportDesign).create(this.reportTableAndColumnMetadata);
                this.reportDesign =
                        new TableDesignBinder(this.reportDesign).bind(tableComponent);
                // Compile report
                this.reportDesign = JasperCompileManager.compileReport((JasperDesign) this.reportDesign); // TODO Investigate why this is always null?
            } catch (JRException e) {
                throw new ReportGeneratorException(MessageFormat.format("Building failed. An exception occurred: {0} ", e.getMessage()), e);
            }

            return new JasperReportGenerator(this);
        }

        private void validateBuilder() {
            if (this.reportDataSource == null) {
                throw new ReportGeneratorException("Builder must have a non-null data source.");
            }

            if (this.reportTableAndColumnMetadata == null) {
                throw new ReportGeneratorException("Builder must have non-null fields and column metadata.");
            }
        }
    }
}
