/*
 * Copyright (c) 2015 Teamnet S.A. All Rights Reserved.
 *
 * This source file may not be copied, modified or redistributed,
 * in whole or in part, in any form or for any reason, without the express
 * written consent of Teamnet S.A.
 */

package ro.teamnet.solutions.reportinator.load;

import net.sf.jasperreports.engine.JRReport;
import ro.teamnet.solutions.reportinator.load.jasper.FileLoader;
import ro.teamnet.solutions.reportinator.load.jasper.InputStreamLoader;

import java.io.File;
import java.io.InputStream;

/**
 * Acts as a facade for the {@link ro.teamnet.solutions.reportinator.load.Loader} implementing classes,
 * it's similar to {@link net.sf.jasperreports.engine.xml.JRXmlLoader} class.
 *
 * @author Andrei.Marica
 * @version 1.0 Date: 2/11/2015
 */
public class JasperDesignLoader {

    /**
     * Loads a {@link java.io.File} into {@link net.sf.jasperreports.engine.design.JasperDesign}
     * to be further processed by {@link ro.teamnet.solutions.reportinator.bind.Binder}
     *
     * @param file A {@code .jrxml} file that contains valid information
     *             in order to be loaded into a {@link net.sf.jasperreports.engine.design.JasperDesign}
     * @return a {@link net.sf.jasperreports.engine.design.JasperDesign}
     * @throws LoaderException If loading the template from a {@code File} fails.
     */
    public static JRReport load(File file) throws LoaderException {
        return new FileLoader().load(file);
    }

    /**
     * Loads a {@link java.io.InputStream} into {@link net.sf.jasperreports.engine.design.JasperDesign}
     * to be further processed by {@link ro.teamnet.solutions.reportinator.bind.Binder}
     *
     * @param inputStream A valid {@link java.io.InputStream} that gives valid information in order to be loaded into a
     *                    {@link net.sf.jasperreports.engine.design.JasperDesign}
     * @return a {@link net.sf.jasperreports.engine.design.JasperDesign}
     * @throws LoaderException If loading the template from an {@code InputStream} fails.
     */
    public static JRReport load(InputStream inputStream) throws LoaderException {
        return new InputStreamLoader().load(inputStream);
    }
}
