package ro.teamnet.solutions.reportinator;

import net.sf.jasperreports.engine.*;
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

    public JasperReportBuilder withTitle(String title) {
        // TODO Attach title as a parameter

        return this;
    }

    public JasperReportBuilder withSubtitle(String subtitle) {
        // TODO Attach subtitle as a parameter
        return this;
    }

    public JasperReportBuilder withTableComponent(JRComponentElement tableComponent) {
        // TODO Attach table from creator
        return this;
    }

    public JasperReportBuilder withParameters(Map<String, ?> parameters) {
        // TODO Add parameters to local ones
        return this;
    }

    public JRReport build() throws ReportBuilderException {
        try {
            return JasperCompileManager.compileReport(this.reportDesign);
        } catch (JRException e) {
            throw new ReportBuilderException(e);
        }
    }
}
