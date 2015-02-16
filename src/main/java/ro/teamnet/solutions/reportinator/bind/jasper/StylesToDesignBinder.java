package ro.teamnet.solutions.reportinator.bind.jasper;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.design.JasperDesign;
import ro.teamnet.solutions.reportinator.bind.Binder;
import ro.teamnet.solutions.reportinator.bind.BindingException;

import java.util.List;
import java.util.Objects;

/**
 * TODO:DOC
 *
 * @author Andrei.Marica
 * @version 1.0 Date: 2/11/2015
 */
public class StylesToDesignBinder implements Binder<List<JRStyle>, JasperDesign> {

    /**
     * TODO: DOC
     */
    private final JasperDesign jasperDesign;

    /**
     * TODO: DOC
     *
     * @param jasperDesign
     */
    public StylesToDesignBinder(JasperDesign jasperDesign) {

        this.jasperDesign = JasperDesign.class.cast(
                Objects.requireNonNull(jasperDesign, "Jasper report reference must not be null."));
    }

    /**
     * TODO: DOC
     *
     * @param item The item to bind.
     * @return
     * @throws BindingException
     */
    @Override
    public JasperDesign bind(List<JRStyle> item) throws BindingException {

        if (!item.isEmpty()) {
            for (JRStyle styleToBind : item) {
                try {
                    this.jasperDesign.addStyle(styleToBind);
                } catch (JRException e) {
                    throw new BindingException("EXCEPTION MESSAGE", e);
                }
            }
        } else {
            throw new BindingException("EXCEPTION MESSAGE");
        }
        return jasperDesign;
    }
}
