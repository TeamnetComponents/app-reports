package ro.teamnet.solutions.reportinator.convert.jasper;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import ro.teamnet.solutions.reportinator.convert.Converter;

import java.sql.ResultSet;
import java.util.Collection;

/**
 * Converts a {@link java.sql.ResultSet} to a {@link net.sf.jasperreports.engine.JRDataSource}, specific to
 * JasperReports, through some internal heuristics.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/6/2015
 */
public final class SqlResultSetJasperDataSourceConverter implements Converter<ResultSet, JRDataSource> {

    /**
     * A helper class which adapts a {@link java.sql.ResultSet} to a {@link net.sf.jasperreports.engine.JRDataSource}.
     */
    private static final class SqlResultSetJasperAdapter implements JRDataSource {

        @Override
        public boolean next() throws JRException {
            return false;
        }

        @Override
        public Object getFieldValue(JRField jrField) throws JRException {
            return null;
        }
    }

    @Override
    public JRDataSource convert(ResultSet inputSource) {
        // TODO Implement this
        return new SqlResultSetJasperAdapter();
    }

    @Override
    public Collection<String> getFieldMetanames() {
        // TODO
        return null;
    }
}
