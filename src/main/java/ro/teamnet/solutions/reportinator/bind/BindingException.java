/*
 * Copyright (c) 2015 Teamnet S.A. All Rights Reserved.
 *
 * This source file may not be copied, modified or redistributed,
 * in whole or in part, in any form or for any reason, without the express
 * written consent of Teamnet S.A.
 */

package ro.teamnet.solutions.reportinator.bind;

/**
 * An unchecked exception denoting binding failures (usually pertaining to concrete implementations' internal heuristics).
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/10/2015
 */
public class BindingException extends RuntimeException {

    public BindingException() {
        super();
    }

    public BindingException(Throwable cause) {
        super(cause);
    }

    public BindingException(String message) {
        super(message);
    }

    public BindingException(String message, Throwable cause) {
        super(message, cause);
    }
}
