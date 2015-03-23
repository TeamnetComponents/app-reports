/*
 * Copyright (c) 2015 Teamnet S.A. All Rights Reserved.
 *
 * This source file may not be copied, modified or redistributed,
 * in whole or in part, in any form or for any reason, without the express
 * written consent of Teamnet S.A.
 */

package ro.teamnet.solutions.reportinator.convert;

import java.util.Collection;

/**
 * An interface to be implemented by wrapper classes which translate from an {@code IN}put format (representing any data
 * source), to a custom {@code OUT}put format, to be used as an internal report engine data source.
 * <p>This usually follows the principles of an <em>adapter design pattern</em> combined with enforced
 * compile-time consistency checking through <em>generics</em>.
 * </p>
 * <p>
 * <strong>Note</strong>: Type parameters are used to enforce type checking at compile type.
 * </p>
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/6/2015
 */
public interface DataSourceConverter<IN, OUT> {

    /**
     * Converts (or adapts) an <em>input source of data</em> (established through the {@code IN} type parameter) to a
     * desired <em>output source of data</em> (denoted by the {@code OUT} type parameter) to be used as a report's source
     * of data.
     *
     * @param inputSource The original input source of data.
     * @return An source of data to be used by an report's dataset.
     * @throws ro.teamnet.solutions.reportinator.convert.ConversionException If the input source contains
     * no records.
     * @throws java.lang.NullPointerException If the input source is null.
     */
    OUT convert(IN inputSource) throws ConversionException, NullPointerException;

    /**
     * A collection of metadata representing a data source's field names, to be used as fields during binding, as
     * column names for dynamically generated tables as well as a source id for data during runtime filling.
     *
     * @return An ordered collection of field names.
     */
    Collection<String> getFieldMetadata();
}
