package org.mathpiper.ui.gui.help;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;
import org.scilab.forge.jlatexmath.DefaultTeXFont;
import org.scilab.forge.jlatexmath.cyrillic.CyrillicRegistration;
import org.scilab.forge.jlatexmath.greek.GreekRegistration;

public class RenderedLatex extends JLabel {

    public RenderedLatex() {
        super();
        //this.setText("Hello.");


    }


    public void setLatex(String latexString) {
        
        DefaultTeXFont.registerAlphabet(new CyrillicRegistration());
	DefaultTeXFont.registerAlphabet(new GreekRegistration());
	TeXFormula formula = new TeXFormula(latexString);
	TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 17);
	icon.setInsets(new Insets(1, 1, 1, 1));
        this.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        this.setAlignmentY(icon.getBaseLine());
        this.setIcon(icon);

    }

}
