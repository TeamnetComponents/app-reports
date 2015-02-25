/*
 * Copyright (c) 2015 Teamnet S.A. All Rights Reserved.
 *
 * This source file may not be copied, modified or redistributed,
 * in whole or in part, in any form or for any reason, without the express
 * written consent of Teamnet S.A.
 */

package ro.teamnet.solutions.reportinator.load.jasper;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import ro.teamnet.solutions.reportinator.load.Loader;
import ro.teamnet.solutions.reportinator.load.LoaderException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * A {@link ro.teamnet.solutions.reportinator.load.Loader} implementation that
 * load a {@link java.io.InputStream} into a {@link net.sf.jasperreports.engine.design.JasperDesign} using
 * {@link net.sf.jasperreports.engine.xml.JRXmlLoader} method .
 * <p>
 * In order to perform the load sequence of JRXmlLoader , the loadSource must not be null.
 *
 * @author Andrei.Marica
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/11/2015
 */
public final class InputStreamLoader implements Loader<InputStream, JRReport> {
    /**
     * A method that loads an InputStream of a {@link net.sf.jasperreports.engine.design.JasperDesign} using
     * {@link net.sf.jasperreports.engine.xml.JRXmlLoader}.
     *
     * @param loadSource given InputStream to be loaded in the JRXMLLoader.
     * @return The resulting {@code JasperDesign}.
     * @throws LoaderException If loading the report template failed for some reason.
     */
    @Override
    public JRReport load(final InputStream loadSource) throws LoaderException {
        JasperDesign jasperDesign;
        try (InputStream source = Objects.requireNonNull(loadSource, "The load source must not be null.")) {
            jasperDesign = JRXmlLoader.load(loadSource.available() != 0 ? source : null);
        } catch (JRException | IOException | NullPointerException e) {
            //Re-throw
            throw new LoaderException("Could not load given InputStream into a JRReport", e.getCause());
        }

        return jasperDesign;
    }


}
