package ro.teamnet.solutions.reportinator.convert.jasper;

import net.sf.jasperreports.data.ds.DataSourceDataAdapterImpl;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import ro.teamnet.solutions.reportinator.convert.Converter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.*;

/**
 * TODO Doc
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/6/2015
 */
public final class BeanCollectionJasperDataSourceConverter<B> implements Converter<Collection<B>, JRDataSource> {

    //Collection<String> fieldMetadata;
    private List<List<String>> rows;
    private List<String> fields;

    public BeanCollectionJasperDataSourceConverter(Collection<String> fieldMetadata) {
        //this.fieldMetadata = fieldMetadata;
        fields = new ArrayList<>(fieldMetadata);
    }

    @Override
    public JRDataSource convert(Collection<B> inputSource) {

        rows = new ArrayList<>();

        for(Object o : inputSource){
            List<String> row = new ArrayList<>();
            for(String field : fields){
                String methodName = "get" + field.substring(0,1).toUpperCase() + field.substring(1);
                Method method = null;
                String arg = null;
                try {
                    method = o.getClass().getDeclaredMethod(methodName);
                    arg = method.invoke(o).toString();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                row.add(arg);
            }
            rows.add(row);
        }



        return new DataSourceAdapter();

    }

    @Override
    public Collection<String> getFieldMetadata() {
        return fields;
    }

    private class DataSourceAdapter implements JRDataSource{

        private final Iterator<List<String>> rowIterator;
        private List<String> currentRow;

        private DataSourceAdapter() {
            this.rowIterator = BeanCollectionJasperDataSourceConverter.this.rows.iterator();
        }

        @Override
        public boolean next() throws JRException {
            boolean retValue = this.rowIterator.hasNext();
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
            int index = fields.indexOf(Objects.requireNonNull(jrField, "Field must not be null!").getName());
            Object retValue = this.currentRow.get(index);
            if (retValue == null) {
                throw new JRException( // TODO ----.    Maybe map message below to an i18n key?
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
