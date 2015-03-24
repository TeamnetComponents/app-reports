/*
 * Copyright (c) 2015 Teamnet S.A. All Rights Reserved.
 *
 * This source file may not be copied, modified or redistributed,
 * in whole or in part, in any form or for any reason, without the express
 * written consent of Teamnet S.A.
 */

package ro.teamnet.solutions.reportinator.load;

import net.sf.jasperreports.engine.JRReport;
import ro.teamnet.solutions.reportinator.load.jasper.*;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

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


    /**
     * Loads a {@link java.lang.String} into {@link net.sf.jasperreports.engine.design.JasperDesign}
     * to be further processed by {@link ro.teamnet.solutions.reportinator.bind.Binder}
     *
     * @param fileName A valid {@code String} of the file name , in order to return it's InputStream by the ClassLoader
     * @return a {@link net.sf.jasperreports.engine.design.JasperDesign}
     * @throws LoaderException If loading the template from an {@code String} fails.
     * @see ro.teamnet.solutions.reportinator.bind
     */
    public static JRReport load(String fileName) throws LoaderException {
        return new ContextFileLoader().load(fileName);
    }

    /**
     * Loads a {@link java.net.URL} into {@link net.sf.jasperreports.engine.design.JasperDesign}
     * to be further processed by {@link ro.teamnet.solutions.reportinator.bind.Binder}
     *
     * @param urlSource A valid URL , designating the path to the file containing valid information for the JRXMLLoader
     *                  to process into a JRReport
     * @return a {@link net.sf.jasperreports.engine.design.JasperDesign}
     * @throws LoaderException If loading the template from an {@code URL} fails.
     */
    public static JRReport load(URL urlSource) throws LoaderException {
        return new UrlLoader().load(urlSource);
    }

    /**
     * Loads a {@link java.net.URI} into {@link net.sf.jasperreports.engine.design.JasperDesign}
     * to be further processed by {@link ro.teamnet.solutions.reportinator.bind.Binder}
     *
     * @param uriSource A valid URI , designating the path to the file containing  valid information in order to be loaded into a
     *                  {@link net.sf.jasperreports.engine.design.JasperDesign}
     * @return a {@link net.sf.jasperreports.engine.design.JasperDesign}
     * @throws LoaderException If loading the template from an {@code URI} fails.
     */
    public static JRReport load(URI uriSource) throws LoaderException {
        return new UriLoader().load(uriSource);
    }

    /**
     * Loads a {@link java.nio.file.Path} into {@link net.sf.jasperreports.engine.design.JasperDesign}
     * to be further processed by {@link ro.teamnet.solutions.reportinator.bind.Binder}
     *
     * @param pathSource A valid Path to the file that holds valid information in order to be loaded into a
     *                   {@link net.sf.jasperreports.engine.design.JasperDesign}
     * @return a {@link net.sf.jasperreports.engine.design.JasperDesign}
     * @throws LoaderException If loading the template from an {@code Path} fails.
     */
    public static JRReport load(Path pathSource) throws LoaderException {
        return new PathLoader().load(pathSource);
    }
}
