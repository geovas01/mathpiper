package org.mathpiper.ui.gui.consoles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;


public class ResultHolder extends JPanel{
    private TeXFormula texFormula;
    private JLabel texLabel;
    private String resultString;


    public ResultHolder(String latexString, String resultString, int initialValue)
    {
        this.texLabel = new JLabel();
        this.resultString = resultString;

        texFormula = new TeXFormula(latexString);
        TeXIcon icon = texFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, initialValue);
        texLabel.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        texLabel.setAlignmentY(icon.getBaseLine());
        texLabel.setIcon(icon);
        //texLabel.setText(latexString);

        //texLabel.setBackground(Color.white);

        

        this.setBackground(Color.white);

        this.setOpaque(true);

        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        this.add(texLabel);
        
    }//end constructor.


}//end class.
