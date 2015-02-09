package ro.teamnet.solutions.reportinator.bind;

/**
 * An interface for classes which will bind data sources to report abstractions.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/6/2015
 */
public interface Binder<WHAT, TO> {

    /**
     * Binds an item (e.g. <em>data source metadata</em>), at logical level, to a another item (i.e. a report template)
     * using implementation specific internal heuristics.
     *
     * @param item The item to bind.
     * @return An implementation specific representation with the {@code item} bound to it.
     */
    TO bind(WHAT item);
}
