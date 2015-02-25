/*
 * Copyright (c) 2015 Teamnet S.A. All Rights Reserved.
 *
 * This source file may not be copied, modified or redistributed,
 * in whole or in part, in any form or for any reason, without the express
 * written consent of Teamnet S.A.
 */

package ro.teamnet.solutions.reportinator.generation;

/**
 * An unchecked exception, denoting report build failures (usually pertaining to concrete implementations' internal heuristics).
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/10/2015
 */
public class ReportGeneratorException extends RuntimeException {

    public ReportGeneratorException(Throwable cause) {
        super(cause);
    }

    public ReportGeneratorException() {
    }

    public ReportGeneratorException(String message) {
        super(message);
    }

    public ReportGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }
}
