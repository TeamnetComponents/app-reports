package ro.teamnet.solutions.reportinator.convert.jasper;

import net.sf.jasperreports.engine.JRDataSource;
import ro.teamnet.solutions.reportinator.convert.Converter;

import java.util.Collection;
import java.util.Map;

/**
 * A {@link ro.teamnet.solutions.reportinator.convert.Converter} implementation which converts a {@link java.util.Map}
 * {@link java.util.Collection} into a {@link net.sf.jasperreports.engine.JRDataSource} (specific to JasperReports)
 * through some internal heuristics.
 * <p>
 *     The dictionary binds its {@code keys} to specific {@code parameters, variables or fields}, to be filled with
 *     their respective values at run-time.
 * </p>
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/6/2015
 */
public final class MapCollectionJasperDataSourceConverter implements Converter<Collection<Map<String, ?>>, JRDataSource> {

    /**
     * {@inheritDoc}
     */
    @Override
    public JRDataSource convert(Collection<Map<String, ?>> inputSource) {
        // TODO Implement this
        return null;
    }
}
