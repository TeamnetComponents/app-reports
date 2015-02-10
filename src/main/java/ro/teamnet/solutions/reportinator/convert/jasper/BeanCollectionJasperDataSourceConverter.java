package ro.teamnet.solutions.reportinator.convert.jasper;

import net.sf.jasperreports.engine.JRDataSource;
import ro.teamnet.solutions.reportinator.convert.Converter;

import java.util.Collection;

/**
 * TODO Doc
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/6/2015
 */
public final class BeanCollectionJasperDataSourceConverter<B> implements Converter<Collection<B>, JRDataSource> {

    @Override
    public JRDataSource convert(Collection<B> inputSource) {
        // TODO
        return null;
    }

    @Override
    public Collection<String> getFieldMetadata() {
        // TODO
        return null;
    }
}
