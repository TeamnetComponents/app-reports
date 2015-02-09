package ro.teamnet.solutions.reportinator.load;

import java.io.File;
import java.net.URL;

/**
 * An interface to be implemented by classes as a loader for static (template) report data.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/6/2015
 */
public interface Loader<T> {

    // TODO Are methods below appropriate interface methods?

    T load(File templateFile);

    T load(URL templateFileUrl);
}
