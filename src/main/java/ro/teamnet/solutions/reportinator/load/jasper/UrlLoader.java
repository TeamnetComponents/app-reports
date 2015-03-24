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

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * A {@link ro.teamnet.solutions.reportinator.load.Loader} implementation that loads a {@link java.net.URL}
 * into a {@link net.sf.jasperreports.engine.JRReport} by sending it's InputStream to the InputStreamLoader.
 *
 * @author Andrei.Marica
 * @author Bogdan.Stefan
 */
public final class UrlLoader implements Loader<URL, JRReport> {
    /**
     * Method that loads the file from the given URL to a JRReport
     *
     * @param sourceFile URL to the ".jrxml" file
     * @return a JRReport based on the ".jrxml" file
     * @throws LoaderException
     */
    @Override
    public JRReport load(URL sourceFile) throws LoaderException {
        URL givenUrl = Objects.requireNonNull(sourceFile, "Could not load URL into JRReport; The given input is null");
        try {
            return new InputStreamLoader().load(givenUrl.openStream());
        } catch (IOException e) {
            throw new LoaderException("Could not load URL into JRReport", e.getCause());
        }
    }
}
