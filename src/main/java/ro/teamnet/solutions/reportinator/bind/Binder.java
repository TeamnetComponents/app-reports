package ro.teamnet.solutions.reportinator.bind;

/**
 * An interface for classes which will bind data sources to report abstractions.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/6/2015
 */
public interface Binder<WHAT, TO> {

    /**
     * Binds a data source ({@code WHAT }) to a report ({@code TO }) using implementation pecific internal heuristics.
     *
     * @param dataSource A source of data.
     * @return A report abstraction with the data source bound to it.
     */
    TO bind(WHAT dataSource);
}
