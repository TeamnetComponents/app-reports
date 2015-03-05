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

import java.nio.file.Path;

/**
 * A {@link ro.teamnet.solutions.reportinator.load.Loader} implementation that loads a {@link java.nio.file.Path}
 * into a {@link net.sf.jasperreports.engine.JRReport} by sending its file name as parameter to the {@code load} method
 * from {@code ContextFileLoader}.
 *
 * @author Andrei.Marica
 * @author Bogdan.Stefan
 */
public final class PathLoader implements Loader<Path, JRReport> {
    /**
     * Method that loads a {@code Path} into a {@code JRReport} by sending the file name, using
     * {getFileName} method , to the ContextFileLoader.
     *
     * @param sourceFile The path to the ".jrxml" file
     * @return a JRReport based on the ".jrxml" file
     * @throws LoaderException if the input parameter is null or the loading process doesn't work properly
     */
    @Override
    public JRReport load(Path sourceFile) throws LoaderException {
        if (sourceFile == null) {
            throw new LoaderException("Could not load the Path to a JRReport; the given input is null");
        }
        return new FileLoader().load(sourceFile.toFile());
    }
}
