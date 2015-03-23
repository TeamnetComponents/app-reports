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
import ro.teamnet.solutions.reportinator.convert.ConversionException;
import ro.teamnet.solutions.reportinator.convert.DataSourceConverter;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.*;

/**
 * Class that converts a collection of beans into a JRDataSource
 *
 * @author Bogdan.Iancu
 * @author Bogdan.Stefan
 * @version 1.0.1 Date: 2015-03-10
 * @since 1.0 Date: 2015-02-06
 */
public final class BeanCollectionJasperDataSourceConverter<B> implements DataSourceConverter<Collection<B>, JRDataSource> {

    private final List<String> fieldMetadata;

    /**
     * Constructor for class
     *
     * @param fieldMetadata collection which specifies what fields of the bean will be added to the data source
     */
    public BeanCollectionJasperDataSourceConverter(Collection<String> fieldMetadata) {
        if (fieldMetadata == null || fieldMetadata.size() == 0) {
            throw new ConversionException("Field Metadata should not be null");
        }
        this.fieldMetadata = new ArrayList<>(fieldMetadata);
    }

    /**
     * Method that converts a collection of beans to a JRDataSource.
     *
     * @param aBeanCollection The collection of beans to be converted.
     * @return A JRDataSource consisting of the selected fields in the bean collection
     */
    @Override
    public JRDataSource convert(Collection<B> aBeanCollection) throws ConversionException, NullPointerException {
        Collection<B> beanCollection = Collections.unmodifiableCollection(
                Objects.requireNonNull(aBeanCollection, "Input bean collection must not be null!")
        );
        if (beanCollection.isEmpty()) {
            throw new ConversionException("The bean collection must not be empty.");
        }

        List<List<String>> rows = new ArrayList<>();
        List<Field> fields = null;

        Iterator<B> iterator = beanCollection.iterator();
        Object o1 = iterator.next();
        fields = getSelectedFields(o1.getClass());
        List<String> row = parseRow(o1, fields);

        //We need to add an empty row because jasper reports jumps over the first row
        //FUTURE remove this (in case of jasper bug fix)
        rows.add(new ArrayList<String>());

        rows.add(row);

        while (iterator.hasNext()) {
            row = parseRow(iterator.next(), fields);
            rows.add(row);
        }

        return new DataSourceAdapter(rows);
    }

    /**
     * Method that gets all the desired fields of the class including inherited fields
     *
     * @param c The class that will be scanned for fields
     * @return a list of the selected fields
     * @throws ro.teamnet.solutions.reportinator.convert.ConversionException if the field isn't found in the class or any of it's superclasses
     */
    private List<Field> getSelectedFields(Class c) {
        List<Field> fields = new ArrayList<>();

        for (String fieldName : fieldMetadata) {
            try {
                Field field = getSelectedField(null, fieldName, c);
                fields.add(field);
            } catch (NoSuchFieldException e) {
                throw new ConversionException("Exception getting field named " + fieldName, e.getCause());
            }
        }
        return fields;
    }

    /**
     * Method that travels recursively on the superclasses of a class to get a desired field
     *
     * @param field     the value of the desired field used for recursivity , MUST be null when first calling the method
     * @param fieldName the name of the desired field
     * @param c         The class that will be scanned for the field
     * @return the field with the given fieldName if it is found
     * @throws NoSuchFieldException if no such field is found in the class or any of it's superclasses
     */
    private Field getSelectedField(Field field, String fieldName, Class c) throws NoSuchFieldException {

        if (c.equals(Object.class))
            throw new NoSuchFieldException();

        try {
            field = c.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            field = getSelectedField(field, fieldName, c.getSuperclass());
        }

        return field;
    }

    /**
     * Method that gets the value of each selected field of a bean(Object)
     *
     * @param o The object(bean) on which the selected fields will be accessed and their values added to the list
     * @return A List of the object's selected fields values
     */
    private List<String> parseRow(Object o, List<Field> fields) {
        List<String> args = new ArrayList<>();
        for (Field field : fields) {
            String fieldValue = null;
            try {
                field.setAccessible(true);
                fieldValue = field.get(o).toString();
                args.add(fieldValue);
            } catch (IllegalAccessException e) {
                throw new ConversionException("Exception parsing : " + o + " object", e.getCause());
            }
        }
        return args;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> getFieldMetadata() {
        return fieldMetadata;
    }

    /**
     * Inner class that takes a collection of Strings and converts it to a JRDataSource
     */
    private class DataSourceAdapter implements JRDataSource {

        private final Iterator<List<String>> rowIterator;
        private List<String> currentRow;

        private DataSourceAdapter(List<List<String>> rows) {
            this.rowIterator = rows.iterator();
        }

        @Override
        public boolean next() throws JRException {
            boolean retValue = this.rowIterator.hasNext();
            if (retValue)
                try {
                    this.currentRow = this.rowIterator.next();
                } catch (NoSuchElementException e) {
                    // Re-throw
                    throw new JRException("No more elements.", e.getCause());
                }

            return retValue;
        }

        @Override
        public Object getFieldValue(JRField jrField) throws JRException {
            int index = fieldMetadata.indexOf(Objects.requireNonNull(jrField, "Field must not be null!").getName());
            Object retValue = this.currentRow.get(index);
            if (retValue == null) {
                throw new JRException(
                        MessageFormat.format("Given field {0} cannot be matched to a " +
                                "valid key of the current row.", jrField.getName()));
            }
            // Return types differ?
            if (!retValue.getClass().equals(jrField.getValueClass())) {
                throw new JRException(
                        MessageFormat.format("Retrieved value type {0} does not match required return type {1}.",
                                retValue.getClass().getName(),
                                jrField.getValueClassName())
                );
            }
            return retValue;
        }
    }
}
