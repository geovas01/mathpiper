package org.mathpiper.ui.gui.help;

import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JLabel;
import org.mathpiper.ui.gui.jmathtex.TeXFormula;
import org.mathpiper.ui.gui.jmathtex.TeXIcon;
import org.mathpiper.ui.gui.jmathtex.exceptions.ParseException;

public class RenderedLatex extends JLabel {

    public RenderedLatex() {
        super();
        //this.setText("Hello.");


    }


    public void setLatex(String latexString) {
        try {
            TeXFormula formula = new TeXFormula(latexString);
            TeXIcon icon = formula.createTeXIcon(0, 17);
            icon.setInsets(new Insets(1, 1, 1, 1));

            this.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
            this.setAlignmentY(icon.getBaseLine());
            this.setIcon(icon);
        } catch (ParseException pe) {
            this.setText("Can not render: " + latexString);
        }
    }

}
