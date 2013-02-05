package org.mathpiper.ui.gui.worksheets;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

public class LatexRenderingController extends JPanel implements ChangeListener {

    private JSlider scaleSlider;
    private JLabel texLabel;
    private TeXFormula texFormula;

    public LatexRenderingController(TeXFormula texFormula, JLabel texLabel, int initialValue) {
        super();
        this.texFormula = texFormula;
        this.texLabel = texLabel;

        TeXIcon icon = texFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, initialValue);
        icon.setInsets(new Insets(5, 5, 5, 5));
        texLabel.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        texLabel.setAlignmentY(icon.getBaseLine());
        texLabel.setIcon(icon);

        scaleSlider = new JSlider(JSlider.HORIZONTAL, 1, 500, initialValue);
        scaleSlider.addChangeListener(this);

        //Turn on labels at major tick marks.
        //framesPerSecond.setMajorTickSpacing(10);
        //framesPerSecond.setMinorTickSpacing(1);
        //framesPerSecond.setPaintTicks(true);
        scaleSlider.setPaintLabels(true);

        this.add(new JLabel("Resize"));
        this.add(scaleSlider);

    }

    public void stateChanged(ChangeEvent e) {

        JSlider source = (JSlider) e.getSource();
        //if (!source.getValueIsAdjusting()) {
        int intValue = (int) source.getValue();

        TeXIcon icon = texFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, intValue);
        icon.setInsets(new Insets(5, 5, 5, 5));
        texLabel.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        texLabel.setAlignmentY(icon.getBaseLine());
        texLabel.setIcon(icon);
        texLabel.repaint();

        //}
        }//end method.


    }//end class.
