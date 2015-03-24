/*
 * Copyright (c) 2015 Teamnet S.A. All Rights Reserved.
 *
 * This source file may not be copied, modified or redistributed,
 * in whole or in part, in any form or for any reason, without the express
 * written consent of Teamnet S.A.
 */

package ro.teamnet.solutions.reportinator.load.jasper;

import net.sf.jasperreports.engine.JRReport;
import ro.teamnet.solutions.reportinator.load.Loader;
import ro.teamnet.solutions.reportinator.load.LoaderException;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Objects;

/**
 * A {@link ro.teamnet.solutions.reportinator.load.Loader} implementation that loads a {@link java.net.URI}
 * into a {@link net.sf.jasperreports.engine.JRReport} by sending it's {@code URL} to the {@code UrlLoader}.
 *
 * @author Andrei.Marica
 * @author Bogdan.Stefan
 */
public final class UriLoader implements Loader<URI, JRReport> {
    /**
     * Method that loads the file from the given {@code URI} by sending it's {@code URL} to
     * {@code UrlLoader}
     *
     * @param sourceFile the URI to the ".jrxml" file
     * @return a JRReport based on the ".jrxm" file
     * @throws LoaderException
     */
    @Override
    public JRReport load(URI sourceFile) throws LoaderException {
        URI givenUri = Objects.requireNonNull(sourceFile, "Could not load URI into JRReport; The given input is null");
        if (!givenUri.isAbsolute()) {
            throw new LoaderException("Could not load given URI: not an absolute URI.");
        }
        try {
            return new UrlLoader().load(sourceFile.toURL());
        } catch (MalformedURLException e) {
            throw new LoaderException("Could not load URI to JRReport", e.getCause());
        }
    }
}
