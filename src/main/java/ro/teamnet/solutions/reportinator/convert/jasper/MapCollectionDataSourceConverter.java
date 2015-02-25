/*
 * Copyright (c) 2015 Teamnet S.A. All Rights Reserved.
 *
 * This source file may not be copied, modified or redistributed,
 * in whole or in part, in any form or for any reason, without the express
 * written consent of Teamnet S.A.
 */

package ro.teamnet.solutions.reportinator.convert.jasper;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import ro.teamnet.solutions.reportinator.convert.ConversionException;
import ro.teamnet.solutions.reportinator.convert.DataSourceConverter;

import java.text.MessageFormat;
import java.util.*;

/**
 * A {@link ro.teamnet.solutions.reportinator.convert.DataSourceConverter} implementation which converts a {@link java.util.Map}
 * {@link java.util.Collection} into a {@link net.sf.jasperreports.engine.JRDataSource} (specific to JasperReports)
 * through some internal heuristics.
 * <p>
 * The dictionary binds its {@code keys} to specific {@code parameters, variables or fields}, to be filled with
 * their respective values at run-time.
 * </p>
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/6/2015
 */
public final class MapCollectionDataSourceConverter implements DataSourceConverter<Collection<Map<String, ?>>, JRDataSource> {

    private final Collection<String> fieldMetadata;
    private Collection<Map<String, ?>> rowsCollection;

    /**
     * Instantiates and assigns required field metadata to a converter. This is then matched with
     * {@link #convert(java.util.Collection)}'s contained metadata.
     *
     * @param fieldMetadata A collection representing the required metadata.
     */
    public MapCollectionDataSourceConverter(Collection<String> fieldMetadata) {
        this.fieldMetadata = fieldMetadata;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JRDataSource convert(Collection<Map<String, ?>> inputSource) throws ConversionException {
        this.rowsCollection = Collections.unmodifiableCollection(
                Objects.requireNonNull(inputSource, "Input source collection must not be null!")
        );
        // Empty collection of items?
        if (this.rowsCollection.size() == 0) {
            throw new ConversionException(
                    MessageFormat.format("Input source was empty (size = {0}). Cannot convert to a valid " +
                            "Jasper data source!", inputSource.size()));
        }
        // Collection lacking required metadata?
        for (Map<String, ?> row : this.rowsCollection) { // Loops just once (on purpose)
            if (!row.keySet().containsAll(this.fieldMetadata)) {
                throw new ConversionException(
                        MessageFormat.format("Input source metadata {0} does not contain all converter required " +
                                "metadata {1}!", inputSource.size(), this.fieldMetadata)
                );
            } else {
                break;
            }
        }

//        return new DataSourceAdapter();

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
     * A custom adapter for a {@link net.sf.jasperreports.engine.JRDataSource}, which uses the map collection as
     * the ADT backing.
     *
     * @see net.sf.jasperreports.engine.data.JRMapCollectionDataSource
     * @deprecated Not used anymore, because a Jasper specific implementation is preferred.
     */
    @Deprecated
    private final class DataSourceAdapter implements JRDataSource {

        private final Iterator<Map<String, ?>> rowIterator;
        private Map<String, ?> currentRow;

        public DataSourceAdapter() {
            this.rowIterator = MapCollectionDataSourceConverter.this.rowsCollection.iterator();

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
                    Objects.requireNonNull(field, "Field must not be null!").getName());
            if (retValue == null) {
                throw new JRException(
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
