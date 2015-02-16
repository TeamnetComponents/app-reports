package ro.teamnet.solutions.reportinator.load;

/**
 * An interface to be implemented by classes as a loader for static (template) report data.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/6/2015
 */
public interface Loader<WHAT,IN> {

    // TODO Are methods below appropriate interface methods?

//    T load(File templateFile);
//
//    T load(URL templateFileUrl);

    /**
     * Loads a sourceFile (e.g. <em>Files , InputStream</em>), at logical level, into another item (i.e. <em>a JRReport
     * </em>) using implementation specific internal heuristics.
     * @param sourceFile The item to load.
     * @return A result of logical implementation with a {@code sourceFile} bound to it.
     * @throws LoaderException
     */
      IN load(WHAT sourceFile) throws LoaderException;

}
