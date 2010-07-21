package org.mathpiper.ui.gui.consoles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

public class ResultHolder extends JPanel {

    private TeXFormula texFormula;
    private JLabel texLabel;
    private String resultString;


    public ResultHolder(String latexString, String resultString, int fontPointSize) {
        this.texLabel = new JLabel();
        this.resultString = resultString;

        texFormula = new TeXFormula(latexString);
        TeXIcon icon = texFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, fontPointSize);
        texLabel.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        texLabel.setAlignmentY(icon.getBaseLine());
        texLabel.setIcon(icon);
        //texLabel.setText(latexString);

        //texLabel.setBackground(Color.white);



        this.setBackground(Color.white);

        this.setOpaque(true);

        //this.setLayout(new FlowLayout(FlowLayout.LEFT));

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        this.add(texLabel);

    }//end constructor.




    public void setScale(int scaleValue) {

        TeXIcon icon = texFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, scaleValue);
        texLabel.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        texLabel.setAlignmentY(icon.getBaseLine());
        texLabel.setIcon(icon);
        texLabel.repaint();

    }//end method.



}//end class.

