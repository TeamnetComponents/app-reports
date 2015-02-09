package ro.teamnet.solutions.reportinator.convert.jasper;

import net.sf.jasperreports.engine.JRDataSource;
import ro.teamnet.solutions.reportinator.convert.Converter;

import java.sql.Connection;
import java.util.Collection;

/**
 * Converts the results of a SQL query string execution to a {@link net.sf.jasperreports.engine.JRDataSource} (specific to
 * JasperReports) through some internal heuristics.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/6/2015
 */
public final class SqlQueryJasperDataSourceConverter implements Converter<String, JRDataSource> {

    private Connection connection;

    public SqlQueryJasperDataSourceConverter(Connection connection) {
        this.connection = connection;
    }

    @Override
    public JRDataSource convert(String sqlString) {
        // TODO Implement this
        return null;
    }

    @Override
    public Collection<String> getFieldMetanames() {
        // TODO
        return null;
    }

}
