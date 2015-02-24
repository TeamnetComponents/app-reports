package ro.teamnet.solutions.reportinator.convert.jasper;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import ro.teamnet.solutions.reportinator.convert.ConversionException;
import ro.teamnet.solutions.reportinator.convert.DataSourceConverter;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.*;

/**
 * Class that converts a collection of beans into a JRDataSource
 *
 * @author Bogdan.Iancu
 * @version 1.0 Date: 2/6/2015
 */
public final class BeanCollectionJasperDataSourceConverter<B> implements DataSourceConverter<Collection<B>, JRDataSource> {


    private final List<String> fieldMetadata;

    /**
     * Constructor for class
     * @param fieldMetadata collection which specifies what fields of the bean will be added to the data source
     */
    public BeanCollectionJasperDataSourceConverter(Collection<String> fieldMetadata) {
        if(fieldMetadata == null || fieldMetadata.size()==0 ){
            throw new ConversionException("Field Metadata should not be null");
        }
        this.fieldMetadata = new ArrayList<>(fieldMetadata);
    }

    /**
     * Method that converts a collection of beans to a JRDataSource
     * @param inputSource The collection of beans to be converted
     * @return
     */
    @Override
    public JRDataSource convert(Collection<B> inputSource) {

        List<List<String>> rows = new ArrayList<>();
        List<Field> fields = null;

        Iterator<B> iterator = inputSource.iterator();

        Object o1 = iterator.next();
        fields = getSelectedFields(o1.getClass());
        List<String> row = parseRow(o1, fields);

        //We need to add an empty row because jasper reports jumps over the first row
        //FUTURE remove this (in case of jasper bug fix)
        rows.add(new ArrayList<>());

        rows.add(row);

        while (iterator.hasNext()){
            row = parseRow(iterator.next(),fields);
            rows.add(row);
        }

        return new DataSourceAdapter(rows);
    }

    /**
     * Method that gets all the desired fields of the class including inherited fields
     * @param c
     * @return
     */
    private List<Field> getSelectedFields(Class c) {
        List<Field> fields = new ArrayList<>();

        for (String fieldName : fieldMetadata) {
            try {
                Field field = getSelectedField(null, fieldName, c);
                fields.add(field);
            } catch (Exception e) {
                throw new ConversionException("Exception getting field named " + fieldName, e.getCause());
            }
        }
        return fields;
    }

    /**
     * Method that travels recursively on the superclasses of a class to get a desired field
     * @param field the value of the desired field used for recursivity , MUST be null when first calling the method
     * @param fieldName the name of the desired field
     * @param c
     * @return
     * @throws NoSuchFieldException if no such field is found
     */
    private Field getSelectedField(Field field, String fieldName, Class c) throws NoSuchFieldException {

        if(c.equals(Object.class))
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
     * @param o
     * @return
     */
    private List<String> parseRow(Object o, List<Field> fields){
        List<String> args = new ArrayList<>();
        for (Field field : fields) {
            String arg = null;
            try {
                field.setAccessible(true);
                arg = field.get(o).toString();
                args.add(arg);
            } catch (Exception e) {
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
    private class DataSourceAdapter implements JRDataSource{

        private final Iterator<List<String>> rowIterator;
        private List<String> currentRow;

        private DataSourceAdapter(List<List<String>> rows) {
            this.rowIterator = rows.iterator();
        }

        @Override
        public boolean next() throws JRException {
            boolean retValue = this.rowIterator.hasNext();
            if(retValue)
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
