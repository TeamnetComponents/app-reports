/*
 * Copyright (c) 2015 Teamnet S.A. All Rights Reserved.
 *
 * This source file may not be copied, modified or redistributed,
 * in whole or in part, in any form or for any reason, without the express
 * written consent of Teamnet S.A.
 */

package ro.teamnet.solutions.reportinator.util;

import java.util.Objects;

/**
 * Utility class for {@link java.lang.Exception}s/{@link java.lang.Throwable}s. It contains various utility methods
 * which parse or process exceptions or throwables.
 *
 * @author Bogdan.Stefan
 * @version 1.0.1 Date: 2015-03-24
 * @since 1.0.1 Date: 2015-03-24
 */
public class ExceptionUtils {

    /**
     * A generic helper method which decides between returning the cause of an {@link java.lang.Throwable} or the {@code Throwable}
     * instance itself, depending on whichever is present.
     * <p>The cause always takes precedence, because it might represent the actual point of failure, thus providing the
     * most accurate failure information.</p>
     *
     * @param thrownException The {@code Throwable} instance to be <em>analyzed</em>.
     * @return A {@code Throwable} instance as the result.
     */
    public static <T extends Throwable> Throwable retrieveCauseOrActual(T thrownException) {
        Throwable throwable = Objects.requireNonNull(thrownException, "Passed instance of Throwable should not be null.");

        return throwable.getCause() != null ? throwable.getCause() : throwable;
    }
}
