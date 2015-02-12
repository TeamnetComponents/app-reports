package ro.teamnet.solutions.reportinator;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.design.JasperDesign;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO Doc
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/10/2015
 */
public class JasperReportBuilder {

    private final Map<String, ?> reportParameters = new HashMap<String, Object>();

    private JasperDesign reportDesign;

    private JRDataSource reportDatasource;

    public JasperReportBuilder withDatasource(JRDataSource datasource) {
        // TODO Attach datasource?
        return this;
    }

    public JasperReportBuilder withPageHeader(String headerText) {
        // TODO Attach header as a parameter

        return this;
    }

    public JasperReportBuilder withPageFooter(String footerText) {
        // TODO Attach footer as a parameter

        return this;
    }

    public JasperReportBuilder withTitle(String title) {
        // TODO Attach title as a parameter

        return this;
    }

    public JasperReportBuilder withSubtitle(String subtitle) {
        // TODO Attach subtitle as a parameter
        return this;
    }

    public JasperReportBuilder withTableColumns(Map<String, String> tableColumnsMetadata) {
        // TODO Attach as a dictionary
        return this;
    }

    public JasperReportBuilder withParameters(Map<String, ?> parameters) {
        // TODO Add parameters to local ones
        return this;
    }

    public JRReport build() throws ReportBuilderException {
        // TODO Other pre-processing and other algorithms here
        try {
            return JasperCompileManager.compileReport(this.reportDesign);
        } catch (JRException e) {
            throw new ReportBuilderException(e);
        }
    }
}
