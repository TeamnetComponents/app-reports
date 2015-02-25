/*
 * Copyright (c) 2015 Teamnet S.A. All Rights Reserved.
 *
 * This source file may not be copied, modified or redistributed,
 * in whole or in part, in any form or for any reason, without the express
 * written consent of Teamnet S.A.
 */

package ro.teamnet.solutions.reportinator.bind.jasper;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.design.JasperDesign;
import ro.teamnet.solutions.reportinator.bind.Binder;
import ro.teamnet.solutions.reportinator.bind.BindingException;

import java.util.List;
import java.util.Objects;

/**
 * Binds a list of {@link net.sf.jasperreports.engine.design.JRDesignStyle} to a {@link net.sf.jasperreports.engine.JRReport}
 * which is the default report template. The styles is to be used by setting the styles to the different parts of the report
 * such as the column content.
 *
 * @author Andrei.Marica
 * @version 1.0 Date: 2/11/2015
 */
public final class StylesDesignBinder implements Binder<List<JRStyle>, JRReport> {

    /**
     * the report design to bind JRStyles to it
     */
    private final JasperDesign jasperDesign;

    /**
     * Receives a {@link net.sf.jasperreports.engine.JRReport}
     * (usually a {@link net.sf.jasperreports.engine.design.JasperDesign}) to bind JRStyles to it.
     *
     * @param reportDesign A mutable report design to bind JRDesignStyles to.
     */
    public StylesDesignBinder(JRReport reportDesign) {

        this.jasperDesign = JasperDesign.class.cast(
                Objects.requireNonNull(reportDesign, "Jasper report reference must not be null."));
    }

    /**
     * Binds a list of {@link net.sf.jasperreports.engine.JRStyle} to the
     * attached report template
     *
     * @param item The list of {@link net.sf.jasperreports.engine.JRStyle}
     * @return the report template , having all the {@link net.sf.jasperreports.engine.JRStyle} given , bound to it
     * @throws BindingException if the given list is empty
     */
    @Override
    public JRReport bind(List<JRStyle> item) throws BindingException {

        if (item != null && !item.isEmpty()) {
            for (JRStyle styleToBind : item) {
                try {
                    this.jasperDesign.addStyle(styleToBind);
                } catch (JRException e) {
                    throw new BindingException("The Binder could not bind the given List<JRStyle> to a JasperDesign", e.getCause());
                }
            }
        } else {
            throw new BindingException("The Binder could not bind the given List<JRStyle> to a JasperDesign: " +
                    "The given List<JRStyles> is either empty or null;"
            );
        }
        return jasperDesign;
    }
}
