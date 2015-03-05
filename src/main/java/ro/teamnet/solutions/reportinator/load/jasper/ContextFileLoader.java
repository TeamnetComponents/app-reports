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

/**
 * A {@link ro.teamnet.solutions.reportinator.load.Loader} implementation that loads a {@link java.lang.String}
 * ,representing a file name,into a {@link net.sf.jasperreports.engine.JRReport}.
 * It manages this by sending it's {@code InputStream} called from the {@code ClassLoader} , to the {@code InputStreamLoader}.
 *
 * @author Andrei.Marica
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/11/2015
 */
public final class ContextFileLoader implements Loader<String, JRReport> {

    /**
     * Method that loads a given {@code String} ,representing the file name ,
     * by calling the {@code ClassLoader} and returning the file's  {@code InputStream}
     *
     * @param fileName the item that represent the ".jrxml" file name
     * @return a JRReport based on the given InputStream
     * @throws LoaderException if the given {@code String} is null or empty or the loading process doesn't
     *                         work properly
     */
    @Override
    public JRReport load(String fileName) throws LoaderException {
        if (fileName == null || fileName.isEmpty()) {
            throw new LoaderException("Could not load the Filename to a JRReport; The given input is either null or empty");
        }
        return new InputStreamLoader().load(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName));
    }
}
