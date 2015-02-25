/*
 * Copyright (c) 2015 Teamnet S.A. All Rights Reserved.
 *
 * This source file may not be copied, modified or redistributed,
 * in whole or in part, in any form or for any reason, without the express
 * written consent of Teamnet S.A.
 */

package ro.teamnet.solutions.reportinator.create;

/**
 * An interface to be implemented by creators of different components, for a report.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/18/2015
 */
public interface ComponentCreator<C, U> {

    /**
     * A method which creates a component (denoted by the type parameter {@code C}), to be used in a report, utilizing the
     * given custom parameter {@code U}.
     * <p>{@code U} can be an ADT containing useful things for the creation process (e.g. if the output component is a
     * <em>table</em>, {@code U} could contain <em>column metadata</em>.</p>
     *
     * @param using An <em>A.D.T.</em> containing things to help in the creation.
     * @return A component as a result of the creation process.
     */
    C create(U using);
}
