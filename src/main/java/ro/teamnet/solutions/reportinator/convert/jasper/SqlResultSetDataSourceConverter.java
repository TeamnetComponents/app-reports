package ro.teamnet.solutions.reportinator.convert.jasper;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import ro.teamnet.solutions.reportinator.convert.DataSourceConverter;

import java.sql.ResultSet;
import java.util.Collection;

/**
 * Converts a {@link java.sql.ResultSet} to a {@link net.sf.jasperreports.engine.JRDataSource}, specific to
 * JasperReports, through some internal heuristics.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/6/2015
 * @deprecated <strong>NOT IMPLEMENTED YET.</strong>
 */
@Deprecated
public final class SqlResultSetDataSourceConverter implements DataSourceConverter<ResultSet, JRDataSource> {

    @Override
    public JRDataSource convert(ResultSet inputSource) {
        // FUTURE Implement this
        throw new IllegalStateException("Not implemented yet.");
    }

    @Override
    public Collection<String> getFieldMetadata() {
        // FUTURE Implement this
        throw new IllegalStateException("Not implemented yet.");
    }

    /**
     * A helper class which adapts a {@link java.sql.ResultSet} to a {@link net.sf.jasperreports.engine.JRDataSource}.
     */
    private static final class DataSourceAdapter implements JRDataSource {

        @Override
        public boolean next() throws JRException {
            // FUTURE Implement this
            throw new IllegalStateException("Not implemented yet.");
        }

        @Override
        public Object getFieldValue(JRField jrField) throws JRException {
            // FUTURE Implement this
            throw new IllegalStateException("Not implemented yet.");
        }
    }
}
