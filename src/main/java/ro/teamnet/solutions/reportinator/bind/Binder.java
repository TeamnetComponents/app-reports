/*
 * Copyright (c) 2015 Teamnet S.A. All Rights Reserved.
 *
 * This source file may not be copied, modified or redistributed,
 * in whole or in part, in any form or for any reason, without the express
 * written consent of Teamnet S.A.
 */

package ro.teamnet.solutions.reportinator.bind;

/**
 * An interface for classes which will bind various items to each other. During the binding process, implementations'
 * internal heuristics might contain special manipulation code for the items to be bound.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/6/2015
 */
public interface Binder<WHAT, TO> {

    /**
     * Binds an item (e.g. <em>data source and related metadata</em>), at logical level, to a another item (i.e. <em>a
     * report template</em>) using implementation specific internal heuristics.
     *
     * @param item The item to bind.
     * @return An implementation specific representation with the {@code item} bound to it.
     */
    TO bind(WHAT item) throws BindingException;
}
