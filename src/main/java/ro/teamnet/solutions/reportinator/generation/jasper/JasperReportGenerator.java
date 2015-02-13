package ro.teamnet.solutions.reportinator.generation.jasper;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import ro.teamnet.solutions.reportinator.bind.jasper.TableDesignBinder;
import ro.teamnet.solutions.reportinator.config.JasperConstants;
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
                    MessageFormat.format("Generating the print for the report failed. Exception was: {0}", e.getMessage()), e);
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
         * An optional design JRXML file. Set to a default value.
         */
        private JRReport reportDesign = JasperDesign.class.cast(
                DesignLoader.load(new File(JasperConstants.JASPER_DEFAULT_TEMPLATE_RESOURCE_PATH)));

        private JRDataSource reportDataSource;

        private Map<String, String> reportTableAndColumnMetadata;

        /**
         * A constructor which manages a template from an absolute pathname defined by a String value.
         *
         * @param absolutePathnameToJasperXml The path (as a {@code String} to the .JRXML file.
         * @throws ro.teamnet.solutions.reportinator.generation.ReportGeneratorException If anything happens during template loading.
         */
        public Builder(String absolutePathnameToJasperXml) throws ReportGeneratorException {
            try {
                this.reportDesign = JasperDesign.class.cast(
                        DesignLoader.load(new File(absolutePathnameToJasperXml == null ?
                                JasperConstants.JASPER_DEFAULT_TEMPLATE_RESOURCE_PATH :
                                absolutePathnameToJasperXml))
                );
            } catch (LoaderException e) {
                // Re-throw
                throw new ReportGeneratorException(
                        MessageFormat.format("No template file found on path: {0}", absolutePathnameToJasperXml), e);
            }
        }

        public Builder withDatasource(JRDataSource datasource) {
            this.reportDataSource = datasource;

            return this;
        }

        /**
         * Establishes the report's runtime page header text.
         *
         * @param headerText The value of the report's header text.
         * @return
         */
        public Builder withPageHeader(String headerText) {
            this.reportParameters.put(JasperConstants.JASPER_PAGE_HEADER_IDENTIFIER_KEY, headerText);

            return this;
        }

        public Builder withPageFooter(String footerText) {
            this.reportParameters.put(JasperConstants.JASPER_PAGE_FOOTER_IDENTIFIER_KEY, footerText);

            return this;
        }

        public Builder withEncoding(String encoding) {
            // TODO Attach encoding to a style
            return this;
        }

        public Builder withTitle(String titleText) {
            this.reportParameters.put(JasperConstants.JASPER_TITLE_IDENTIFIER_KEY, titleText);

            return this;
        }

        public Builder withSubtitle(String subtitleText) {
            this.reportParameters.put(JasperConstants.JASPER_SUBTITLE_IDENTIFIER_KEY, subtitleText);

            return this;
        }

        /**
         * Establishes a dictionary mapping between table runtime rows-to-column metadata, and runtime column labels
         * information.
         * <p>The dictionary {@code keys} represent the field names to be used to extract data from the data source
         * and they must be the same.</p>
         *
         * @param tableColumnsMetadata A dictionary containing columns metadata and labels.
         * @return The current instance, with assigned column metadata
         */
        public Builder withTableColumnsMetadata(Map<String, String> tableColumnsMetadata) {
            this.reportTableAndColumnMetadata = Collections.unmodifiableMap(tableColumnsMetadata);

            return this;
        }

        /**
         * A method to add extra reportParameters to the underlying dictionary, used for report compilation. Parameters added this
         * way have precedence over defaults (taking into account that they obey the same naming conventions).
         *
         * @see ro.teamnet.solutions.reportinator.config.JasperConstants
         *
         * @param parameters A dictionary containing overridden or extra report reportParameters.
         * @return The current instance, with added parameters.
         */
        public Builder withParameters(Map<String, Object> parameters) {
            // Add everything to our parameter dictionary
            this.reportParameters.putAll(parameters);

            return this;
        }

        public JasperReportGenerator build() throws ReportGeneratorException {
            // TODO Other pre-processing and other algorithms here
            try {
                this.reportDesign =
                        new TableDesignBinder(this.reportDesign).bind(
                                new JasperTableComponentCreator(this.reportDesign).create(this.reportTableAndColumnMetadata));
                this.reportDesign = JasperCompileManager.compileReport((JasperDesign) this.reportDesign);
            } catch (JRException e) {
                throw new ReportGeneratorException(e);
            }

            return new JasperReportGenerator(this);
        }

//        private void validateBuild() {
//            // TODO Validate if builder has all required items
//            // i.e. Check if it contains parameters for title and such
//        }
    }
}
