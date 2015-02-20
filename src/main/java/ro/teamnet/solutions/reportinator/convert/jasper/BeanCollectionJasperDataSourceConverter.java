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

        for(Object o : inputSource){
            List<String> row = parseRow(o);
            rows.add(row);
        }
        return new DataSourceAdapter(rows);
    }

    /**
     * Method that gets the value of each selected field of a bean(Object)
     * @param o
     * @return
     */
    private List<String> parseRow(Object o){
        List<String> args = new ArrayList<>();
        for (String fieldName : fieldMetadata) {
            String arg = null;
            try {
                Field field = o.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                arg = field.get(o).toString();
                args.add(arg);
            } catch (Exception e) {
                throw new ConversionException("Exception parsing : " + o + " object", e);
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
                    throw new JRException("No more elements.", e);
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
