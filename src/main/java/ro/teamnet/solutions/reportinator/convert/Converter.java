package ro.teamnet.solutions.reportinator.convert;

import java.util.Collection;

/**
 * An interface to be implemented by wrapper classes which translate from an {@code IN}put format, to a custom
 * {@code OUT}put format.
 * <p>This usually follows the principles of an <em>adapter design pattern</em> combined with enforced
 * compile-time consistency checking through <em>generics</em>.
 * </p>
 * <p>
 * Note: Type parameters are used to enforce type checking at compile type.
 * </p>
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/6/2015
 */
public interface Converter<IN, OUT> {

    /**
     * Converts (or adapts) an <em>input source of data</em> (established through the {@code IN} type parameter) to a
     * desired <em>output source of data</em> (denoted by the {@code OUT} type parameter) to be used as a report's source
     * of data.
     *
     * @param inputSource The original input source of data.
     * @return An source of data to be used by an report's dataset.
     */
    OUT convert(IN inputSource) throws ConversionException;

    /**
     * A collection of metadata representing a data source's field names, to be used as fields during binding, as
     * column names for dynamically generated tables as well as a source id for data during runtime filling.
     *
     * @return An ordered collection of field names.
     */
    Collection<String> getFieldMetadata();
}
