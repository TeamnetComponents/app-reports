/*
 * Copyright (c) 2015 Teamnet S.A. All Rights Reserved.
 *
 * This source file may not be copied, modified or redistributed,
 * in whole or in part, in any form or for any reason, without the express
 * written consent of Teamnet S.A.
 */

package ro.teamnet.solutions.reportinator.generation;

import java.util.Map;

/**
 * An interface for report generators.
 *
 * @author Bogdan.Stefan
 * @version 1.0.1 Date: 2015-03-20
 * @since 1.0 Date: 2015-02-13
 */
public interface ReportGenerator<T> {

    /**
     * Generates a report abstraction, specific to the underlying reporting engine (denoted by {@code T}),
     * making it ready for exporting.
     *
     * @return A report abstraction of the specified type.
     * @throws ReportGeneratorException If anything happened during report generation.
     */
    T generate() throws ReportGeneratorException;

    /**
     * Generates a report abstraction, with extra parameters (i.e. for fine tuning the report based on
     * specific requirements of the export type), specific to the underlying reporting engine (denoted by {@code T}),
     * making it ready for exporting.
     *
     * @param parameters extra parameters required for fine tuning
     * @return A report abstraction of the specified type.
     * @throws ReportGeneratorException If anything happened during report generation.
     */
    T generate(Map<String, Object> parameters) throws ReportGeneratorException;

}
