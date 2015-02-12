package ro.teamnet.solutions.reportinator.convert.jasper;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import ro.teamnet.solutions.reportinator.convert.ConversionException;
import ro.teamnet.solutions.reportinator.convert.Converter;

import java.text.MessageFormat;
import java.util.*;

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
public final class MapCollectionJasperDataSourceConverter implements Converter<List<Map<String, ?>>, JRDataSource> {

    private final Collection<String> fieldMetadata;
    private List<Map<String, ?>> rowsCollection;

    /**
     * Instantiates and assigns required field metadata to a converter. This is then matched with
     * {@link #convert(java.util.List)}'s contained metadata.
     *
     * @param fieldMetadata A collection representing the required metadata.
     */
    public MapCollectionJasperDataSourceConverter(Collection<String> fieldMetadata) {
        this.fieldMetadata = fieldMetadata;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JRDataSource convert(List<Map<String, ?>> inputSource) throws ConversionException {
        this.rowsCollection = Collections.unmodifiableList(
                Objects.requireNonNull(inputSource, "Input source collection must not be null!")
        );
        // Empty collection of items?
        if (this.rowsCollection.size() == 0) {
            throw new ConversionException(  // TODO ----.    Maybe map message below to an i18n key?
                    MessageFormat.format("Input source was empty (size = {0}). Cannot convert to a valid " +
                            "Jasper data source!", inputSource.size())
            );
        }
        // Collection lacking required metadata?
        if (!this.rowsCollection.get(0).keySet().containsAll(this.fieldMetadata)) {
            throw new ConversionException(  // TODO ----.    Maybe map message below to an i18n key?
                    MessageFormat.format("Input source metadata {0} does not contain all converter required " +
                            "metadata {1}!", inputSource.size(), this.fieldMetadata)
            );
        }

//        return new JasperDataSourceAdapter();

        // Use built-in Jasper adapter
        return new JRMapCollectionDataSource(this.rowsCollection);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> getFieldMetadata() {
        return this.fieldMetadata;
    }

    /**
     * A custom internal adapter for a {@link net.sf.jasperreports.engine.JRDataSource}.
     * <p>Not used anymore, because a native implementation is preferred.</p>
     * @see net.sf.jasperreports.engine.data.JRMapCollectionDataSource
     */
    @Deprecated
    private class JasperDataSourceAdapter implements JRDataSource {

        private final Iterator<Map<String, ?>> rowIterator;
        private Map<String, ?> currentRow;

        public JasperDataSourceAdapter() {
            this.rowIterator = MapCollectionJasperDataSourceConverter.this.rowsCollection.iterator();

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean next() throws JRException {
            boolean retValue = this.rowIterator.hasNext();
            try {
                this.currentRow = this.rowIterator.next();
            } catch (NoSuchElementException e) {
                // Re-throw
                throw new JRException("No more dictionary elements.", e);
            }

            return retValue;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object getFieldValue(JRField field) throws JRException {
            Object retValue = this.currentRow.get(
                    Objects.requireNonNull(field, "Field must not be null!").getName()
            );
            if (retValue == null) {
                throw new JRException( // TODO ----.    Maybe map message below to an i18n key?
                        MessageFormat.format("Given field {0} cannot be matched to a " +
                                "valid key of the current row.", field.getName()));
            }
            // Return types differ?
            if (!retValue.getClass().equals(field.getValueClass())) {
                throw new JRException(
                        MessageFormat.format("Retrieved value type {0} does not match required return type {1}.",
                        retValue.getClass().getName(),
                        field.getValueClassName())
                );
            }
            return retValue;

        }
    }
}
